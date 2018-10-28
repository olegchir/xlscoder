package com.xlscoder;

import com.xlscoder.coder.KeyGen;
import com.xlscoder.coder.KeyPairHolder;
import org.junit.Test;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public class CodeTest {
    @Test
    public void CodeRestoredEqualsTest() throws NoSuchAlgorithmException {
        KeyPairHolder keyPairHolder = KeyGen.generatePair();
        KeyPair keyPair = keyPairHolder.getKeyPair();


    }
}
