package ua.pimenova.model.util;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.pimenova.model.database.entity.Order;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY;

public class ReportBuilder {
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(ReportBuilder.class);
    private final Locale locale;

    public ReportBuilder(Locale locale) {
        this.locale = locale;
    }

    public void billPdf(HttpServletResponse response, Order order) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", locale);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument, PageSize.A4);
        PdfFont font = getFont();
        if (font != null) {
            document.setFont(font);
        }
        setDocumentContent(order, resourceBundle, document);
        document.close();
        openInBrowser(response, outputStream);
    }

    private void setDocumentContent(Order order, ResourceBundle resourceBundle, Document document) {
        String title = resourceBundle.getString("bill.title");
        document.add(getReportTitle(title));
        document.add(getHeader(order.getOrderDate(), resourceBundle));
        document.add(getEmptyLine());
        document.add(getSenderInfo(order, resourceBundle));
        document.add(getReceiverInfo(order, resourceBundle));
        document.add(getServiceTable(order, resourceBundle));
        document.add(getFooter(resourceBundle));
    }

    private Paragraph getFooter(ResourceBundle resourceBundle) {
        return new Paragraph(new Text(resourceBundle.getString("bill.issued"))).setTextAlignment(TextAlignment.RIGHT);
    }

    private Table getServiceTable(Order order, ResourceBundle resourceBundle) {
        Table table = new Table(new float[]{15f, 90f, 20f, 20f, 30f, 20f});
        table.setWidth(UnitValue.createPercentValue(95));
        table.setMarginBottom(25);
        table.setMarginTop(25);
        addServiceHeader(table, resourceBundle);
        addServiceRow(order, table, resourceBundle);
        return table;
    }

    private void addServiceRow(Order order, Table table, ResourceBundle resourceBundle) {
        String serviceInfo = resourceBundle.getString("delivery.service") + "\n"
                + resourceBundle.getString("table.shipment.date") + ": " + order.getOrderDate() + "\n"
                + resourceBundle.getString("calculator.label.from") + ": " + order.getCityFrom() + "\n"
                + resourceBundle.getString("calculator.label.delivery") + ": " + order.getDeliveryType() + "\n"
                + resourceBundle.getString("table.freight.info") + ":\n" + order.getFreight().toString();
        table.addCell("1");
        table.addCell(serviceInfo);
        table.addCell(resourceBundle.getString("table.service"));
        table.addCell("1");
        table.addCell("20%");
        table.addCell(String.valueOf(order.getTotalCost()));
    }

    private void addServiceHeader(Table table, ResourceBundle resourceBundle) {
        Stream.of("number.character", "bill.table.service", "bill.table.unit", "bill.table.quantity",
                        "bill.table.vat", "table.total.cost")
                .forEach(columnTitle -> {
                    Cell header = new Cell();
                    header.setBackgroundColor(LIGHT_GRAY);
                    header.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    header.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    header.add(new Paragraph(resourceBundle.getString(columnTitle)));
                    table.addCell(header);
                });
    }

    private Table getSenderInfo(Order order, ResourceBundle resourceBundle) {
        Cell header = new Cell();
        header.setBackgroundColor(LIGHT_GRAY);
        header.setHorizontalAlignment(HorizontalAlignment.LEFT);
        header.setVerticalAlignment(VerticalAlignment.MIDDLE);
        header.add(new Paragraph(resourceBundle.getString("option.sender")));
        return new Table(1)
                .addCell(header)
                .addCell(order.getSender().toString())
                .setWidth(UnitValue.createPercentValue(95));
    }

    private Table getReceiverInfo(Order order, ResourceBundle resourceBundle) {
        Cell header = new Cell();
        header.setBackgroundColor(LIGHT_GRAY);
        header.setHorizontalAlignment(HorizontalAlignment.LEFT);
        header.setVerticalAlignment(VerticalAlignment.MIDDLE);
        header.add(new Paragraph(resourceBundle.getString("table.receiver")));
        return new Table(1)
                .addCell(header)
                .addCell(order.getReceiver().toString())
                .setWidth(UnitValue.createPercentValue(95));
    }

    private Paragraph getHeader(Date orderDate, ResourceBundle resourceBundle) {
        Date paymentTerm = new Date(orderDate.getTime() + 14*24*60*60*1000);
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        return new Paragraph(new Text(resourceBundle.getString("bill.date") + " " + orderDate + "\n"
        + resourceBundle.getString("bill.term") + " " + formatDate.format(paymentTerm)))
                .setFontSize(11)
                .setTextAlignment(TextAlignment.RIGHT);
    }

    public void reportPdf(HttpServletResponse response, List<Order> list, String parameter) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", locale);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = getDocument(outputStream);
        String reportTitle = resourceBundle.getString("report.title") + ": " + parameter;
        document.add(getReportTitle(reportTitle));
        document.add(getTable(list, resourceBundle));
        document.close();
        openInBrowser(response, outputStream);
    }

    private void openInBrowser(HttpServletResponse response, ByteArrayOutputStream outputStream) {
        // setting some response headers
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // setting the content type
        response.setContentType("application/pdf");
        // the content length
        response.setContentLength(outputStream.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream os;
        try {
            os = response.getOutputStream();
            outputStream.writeTo(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private Document getDocument(ByteArrayOutputStream outputStream) {
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument, PageSize.A4.rotate());
        document.setMargins(10, 10, 10, 10);
        PdfFont font = getFont();
        if (font != null) {
            document.setFont(font);
        }
        return document;
    }

    private PdfFont getFont() {
        try {
            return PdfFontFactory.createFont("timesNewRoman.ttf");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private Paragraph getReportTitle(String title) {
        return new Paragraph(new Text(title.toUpperCase()))
                .setFontSize(14)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
    }

    private Paragraph getEmptyLine() {
        return new Paragraph(new Text("\n"));
    }

    private Table getTable(List<Order> list, ResourceBundle resourceBundle) {
        Table table = new Table(new float[]{7f, 30f, 20f, 20f, 40f, 15f, 40f, 35f, 40f, 25f, 40f});
        table.setMarginBottom(25);
        table.setMarginTop(25);
        addTableHeader(table, resourceBundle);
        addRows(list, table);
        return table;
    }

    private void addTableHeader(Table table, ResourceBundle resourceBundle) {
        Stream.of("number.character", "table.shipment.date", "calculator.label.from", "calculator.label.to",
                        "table.freight.info", "table.total.cost", "calculator.label.delivery", "option.sender",
                        "table.receiver", "option.filter.payment", "option.filter.execution")
                .forEach(columnTitle -> {
                    Cell header = new Cell();
                    header.setBackgroundColor(LIGHT_GRAY);
                    header.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    header.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    header.add(new Paragraph(resourceBundle.getString(columnTitle)));
                    table.addCell(header);
                });
    }

    private void addRows(List<Order> list, Table table) {
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(order -> {
                    table.addCell(String.valueOf(i.get())).setFontSize(11);
                    table.addCell(order.getOrderDate().toString()).setFontSize(11);
                    table.addCell(order.getCityFrom()).setFontSize(11);
                    table.addCell(order.getReceiver().getCity()).setFontSize(11);
                    table.addCell(order.getFreight().toString()).setFontSize(11);
                    table.addCell(String.valueOf(order.getTotalCost())).setFontSize(11);
                    table.addCell(order.getDeliveryType().toString()).setFontSize(11);
                    table.addCell(order.getSender().toString()).setFontSize(11);
                    table.addCell(order.getReceiver().toString()).setFontSize(11);
                    table.addCell(order.getPaymentStatus().toString()).setFontSize(11);
                    table.addCell(order.getExecutionStatus().toString()).setFontSize(11);
                    i.getAndIncrement();
                });
    }
}
