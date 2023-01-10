package ua.pimenova.model.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.pimenova.model.database.dao.FreightDao;
import ua.pimenova.model.database.entity.Freight;
import ua.pimenova.model.exception.DaoException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FreightServiceImplTest {
    @Mock
    FreightDao freightDao;

    private Freight testFreight = new Freight();
    List<Freight> testList = new ArrayList<>();
    private AutoCloseable closeable;

    @InjectMocks
    FreightServiceImpl impl;

    @BeforeEach
    public void setUp() {
        testFreight.setId(1);
        testFreight.setWeight(5.0);
        testFreight.setLength(10.0);
        testFreight.setWidth(10.0);
        testFreight.setHeight(10.0);
        testFreight.setEstimatedCost(100);
        testFreight.setType(Freight.FreightType.GOODS);
        testList.add(testFreight);

        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void close() throws Exception {
        closeable.close();
    }

    @Test
    public void getByIdTest() throws DaoException {
        Mockito.when(freightDao.getByID(1)).thenReturn(testFreight);
        assertEquals(testFreight, impl.getByID(1));
    }

    @Test
    public void getAllTest() throws DaoException {
        Mockito.when(freightDao.getAll()).thenReturn(testList);
        assertEquals(testList, impl.getAll());
    }

    @Test
    public void createTest() throws DaoException {
        Mockito.when(freightDao.create(testFreight)).thenReturn(testFreight);
        assertNotNull(impl.create(testFreight));
    }

    @Test
    public void updateTest() throws DaoException {
        Mockito.when(freightDao.update(testFreight)).thenReturn(true);
        assertTrue(impl.update(testFreight));
    }

    @Test
    public void deleteTest() throws DaoException {
        Mockito.when(freightDao.delete(testFreight)).thenReturn(true);
        assertTrue(impl.delete(testFreight));
    }

    @Test
    public void getFreightByTypeTest() throws DaoException {
        Mockito.when(freightDao.getAllFreightsByType(Freight.FreightType.GOODS)).thenReturn(testList);
        assertEquals(testList, impl.getAllFreightsByType(Freight.FreightType.GOODS));
    }
}
