package com.xlscoder.coder;

import org.apache.commons.codec.digest.Crypt;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashHelper {
    public static final Logger logger = LoggerFactory.getLogger(HashHelper.class);

    private static final Pattern SALT_PATTERN = Pattern
            .compile("^\\$([56])\\$(rounds=(\\d+)\\$)?([\\.\\/a-zA-Z0-9]{1,16}).*");

    public static String sha512(String src, String salt) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(src.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return result;
    }

    public static String stdSha(String src, String salt) {
        //https://commons.apache.org/proper/commons-codec/apidocs/org/apache/commons/codec/digest/Crypt.html
        String sha512tag = "$6$";
        String sha512salt = sha512tag + salt;

        final Matcher m = SALT_PATTERN.matcher(sha512salt);
        if (!m.find()) {
            logger.error(String.format("Cant' find salt in salt! Something really bad happens now: %s", sha512salt));
        }
        final String saltString = m.group(4);
        String garbage = sha512tag + saltString + "$";

        String crypted = Crypt.crypt(src, sha512salt);
        String clearHash = StringUtils.replaceOnce(crypted, garbage, "");

        return clearHash;
    }

    public static String base64encode(String src) {
        return base64encode(src.getBytes());
    }

    public static String base64encode(byte[] src) {
        return Base64.getEncoder().encodeToString(src);
    }
}
