package com.xlscoder.coder;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class KeyPairHolder {
    private String publicKey;
    private String privateKey;
    private KeyPair keyPair;

    public KeyPairHolder(KeyPair keyPair) {
        this.keyPair = keyPair;
        regenerateStrings();
    }

    private void regenerateStrings() {
        this.publicKey = encodedToPublicText(keyPair.getPublic().getEncoded());
        this.privateKey = encodedToPrivateText(keyPair.getPrivate().getEncoded());
    }

    private String encodedToPrivateText(byte[] src) {
        Base64.Encoder encoder = Base64.getEncoder();
        String result = "-----BEGIN RSA PRIVATE KEY-----\n";
        result += encoder.encodeToString(src);
        result += "\n-----END RSA PRIVATE KEY-----\n";
        return result;
    }

    private String encodedToPublicText(byte[] src) {
        Base64.Encoder encoder = Base64.getEncoder();
        String result = "-----BEGIN RSA PUBLIC KEY-----\n";
        result += encoder.encodeToString(src);
        result += "\n-----END RSA PUBLIC KEY-----\n";
        return result;
    }

    public static String firstNOfKey(String source, int maxlen) {
        source = source.replace("-----BEGIN RSA PUBLIC KEY-----\n", "");
        source = source.replace("-----BEGIN RSA PRIVATE KEY-----\n", "");
        return source.substring(0, Math.min(source.length(), maxlen));
    }


    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }
}
