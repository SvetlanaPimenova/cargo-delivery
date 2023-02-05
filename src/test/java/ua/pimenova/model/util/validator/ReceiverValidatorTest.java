package ua.pimenova.model.util.validator;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.exception.IncorrectFormatException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ReceiverValidatorTest {
    HttpServletRequest request = mock(HttpServletRequest.class);

    Receiver receiver = new Receiver(1, "Ivan", "Ivanov", "+380111111111",
            "City", "Street", "Postal Code");

    ReceiverValidator validator = new ReceiverValidator();

    @ParameterizedTest
    @ValueSource(strings = {"Юрій", "Д'Артаньян", "Olga", "Olga Pimenova", "olga"})
    void testCorrectName(String name) {
        receiver.setFirstname(name);

        assertDoesNotThrow(() -> validator.validate(receiver, request));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Юрій1", "123", "Панас Мирный", "Ім'ямаєбутинебільшедвадцятисимволів"})
    void testIncorrectName(String name) {
        receiver.setFirstname(name);

        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validator.validate(receiver, request));
        assertEquals("Error: Name does not match", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyName(String name) {
        receiver.setFirstname(name);

        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validator.validate(receiver, request));
        assertEquals("Error: Name does not match", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"+380989010271", "+380111111111"})
    void testCorrectPhone(String phone) {
        receiver.setPhone(phone);

        assertDoesNotThrow(() -> validator.validate(receiver, request));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0989010271", "80989010271", "+3809890102711", "380989010271"})
    void testIncorrectPhone(String phone) {
        receiver.setPhone(phone);

        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validator.validate(receiver, request));
        assertEquals("Error: Phone does not match +380XXXXXXXXX", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyPhone(String phone) {
        receiver.setPhone(phone);

        IncorrectFormatException exception = assertThrows(IncorrectFormatException.class, () -> validator.validate(receiver, request));
        assertEquals("Error: Phone does not match +380XXXXXXXXX", exception.getMessage());
    }
}