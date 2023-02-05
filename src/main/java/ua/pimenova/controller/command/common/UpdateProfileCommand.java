package ua.pimenova.controller.command.common;

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

/**
 * UpdateProfileCommand class. Accessible by any user. Allows to update user profile. Implements PRG pattern
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class UpdateProfileCommand implements ICommand {
    private final UserService userService;
    private boolean isUpdated = false;
    private static final Logger LOGGER = Logger.getLogger(UpdateProfileCommand.class);

    /**
     * @param userService - UserService implementation to use in command
     */
    public UpdateProfileCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Checks method and calls required implementation
     *
     * @param request - to get method, session and set all required attributes
     * @param response - passed by application
     * @return path to redirect or forward by front-controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return isMethodPost(request) ? executePost(request) : executeGet(request);
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request
     *
     * @param request to get attributes from session and put it in request
     * @return profile page
     */
    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request, "message");
        return getUrlAttribute(request);
    }

    /**
     * Called from doPost method in front-controller. Tries to update profile. If successful redirects to profile page,
     * if not sets error and redirects to executeGet
     *
     * @param request to get session and different parameters
     * @return path to redirect to executeGet method through front-controller
     */
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
