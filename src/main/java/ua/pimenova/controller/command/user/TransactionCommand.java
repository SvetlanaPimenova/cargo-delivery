package ua.pimenova.controller.command.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import ua.pimenova.model.service.UserService;
import ua.pimenova.model.util.EmailSender;
import java.util.Locale;
import java.util.ResourceBundle;

import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;
import static ua.pimenova.model.util.constants.Email.*;

/**
 * TransactionCommand class. Accessible by authorized user. Allows to pay for an order. Implements PRG pattern
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class TransactionCommand implements ICommand {
    private final OrderService orderService;
    private final UserService userService;
    private boolean isUpdated;
    private static final Logger LOGGER = Logger.getLogger(TransactionCommand.class);
    private final EmailSender emailSender = new EmailSender();

    /**
     * @param orderService - OrderService implementation to use in command
     * @param userService - UserService implementation to use in command
     */
    public TransactionCommand(OrderService orderService, UserService userService) {
        this.orderService = orderService;
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
     * @return update order page
     */
    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request,"order_id");
        getAttributeFromSessionToRequest(request, "isUpdated");
        getAttributeFromSessionToRequest(request, "errorMessage");
        return getUrlAttribute(request) + formParameters(request);
    }

    private String formParameters(HttpServletRequest request) {
        String orderId = (String) request.getAttribute("order_id");
        String isUpdated = (String) request.getAttribute("isUpdated");
        return "?order_id=" + orderId + "&isUpdated=" + isUpdated;
    }

    /**
     * Called from doPost method in front-controller. Tries to pay for an order. If successful redirects to transaction page,
     * if not sets error and redirects to executeGet. Sends email if updating was successful
     *
     * @param request to get session and different parameters
     * @return path to redirect to executeGet method through front-controller
     */
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
                    Locale locale = (Locale) request.getSession().getAttribute("locale");
                    String errorMessage = ResourceBundle.getBundle("messages", locale).getString("account.not.enough.funds");
                    session.setAttribute("errorMessage", errorMessage);
                    session.setAttribute("url", ERROR);
                    return request.getContextPath() + ERROR;
                }
                doTransaction(order, user, totalCost, currentAccount);
                session.setAttribute("isUpdated", String.valueOf(isUpdated));
                session.setAttribute("currentOrder", order);
                session.setAttribute("order_id", request.getParameter("order_id"));
                session.setAttribute("url", SHOW_PAGE_UPDATE_ORDER);
                sendEmail(user, order, getURL(request));
                return request.getContextPath() + TRANSACTION;
            }
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
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

    private void sendEmail(User user, Order order, String url) {
        String body = String.format(MESSAGE_CHANGE_ORDER_STATUS, user.getFirstname(), order.getPaymentStatus(),
                order.getExecutionStatus(), url);
        new Thread(() -> emailSender.send(SUBJECT_NOTIFICATION, body, user.getEmail())).start();
    }
}


