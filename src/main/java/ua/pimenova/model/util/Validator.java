package ua.pimenova.model.util;

import jakarta.servlet.http.HttpServletRequest;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.exception.IncorrectFormatException;
import ua.pimenova.model.service.UserService;

import java.util.Locale;
import java.util.ResourceBundle;

public class Validator {
    private static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "(?=.*\\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{8,}";

    private static final String NAME_REGEX = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{1,20}";

    private static final String PHONE_REGEX = "^\\+380\\d{2}\\d{3}\\d{2}\\d{2}$";

    private Validator() {}

    public static void validateEmail(String email) throws IncorrectFormatException {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new IncorrectFormatException("Error: E-mail does not match");
        }
    }

    public static void validatePassword(String password) throws IncorrectFormatException {
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            throw new IncorrectFormatException("Error: Password does not match");
        }
    }

    public static void validateName(String name) throws IncorrectFormatException {
        if(name == null || !name.matches(NAME_REGEX)) {
            throw new IncorrectFormatException("Error: Name does not match");
        }
    }

    public static void checkConfirmPassword(String password, String confirmPassword) throws IncorrectFormatException {
        if (!password.equals(confirmPassword)) {
            throw new IncorrectFormatException("Error: Fields 'Password' and 'Repeat password' must match");
        }
    }

    public static void validatePhone(String phone) throws IncorrectFormatException {
        if(phone == null || !phone.matches(PHONE_REGEX)) {
            throw new IncorrectFormatException("Error: Phone does not match +380XXXXXXXXX");
        }
    }

    public static boolean verifyPhoneIsUnique(String phone, UserService userService, HttpServletRequest request) throws DaoException {
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        if(userService.getByPhone(phone) != null) {
            String errorPhone = ResourceBundle.getBundle("messages", locale).getString("signup.phone.in.use");
            request.getSession().setAttribute("errorPhone", errorPhone);
            return true;
        }
        return false;
    }

    public static boolean verifyEmailIsUnique(String email, UserService userService, HttpServletRequest request) throws DaoException {
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        if(userService.getByEmail(email) != null) {
            String errorEmail = ResourceBundle.getBundle("messages", locale).getString("signup.email.in.use");
            request.getSession().setAttribute("errorEmail", errorEmail);
            return true;
        }
        return false;
    }
}
