package ua.pimenova.controller.command.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;

public class DeleteOrderCommand implements ICommand {
    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(DeleteOrderCommand.class);

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
            logger.error(e.getMessage());
        }
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        String errorMessage = ResourceBundle.getBundle("messages", locale).getString("cannot.delete.warning");
        request.getSession().setAttribute("errorMessage", errorMessage);
        request.getSession().setAttribute("url", ERROR);
        return request.getContextPath() + ERROR;
    }
}
