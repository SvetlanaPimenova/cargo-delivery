package ua.pimenova.model.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptingUserPassword {
    public static String encryptPassword(String password) {
        MessageDigest crypt = null;
        try {
            crypt = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return toHexStr(crypt.digest(password.getBytes(StandardCharsets.UTF_8)));
    }

    private static String toHexStr(byte[] hash) {
        BigInteger no = new BigInteger(1, hash);
        StringBuilder hexStr = new StringBuilder(no.toString(16));
        while (hexStr.length() < 32)
        {
            hexStr.insert(0, '0');
        }
        return hexStr.toString();
    }
}
