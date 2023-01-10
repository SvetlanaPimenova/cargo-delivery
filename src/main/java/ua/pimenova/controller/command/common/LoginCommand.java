package ua.pimenova.controller.command.common;

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

public class LoginCommand implements ICommand {

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        return isMethodPost(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request, "errorMessage");
        return getURL(request);
    }

    private String executePost(HttpServletRequest request) {
        String login = request.getParameter("emaillogin");
        String password = request.getParameter("passlogin");
        User user;
        String path;
        try {
            user = userService.getUserByEmailAndPassword(login, password);
            if (user != null) {
                HttpSession newSession = request.getSession(false);
                newSession.setAttribute("user", user);
                newSession.setAttribute("userRole", user.getRole());
                path = PROFILE;
            } else {
                String errorMessage = "Either username or password is wrong.";
                request.getSession().setAttribute("errorMessage", errorMessage);
                path = ERROR;
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return Pages.PAGE_ERROR;
        }
        request.getSession().setAttribute("url", path);
        return request.getContextPath() + path;
    }
}
