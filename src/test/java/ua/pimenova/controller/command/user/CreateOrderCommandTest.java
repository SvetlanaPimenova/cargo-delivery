package ua.pimenova.controller.command.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ua.pimenova.model.database.entity.*;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.exception.IncorrectFormatException;
import ua.pimenova.model.service.OrderService;
import ua.pimenova.model.util.validator.ReceiverValidator;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.pimenova.controller.constants.Commands.SHOW_PAGE_CREATE_ORDER;

class CreateOrderCommandTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
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
    public void createOrderTest() throws DaoException, ServletException, IOException, IncorrectFormatException {
        setPostRequest(request);
        mockingFreight();
        mockingReceiver();
        when(request.getParameter("cityfrom")).thenReturn("City");
        when(request.getParameter("deliverytype")).thenReturn("TO_THE_BRANCH");

        when(session.getAttribute("locale")).thenReturn(new Locale("en"));
        doReturn(testOrder).when(orderService).create(isA(Order.class));

        String path = command.execute(request, response);

        verify(session).setAttribute("newOrder", testOrder);
        verify(session).setAttribute("url", SHOW_PAGE_CREATE_ORDER);
        assertEquals(request.getContextPath() + SHOW_PAGE_CREATE_ORDER, path);
    }

    @Test
    void testIncorrectReceiver() throws IncorrectFormatException, DaoException, ServletException, IOException {
        setPostRequest(request);
        mockingFreight();
        mockingReceiver();
        when(request.getParameter("cityfrom")).thenReturn("City");
        when(request.getParameter("deliverytype")).thenReturn("TO_THE_BRANCH");
        when(request.getParameter("rfname")).thenReturn("123");
        when(request.getContextPath()).thenReturn("/delivery/");

        String path = command.execute(request, response);

        verify(session).setAttribute("errorMessage", "Error: Name does not match");
        verify(session).setAttribute("url", SHOW_PAGE_CREATE_ORDER);
        assertEquals(request.getContextPath() + SHOW_PAGE_CREATE_ORDER, path);

    }

    @Test
    void testExecuteGet() throws ServletException, IOException {
        setGetRequest(request);

        String path = command.execute(request, response);

        assertEquals(SHOW_PAGE_CREATE_ORDER, path);
    }

    private void setPostRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("post");
        when(request.getSession(false)).thenReturn(session);
        when(request.getContextPath()).thenReturn("delivery");
        when(session.getAttribute("user")).thenReturn(sender);
    }

    private void setGetRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("get");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("url")).thenReturn(SHOW_PAGE_CREATE_ORDER);
    }

    private void mockingFreight() {
        Mockito.when(request.getParameter("freighttype")).thenReturn("GOODS");
        Mockito.when(request.getParameter("weight")).thenReturn("5.0");
        Mockito.when(request.getParameter("length")).thenReturn("10.0");
        Mockito.when(request.getParameter("width")).thenReturn("10.0");
        Mockito.when(request.getParameter("height")).thenReturn("10.0");
        Mockito.when(request.getParameter("cost")).thenReturn("0");
    }

    private void mockingReceiver() {
        Mockito.when(request.getParameter("rfname")).thenReturn("Ivan");
        Mockito.when(request.getParameter("rlname")).thenReturn("Ivanov");
        Mockito.when(request.getParameter("rphone")).thenReturn("+380111111111");
        Mockito.when(request.getParameter("cityto")).thenReturn("City");
        Mockito.when(request.getParameter("rstreet")).thenReturn("Street");
        Mockito.when(request.getParameter("rpcode")).thenReturn("Postal Code");
    }
}