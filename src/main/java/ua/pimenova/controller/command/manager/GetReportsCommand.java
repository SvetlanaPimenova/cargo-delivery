package ua.pimenova.controller.command.manager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import ua.pimenova.model.service.ReceiverService;
import ua.pimenova.model.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * GetReportsCommand class. Accessible by manager. Allows to get a list of all orders/users due to the searching parameter
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class GetReportsCommand implements ICommand {
    private final OrderService orderService;
    private final UserService userService;
    private final ReceiverService receiverService;

    private static final Logger LOGGER = Logger.getLogger(GetReportsCommand.class);

    /**
     * @param orderService - OrderService implementation to use in command
     * @param userService - UserService implementation to use in command
     * @param receiverService - ReceiverService implementation to use in command
     */
    public GetReportsCommand(OrderService orderService, UserService userService, ReceiverService receiverService) {
        this.orderService = orderService;
        this.userService = userService;
        this.receiverService = receiverService;
    }

    /**
     * Obtains a list of all orders/users due to the searching parameter, sets list in request and obtains required path
     *
     * @param request to get searching parameter and put list in request
     * @return view users page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String searchParameter = request.getParameter("searchParameter");
        if(searchParameter == null || searchParameter.equals("")) {
            return Pages.REPORTS;
        }
        List<Order> orders = new ArrayList<>();
        User user = null;
        List<Order> userShipments = new ArrayList<>();
        switch (searchParameter) {
            case "sender":
                user = searchBySender(request);
                userShipments = getAllOrdersBySender(user, userShipments);
                break;
            case "city_from":
                orders = searchByCityFrom(request, orders);
                break;
            case "city_to":
                orders = searchByCityTo(request, orders);
                break;
            case "date":
                orders = searchByDate(request, orders);
                break;
        }
        request.setAttribute("searchParameter", searchParameter);
        request.setAttribute("user", user);
        request.setAttribute("list", orders);
        request.setAttribute("userShipments", userShipments);
        return Pages.REPORTS;
    }

    private List<Order> getAllOrdersBySender(User user, List<Order> userShipments) {
        try {
            userShipments = orderService.getAllOrdersBySender(user);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        return userShipments;
    }

    private List<Order> searchByDate(HttpServletRequest request, List<Order> orders) {
        String parameter = request.getParameter("calendar");
        if(parameter == null || parameter.equals("")) {
            return orders;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(parameter);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
        }
        try {
            orders = orderService.getAllOrdersByDate(date);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        request.setAttribute("parameter", parameter);
        return orders;
    }

    private List<Order> searchByCityTo(HttpServletRequest request, List<Order> orders) {
        String parameter = request.getParameter("search");
        List<Receiver> receivers;
        if(parameter == null || parameter.equals("")) {
            return orders;
        }
        try {
            receivers = receiverService.getAllReceiversByCity(parameter);
            for(Receiver receiver:receivers) {
                orders.addAll(orderService.getAllOrdersByReceiver(receiver));
            }
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        request.setAttribute("parameter", parameter);
        return orders;
    }

    private List<Order> searchByCityFrom(HttpServletRequest request, List<Order> orders) {
        String parameter = request.getParameter("search");
        if(parameter == null || parameter.equals("")) {
            return orders;
        }
        try {
            orders = orderService.getAllOrdersByCityFrom(parameter);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        request.setAttribute("parameter", parameter);
        return orders;
    }

    private User searchBySender(HttpServletRequest request) {
        String parameter = request.getParameter("search");
        User user = null;
        if(parameter == null || parameter.equals("")) {
            return null;
        }
        try {
            user = userService.getByPhone(parameter);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        request.setAttribute("parameter", parameter);
        return user;
    }
}
