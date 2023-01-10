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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginCommandTest {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;
    @Mock
    UserService userService;
    @Mock
    HttpSession session;
    @InjectMocks
    LoginCommand command;

    User user = new User();
    User manager = new User();
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        user.setEmail("user@gmail.com");
        user.setPassword(EncryptingUserPassword.encryptPassword("pass"));
        user.setRole(User.Role.USER);

        manager.setEmail("manager@gmail.com");
        manager.setPassword(EncryptingUserPassword.encryptPassword("pass"));
        manager.setRole(User.Role.MANAGER);

        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void close() throws Exception {
        closeable.close();
    }

    @Test
    public void ifUserIsUser() throws DaoException, ServletException, IOException {
        Mockito.when(req.getParameter("emaillogin")).thenReturn("user@gmail.com");
        Mockito.when(req.getParameter("passlogin")).thenReturn("pass");
        Mockito.when(userService.getUserByEmailAndPassword("user@gmail.com", "pass")).thenReturn(user);
        Mockito.when(req.getSession(false)).thenReturn(session);

        String result = command.execute(req, resp);
        assertEquals(Pages.USER_PROFILE, result);
    }

    @Test
    public void ifUserIsAdmin() throws DaoException, ServletException, IOException {
        Mockito.when(req.getParameter("emaillogin")).thenReturn("manager@gmail.com");
        Mockito.when(req.getParameter("passlogin")).thenReturn("pass");
        Mockito.when(userService.getUserByEmailAndPassword("manager@gmail.com", "pass")).thenReturn(manager);
        Mockito.when(req.getSession(false)).thenReturn(session);

        String result = command.execute(req, resp);
        assertEquals(Pages.MANAGER_PROFILE, result);
    }
    @Test
    public void ifUserIsBlank() throws ServletException, IOException, DaoException {
        Mockito.when(req.getParameter("emaillogin")).thenReturn(null);
        Mockito.when(req.getParameter("passlogin")).thenReturn(null);
        Mockito.when(userService.getUserByEmailAndPassword(null, null)).thenReturn(null);
        Mockito.when(req.getSession()).thenReturn(session);
        String result = command.execute(req, resp);
        assertEquals(Pages.PAGE_ERROR, result);
    }

    @Test
    public void ifPasswordOrEmailIsIncorrect() throws DaoException, ServletException, IOException {
        Mockito.when(req.getParameter("emaillogin")).thenReturn("admin@mail.com");
        Mockito.when(req.getParameter("passlogin")).thenReturn("1234");
        Mockito.when(userService.getUserByEmailAndPassword("admin@mail.com", "1234")).thenReturn(null);
        Mockito.when(req.getSession()).thenReturn(session);

        String result = command.execute(req, resp);
        assertEquals(Pages.PAGE_ERROR, result);
    }

}
