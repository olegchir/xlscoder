package com.xlscoder.coder;

import java.security.SecureRandom;
import java.util.Random;

public class InsecureRandom extends SecureRandom {
    private byte initialSeed;

    public InsecureRandom(byte seed) {
        this.initialSeed = seed;
    }

    /**
     * Fuck you Bouncy Castle!
     */
    @Override
    public synchronized void nextBytes(byte[] bytes) {
        //new Random(initialSeed).nextBytes(bytes);
        for (int i = 0, len = bytes.length; i < len; ) {
            bytes[i++] = initialSeed;
        }
    }
}
