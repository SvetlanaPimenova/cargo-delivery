package ua.pimenova.controller.command.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import java.util.Locale;
import java.util.ResourceBundle;

import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;

/**
 * DeleteOrderCommand class. Accessible by authorized user. Allows to delete an order. Implements PRG pattern
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class DeleteOrderCommand implements ICommand {
    private final OrderService orderService;
    private static final Logger LOGGER = Logger.getLogger(DeleteOrderCommand.class);

    /**
     * @param orderService - OrderService implementation to use in command
     */
    public DeleteOrderCommand(OrderService orderService) {
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
     * @param request to get attributes from session and put it in request
     * @return all orders or error page
     */
    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request, "errorMessage");
        return getUrlAttribute(request);
    }

    /**
     * Called from doPost method in front-controller. Tries to delete an order. If successful redirects to all orders page,
     * if not sets error and redirects to executeGet.
     *
     * @param request to get order id and locale
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("order_id"));
        try {
            Order order = orderService.getByID(id);
            if(order != null) {
                if(order.getExecutionStatus() == Order.ExecutionStatus.IN_PROCESSING) {
                    orderService.delete(order);
                    request.getSession().setAttribute("url", GET_ORDERS);
                    return request.getContextPath() + GET_ORDERS;
                }
            }
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        String errorMessage = ResourceBundle.getBundle("messages", locale).getString("cannot.delete.warning");
        request.getSession().setAttribute("errorMessage", errorMessage);
        request.getSession().setAttribute("url", ERROR);
        return request.getContextPath() + ERROR;
    }
}
