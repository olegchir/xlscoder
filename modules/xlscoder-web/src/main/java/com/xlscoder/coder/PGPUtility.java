package com.xlscoder.coder;

import com.xlscoder.model.Key;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.PublicKeyDataDecryptorFactory;
import org.bouncycastle.openpgp.operator.jcajce.*;
import org.bouncycastle.util.Arrays;

import java.io.*;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;

public class PGPUtility {

    public static final Date DEFAULT_DETERMINISTIC_DATE = new Date(1220227200L * 1000);
    public static final InsecureRandom DEFAULT_DETERMINISTIC_RANDOM = new InsecureRandom(new Byte("1"));

    public static String decryptString(String src, Key key) throws Exception {
        byte[] decPriv = Base64.getDecoder().decode(key.getPgpPrivateKey());
        return decryptString(src, decPriv, key.getPgpPassword());
    }

    @SuppressWarnings("unchecked")
    public static String decryptString(String src, byte[] privKeyIn, String password)
            throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());

        InputStream in  = new ByteArrayInputStream(Base64.getDecoder().decode(src));
        in = PGPUtil.getDecoderStream(in);
        PGPObjectFactory objectFactory = new PGPObjectFactory(in, new JcaKeyFingerprintCalculator());

        PGPEncryptedDataList encDataList;
        Object nextObject = objectFactory.nextObject();

        // PGP marker packet?
        if (nextObject instanceof  PGPEncryptedDataList) {
            encDataList = (PGPEncryptedDataList) nextObject;
        } else {
            encDataList = (PGPEncryptedDataList) objectFactory.nextObject();
        }

        // Secret key?
        Iterator<PGPPublicKeyEncryptedData> it = encDataList.getEncryptedDataObjects();
        PGPPrivateKey privateKey = null;
        PGPPublicKeyEncryptedData publicKeyEncryptedData = null;

        while (privateKey == null && it.hasNext()) {
            publicKeyEncryptedData = it.next();
            privateKey = extractPrivateKey(privKeyIn, publicKeyEncryptedData.getKeyID(), password);
        }

        if (privateKey == null) {
            throw new IllegalArgumentException("Secret key for message was not found.");
        }

        PublicKeyDataDecryptorFactory dataDecryptorFactory = new JcePublicKeyDataDecryptorFactoryBuilder().setProvider("BC").setContentProvider("BC").build(privateKey);
        InputStream clear = publicKeyEncryptedData.getDataStream(dataDecryptorFactory);
        PGPObjectFactory clearData = new PGPObjectFactory(clear, new JcaKeyFingerprintCalculator());
        Object message = clearData.nextObject();
        if (message instanceof  PGPCompressedData) {
            PGPCompressedData cData = (PGPCompressedData) message;
            PGPObjectFactory pgpFact = new PGPObjectFactory(cData.getDataStream(), new JcaKeyFingerprintCalculator());
            message = pgpFact.nextObject();
        }

        String result;
        if (message instanceof  PGPLiteralData) {
            PGPLiteralData ld = (PGPLiteralData) message;
            InputStream unc = ld.getInputStream();
            int ch;
            try (OutputStream out = new ByteArrayOutputStream()) {
                while ((ch = unc.read()) >= 0) {
                    out.write(ch);
                }
                result = out.toString();
            }
        } else if (message instanceof  PGPOnePassSignatureList) {
            throw new PGPException("Signed message instead of literal data.");
        } else {
            throw new PGPException("Unknown type of the message.");
        }

        return result;
    }

    public static PGPPrivateKey extractPrivateKey(byte[] privKeyIn, long keyID, String password)
            throws IOException, PGPException, NoSuchProviderException
    {
        InputStream inKeyStream = new ByteArrayInputStream(privKeyIn);

        PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
                org.bouncycastle.openpgp.PGPUtil.getDecoderStream(inKeyStream), new JcaKeyFingerprintCalculator());

        PGPSecretKey pgpSecKey = pgpSec.getSecretKey(keyID);

        if (pgpSecKey == null) {
            return null;
        }

        PBESecretKeyDecryptor a = new JcePBESecretKeyDecryptorBuilder(new JcaPGPDigestCalculatorProviderBuilder().setProvider("BC").build()).setProvider("BC").build(password.toCharArray());
        return pgpSecKey.extractPrivateKey(a);
    }

    public static String encryptString(String data, Key key) throws IOException, PGPException {
        byte[] decPub = Base64.getDecoder().decode(key.getPgpPublicKey());
        PGPPublicKey pgpPublicKey = PGPUtility.extractPublicKey(decPub);
        return encryptString(data, pgpPublicKey, false, null, null);
    }

    public static String encryptString(String data, PGPPublicKey encKey,
                                             boolean deterministic, Date deterministicDate, Byte deterministicSeed)
            throws IOException, PGPException {
        // Destroy all randomness. Very bad solution, but I don't know anything better.
        Date modificationDate;
        SecureRandom conditionalRandom;
        if (deterministic) {
            modificationDate = (null == deterministicDate ? DEFAULT_DETERMINISTIC_DATE : deterministicDate);
            conditionalRandom = (null == deterministicSeed ? DEFAULT_DETERMINISTIC_RANDOM : new InsecureRandom(deterministicSeed));
        } else {
            modificationDate = new Date();
            conditionalRandom = new SecureRandom();
        }
        // End of: destroy all randomness

        Security.addProvider(new BouncyCastleProvider());

        ByteArrayOutputStream binaryOut = new ByteArrayOutputStream();
        PGPCompressedDataGenerator zippedData = new PGPCompressedDataGenerator(PGPCompressedData.ZIP);

        InputStream is = new ByteArrayInputStream(data.getBytes());
        writeStreamToLiteralData(zippedData.open(binaryOut), PGPLiteralData.BINARY, is, modificationDate);

        zippedData.close();

        JcePGPDataEncryptorBuilder c = new JcePGPDataEncryptorBuilder(PGPEncryptedData.CAST5).setWithIntegrityPacket(false).setSecureRandom(conditionalRandom).setProvider("BC");
        PGPEncryptedDataGenerator encDataGenerator = new PGPEncryptedDataGenerator(c);
        JcePublicKeyKeyEncryptionMethodGenerator jceEncMethod = new JcePublicKeyKeyEncryptionMethodGenerator(encKey).setProvider(new BouncyCastleProvider()).setSecureRandom(conditionalRandom);

        encDataGenerator.addMethod(jceEncMethod);

        byte[] bytes = binaryOut.toByteArray();
        String result = null;

        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (OutputStream cOut = encDataGenerator.open(out, bytes.length)) {
                cOut.write(bytes);
            }
            result = Base64.getEncoder().encodeToString(out.toByteArray());
        }

        return result;
    }

    public static void writeStreamToLiteralData(
            OutputStream out,
            char fileType,
            InputStream in,
            Date modificationDate)
            throws IOException
    {
        PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();
        OutputStream pOut = lData.open(out, fileType, "temp", modificationDate, new byte[4096]);
        pipeStreamContents(in, pOut, new byte[32768]);
    }


    private static void pipeStreamContents(InputStream in, OutputStream pOut, byte[] buf)
            throws IOException
    {
        try
        {
            int len;
            while ((len = in.read(buf)) > 0)
            {
                pOut.write(buf, 0, len);
            }

            pOut.close();
        }
        finally
        {
            Arrays.fill(buf, (byte)0);
            try
            {
                in.close();
            }
            catch (IOException ignored)
            {
                // ignore...
            }
        }
    }

    public static PGPPublicKey extractPublicKey(byte[] data) throws IOException, PGPException {
        InputStream in = new ByteArrayInputStream(data);
        in = org.bouncycastle.openpgp.PGPUtil.getDecoderStream(in);
        PGPPublicKeyRingCollection publicCollection = new PGPPublicKeyRingCollection(in, new JcaKeyFingerprintCalculator());

        PGPPublicKey key = null;
        Iterator<PGPPublicKeyRing> keyrings = publicCollection.getKeyRings();
        while (key == null && keyrings.hasNext()) {
            PGPPublicKeyRing keyring = keyrings.next();
            Iterator<PGPPublicKey> pubKeysInKeyring = keyring.getPublicKeys();
            while (key == null && pubKeysInKeyring.hasNext()) {
                PGPPublicKey publickey = pubKeysInKeyring.next();
                if (publickey.isEncryptionKey()) {
                    key = publickey;
                }
            }
        }

        if (key == null) {
            throw new IllegalArgumentException("Can't find encryption key in key ring.");
        }

        return key;
    }
}
