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
import org.mockito.MockitoAnnotations;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.UserService;
import ua.pimenova.model.util.EncryptingUserPassword;

import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ua.pimenova.controller.constants.Commands.*;


public class LoginCommandTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
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
    void testExecuteGet() throws ServletException, IOException {
        setGetRequest(request);

        String path = command.execute(request, response);

        verify(request).setAttribute(eq("errorMessage"), eq("Either username or password is wrong."));
        verify(session).removeAttribute(eq("errorMessage"));
        assertEquals(ERROR, path);
    }

    @Test
    void testExecutePost() throws DaoException, ServletException, IOException {
        setPostRequest(request);
        when(request.getParameter("emaillogin")).thenReturn("user@gmail.com");
        when(request.getParameter("passlogin")).thenReturn("pass");
        when(userService.getUserByEmailAndPassword("user@gmail.com", "pass")).thenReturn(user);

        String path = command.execute(request, response);

        verify(session).setAttribute("user", user);
        verify(session).setAttribute("userRole", user.getRole());
        verify(session).setAttribute("url", PROFILE);

        assertEquals(request.getContextPath() + PROFILE, path);
    }

    @Test
    void testIfPasswordOrEmailIsIncorrect() throws DaoException, ServletException, IOException {
        setPostRequest(request);
        when(request.getParameter("emaillogin")).thenReturn("admin@mail.com");
        when(request.getParameter("passlogin")).thenReturn("1234");
        when(userService.getUserByEmailAndPassword("admin@mail.com", "1234")).thenReturn(null);

        String path = command.execute(request, response);

        verify(session).setAttribute("errorMessage", "Either username or password is wrong.");
        assertEquals(request.getContextPath() + ERROR, path);
    }


    private void setPostRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("post");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("delivery");
        when(session.getAttribute("locale")).thenReturn(new Locale("en"));
    }

    private void setGetRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("get");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("errorMessage")).thenReturn("Either username or password is wrong.");
        when(session.getAttribute("url")).thenReturn(ERROR);
    }

}
