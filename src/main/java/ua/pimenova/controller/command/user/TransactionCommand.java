package ua.pimenova.controller.command.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import ua.pimenova.model.service.UserService;
import java.io.IOException;
import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;

public class TransactionCommand implements ICommand {
    private final OrderService orderService;
    private final UserService userService;
    private boolean isUpdated;

    public TransactionCommand(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return isMethodPost(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request,"order_id");
        getAttributeFromSessionToRequest(request, "isUpdated");
        getAttributeFromSessionToRequest(request, "errorMessage");
        return getURL(request) + formURL(request);
    }

    private String formURL(HttpServletRequest request) {
        String orderId = (String) request.getAttribute("order_id");
        String isUpdated = (String) request.getAttribute("isUpdated");
        return "?order_id=" + orderId + "&isUpdated=" + isUpdated;
    }

    private String executePost(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int id = Integer.parseInt(request.getParameter("order_id"));
        try {
            Order order = orderService.getByID(id);
            if (order != null) {
                int totalCost = order.getTotalCost();
                int currentAccount = user.getAccount();
                if (currentAccount < totalCost) {
                    String errorMessage = "Your account does not have enough funds for debiting. Please top up your account!";
                    session.setAttribute("errorMessage", errorMessage);
                    session.setAttribute("url", ERROR);
                    return request.getContextPath() + ERROR;
                }
                doTransaction(order, user, totalCost, currentAccount);
                session.setAttribute("isUpdated", String.valueOf(isUpdated));
                session.setAttribute("currentOrder", order);
                session.setAttribute("order_id", request.getParameter("order_id"));
                session.setAttribute("url", SHOW_PAGE_UPDATE_ORDER);
                return request.getContextPath() + TRANSACTION;
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        session.setAttribute("url", ERROR);
        return request.getContextPath() + ERROR;
    }

    private void doTransaction(Order order, User user, int totalCost, int currentAccount) throws DaoException {
        user.setAccount(currentAccount - totalCost);
        if (userService.update(user)) {
            order.setPaymentStatus(Order.PaymentStatus.PAID);
            order.setExecutionStatus(Order.ExecutionStatus.FORMED);
            isUpdated = orderService.update(order);
        }
    }
}


