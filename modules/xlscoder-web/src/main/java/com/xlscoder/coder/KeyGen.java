package com.xlscoder.coder;

import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.RSASecretBCPGKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPDigestCalculatorProviderBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPKeyConverter;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyEncryptorBuilder;

import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.util.Date;

public class KeyGen {
    public static final int DEFAULT_KEY_SIZE = 2048;
    public static final String IDENTITY = "xlscoder";
    public static final String PASSWORD = "xlscoder";
    public static final String DEFAULT_CRYPTO_PROVIDER = "BC";

    public static KeyPairHolder generatePairs() throws NoSuchAlgorithmException, NoSuchProviderException, PGPException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", DEFAULT_CRYPTO_PROVIDER);
        generator.initialize(DEFAULT_KEY_SIZE);
        KeyPair keyPair = generator.generateKeyPair();
        
        Pair<byte[], byte[]> pgpKeys = getPgpKeys(keyPair.getPrivate(), keyPair.getPublic(), IDENTITY, PASSWORD.toCharArray());

        KeyPairHolder keyPairHolder = new KeyPairHolder(
                keyPair.getPrivate().getEncoded(),
                keyPair.getPublic().getEncoded(),
                pgpKeys.first, pgpKeys.second);

        return keyPairHolder;
    }

    public static Pair<byte[], byte[]> getPgpKeys(PrivateKey privateKey, PublicKey publicKey, String identity, char[] password) throws PGPException, IOException {
        PGPPublicKey pgpPublicKey = (new JcaPGPKeyConverter().getPGPPublicKey(PGPPublicKey.RSA_GENERAL, publicKey, new Date()));
        RSAPrivateCrtKey rsaPriv = (RSAPrivateCrtKey) privateKey;
        RSASecretBCPGKey rsaSecret = new RSASecretBCPGKey(rsaPriv.getPrivateExponent(), rsaPriv.getPrimeP(), rsaPriv.getPrimeQ());
        PGPPrivateKey phpPrivateKey = new PGPPrivateKey(pgpPublicKey.getKeyID(), pgpPublicKey.getPublicKeyPacket(), rsaSecret);

        PGPDigestCalculator digestCalc = new JcaPGPDigestCalculatorProviderBuilder().build().get(HashAlgorithmTags.SHA1);
        PGPKeyPair pgpKeyPair = new PGPKeyPair(pgpPublicKey, phpPrivateKey);
        PGPSecretKey pgpSecretKey = new PGPSecretKey(
                PGPSignature.DEFAULT_CERTIFICATION, pgpKeyPair, identity, digestCalc,
                null, null,
                new JcaPGPContentSignerBuilder(pgpKeyPair.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA1),
                new JcePBESecretKeyEncryptorBuilder(PGPEncryptedData.CAST5, digestCalc)
                        .setProvider(DEFAULT_CRYPTO_PROVIDER)
                        .build(password));

        return new Pair<>(pgpSecretKey.getEncoded(), pgpSecretKey.getPublicKey().getEncoded());
    }

    private KeyPair generatePairFromStrings() throws NoSuchAlgorithmException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return null;
    }
}
