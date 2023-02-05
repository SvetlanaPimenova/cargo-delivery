package ua.pimenova.model.exception;

/**
 * Use different messages for incorrect email, password, name, surname, phone
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class IncorrectFormatException extends Exception {
    public IncorrectFormatException() {}

    public IncorrectFormatException(String message) {
        super(message);
    }

    public IncorrectFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectFormatException(Throwable cause) {
        super(cause);
    }
}
