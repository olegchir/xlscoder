package com.xlscoder;

import com.xlscoder.coder.KeyGen;
import com.xlscoder.coder.KeyPairHolder;
import org.bouncycastle.openpgp.PGPException;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class CodeTest {
    @Test
    public void CodeRestoredEqualsTest() throws NoSuchAlgorithmException, PGPException, NoSuchProviderException, IOException {
        KeyPairHolder keyPairHolder = KeyGen.generatePairs();
    }
}
