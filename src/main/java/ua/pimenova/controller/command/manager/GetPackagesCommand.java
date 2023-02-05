package ua.pimenova.controller.command.manager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.builder.QueryBuilder;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import java.util.List;

/**
 * GetPackagesCommand class. Accessible by manager. Allows to get a list of all orders
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class GetPackagesCommand implements ICommand {
    private final OrderService orderService;
    private static final Logger LOGGER = Logger.getLogger(GetPackagesCommand.class);

    /**
     * @param orderService - OrderService implementation to use in command
     */
    public GetPackagesCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Builds required query for service, sets orders list in request and obtains required path. Also sets all required
     * for pagination attributes
     *
     * @param request to get queries parameters and put orders list in request
     * @return all packages page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        int noOfRecords;
        List<Order> orders;
        try {
            orders = orderService.getAll(queryBuilder.getQuery());
            noOfRecords = orderService.getNumberOfRows(queryBuilder.getRecordQuery());
            doPagination(noOfRecords, request);
            request.setAttribute("shipments", orders);
            return Pages.PACKAGES;
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

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return new QueryBuilder()
                .setSortParameter(request.getParameter("sort"))
                .setDeliveryFilter(request.getParameter("deliveryFilter"))
                .setFreightTypeFilter(request.getParameter("freightFilter"))
                .setPaymentFilter(request.getParameter("paymentFilter"))
                .setExecutionFilter(request.getParameter("executionFilter"))
                .setLimits(request.getParameter("currentPage"), request.getParameter("recordsPerPage"));
    }
}
