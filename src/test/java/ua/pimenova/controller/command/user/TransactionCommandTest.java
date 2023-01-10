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
import ua.pimenova.model.service.UserService;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TransactionCommandTest {

    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
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
    public void transactionTest() throws DaoException, ServletException, IOException {
        Mockito.when(req.getSession(false)).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(sender);
        Mockito.when(req.getParameter("order_id")).thenReturn("1");
        Mockito.when(orderService.getByID(1)).thenReturn(testOrder);
        Mockito.when(userService.update(sender)).thenReturn(true);
        Mockito.when(orderService.update(testOrder)).thenReturn(true);

        String result = command.execute(req, resp);
        assertEquals(Pages.UPDATE_ORDER_PAGE, result);
    }

    @Test
    public void notEnoughFunds() throws DaoException, ServletException, IOException {
        sender.setAccount(0);
        Mockito.when(req.getSession(false)).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(sender);
        Mockito.when(req.getParameter("order_id")).thenReturn("1");
        Mockito.when(orderService.getByID(1)).thenReturn(testOrder);

        String result = command.execute(req, resp);
        assertEquals(Pages.PAGE_ERROR, result);
    }
}