package ua.pimenova.model.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.pimenova.model.database.dao.UserDao;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.util.EncryptingUserPassword;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceImplTest {
    @Mock
    UserDao userDao;

    private User testUser = new User();
    List<User> testList = new ArrayList<>();
    private AutoCloseable closeable;

    @InjectMocks
    UserServiceImpl impl;

    @BeforeEach
    public void setUp() {
        testUser.setId(1);
        testUser.setPassword("password");
        testUser.setFirstname("Ivan");
        testUser.setLastname("Ivanov");
        testUser.setPhone("+380111111111");
        testUser.setEmail("email");
        testUser.setAccount(0);
        testUser.setRole(User.Role.USER);
        testUser.setCity("City");
        testUser.setStreet("Street");
        testUser.setPostalCode("Postal Code");
        testList.add(testUser);

        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void close() throws Exception {
        closeable.close();
    }

    @Test
    public void getByIdTest() throws DaoException {
        Mockito.when(userDao.getByID(1)).thenReturn(testUser);
        assertEquals(testUser, impl.getByID(1));
    }

    @Test
    public void getAllTest() throws DaoException {
        Mockito.when(userDao.getAll()).thenReturn(testList);
        assertEquals(testList, impl.getAll());
    }

    @Test
    public void createTest() throws DaoException {
        Mockito.when(userDao.create(testUser)).thenReturn(testUser);
        assertNotNull(impl.create(testUser));
    }

    @Test
    public void updateTest() throws DaoException {
        Mockito.when(userDao.update(testUser)).thenReturn(true);
        assertTrue(impl.update(testUser));
    }

    @Test
    public void deleteTest() throws DaoException {
        Mockito.when(userDao.delete(testUser)).thenReturn(true);
        assertTrue(impl.delete(testUser));
    }

    @Test
    public void updatePassword() throws DaoException {
        Mockito.when(userDao.updatePassword(testUser)).thenReturn(true);
        assertTrue(impl.updatePassword(testUser));
    }

    @Test
    public void getByPhone() throws DaoException {
        Mockito.when(userDao.getByPhone(testUser.getPhone())).thenReturn(testUser);
        assertEquals(testUser, impl.getByPhone("+380111111111"));
    }

    @Test
    public void getByEmail() throws DaoException {
        Mockito.when(userDao.getByEmail(testUser.getEmail())).thenReturn(testUser);
        assertEquals(testUser, impl.getByEmail("email"));
    }

    @Test
    public void getUserByEmailAndPassword() throws DaoException {
        Mockito.when(userDao.getUserByEmailAndPassword(testUser.getEmail(), EncryptingUserPassword.encryptPassword(testUser.getPassword()))).thenReturn(testUser);
        assertEquals(testUser, impl.getUserByEmailAndPassword("email", "password"));
    }
}
