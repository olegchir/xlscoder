package com.xlscoder.coder;

import java.util.Arrays;
import java.util.Base64;

public class KeyPairHolder {

    // Can't remove this garbage easy, because
    // textarea in browsers always change LF to CR LF, and that's dumbest thing I ever seen.
    // Technically we can just strip newlines. But what's our guarantee?
    // https://crypto.stackexchange.com/questions/19043/can-i-remove-newlines-in-a-public-key
    // So, I prefer just not to store garbage in database, and generate it ad-hoc.

    public static final String BEGIN_RSA_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----\n";
    public static final String END_RSA_PRIVATE_KEY = "\n-----END RSA PRIVATE KEY-----\n";
    public static final String BEGIN_RSA_PUBLIC_KEY = "-----BEGIN RSA PUBLIC KEY-----\n";
    public static final String END_RSA_PUBLIC_KEY = "\n-----END RSA PUBLIC KEY-----\n";
    public static String[] GARBAGE = new String[] {
            BEGIN_RSA_PRIVATE_KEY, END_RSA_PRIVATE_KEY, BEGIN_RSA_PUBLIC_KEY, END_RSA_PUBLIC_KEY};

    private String publicKey;
    private String privateKey;
    private String pgpPrivateKey;
    private String pgpPublicKey;

    public KeyPairHolder(byte[] privateSrc, byte[] publicSrc, byte[] pgpPrivateSrc, byte[] pgpPublicSrc) {
        this.publicKey = toBase64String(publicSrc);
        this.privateKey = toBase64String(privateSrc);
        this.pgpPrivateKey = toBase64String(pgpPrivateSrc);
        this.pgpPublicKey = toBase64String(pgpPublicSrc);
    }

    private String encodedToPrivateText(byte[] src) {
        Base64.Encoder encoder = Base64.getEncoder();
        String result = BEGIN_RSA_PRIVATE_KEY;
        result += encoder.encodeToString(src);
        result += END_RSA_PRIVATE_KEY;
        return result;
    }

    private String encodedToPublicText(byte[] src) {
        Base64.Encoder encoder = Base64.getEncoder();
        String result = BEGIN_RSA_PUBLIC_KEY;
        result += encoder.encodeToString(src);
        result += END_RSA_PUBLIC_KEY;
        return result;
    }

    private String toBase64String(byte[] src) {
        return Base64.getEncoder().encodeToString(src);
    }

    public static String firstNOfKey(String source, int maxlen) {
        for (String curr : Arrays.asList(GARBAGE)) {
            source = source.replace(curr, "");
        }
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

    public String getPgpPrivateKey() {
        return pgpPrivateKey;
    }

    public void setPgpPrivateKey(String pgpPrivateKey) {
        this.pgpPrivateKey = pgpPrivateKey;
    }

    public String getPgpPublicKey() {
        return pgpPublicKey;
    }

    public void setPgpPublicKey(String pgpPublicKey) {
        this.pgpPublicKey = pgpPublicKey;
    }
}
