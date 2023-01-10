package ua.pimenova.controller.command.common;

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
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.UserService;
import ua.pimenova.model.util.EncryptingUserPassword;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SignupCommandTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    UserService userService;
    @Mock
    HttpSession session;
    @InjectMocks
    SignupCommand command;
    User user = new User();
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        user.setId(1);
        user.setPassword(EncryptingUserPassword.encryptPassword("pass"));
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhone("+380111111111");
        user.setEmail("user@gmail.com");
        user.setAccount(0);
        user.setRole(User.Role.USER);
        user.setCity("City");
        user.setStreet("Street");
        user.setPostalCode("Postal Code");

        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void close() throws Exception {
        closeable.close();
    }

    @Test
    public void ifEmailAndPasswordAreUnique() throws DaoException, ServletException, IOException {
        mockingUser();

        Mockito.when(userService.getByPhone("+380111111111")).thenReturn(null);
        Mockito.when(userService.getByEmail("user@gmail.com")).thenReturn(null);
        Mockito.when(userService.create(user)).thenReturn(user);
        Mockito.when(req.getSession(true)).thenReturn(session);

        String result = command.execute(req, resp);
        assertEquals(Pages.USER_PROFILE, result);
    }

    @Test
    public void ifPhoneIsUsed() throws DaoException, ServletException, IOException {
        mockingUser();

        Mockito.when(userService.getByPhone("+380111111111")).thenReturn(user);

        String result = command.execute(req, resp);
        assertEquals(Pages.SIGNUP_PAGE, result);
    }

    @Test
    public void ifEmailIsUsed() throws DaoException, ServletException, IOException {
        mockingUser();

        Mockito.when(userService.getByEmail("user@gmail.com")).thenReturn(user);

        String result = command.execute(req, resp);
        assertEquals(Pages.SIGNUP_PAGE, result);
    }

    private void mockingUser() {
        Mockito.when(req.getParameter("firstname")).thenReturn("Ivan");
        Mockito.when(req.getParameter("lastname")).thenReturn("Ivanov");
        Mockito.when(req.getParameter("email")).thenReturn("user@gmail.com");
        Mockito.when(req.getParameter("phone")).thenReturn("+380111111111");
        Mockito.when(req.getParameter("city")).thenReturn("City");
        Mockito.when(req.getParameter("street")).thenReturn("Street");
        Mockito.when(req.getParameter("postalcode")).thenReturn("Postal Code");
        Mockito.when(req.getParameter("password")).thenReturn("pass");
    }
}