package ua.pimenova.controller.command.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.UserService;
import java.util.Locale;
import java.util.ResourceBundle;

import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;

/**
 * LoginCommand class. Accessible by any user. Allows to log in to the web app. Implements PRG pattern
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class LoginCommand implements ICommand {

    private final UserService userService;
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    /**
     * @param userService - UserService implementation to use in command
     */
    public LoginCommand(UserService userService) {
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
     * @param request to get errorMessage attribute from session and put it in request
     * @return error page after failing to log in
     */
    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request, "errorMessage");
        return getUrlAttribute(request);
    }

    /**
     * Called from doPost method in front-controller. Tries to log in web app. If successful sets user to session and
     * redirects to profile page, if not sets error and redirects to executeGet
     *
     * @param request to get users email, password and set some attributes in session
     * @return profile page if successful or path to redirect to executeGet method through front-controller if not
     */
    private String executePost(HttpServletRequest request) {
        String login = request.getParameter("emaillogin");
        String password = request.getParameter("passlogin");
        User user;
        String path = ERROR;
        try {
            user = userService.getUserByEmailAndPassword(login, password);
            if (user != null) {
                HttpSession session = request.getSession();
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
