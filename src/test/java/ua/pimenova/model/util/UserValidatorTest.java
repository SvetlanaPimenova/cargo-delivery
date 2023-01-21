package ua.pimenova.model.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.exception.IncorrectFormatException;
import ua.pimenova.model.service.UserService;
import ua.pimenova.model.util.validator.UserValidator;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserValidatorTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    UserService userService = mock(UserService.class);
    HttpSession session = mock(HttpSession.class);

    UserValidator validator = new UserValidator(userService);

    User user = new User(1, "Password1", "Ivan", "Ivanov", "+380111111111", "user@gmail.com",
            0, User.Role.USER, "City", "Street", "Postal Code");

    @BeforeEach
    void setUp() {
        when(request.getParameter("reppass")).thenReturn("Password1");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("locale")).thenReturn(new Locale("en"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"mail@mail.com.ua", "mail@gmail.com", "my.mail@ukr.net", "my_maill@g.mail.com"})
    void testCorrectEmail(String email) throws DaoException {
        user.setEmail(email);

        when(userService.getByPhone(user.getPhone())).thenReturn(null);
        when(userService.getByEmail(user.getEmail())).thenReturn(null);

        assertDoesNotThrow(() -> validator.validate(user, request));
    }

    @ParameterizedTest
    @ValueSource(strings = {"mail.com.ua", "@gmail.com", "my.mail@ukr", "my_maill@g.mail.com.u"})
    void testIncorrectEmail(String email) {
        user.setEmail(email);

        IncorrectFormatException exception =
                assertThrows(IncorrectFormatException.class, () -> validator.validate(user, request));
        assertEquals("Error: E-mail does not match", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyEmail(String email) {
        user.setEmail(email);

        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validator.validate(user, request));
        assertEquals("Error: E-mail does not match", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Password1", "PassWord123", "pass_Word1", "123456Pw"})
    void testCorrectPassword(String password) throws DaoException {
        user.setPassword(password);
        when(request.getParameter("reppass")).thenReturn(password);

        when(userService.getByPhone(user.getPhone())).thenReturn(null);
        when(userService.getByEmail(user.getEmail())).thenReturn(null);

        assertDoesNotThrow(() -> validator.validate(user, request));
    }

    @ParameterizedTest
    @ValueSource(strings = {"NoDigitPass", "no_upper_letters1", "NO_LOW_CASE_1", "Short1"})
    void testIncorrectPassword(String password) {
        user.setPassword(password);
        when(request.getParameter("reppass")).thenReturn(password);

        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validator.validate(user, request));
        assertEquals("Error: Password does not match", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyPassword(String password) {
        user.setPassword(password);
        when(request.getParameter("reppass")).thenReturn(password);

        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validator.validate(user, request));
        assertEquals("Error: Password does not match", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Юрій", "Д'Артаньян", "Olga", "Olga Pimenova", "olga"})
    void testCorrectName(String name) throws DaoException {
        user.setFirstname(name);

        when(userService.getByPhone(user.getPhone())).thenReturn(null);
        when(userService.getByEmail(user.getEmail())).thenReturn(null);

        assertDoesNotThrow(() -> validator.validate(user, request));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Юрій1", "123", "Панас Мирный", "Ім'ямаєбутинебільшедвадцятисимволів"})
    void testIncorrectName(String name) {
        user.setFirstname(name);

        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validator.validate(user, request));
        assertEquals("Error: Name does not match", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyName(String name) {
        user.setFirstname(name);

        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validator.validate(user, request));
        assertEquals("Error: Name does not match", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"+380989010271", "+380111111111"})
    void testCorrectPhone(String phone) throws DaoException {
        user.setPhone(phone);

        when(userService.getByPhone(user.getPhone())).thenReturn(null);
        when(userService.getByEmail(user.getEmail())).thenReturn(null);

        assertDoesNotThrow(() -> validator.validate(user, request));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0989010271", "80989010271", "+3809890102711", "380989010271"})
    void testIncorrectPhone(String phone) {
        user.setPhone(phone);

        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validator.validate(user, request));
        assertEquals("Error: Phone does not match +380XXXXXXXXX", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyPhone(String phone) {
        user.setPhone(phone);

        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validator.validate(user, request));
        assertEquals("Error: Phone does not match +380XXXXXXXXX", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"Abcdefg1, Abcdefg1", "Ab345678, Ab345678", "Password1, Password1"})
    void testConfirmPassword(String password, String confirmPassword) throws DaoException {
        user.setPassword(password);
        when(request.getParameter("reppass")).thenReturn(confirmPassword);

        when(userService.getByPhone(user.getPhone())).thenReturn(null);
        when(userService.getByEmail(user.getEmail())).thenReturn(null);

        assertDoesNotThrow(() -> validator.validate(user, request));
    }

    @ParameterizedTest
    @CsvSource({"Abcdefg1, Ab345678", "Ab345678, Password1", "Password1, Abcdefg1"})
    void testWrongConfirmPassword(String password, String confirmPassword) {
        user.setPassword(password);
        when(request.getParameter("reppass")).thenReturn(confirmPassword);

        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validator.validate(user, request));
        assertEquals("Error: Fields 'Password' and 'Repeat password' must match", exception.getMessage());
    }

}