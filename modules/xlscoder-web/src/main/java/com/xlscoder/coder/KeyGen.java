package com.xlscoder.coder;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyGen {
    public static final int DEFAULT_KEY_SIZE = 2048;
    public static KeyPairHolder generatePair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(DEFAULT_KEY_SIZE);
        KeyPairHolder result = new KeyPairHolder(keyGen.genKeyPair());
        return  result;
    }

    private KeyPair generatePairFromStrings() throws NoSuchAlgorithmException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return null;
    }
}
