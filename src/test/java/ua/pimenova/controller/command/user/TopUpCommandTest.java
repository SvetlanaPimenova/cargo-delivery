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
import org.mockito.MockitoAnnotations;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.UserService;
import ua.pimenova.model.util.EmailSender;
import ua.pimenova.model.util.EncryptingUserPassword;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static ua.pimenova.controller.constants.Commands.*;

class TopUpCommandTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
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
        user.setPassword(EncryptingUserPassword.encryptPassword("Password1"));
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
    void testExecuteGet() throws ServletException, IOException {
        setGetRequest(request);
        String path = command.execute(request, response);

        assertEquals(ACCOUNT, path);
    }

    @Test
    void testExecutePost() throws ServletException, IOException, DaoException {
        setPostRequest(request);
        when(request.getContextPath()).thenReturn("delivery");
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("account")).thenReturn("100");
        when(userService.update(user)).thenReturn(true);

        EmailSender emailSender = mock(EmailSender.class);

        doNothing().when(emailSender).send(isA(String.class), isA(String.class), isA(String.class));

        String path = command.execute(request, response);

        assertEquals(100, user.getAccount());
        verify(session).setAttribute("url", ACCOUNT);
        assertEquals(request.getContextPath() + ACCOUNT, path);
    }

    @Test
    void testExceptionInPost() throws DaoException, ServletException, IOException {
        setPostRequest(request);
        when(request.getContextPath()).thenReturn("delivery");
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("account")).thenReturn("100");
        when(userService.update(user)).thenThrow(DaoException.class);

        String path = command.execute(request, response);

        verify(session).setAttribute("url", ERROR);
        assertEquals(request.getContextPath() + ERROR, path);
    }

    private void setPostRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("post");
        when(request.getSession(false)).thenReturn(session);
        when(request.getServletPath()).thenReturn("/top_up");
        doReturn(new StringBuffer("http://localhost:8080/delivery/top_up")).when(request).getRequestURL();
    }

    private void setGetRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("get");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(eq("url"))).thenReturn(ACCOUNT);
    }
}