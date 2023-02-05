package ua.pimenova.controller.command.manager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import ua.pimenova.model.util.EmailSender;

import java.util.Locale;
import java.util.ResourceBundle;

import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;
import static ua.pimenova.model.util.constants.Email.MESSAGE_CHANGE_ORDER_STATUS;
import static ua.pimenova.model.util.constants.Email.SUBJECT_NOTIFICATION;

/**
 * UpdateOrderByManagerCommand class. Accessible by manager. Allows to update an order's execution status. Implements PRG pattern
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class UpdateOrderByManagerCommand implements ICommand {
    private final OrderService orderService;
    private static final Logger LOGGER = Logger.getLogger(UpdateOrderByManagerCommand.class);
    private final EmailSender emailSender = new EmailSender();

    /**
     * @param orderService - OrderService implementation to use in command
     */
    public UpdateOrderByManagerCommand(OrderService orderService) {
        this.orderService = orderService;
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
     * @return error page after failing or page with all orders
     */
    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request, "errorMessage");
        return getUrlAttribute(request);
    }

    /**
     * Called from doPost method in front-controller. Tries to update an order. If successful redirects to page with all orders,
     * if not sets error and redirects to executeGet. Sends email if updating was successful
     *
     * @param request to get order id and new execution status
     * @return orders page if successful or path to redirect to executeGet method through front-controller if not
     */
    private String executePost(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("shipment_id"));
        Order.ExecutionStatus status = Order.ExecutionStatus.valueOf(request.getParameter("newStatus").toUpperCase());
        try {
            Order order = orderService.getByID(id);
            if(order != null) {
                if(order.getExecutionStatus() != Order.ExecutionStatus.IN_PROCESSING) {
                    order.setExecutionStatus(status);
                    orderService.update(order);
                    String path = GET_PACKAGES;
                    request.getSession().setAttribute("url", path);
                    sendEmail(order, getURL(request));
                    return request.getContextPath() + path;
                }
            }
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        String errorMessage = ResourceBundle.getBundle("messages", locale).getString("shipment.not.formed.warning");
        request.getSession().setAttribute("errorMessage", errorMessage);
        return request.getContextPath() + ERROR;
    }

    private void sendEmail(Order order, String url) {
        String body = String.format(MESSAGE_CHANGE_ORDER_STATUS, order.getSender().getFirstname(), order.getPaymentStatus(),
                order.getExecutionStatus(), url);
        new Thread(() -> emailSender.send(SUBJECT_NOTIFICATION, body, order.getSender().getEmail())).start();
    }
}
