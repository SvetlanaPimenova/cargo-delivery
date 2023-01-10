package ua.pimenova.controller.command.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.builder.QueryBuilder;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import java.io.IOException;
import java.util.List;

public class GetOrdersCommand implements ICommand {
    private final OrderService orderService;

    public GetOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
        } catch (DaoException e) {
            e.printStackTrace();
            return Pages.PAGE_ERROR;
        }
        doPagination(noOfRecords, request);
        request.setAttribute("orders", orders);
        return Pages.ORDERS_LIST_PAGE;
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
