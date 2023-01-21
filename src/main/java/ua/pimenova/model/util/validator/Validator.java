package ua.pimenova.model.util.validator;

import jakarta.servlet.http.HttpServletRequest;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.exception.IncorrectFormatException;

public interface Validator<T> {

    void validate(T item, HttpServletRequest request) throws IncorrectFormatException, DaoException;
}
