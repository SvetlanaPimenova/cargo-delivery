package ua.pimenova.controller.command.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.entity.*;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DeleteOrderCommandTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    OrderService orderService;
    @Mock
    HttpSession session;
    @InjectMocks
    DeleteOrderCommand command;

    private AutoCloseable closeable;
    private Order testOrder = new Order();

    @BeforeEach
    public void setUp() {
        testOrder.setId(1);
        testOrder.setOrderDate(new Date());
        testOrder.setCityFrom("City");
        testOrder.setFreight(new Freight());
        testOrder.setTotalCost(100);
        testOrder.setDeliveryType(ExtraOptions.DeliveryType.TO_THE_BRANCH);
        testOrder.setReceiver(new Receiver());
        testOrder.setSender(new User());
        testOrder.setPaymentStatus(Order.PaymentStatus.UNPAID);
        testOrder.setExecutionStatus(Order.ExecutionStatus.IN_PROCESSING);

        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void close() throws Exception {
        closeable.close();
    }

    @Test
    public void deleteOrderTest() throws DaoException, ServletException, IOException {
        Mockito.when(req.getParameter("order_id")).thenReturn("1");
        Mockito.when(orderService.getByID(1)).thenReturn(testOrder);
        Mockito.when(orderService.delete(testOrder)).thenReturn(true);

        String result = command.execute(req, resp);
        assertEquals(Pages.ORDERS_LIST_PAGE, result);
    }

    @Test
    public void ifOrderIsNull() throws DaoException, ServletException, IOException {
        Mockito.when(req.getParameter("order_id")).thenReturn("1");
        Mockito.when(orderService.getByID(1)).thenReturn(null);

        String result = command.execute(req, resp);
        assertEquals(Pages.PAGE_ERROR, result);
    }

    @Test
    public void ifOrderIsFormed() throws DaoException, ServletException, IOException {
        Mockito.when(req.getParameter("order_id")).thenReturn("1");
        testOrder.setExecutionStatus(Order.ExecutionStatus.FORMED);
        Mockito.when(orderService.getByID(1)).thenReturn(testOrder);

        String result = command.execute(req, resp);
        assertEquals(Pages.PAGE_ERROR, result);
    }
}