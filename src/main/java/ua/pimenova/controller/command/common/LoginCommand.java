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

public class LoginCommand implements ICommand {

    private final UserService userService;
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

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
        String path = ERROR;
        try {
            user = userService.getUserByEmailAndPassword(login, password);
            if (user != null) {
                HttpSession session = request.getSession(false);
                session.setAttribute("user", user);
                session.setAttribute("userRole", user.getRole());
                path = PROFILE;
            } else {
                Locale locale = (Locale) request.getSession().getAttribute("locale");
                String errorMessage = ResourceBundle.getBundle("messages", locale).getString("wrong.username.password");
                request.getSession().setAttribute("errorMessage", errorMessage);
                path = ERROR;
            }
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        request.getSession().setAttribute("url", path);
        return request.getContextPath() + path;
    }
}
