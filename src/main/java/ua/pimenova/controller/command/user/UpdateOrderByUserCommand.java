package ua.pimenova.controller.command.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.ExtraOptions;
import ua.pimenova.model.database.entity.Freight;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import ua.pimenova.model.util.Calculator;
import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;

/**
 * UpdateOrderByUserCommand class. Accessible by authorized user. Allows to update order. Implements PRG pattern
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class UpdateOrderByUserCommand implements ICommand {

    private final OrderService orderService;
    private boolean isUpdated;
    private static final Logger LOGGER = Logger.getLogger(UpdateOrderByUserCommand.class);

    /**
     * @param orderService - OrderService implementation to use in command
     */
    public UpdateOrderByUserCommand(OrderService orderService) {
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
     * @param request to get errorMessage attribute from session and put it in request
     * @return error page after failing or update order page
     */
    private String executeGet(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request,"order_id");
        getAttributeFromSessionToRequest(request, "isUpdated");
        return getUrlAttribute(request) + formParameters(request);
    }

    private String formParameters(HttpServletRequest request) {
        String orderId = (String) request.getAttribute("order_id");
        String isUpdated = (String) request.getAttribute("isUpdated");
        return "?order_id=" + orderId + "&isUpdated=" + isUpdated;
    }

    /**
     * Called from doPost method in front-controller. Tries to update an order. If successful redirects to update order page,
     * if not sets error and redirects to executeGet
     *
     * @param request to get order id and parameters
     * @return update order page if successful or path to redirect to executeGet method through front-controller if not
     */
    private String executePost(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("order_id"));
        try {
            Order order = orderService.getByID(id);
            if(order != null) {
                Freight freight = order.getFreight();
                Freight newFreight = setNewFreight(request, freight);
                Receiver receiver = order.getReceiver();
                Receiver newReceiver = setNewReceiver(request, receiver);
                order = setNewOrder(request, order, newFreight, newReceiver);
                isUpdated = orderService.update(order);
            }
            request.getSession().setAttribute("isUpdated", String.valueOf(isUpdated));
            request.getSession().setAttribute("currentOrder", order);
            request.getSession().setAttribute("order_id", request.getParameter("order_id"));
            request.getSession().setAttribute("url", SHOW_PAGE_UPDATE_ORDER);
            return request.getContextPath() + UPDATE_ORDER_BY_USER;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        request.getSession().setAttribute("url", ERROR);
        return request.getContextPath() + ERROR;
    }

    private Freight setNewFreight(HttpServletRequest request, Freight freight) {
        freight.setType(Freight.FreightType.valueOf(request.getParameter("freighttype").toUpperCase()));
        freight.setWeight(Double.parseDouble(request.getParameter("weight")));
        freight.setLength(Double.parseDouble(request.getParameter("length")));
        freight.setWidth(Double.parseDouble(request.getParameter("width")));
        freight.setHeight(Double.parseDouble(request.getParameter("height")));
        freight.setEstimatedCost(Integer.parseInt(request.getParameter("cost")));
        return freight;
    }

    private Receiver setNewReceiver(HttpServletRequest request, Receiver receiver) {
        receiver.setCity(request.getParameter("cityto"));
        receiver.setFirstname(request.getParameter("rfname"));
        receiver.setLastname(request.getParameter("rlname"));
        receiver.setPhone(request.getParameter("rphone"));
        receiver.setStreet(request.getParameter("rstreet"));
        receiver.setPostal_code(request.getParameter("rpcode"));
        return receiver;
    }

    private Order setNewOrder(HttpServletRequest request, Order order, Freight freight, Receiver receiver) {
        order.setFreight(freight);
        order.setReceiver(receiver);
        order.setTotalCost(updateTotalCost(order, receiver, freight));
        order.setDeliveryType(ExtraOptions.DeliveryType.valueOf(request.getParameter("deliverytype").toUpperCase()));
        return order;
    }

    private int updateTotalCost(Order order, Receiver receiver, Freight freight) {
        return Calculator.getTotalCost(order.getCityFrom(), receiver.getCity(), freight.getType(),
                order.getDeliveryType(), freight.getWeight());
    }
}
