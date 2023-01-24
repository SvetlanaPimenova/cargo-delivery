package ua.pimenova.controller.command.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.UserService;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;

public class UpdateProfileCommand implements ICommand {
    private final UserService userService;
    private boolean isUpdated = false;
    private static final Logger LOGGER = Logger.getLogger(UpdateProfileCommand.class);
    public UpdateProfileCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return isMethodPost(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request, "message");
        return getUrlAttribute(request);
    }

    private String executePost(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String action = request.getParameter("updateAction");
        switch (action) {
            case "personalData":
                try {
                    updatePersonalData(request, user);
                } catch (DaoException e) {
                    LOGGER.error(e.getMessage());
                }
                break;
            case "contactData":
                try {
                    updateContactData(request, user);
                } catch (DaoException e) {
                    LOGGER.error(e.getMessage());
                }
                break;
            case "passwordData":
                try {
                    updatePasswordData(request, user);
                } catch (DaoException e) {
                    LOGGER.error(e.getMessage());
                }
                break;
        }
        session.setAttribute("url", PROFILE);
        return request.getContextPath() + UPDATE_PROFILE;
    }

    private void updatePersonalData(HttpServletRequest request, User user) throws DaoException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        user.setFirstname(firstname);
        user.setLastname(lastname);
        String message;
        isUpdated = userService.update(user);
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        if (isUpdated) {
            message = ResourceBundle.getBundle("messages", locale).getString("personal.data.changed");
        } else {
            message = ResourceBundle.getBundle("messages", locale).getString("personal.data.not.changed");
        }
        request.getSession().setAttribute("message", message);
    }

    private void updateContactData(HttpServletRequest request, User user) throws DaoException {
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String postalCode = request.getParameter("postalcode");
        user.setPhone(phone);
        user.setEmail(email);
        user.setCity(city);
        user.setStreet(street);
        user.setPostalCode(postalCode);
        String message;
        isUpdated = userService.update(user);
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        if (isUpdated) {
            message = ResourceBundle.getBundle("messages", locale).getString("contact.data.changed");
        } else {
            message = ResourceBundle.getBundle("messages", locale).getString("contact.data.not.changed");
        }
        request.getSession().setAttribute("message", message);
    }

    private void updatePasswordData(HttpServletRequest request, User user) throws DaoException {
        String password = request.getParameter("password");
        user.setPassword(password);
        String message;
        isUpdated = userService.updatePassword(user);
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        if (isUpdated) {
            message = ResourceBundle.getBundle("messages", locale).getString("password.changed");
        } else {
            message = ResourceBundle.getBundle("messages", locale).getString("password.not.changed");
        }
        request.getSession().setAttribute("message", message);
    }
}
