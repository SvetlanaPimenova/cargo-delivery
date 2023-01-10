package ua.pimenova.model.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptingUserPasswordTest {

    @Test
    void encryptPasswordTest() {
        String password = "Password1234";
        String encrypted = EncryptingUserPassword.encryptPassword(password);
        assertEquals("a0f3285b07c26c0dcd2191447f391170d06035e8d57e31a048ba87074f3a9a15", encrypted);
    }
}