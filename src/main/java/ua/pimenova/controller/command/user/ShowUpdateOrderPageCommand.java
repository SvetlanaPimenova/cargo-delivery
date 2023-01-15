package ua.pimenova.controller.command.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ShowUpdateOrderPageCommand implements ICommand {
    private final OrderService orderService;
    private boolean isUpdated = false;

    public ShowUpdateOrderPageCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
