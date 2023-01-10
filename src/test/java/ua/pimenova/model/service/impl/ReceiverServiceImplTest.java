package ua.pimenova.model.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.pimenova.model.database.dao.ReceiverDao;
import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.exception.DaoException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReceiverServiceImplTest {
    @Mock
    ReceiverDao receiverDao;

    private Receiver testReceiver = new Receiver();
    List<Receiver> testList = new ArrayList<>();
    private AutoCloseable closeable;

    @InjectMocks
    ReceiverServiceImpl impl;

    @BeforeEach
    public void setUp() {
        testReceiver.setId(1);
        testReceiver.setFirstname("Ivan");
        testReceiver.setLastname("Ivanov");
        testReceiver.setPhone("+380111111111");
        testReceiver.setCity("City");
        testReceiver.setStreet("Street");
        testReceiver.setPostal_code("Postal Code");
        testList.add(testReceiver);

        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void close() throws Exception {
        closeable.close();
    }

    @Test
    public void getByIdTest() throws DaoException {
        Mockito.when(receiverDao.getByID(1)).thenReturn(testReceiver);
        assertEquals(testReceiver, impl.getByID(1));
    }

    @Test
    public void getAllTest() throws DaoException {
        Mockito.when(receiverDao.getAll()).thenReturn(testList);
        assertEquals(testList, impl.getAll());
    }

    @Test
    public void createTest() throws DaoException {
        Mockito.when(receiverDao.create(testReceiver)).thenReturn(testReceiver);
        assertNotNull(impl.create(testReceiver));
    }

    @Test
    public void updateTest() throws DaoException {
        Mockito.when(receiverDao.update(testReceiver)).thenReturn(true);
        assertTrue(impl.update(testReceiver));
    }

    @Test
    public void deleteTest() throws DaoException {
        Mockito.when(receiverDao.delete(testReceiver)).thenReturn(true);
        assertTrue(impl.delete(testReceiver));
    }

    @Test
    public void getByPhoneTest() throws DaoException {
        Mockito.when(receiverDao.getByPhone(testReceiver.getPhone())).thenReturn(testReceiver);
        assertEquals(testReceiver, impl.getByPhone("+380111111111"));
    }

    @Test
    void getAllReceiversByCityTest() throws DaoException {
        Mockito.when(receiverDao.getAllReceiversByCity(testReceiver.getCity())).thenReturn(testList);
        assertEquals(testList, impl.getAllReceiversByCity("City"));
    }
}
