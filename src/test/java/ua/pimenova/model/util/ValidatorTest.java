package ua.pimenova.model.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ua.pimenova.model.exception.IncorrectFormatException;

import static org.junit.jupiter.api.Assertions.*;
import static ua.pimenova.model.util.Validator.*;

class ValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"mail@mail.com.ua", "mail@gmail.com", "my.mail@ukr.net", "my_maill@g.mail.com"})
    void testCorrectEmail(String email) {
        assertDoesNotThrow(() -> validateEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"mail.com.ua", "@gmail.com", "my.mail@ukr", "my_maill@g.mail.com.u"})
    void testIncorrectEmail(String email) {
        assertThrows(IncorrectFormatException.class, () -> validateEmail(email));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyEmail(String email) {
        assertThrows(IncorrectFormatException.class, () -> validateEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Password1", "PassWord123", "pass_Word1", "123456Pw"})
    void testCorrectPassword(String password) {
        assertDoesNotThrow(() -> validatePassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"NoDigitPass", "no_upper_letters1", "NO_LOW_CASE_1", "Short1"})
    void testIncorrectPassword(String password) {
        assertThrows(IncorrectFormatException.class, () -> validatePassword(password));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyPassword(String password) {
        assertThrows(IncorrectFormatException.class, () -> validatePassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Юрій", "Д'Артаньян", "Olga", "Olga Pimenova", "olga"})
    void testCorrectName(String name) {
        assertDoesNotThrow(() -> validateName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Юрій1", "123", "Панас Мирный", "Ім'ямаєбутинебільшедвадцятисимволів"})
    void testIncorrectName(String name) {
        assertThrows(IncorrectFormatException.class, () -> validateName(name));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyName(String name) {
        assertThrows(IncorrectFormatException.class, () -> validateName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"+380989010271", "+380111111111"})
    void testCorrectPhone(String phone) {
        assertDoesNotThrow(() -> validatePhone(phone));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0989010271", "80989010271", "+3809890102711", "380989010271"})
    void testIncorrectPhone(String phone) {
        assertThrows(IncorrectFormatException.class, () -> validatePhone(phone));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyPhone(String phone) {
        assertThrows(IncorrectFormatException.class, () -> validatePhone(phone));
    }

    @ParameterizedTest
    @CsvSource({"abc, abc", "123, 123", "Password1, Password1"})
    void testConfirmPassword(String password, String confirmPassword) {
        assertDoesNotThrow(() -> checkConfirmPassword(password, confirmPassword));
    }

    @ParameterizedTest
    @CsvSource({"abc, 123", "123, Password1", "Password1, abc"})
    void testWrongConfirmPassword(String password, String confirmPassword) {
        assertThrows(IncorrectFormatException.class, () -> checkConfirmPassword(password, confirmPassword));
    }
}