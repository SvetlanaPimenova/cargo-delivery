package ua.pimenova.model.util.validator;

import jakarta.servlet.http.HttpServletRequest;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.exception.IncorrectFormatException;
import ua.pimenova.model.service.UserService;

import java.util.Locale;
import java.util.ResourceBundle;

import static ua.pimenova.model.util.constants.Regex.*;

public class UserValidator implements Validator<User> {


    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }
    @Override
    public void validate(User user, HttpServletRequest request) throws IncorrectFormatException, DaoException {
        validateName(user.getFirstname());
        validateName(user.getLastname());
        validateEmail(user.getEmail());
        validatePhone(user.getPhone());
        validatePassword(user.getPassword());
        checkConfirmPassword(user.getPassword(), request.getParameter("reppass"));
        verifyEmailIsUnique(user.getEmail(), userService, request);
        verifyPhoneIsUnique(user.getPhone(), userService, request);
    }
    private void validateEmail(String email) throws IncorrectFormatException {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new IncorrectFormatException("Error: E-mail does not match");
        }
    }

    private void validatePassword(String password) throws IncorrectFormatException {
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            throw new IncorrectFormatException("Error: Password does not match");
        }
    }

    private void validateName(String name) throws IncorrectFormatException {
        if(name == null || !name.matches(NAME_REGEX)) {
            throw new IncorrectFormatException("Error: Name does not match");
        }
    }

    private void checkConfirmPassword(String password, String confirmPassword) throws IncorrectFormatException {
        if (!password.equals(confirmPassword)) {
            throw new IncorrectFormatException("Error: Fields 'Password' and 'Repeat password' must match");
        }
    }

    private void validatePhone(String phone) throws IncorrectFormatException {
        if(phone == null || !phone.matches(PHONE_REGEX)) {
            throw new IncorrectFormatException("Error: Phone does not match +380XXXXXXXXX");
        }
    }

    private void verifyPhoneIsUnique(String phone, UserService userService, HttpServletRequest request) throws DaoException, IncorrectFormatException {
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        if(userService.getByPhone(phone) != null) {
            String errorPhone = ResourceBundle.getBundle("messages", locale).getString("signup.phone.in.use");
            throw new IncorrectFormatException(errorPhone);
        }
    }

    private void verifyEmailIsUnique(String email, UserService userService, HttpServletRequest request) throws DaoException, IncorrectFormatException {
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        if(userService.getByEmail(email) != null) {
            String errorEmail = ResourceBundle.getBundle("messages", locale).getString("signup.email.in.use");
            throw new IncorrectFormatException(errorEmail);
        }
    }
}
