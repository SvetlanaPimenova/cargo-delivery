package ua.pimenova.model.util.validator;

import jakarta.servlet.http.HttpServletRequest;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.exception.IncorrectFormatException;

/**
 * Validator interface. Implement it to create new type of validator
 *
 * @author Svetlana Pimenova
 * @version 1.0
 * @param <T> - entity being validated
 */
public interface Validator<T> {

    /**
     * Checks entity fields for matching regexes
     * @param item - entity being validated
     * @param request - passed by controller
     * @throws IncorrectFormatException - an unhandled exception. Will cause front-controller to redirect to error page
     * @throws DaoException - an unhandled exception. Will cause front-controller to redirect to error page
     */
    void validate(T item, HttpServletRequest request) throws IncorrectFormatException, DaoException;
}
