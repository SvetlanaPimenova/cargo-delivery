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

class CreateOrderCommandTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    OrderService orderService;
    @Mock
    HttpSession session;
    @InjectMocks
    CreateOrderCommand command;
    private AutoCloseable closeable;
    private Order testOrder = new Order();
    private Freight freight = new Freight(1, 5.0, 10.0, 10.0, 10.0, 100, Freight.FreightType.GOODS);
    private Receiver receiver = new Receiver(1, "Ivan", "Ivanov", "+380111111111", "City", "Street", "Postal Code");
    private User sender = new User(1, "password", "Ivan", "Ivanov", "+380111111111", "email",
            0, User.Role.USER, "City", "Street", "Postal Code");

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
    public void createOrderTest() throws DaoException, ServletException, IOException {
        Mockito.when(req.getSession(false)).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(sender);
        mockingFreight();
        mockingReceiver();
        Mockito.when(req.getParameter("cityfrom")).thenReturn("City");
        Mockito.when(req.getParameter("deliverytype")).thenReturn("TO_THE_BRANCH");
        Mockito.when(orderService.create(testOrder)).thenReturn(testOrder);

        String result = command.execute(req, resp);
        assertEquals(Pages.CREATE_ORDER, result);
    }

    @Test
    public void ifUserIsNull() throws ServletException, IOException {
        Mockito.when(req.getSession(false)).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(null);

        String result = command.execute(req, resp);
        assertEquals(Pages.PAGE_ERROR, result);
    }

    private void mockingFreight() {
        Mockito.when(req.getParameter("freighttype")).thenReturn("GOODS");
        Mockito.when(req.getParameter("weight")).thenReturn("5.0");
        Mockito.when(req.getParameter("length")).thenReturn("10.0");
        Mockito.when(req.getParameter("width")).thenReturn("10.0");
        Mockito.when(req.getParameter("height")).thenReturn("10.0");
        Mockito.when(req.getParameter("cost")).thenReturn("0");
    }

    private void mockingReceiver() {
        Mockito.when(req.getParameter("rfname")).thenReturn("Ivan");
        Mockito.when(req.getParameter("rlname")).thenReturn("Ivanov");
        Mockito.when(req.getParameter("rphone")).thenReturn("+380111111111");
        Mockito.when(req.getParameter("cityto")).thenReturn("City");
        Mockito.when(req.getParameter("rstreet")).thenReturn("Street");
        Mockito.when(req.getParameter("rpcode")).thenReturn("Postal Code");
    }
}