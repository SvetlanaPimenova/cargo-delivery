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

public class TopUpCommand implements ICommand {
    private final UserService userService;

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
            e.printStackTrace();
            session.setAttribute("url", ERROR);
            return request.getContextPath() + ERROR;
        }
        session.setAttribute("url", ACCOUNT);
        return request.getContextPath() + ACCOUNT;
    }
}
