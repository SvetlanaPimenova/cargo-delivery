package ua.pimenova.controller.command.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.UserService;
import ua.pimenova.model.util.EmailSender;

import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;
import static ua.pimenova.model.util.constants.Email.*;
import static ua.pimenova.controller.constants.Pages.*;

/**
 * ResetPasswordCommand class. Accessible by any user
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class ResetPasswordCommand implements ICommand {

    private final UserService userService;
    private static final Logger LOGGER = Logger.getLogger(ResetPasswordCommand.class);
    private final EmailSender emailSender = new EmailSender();

    /**
     * @param userService - UserService implementation to use in command
     */
    public ResetPasswordCommand(UserService userService) {
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
     * @param request to get url attribute from session
     * @return notification or error page
     */
    private String executeGet(HttpServletRequest request) {
        return getUrlAttribute(request);
    }

    /**
     * Called from doPost method in front-controller. Tries to reset password. If successful sets new temporary password
     * and sends email with new password, if not sets error and redirects to executeGet
     *
     * @param request to get users email some attributes in session
     * @return notification page if successful or path to redirect to executeGet method through front-controller if not
     */
    private String executePost(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        try {
            User user = userService.getByEmail(email);
            if(user != null) {
                String temporaryPassword = generatePassword();
                userService.resetPassword(user, temporaryPassword);
                session.setAttribute("url", NOTIFICATION_PAGE);
                sendEmail(user, temporaryPassword, getURL(request));
            }
        } catch (DaoException e) {
            request.getSession().setAttribute("url", ERROR);
            LOGGER.error(e.getMessage());
            return request.getContextPath() + RESET_PASSWORD;
        }
        return request.getContextPath() + RESET_PASSWORD;
    }
    private void sendEmail(User user, String temporaryPassword, String url) {
        String body = String.format(MESSAGE_RESET_PASSWORD, user.getFirstname(), temporaryPassword, url);
        new Thread(() -> emailSender.send(SUBJECT_NOTIFICATION, body, user.getEmail())).start();
    }

    private String generatePassword() {
        return "8lL" + RandomStringUtils.randomAlphanumeric(5);
    }

}
