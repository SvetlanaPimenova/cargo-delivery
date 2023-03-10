package ua.pimenova.model.util;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Encrypt incoming password
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class EncryptingUserPassword {
    private static final Logger LOGGER = Logger.getLogger(EncryptingUserPassword.class);
    private EncryptingUserPassword() {}

    /**
     * Encrypt user's password
     * @param password - to properly encrypt use not null value
     * @return - encrypted password
     */
    public static String encryptPassword(String password) {
        MessageDigest crypt = null;
        try {
            crypt = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
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
