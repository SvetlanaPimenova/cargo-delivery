package ua.pimenova.model.exception;

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
