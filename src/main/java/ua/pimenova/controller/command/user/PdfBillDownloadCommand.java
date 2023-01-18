package ua.pimenova.controller.command.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import ua.pimenova.model.util.ReportBuilder;

import java.io.IOException;
import java.util.Locale;

public class PdfBillDownloadCommand implements ICommand {
    private final OrderService orderService;
    private static final Logger LOGGER = Logger.getLogger(PdfBillDownloadCommand.class);
    public PdfBillDownloadCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("order_id"));
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        try {
            Order order = orderService.getByID(id);
            if(order != null) {
                ReportBuilder reportBuilder = new ReportBuilder(locale);
                reportBuilder.billPdf(response, order);
            }
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
