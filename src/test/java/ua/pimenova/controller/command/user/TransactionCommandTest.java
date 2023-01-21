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
import ua.pimenova.model.service.UserService;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.pimenova.controller.constants.Commands.*;

class TransactionCommandTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    OrderService orderService;
    @Mock
    UserService userService;
    @Mock
    HttpSession session;
    @InjectMocks
    TransactionCommand command;

    private AutoCloseable closeable;
    private Order testOrder = new Order();
    private Freight freight = new Freight(1, 5.0, 10.0, 10.0, 10.0, 100, Freight.FreightType.GOODS);
    private Receiver receiver = new Receiver(1, "Ivan", "Ivanov", "+380111111111", "City", "Street", "Postal Code");
    private User sender = new User(1, "password", "Ivan", "Ivanov", "+380111111111", "email",
            500, User.Role.USER, "City", "Street", "Postal Code");
    @BeforeEach
    public void setUp() {
        testOrder.setId(1);
        testOrder.setOrderDate(new Date());
        testOrder.setCityFrom("City");
        testOrder.setFreight(freight);
        testOrder.setTotalCost(100);
        testOrder.setDeliveryType(ExtraOptions.DeliveryType.TO_THE_BRANCH);
        testOrder.setReceiver(receiver);
        testOrder.setSender(sender);
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
        when(request.getAttribute("order_id")).thenReturn("1");
        when(request.getAttribute("isUpdated")).thenReturn("true");

        String path = command.execute(request, response);

        verify(request).setAttribute(eq("order_id"), eq("1"));
        verify(session).removeAttribute(eq("order_id"));
        verify(request).setAttribute(eq("isUpdated"), eq("true"));
        verify(session).removeAttribute(eq("isUpdated"));

        String expectedPath = SHOW_PAGE_UPDATE_ORDER + "?order_id=1&isUpdated=true";
        assertEquals(expectedPath, path);
    }

    @Test
    void testExecutePost() throws DaoException, ServletException, IOException {
        setPostRequest(request);
        when(orderService.getByID(1)).thenReturn(testOrder);
        when(userService.update(sender)).thenReturn(true);
        when(orderService.update(testOrder)).thenReturn(true);

        String path = command.execute(request, response);

        verify(session).setAttribute("isUpdated", "true");
        verify(session).setAttribute("currentOrder", testOrder);
        verify(session).setAttribute("order_id", "1");
        verify(session).setAttribute("url", SHOW_PAGE_UPDATE_ORDER);
        assertEquals(request.getContextPath() + TRANSACTION, path);
    }

    @Test
    public void notEnoughFunds() throws DaoException, ServletException, IOException {
        setPostRequest(request);
        sender.setAccount(0);
        when(orderService.getByID(1)).thenReturn(testOrder);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("locale")).thenReturn(new Locale("en"));

        String path = command.execute(request, response);

        verify(session).setAttribute("errorMessage", "Your account does not have enough funds for debiting. Please top up your account!");
        verify(session).setAttribute("url", ERROR);
        assertEquals(request.getContextPath() + ERROR, path);
    }

    private void setPostRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("post");
        when(request.getContextPath()).thenReturn("delivery");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(sender);
        when(request.getParameter("order_id")).thenReturn("1");
    }

    private void setGetRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("get");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("order_id")).thenReturn("1");
        when(session.getAttribute("isUpdated")).thenReturn("true");
        when(session.getAttribute(eq("url"))).thenReturn(SHOW_PAGE_UPDATE_ORDER);
        when(session.getAttribute("errorMessage")).thenReturn(null);
    }
}