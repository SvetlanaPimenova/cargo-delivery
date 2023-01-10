package ua.pimenova.controller.command.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.UserService;

import java.io.IOException;

import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;

public class UpdateProfileCommand implements ICommand {
    private UserService userService;
    private boolean isUpdated = false;

    public UpdateProfileCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return isMethodPost(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request, "message");
        return getURL(request);
    }

    private String executePost(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String path = PROFILE;
        String action = request.getParameter("updateAction");
        switch (action) {
            case "personalData":
                try {
                    updatePersonalData(request, user);
                } catch (DaoException e) {
                    e.printStackTrace();
                    path = ERROR;
                }
            case "contactData":
                try {
                    updateContactData(request, user);
                } catch (DaoException e) {
                    e.printStackTrace();
                    path = ERROR;
                }
            case "passwordData":
                try {
                    updatePasswordData(request, user);
                } catch (DaoException e) {
                    e.printStackTrace();
                    path = ERROR;
                }
        }
        session.setAttribute("url", path);
        return request.getContextPath() + UPDATE_PROFILE;
    }

    private void updatePersonalData(HttpServletRequest request, User user) throws DaoException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        user.setFirstname(firstname);
        user.setLastname(lastname);
        String message;
        isUpdated = userService.update(user);
        if (isUpdated) {
            message = "Personal data have been successfully changed!";
        } else {
            message = "Personal data have not been changed.";
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
        if (isUpdated) {
            message = "Contact data have been successfully changed!";
        } else {
            message = "Contact data have not been changed.";
        }
        request.getSession().setAttribute("message", message);
    }

    private void updatePasswordData(HttpServletRequest request, User user) throws DaoException {
        String password = request.getParameter("password");
        user.setPassword(password);
        String message;
        isUpdated = userService.updatePassword(user);
        if (isUpdated) {
            message = "Password has been successfully changed!";
        } else {
            message = "Password has not been changed.";
        }
        request.getSession().setAttribute("message", message);
    }
}
