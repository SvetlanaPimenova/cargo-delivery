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
import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;

public class TopUpCommand implements ICommand {
    private final UserService userService;
    private static final Logger LOGGER = Logger.getLogger(TopUpCommand.class);
    public TopUpCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return isMethodPost(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        return getURL(request);
    }

    private String executePost(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int sum = Integer.parseInt(request.getParameter("account"));
        int account = user.getAccount() + sum;
        user.setAccount(account);
        try {
            userService.update(user);
        } catch (DaoException e) {
            session.setAttribute("url", ERROR);
            LOGGER.error(e.getMessage());
            return request.getContextPath() + ERROR;
        }
        session.setAttribute("url", ACCOUNT);
        return request.getContextPath() + ACCOUNT;
    }
}
