package ua.pimenova.controller.command.manager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import java.io.IOException;
import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;

public class UpdateOrderByManagerCommand implements ICommand {
    private final OrderService orderService;

    public UpdateOrderByManagerCommand(OrderService orderService) {
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
                    return request.getContextPath() + path;
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return request.getContextPath() + ERROR;
        }
        String errorMessage = "You cannot update a shipment until it would be formed.";
        request.getSession().setAttribute("errorMessage", errorMessage);
        return request.getContextPath() + ERROR;
    }
}
