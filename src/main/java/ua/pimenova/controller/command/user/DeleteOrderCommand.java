package ua.pimenova.controller.command.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import java.io.IOException;
import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;

public class DeleteOrderCommand implements ICommand {
    private final OrderService orderService;

    public DeleteOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return isMethodPost(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request, "errorMessage");
        return getURL(request);
    }

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
            e.printStackTrace();
            return request.getContextPath() + ERROR;
        }
        String errorMessage = "You cannot delete a shipment if it has already been formed.";
        request.getSession().setAttribute("errorMessage", errorMessage);
        request.getSession().setAttribute("url", ERROR);
        return request.getContextPath() + ERROR;
    }
}
