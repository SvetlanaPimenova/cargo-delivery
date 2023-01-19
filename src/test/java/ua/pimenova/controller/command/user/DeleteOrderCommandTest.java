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
import org.mockito.MockitoAnnotations;
import ua.pimenova.model.database.entity.*;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.pimenova.controller.constants.Commands.*;

class DeleteOrderCommandTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
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
    void testExecuteGet() throws ServletException, IOException {
        setGetRequest(request);

        String path = command.execute(request, response);

        assertEquals(ERROR, path);
        verify(request).setAttribute(eq("errorMessage"), eq("error"));
        verify(session).removeAttribute(eq("errorMessage"));
    }

    @Test
    void testExecuteSuccessfulPost() throws DaoException, ServletException, IOException {
        setPostRequest(request);
        when(request.getParameter("order_id")).thenReturn("1");
        when(orderService.getByID(1)).thenReturn(testOrder);
        when(orderService.delete(testOrder)).thenReturn(true);

        String path = command.execute(request, response);

        verify(session).setAttribute("url", GET_ORDERS);
        assertEquals(request.getContextPath() + GET_ORDERS, path);
    }

    @Test
    void testOrderIsFormed() throws DaoException, ServletException, IOException {
        setPostRequest(request);
        when(session.getAttribute("locale")).thenReturn(new Locale("en"));
        when(request.getParameter("order_id")).thenReturn("1");
        testOrder.setExecutionStatus(Order.ExecutionStatus.FORMED);
        when(orderService.getByID(1)).thenReturn(testOrder);

        String path = command.execute(request, response);

        verify(session).setAttribute("errorMessage", "You cannot delete a shipment if it has already been formed.");
        verify(session).setAttribute("url", ERROR);
        assertEquals(request.getContextPath() + ERROR, path);
    }

    private void setGetRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("get");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(eq("url"))).thenReturn(ERROR);
        when(session.getAttribute("errorMessage")).thenReturn("error");
    }

    private void setPostRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("post");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("delivery");
    }
}