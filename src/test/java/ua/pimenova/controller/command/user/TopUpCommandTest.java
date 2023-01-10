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
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;
import ua.pimenova.model.service.UserService;
import ua.pimenova.model.util.EncryptingUserPassword;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TopUpCommandTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    UserService userService;
    @Mock
    HttpSession session;
    @InjectMocks
    TopUpCommand command;
    private AutoCloseable closeable;
    User user = new User();
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
    public void topUpAccountTest() throws DaoException, ServletException, IOException {
        Mockito.when(req.getSession(false)).thenReturn(session);
        Mockito.when(session.getAttribute("user")).thenReturn(user);
        Mockito.when(req.getParameter("account")).thenReturn("100");
        Mockito.when(userService.update(user)).thenReturn(true);

        String result = command.execute(req, resp);
        assertEquals(Pages.ACCOUNT_PAGE, result);
    }
}