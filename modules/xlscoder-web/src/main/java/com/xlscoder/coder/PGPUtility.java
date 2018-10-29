package com.xlscoder.coder;

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
import java.util.Date;
import java.util.Iterator;

public class PGPUtility {

    @SuppressWarnings("unchecked")
    public static String decryptString(InputStream in, byte[] privKeyIn, String password)
            throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());

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

    public static OutputStream encryptString(String data, PGPPublicKey encKey)
            throws IOException, PGPException {
        Security.addProvider(new BouncyCastleProvider());

        ByteArrayOutputStream binaryOut = new ByteArrayOutputStream();
        PGPCompressedDataGenerator zippedData = new PGPCompressedDataGenerator(PGPCompressedData.ZIP);

        InputStream is = new ByteArrayInputStream(data.getBytes());
        writeStreamToLiteralData(zippedData.open(binaryOut), PGPLiteralData.BINARY, is);

        zippedData.close();

        JcePGPDataEncryptorBuilder c = new JcePGPDataEncryptorBuilder(PGPEncryptedData.CAST5).setWithIntegrityPacket(false).setSecureRandom(new SecureRandom()).setProvider("BC");
        PGPEncryptedDataGenerator encDataGenerator = new PGPEncryptedDataGenerator(c);
        JcePublicKeyKeyEncryptionMethodGenerator jceEncMethod = new JcePublicKeyKeyEncryptionMethodGenerator(encKey).setProvider(new BouncyCastleProvider()).setSecureRandom(new SecureRandom());

        encDataGenerator.addMethod(jceEncMethod);

        byte[] bytes = binaryOut.toByteArray();
        String result = "";

        OutputStream out = new ByteArrayOutputStream();
        try (OutputStream cOut = encDataGenerator.open(out, bytes.length)) {
            cOut.write(bytes);
        }

        return out;
    }

    public static void writeStreamToLiteralData(
            OutputStream out,
            char fileType,
            InputStream in)
            throws IOException
    {
        PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();
        OutputStream pOut = lData.open(out, fileType, "temp", new Date(), new byte[4096]);
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
