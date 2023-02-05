package ua.pimenova.controller.command.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.*;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.exception.IncorrectFormatException;
import ua.pimenova.model.service.OrderService;
import ua.pimenova.model.util.Calculator;
import ua.pimenova.model.util.EmailSender;
import ua.pimenova.model.util.validator.ReceiverValidator;
import java.util.Date;

import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;
import static ua.pimenova.model.util.constants.Email.*;

/**
 * CreateOrderCommand class. Accessible by authorized user. Allows to create new order. Implements PRG pattern
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class CreateOrderCommand implements ICommand {
    private final OrderService orderService;
    private static final Logger LOGGER = Logger.getLogger(CreateOrderCommand.class);
    private final EmailSender emailSender = new EmailSender();

    /**
     * @param orderService - OrderService implementation to use in command
     */
    public CreateOrderCommand(OrderService orderService) {
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
     * @return create order page
     */
    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request, "errorMessage");
        Order orderAttribute = (Order) request.getSession().getAttribute("newOrder");
        if (orderAttribute != null) {
            request.setAttribute("newOrder", orderAttribute);
            request.getSession().removeAttribute("newOrder");
        }
        return getUrlAttribute(request);
    }

    /**
     * Called from doPost method in front-controller. Tries to create an order. If successful redirects to create order page,
     * if not sets error and redirects to executeGet. Sends email if creating was successful
     *
     * @param request to get session and different parameters
     * @return path to redirect to executeGet method through front-controller
     */
    private String executePost(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        Freight freight = getFullFreight(request);
        Receiver receiver = getFullReceiver(request);
        Order order = getFullOrder(request, freight, receiver, user);
        try {
            ReceiverValidator validator = new ReceiverValidator();
            validator.validate(receiver, request);
            order = orderService.create(order);
            session.setAttribute("newOrder", order);
            session.setAttribute("url", SHOW_PAGE_CREATE_ORDER);
            sendEmail(user, order, getURL(request));
            return request.getContextPath() + CREATE_ORDER;
        } catch (DaoException | IncorrectFormatException e) {
            session.setAttribute("errorMessage", e.getMessage());
            session.setAttribute("url", SHOW_PAGE_CREATE_ORDER);
            LOGGER.error(e.getMessage());
            return request.getContextPath() + CREATE_ORDER;
        }
    }

    private void sendEmail(User user, Order order, String url) {
        String body = String.format(MESSAGE_CREATE_ORDER, user.getFirstname(), order.getTotalCost(), url);
        new Thread(() -> emailSender.send(SUBJECT_NOTIFICATION, body, user.getEmail())).start();
    }

    private Freight getFullFreight(HttpServletRequest request) {
        Freight.FreightType freightType = Freight.FreightType.valueOf(request.getParameter("freighttype").toUpperCase());
        double weight = Double.parseDouble(request.getParameter("weight"));
        double length = Double.parseDouble(request.getParameter("length"));
        double width = Double.parseDouble(request.getParameter("width"));
        double height = Double.parseDouble(request.getParameter("height"));
        String cost = request.getParameter("cost");
        int estimatedCost;
        if (cost == null || cost.equalsIgnoreCase("")) {
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
