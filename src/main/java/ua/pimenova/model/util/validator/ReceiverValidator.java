package ua.pimenova.model.util.validator;

import jakarta.servlet.http.HttpServletRequest;
import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.exception.IncorrectFormatException;

import static ua.pimenova.model.util.constants.Regex.NAME_REGEX;
import static ua.pimenova.model.util.constants.Regex.PHONE_REGEX;

public class ReceiverValidator implements Validator<Receiver> {

    @Override
    public void validate(Receiver receiver, HttpServletRequest request) throws IncorrectFormatException, DaoException {
        validateName(receiver.getFirstname());
        validateName(receiver.getLastname());
        validatePhone(receiver.getPhone());
    }

    private void validateName(String name) throws IncorrectFormatException {
        if(name == null || !name.matches(NAME_REGEX)) {
            throw new IncorrectFormatException("Error: Name does not match");
        }
    }

    private void validatePhone(String phone) throws IncorrectFormatException {
        if(phone == null || !phone.matches(PHONE_REGEX)) {
            throw new IncorrectFormatException("Error: Phone does not match +380XXXXXXXXX");
        }
    }
}
