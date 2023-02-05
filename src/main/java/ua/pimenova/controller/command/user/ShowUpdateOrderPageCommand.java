package ua.pimenova.controller.command.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ShowUpdateOrderPageCommand class. Accessible by authorized user
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class ShowUpdateOrderPageCommand implements ICommand {
    private final OrderService orderService;
    private boolean isUpdated = false;

    /**
     * @param orderService - OrderService implementation to use in command
     */
    public ShowUpdateOrderPageCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Checks if order's execution status is equals "IN PROCESSING". Depends on success or not it returns ether update order page
     * or error page with  message.
     * @param request - to get order id and locale
     * @param response - passed by application
     * @return update order page or error page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("order_id"));
        String s = request.getParameter("isUpdated");
        if(s != null && s.equalsIgnoreCase("true")) {
            return Pages.UPDATE_ORDER_PAGE;
        }
        try {
            Order order = orderService.getByID(id);
            if(order != null) {
                if(order.getExecutionStatus() == Order.ExecutionStatus.IN_PROCESSING) {
                    request.setAttribute("isUpdated", isUpdated);
                    request.setAttribute("currentOrder", order);
                    return Pages.UPDATE_ORDER_PAGE;
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return Pages.PAGE_ERROR;
        }
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        String errorMessage = ResourceBundle.getBundle("messages", locale).getString("shipment.is.formed.warning");
        request.setAttribute("errorMessage", errorMessage);
        return Pages.PAGE_ERROR;
    }
}
