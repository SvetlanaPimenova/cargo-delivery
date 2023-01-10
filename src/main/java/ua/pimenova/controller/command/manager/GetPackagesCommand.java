package ua.pimenova.controller.command.manager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.builder.QueryBuilder;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import java.io.IOException;
import java.util.List;


public class GetPackagesCommand implements ICommand {
    private final OrderService orderService;
    public GetPackagesCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        QueryBuilder queryBuilder = getQueryBuilder(request);
        int noOfRecords;
        List<Order> orders;
        try {
            orders = orderService.getAll(queryBuilder.getQuery());
            noOfRecords = orderService.getNumberOfRows(queryBuilder.getRecordQuery());
        } catch (DaoException e) {
            e.printStackTrace();
            return Pages.PAGE_ERROR;
        }
        doPagination(noOfRecords, request);
        request.setAttribute("shipments", orders);
        return Pages.PACKAGES;
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


//        String page = request.getParameter("currentPage");
//        String records = request.getParameter("recordsPerPage");
//        if(page == null || records == null) {
//           page = "1";
//           records = "4";
//        }
//        int currentPage = Integer.parseInt(page);
//        int recordsPerPage = Integer.parseInt(records);
//        int offset = (currentPage - 1)*recordsPerPage;
//        int noOfRecords;
//        List<Order> orders;
//
//        try {
//            orders = orderService.viewAllOrdersLimited(offset, recordsPerPage);
//            noOfRecords = orderService.getNumberOfRows();
//        } catch (DaoException e) {
//            e.printStackTrace();
//            return Pages.PAGE_ERROR;
//        }
//        int noOfPages = (int)Math.ceil(noOfRecords * 1.0 / recordsPerPage);
//        String sort = request.getParameter("sort");
//        sortOrdersList(orders, sort);
//
//        request.setAttribute("noOfPages", noOfPages);
//        request.setAttribute("currentPage", currentPage);
//        request.setAttribute("recordsPerPage", recordsPerPage);
