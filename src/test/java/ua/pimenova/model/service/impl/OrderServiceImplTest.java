package ua.pimenova.model.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.pimenova.model.database.dao.OrderDao;
import ua.pimenova.model.database.entity.*;
import ua.pimenova.model.exception.DaoException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceImplTest {
    @Mock
    OrderDao orderDao;

    private Order testOrder = new Order();
    private Freight freight = new Freight(1, 5.0, 10.0, 10.0, 10.0, 100, Freight.FreightType.GOODS);
    private Receiver receiver = new Receiver(1, "Ivan", "Ivanov", "+380111111111", "City", "Street", "Postal Code");
    private User sender = new User(1, "password", "Ivan", "Ivanov", "+380111111111", "email",
            0, User.Role.USER, "City", "Street", "Postal Code");
    List<Order> testList = new ArrayList<>();
    private AutoCloseable closeable;

    @InjectMocks
    OrderServiceImpl impl;

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
        testList.add(testOrder);

        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void close() throws Exception {
        closeable.close();
    }

    @Test
    void getByIDTest() throws DaoException {
        Mockito.when(orderDao.getByID(1)).thenReturn(testOrder);
        assertEquals(testOrder, impl.getByID(1));
    }

    @Test
    void getAllTest() throws DaoException {
        Mockito.when(orderDao.getAll()).thenReturn(testList);
        assertEquals(testList, impl.getAll());
    }

    @Test
    void createTest() throws DaoException {
        Mockito.when(orderDao.create(testOrder)).thenReturn(testOrder);
        assertNotNull(impl.create(testOrder));
    }

    @Test
    void update() throws DaoException {
        Mockito.when(orderDao.update(testOrder)).thenReturn(true);
        assertTrue(impl.update(testOrder));
    }

    @Test
    void delete() throws DaoException {
        Mockito.when(orderDao.delete(testOrder)).thenReturn(true);
        assertTrue(impl.delete(testOrder));
    }

    @Test
    void getAllOrdersByDate() throws DaoException {
        Mockito.when(orderDao.getAllOrdersByDate(testOrder.getOrderDate())).thenReturn(testList);
        assertEquals(testList, impl.getAllOrdersByDate(testOrder.getOrderDate()));
    }

    @Test
    void getAllOrdersByReceiver() throws DaoException {
        Mockito.when(orderDao.getAllOrdersByReceiver(testOrder.getReceiver())).thenReturn(testList);
        assertEquals(testList, impl.getAllOrdersByReceiver(receiver));
    }

    @Test
    void getAllOrdersBySender() throws DaoException {
        Mockito.when(orderDao.getAllOrdersBySender(testOrder.getSender())).thenReturn(testList);
        assertEquals(testList, impl.getAllOrdersBySender(sender));
    }

    @Test
    void getAllOrdersByCityFrom() throws DaoException {
        Mockito.when(orderDao.getAllOrdersByCityFrom(testOrder.getCityFrom())).thenReturn(testList);
        assertEquals(testList, impl.getAllOrdersByCityFrom("City"));
    }

    @Test
    void getNumberOfRows() throws DaoException {
        Mockito.when(orderDao.getNumberOfRows("")).thenReturn(1);
        assertEquals(testList.size(), impl.getNumberOfRows(""));
    }
}
