package ua.pimenova.controller.command.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import ua.pimenova.model.util.ReportBuilder;
import java.util.Locale;

/**
 * PdfBillDownloadCommand class. Accessible by authorized user. Allows to download an invoice for lading
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class PdfBillDownloadCommand implements ICommand {
    private final OrderService orderService;
    private static final Logger LOGGER = Logger.getLogger(PdfBillDownloadCommand.class);

    /**
     * @param orderService - OrderService implementation to use in command
     */
    public PdfBillDownloadCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Obtains all info about order, transfer locale and response to build a pdf report
     *
     * @param request to get order id parameter and locale
     * @param response to transfer it to the report builder
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
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
