package ua.pimenova.controller.command.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.UserService;
import ua.pimenova.model.util.EmailSender;

import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;
import static ua.pimenova.model.util.constants.Email.*;

/**
 * TopUpCommand class. Accessible by authorized user. Allows to top up an account. Implements PRG pattern
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class TopUpCommand implements ICommand {
    private final UserService userService;
    private static final Logger LOGGER = Logger.getLogger(TopUpCommand.class);
    private final EmailSender emailSender = new EmailSender();

    /**
     * @param userService - UserService implementation to use in command
     */
    public TopUpCommand(UserService userService) {
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
     * @return account page
     */
    private String executeGet(HttpServletRequest request) {
        return getUrlAttribute(request);
    }

    /**
     * Called from doPost method in front-controller. Tries to top up an account. If successful redirects to account page,
     * if not sets error and redirects to executeGet. Sends email if updating was successful
     *
     * @param request to get session and different parameters
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int sum = Integer.parseInt(request.getParameter("account"));
        int account = user.getAccount() + sum;
        user.setAccount(account);
        try {
            userService.update(user);
            sendEmail(user, sum, getURL(request));
        } catch (DaoException e) {
            session.setAttribute("url", ERROR);
            LOGGER.error(e.getMessage());
            return request.getContextPath() + ERROR;
        }
        session.setAttribute("url", ACCOUNT);
        return request.getContextPath() + ACCOUNT;
    }

    private void sendEmail(User user, int sum, String url) {
        String body = String.format(MESSAGE_TOP_UP_ACCOUNT, user.getFirstname(), sum, user.getAccount(), url);
        new Thread(() -> emailSender.send(SUBJECT_NOTIFICATION, body, user.getEmail())).start();
    }
}
