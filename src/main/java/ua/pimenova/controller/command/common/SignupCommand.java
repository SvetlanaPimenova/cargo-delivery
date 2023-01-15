package ua.pimenova.controller.command.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.UserService;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;

public class SignupCommand implements ICommand {
    private final UserService userService;

    public SignupCommand(UserService userService) {
        this.userService = userService;
    }
    private static final Logger logger = LoggerFactory.getLogger(SignupCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return isMethodPost(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request, "errorPhone");
        getAttributeFromSessionToRequest(request, "errorEmail");
        return getURL(request);
    }

    private String executePost(HttpServletRequest request) {
        User user = getUser(request);
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        try {
            if (userService.getByPhone(user.getPhone()) != null) {
                String errorPhone = ResourceBundle.getBundle("messages", locale).getString("signup.phone.in.use");
                request.getSession().setAttribute("errorPhone", errorPhone);
                request.getSession().setAttribute("url", SHOW_SIGNUP_PAGE);
                return request.getContextPath() + SHOW_SIGNUP_PAGE;
            }
            if (userService.getByEmail(user.getEmail()) != null) {
                String errorEmail = ResourceBundle.getBundle("messages", locale).getString("signup.email.in.use");
                request.getSession().setAttribute("errorEmail", errorEmail);
                request.getSession().setAttribute("url", SHOW_SIGNUP_PAGE);
                return request.getContextPath() + SHOW_SIGNUP_PAGE;
            }
            userService.create(user);
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        session.setAttribute("userRole", user.getRole());
        request.getSession().setAttribute("url", PROFILE);
        return request.getContextPath() + PROFILE;
    }

    private User getUser(HttpServletRequest request) {
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
