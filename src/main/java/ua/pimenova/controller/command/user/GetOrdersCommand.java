package ua.pimenova.controller.command.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.builder.QueryBuilder;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import java.util.List;

/**
 * GetOrdersCommand class. Accessible by authorized user. Allows to get a list of all orders
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class GetOrdersCommand implements ICommand {
    private final OrderService orderService;
    private static final Logger LOGGER = Logger.getLogger(GetOrdersCommand.class);

    /**
     * @param orderService - OrderService implementation to use in command
     */
    public GetOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Builds required query for service, sets orders list in request and obtains required path. Also sets all required
     * for pagination attributes
     *
     * @param request to get queries parameters and put orders list in request
     * @return all orders page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Pages.PAGE_ERROR;
        }
        QueryBuilder queryBuilder = getQueryBuilder(request, user.getId());
        int noOfRecords;
        List<Order> orders;
        try {
            orders = orderService.getAll(queryBuilder.getQuery());
            noOfRecords = orderService.getNumberOfRows(queryBuilder.getRecordQuery());
            doPagination(noOfRecords, request);
            request.setAttribute("orders", orders);
            return Pages.ORDERS_LIST_PAGE;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        return Pages.PAGE_ERROR;
    }

    private void doPagination(int noOfRecords, HttpServletRequest request) {
        String page = request.getParameter("currentPage");
        String records = request.getParameter("recordsPerPage");
        if(page == null || records == null) {
            page = "1";
            records = "4";
        }
        int currentPage = Integer.parseInt(page);
        int recordsPerPage = Integer.parseInt(records);
        int noOfPages = (int)Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request, int id) {
        return new QueryBuilder()
                .setUserIdFilter(id)
                .setSortParameter(request.getParameter("sort"))
                .setDeliveryFilter(request.getParameter("deliveryFilter"))
                .setFreightTypeFilter(request.getParameter("freightFilter"))
                .setPaymentFilter(request.getParameter("paymentFilter"))
                .setExecutionFilter(request.getParameter("executionFilter"))
                .setLimits(request.getParameter("currentPage"), request.getParameter("recordsPerPage"));
    }
}
