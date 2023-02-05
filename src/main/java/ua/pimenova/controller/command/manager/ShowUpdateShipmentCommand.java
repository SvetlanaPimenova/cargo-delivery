package ua.pimenova.controller.command.manager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ShowUpdateShipmentCommand class. Accessible by manager
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class ShowUpdateShipmentCommand implements ICommand {
    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(ShowUpdateShipmentCommand.class);

    /**
     * @param orderService - OrderService implementation to use in command
     */
    public ShowUpdateShipmentCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Checks if order's execution status isn't equals "IN PROCESSING". Depends on success or not it returns ether update order page
     * or error page with  message.
     * @param request - to get order id and locale
     * @param response - passed by application
     * @return update order page or error page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("shipment_id"));
        try {
            Order order = orderService.getByID(id);
            if(order != null) {
                if(order.getExecutionStatus() != Order.ExecutionStatus.IN_PROCESSING) {
                    request.setAttribute("currentShipment", order);
                    return Pages.UPDATE_PACKAGE;
                }
            }
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        String errorMessage = ResourceBundle.getBundle("messages", locale).getString("shipment.not.formed.warning");
        request.setAttribute("errorMessage", errorMessage);
        return Pages.PAGE_ERROR;
    }
}
