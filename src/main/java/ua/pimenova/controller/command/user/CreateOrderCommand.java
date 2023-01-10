package ua.pimenova.controller.command.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.entity.*;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import ua.pimenova.model.util.Calculator;
import java.io.IOException;
import java.util.Date;
import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;

public class CreateOrderCommand implements ICommand {
    private final OrderService orderService;

    public CreateOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return isMethodPost(request) ? executePost(request) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        return getURL(request);
    }

    private String executePost(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String path = ERROR;
        if (user != null) {
            Freight freight = getFullFreight(request);
            Receiver receiver = getFullReceiver(request);
            Order order = getFullOrder(request, freight, receiver, user);
            try {
                order = orderService.create(order);
            } catch (DaoException e) {
                e.printStackTrace();
                return path;
            }
            path = SHOW_PAGE_CREATE_ORDER;
            request.getSession().setAttribute("newOrder", order);
            request.getSession().setAttribute("url", path);
        }
        return request.getContextPath() + path;
    }

    private Freight getFullFreight(HttpServletRequest request) {
        Freight.FreightType freightType = Freight.FreightType.valueOf(request.getParameter("freighttype").toUpperCase());
        double weight = Double.parseDouble(request.getParameter("weight"));
        double length = Double.parseDouble(request.getParameter("length"));
        double width = Double.parseDouble(request.getParameter("width"));
        double height = Double.parseDouble(request.getParameter("height"));
        String cost = request.getParameter("cost");
        int estimatedCost;
        if(cost == null || cost.equalsIgnoreCase("")) {
            estimatedCost = 0;
        } else {
            estimatedCost = Integer.parseInt(cost);
        }
        return new Freight(0, weight, length, width, height, estimatedCost, freightType);
    }
    private Receiver getFullReceiver(HttpServletRequest request) {
        String firstName = request.getParameter("rfname");
        String lastName = request.getParameter("rlname");
        String phone = request.getParameter("rphone");
        String city = request.getParameter("cityto");
        String street = request.getParameter("rstreet");
        String postalCode = request.getParameter("rpcode");
        return new Receiver(0, firstName, lastName, phone, city, street, postalCode);
    }
    private Order getFullOrder(HttpServletRequest request, Freight freight, Receiver receiver, User user) {
        String cityFrom = request.getParameter("cityfrom");
        ExtraOptions.DeliveryType deliveryType = ExtraOptions.DeliveryType.valueOf(request.getParameter("deliverytype").toUpperCase());
        int totalCost = Calculator.getTotalCost(cityFrom, receiver.getCity(), freight.getType(),
                deliveryType, freight.getWeight());
        return new Order(0, new Date(), cityFrom, freight, totalCost, deliveryType,
                receiver, user, Order.PaymentStatus.UNPAID, Order.ExecutionStatus.IN_PROCESSING);
    }
}
