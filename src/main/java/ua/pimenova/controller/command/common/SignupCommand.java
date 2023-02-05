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
import ua.pimenova.model.util.EmailSender;
import ua.pimenova.model.util.validator.UserValidator;

import java.io.IOException;

import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;
import static ua.pimenova.model.util.constants.Email.*;

/**
 * SignupCommand class. Accessible by any user. Allows to create an account. Implements PRG pattern
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class SignupCommand implements ICommand {
    private final UserService userService;

    private final EmailSender emailSender = new EmailSender();

    /**
     * @param userService - UserService implementation to use in command
     */
    public SignupCommand(UserService userService) {
        this.userService = userService;
    }

    private static final Logger LOGGER = Logger.getLogger(SignupCommand.class);

    /**
     * Checks method and calls required implementation
     *
     * @param request - to get method, session and set all required attributes
     * @param response - passed by application
     * @return path to redirect or forward by front-controller
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return isMethodPost(request) ? executePost(request) : executeGet(request);
    }

    /**
     * Called from doGet method in front-controller. Obtains required path and transfer attributes from session
     * to request
     *
     * @param request to get error message attribute from session and put it in request.
     * @return either profile page if everything is fine or sign-up if not
     */
    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request, "errorMessage");
        return getUrlAttribute(request);
    }

    /**
     * Called from doPost method in front-controller. Tries to register user. Sets different path to session depends on
     * success or not. Sends email if registration was successful
     *
     * @param request to get users fields from parameters
     * @return path to redirect to executeGet method
     */
    private String executePost(HttpServletRequest request) {
        try {
            User user = getUser(request);
            UserValidator validator = new UserValidator(userService);
            validator.validate(user, request);
            userService.create(user);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("url", PROFILE);
            sendEmail(user, getURL(request));
            return request.getContextPath() + SIGN_UP;
        } catch (DaoException | IncorrectFormatException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            request.getSession().setAttribute("url", SHOW_SIGNUP_PAGE);
            LOGGER.error(e.getMessage());
            return request.getContextPath() + SIGN_UP;
        }
    }

    private void sendEmail(User user, String url) {
        String body = String.format(MESSAGE_GREETINGS, user.getFirstname(), url);
        new Thread(() -> emailSender.send(SUBJECT_GREETINGS, body, user.getEmail())).start();
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
