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

public class ShowUpdateShipmentCommand implements ICommand {
    private final OrderService orderService;

    public ShowUpdateShipmentCommand(OrderService orderService) {
        this.orderService = orderService;
    }

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
            e.printStackTrace();
            return Pages.PAGE_ERROR;
        }
        String errorMessage = "You cannot update a shipment until it would be formed.";
        request.setAttribute("errorMessage", errorMessage);
        return Pages.PAGE_ERROR;
    }
}
