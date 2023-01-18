package ua.pimenova.controller.command.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.exception.IncorrectFormatException;
import ua.pimenova.model.service.UserService;
import ua.pimenova.model.util.UserValidator;

import java.io.IOException;

import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;
import static ua.pimenova.model.util.Validator.*;

public class SignupCommand implements ICommand {
    private final UserService userService;

    public SignupCommand(UserService userService) {
        this.userService = userService;
    }

    private static final Logger LOGGER = Logger.getLogger(SignupCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return isMethodPost(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
//        getAttributeFromSessionToRequest(request, "errorPhone");
//        getAttributeFromSessionToRequest(request, "errorEmail");
        getAttributeFromSessionToRequest(request, "errorMessage");
        return getURL(request);
    }

    private String executePost(HttpServletRequest request) {
        User user;
        try {
            user = getUser(request);
            UserValidator validator = new UserValidator(userService);
            validator.validate(user, request);
            userService.create(user);
        } catch (DaoException | IncorrectFormatException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            request.getSession().setAttribute("url", SHOW_SIGNUP_PAGE);
            LOGGER.error(e.getMessage());
            return request.getContextPath() + SIGN_UP;
        }
        HttpSession session = request.getSession(false);
        session.setAttribute("user", user);
        session.setAttribute("userRole", user.getRole());
        request.getSession().setAttribute("url", PROFILE);
        return request.getContextPath() + SIGN_UP;
    }

    private User getUser(HttpServletRequest request) throws IncorrectFormatException {
        String firstName = request.getParameter("firstname").strip();
        String lastName = request.getParameter("lastname").strip();
        String email = request.getParameter("email").strip();
        String phone = request.getParameter("phone").strip();
        String city = request.getParameter("city").strip();
        String street = request.getParameter("street").strip();
        String postalCode = request.getParameter("postalcode").strip();
        String password = request.getParameter("password");
        return new User(0, password, firstName, lastName, phone, email, 0, User.Role.USER,
                city, street, postalCode);
    }
}
