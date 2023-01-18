package ua.pimenova.controller.command.manager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import ua.pimenova.model.service.ReceiverService;
import ua.pimenova.model.service.UserService;
import ua.pimenova.model.util.ReportBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PdfBuilderCommand implements ICommand {
    private final OrderService orderService;
    private final UserService userService;
    private final ReceiverService receiverService;
    private static final Logger LOGGER = Logger.getLogger(PdfBuilderCommand.class);

    public PdfBuilderCommand(OrderService orderService, UserService userService, ReceiverService receiverService) {
        this.orderService = orderService;
        this.userService = userService;
        this.receiverService = receiverService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String searchParameter = request.getParameter("searchParameter");
        String parameter = request.getParameter("parameter");
        List<Order> list = new ArrayList<>();
        User user;
        List<Order> userShipments = new ArrayList<>();
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        ReportBuilder reportBuilder = new ReportBuilder(locale);
        switch (searchParameter) {
            case "sender":
                user = searchBySender(parameter);
                list = getAllOrdersBySender(user, userShipments);
                break;
            case "city_from":
                list = searchByCityFrom(parameter, list);
                break;
            case "city_to":
                list = searchByCityTo(parameter, list);
                break;
            case "date":
                list = searchByDate(parameter, list);
                break;
        }
        reportBuilder.reportPdf(response, list, parameter);
        return null;
    }
    private List<Order> searchByDate(String parameter, List<Order> orders) {
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
        return orders;
    }
    private List<Order> searchByCityFrom(String parameter, List<Order> orders) {
        if(parameter == null || parameter.equals("")) {
            return orders;
        }
        try {
            orders = orderService.getAllOrdersByCityFrom(parameter);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        return orders;
    }

    private List<Order> searchByCityTo(String parameter, List<Order> orders) {
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
        return orders;
    }
    private User searchBySender(String parameter) {
        User user = null;
        if(parameter == null || parameter.equals("")) {
            return null;
        }
        try {
            user = userService.getByPhone(parameter);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        return user;
    }

    private List<Order> getAllOrdersBySender(User user, List<Order> userShipments) {
        try {
            userShipments = orderService.getAllOrdersBySender(user);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        return userShipments;
    }
}
