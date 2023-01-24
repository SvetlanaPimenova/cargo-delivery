package ua.pimenova.controller.command.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.exception.IncorrectFormatException;
import ua.pimenova.model.service.UserService;
import ua.pimenova.model.util.EmailSender;

import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.pimenova.controller.constants.Commands.*;

class SignupCommandTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    UserService userService;
    @Mock
    HttpSession session;
    @InjectMocks
    SignupCommand command;
    User user = new User(1, "Password1", "Ivan", "Ivanov", "+380111111111", "user@gmail.com",
            0, User.Role.USER, "City", "Street", "Postal Code");
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        when(request.getSession()).thenReturn(session);
    }

    @AfterEach
    public void close() throws Exception {
        closeable.close();
    }

    @Test
    void testExecuteGet() throws ServletException, IOException {
        setGetRequest(request);

        String path = command.execute(request, response);

        assertEquals(SHOW_SIGNUP_PAGE, path);
        verify(request).setAttribute(eq("errorMessage"), eq("error"));
        verify(session).removeAttribute(eq("errorMessage"));
        assertEquals(user, session.getAttribute("user"));
    }

    @Test
    void testSuccessfulExecutePost() throws IncorrectFormatException, DaoException, ServletException, IOException {
        setPostRequest(request);
        mockingUser();

        when(session.getAttribute("locale")).thenReturn(new Locale("en"));

        EmailSender emailSender = mock(EmailSender.class);

        doNothing().when(emailSender).send(isA(String.class), isA(String.class), isA(String.class));

        doReturn(user).when(userService).create(user);
        when(request.getContextPath()).thenReturn("/delivery/");

        String path = command.execute(request, response);

        verify(session).setAttribute("userRole", user.getRole());
        verify(session).setAttribute("url", PROFILE);
        assertEquals(request.getContextPath() + SIGN_UP, path);
    }

    @Test
    void testBadExecutePost() throws IncorrectFormatException, DaoException, ServletException, IOException {
        setPostRequest(request);
        mockingUser();
        when(request.getParameter("reppass")).thenReturn("Password2");
        when(session.getAttribute("locale")).thenReturn(new Locale("en")).thenReturn(new Locale("en"));
        when(request.getContextPath()).thenReturn("/delivery/");

        String path = command.execute(request, response);

        verify(session).setAttribute("errorMessage", "Error: Fields 'Password' and 'Repeat password' must match");
        verify(session).setAttribute("url", SHOW_SIGNUP_PAGE);
        assertEquals(request.getContextPath() + SIGN_UP, path);
    }

    private void setPostRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("post");
        when(request.getServletPath()).thenReturn("/createOrder");
        doReturn(new StringBuffer("http://localhost:8080/delivery/createOrder")).when(request).getRequestURL();
    }

    private void setGetRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("get");
        when(session.getAttribute(eq("errorMessage"))).thenReturn(new IncorrectFormatException("error").getMessage());
        when(session.getAttribute(eq("user"))).thenReturn(user);
        when(session.getAttribute(eq("url"))).thenReturn(SHOW_SIGNUP_PAGE);
    }

    private void mockingUser() {
        when(request.getParameter("firstname")).thenReturn("Ivan");
        when(request.getParameter("lastname")).thenReturn("Ivanov");
        when(request.getParameter("email")).thenReturn("user@gmail.com");
        when(request.getParameter("phone")).thenReturn("+380111111111");
        when(request.getParameter("city")).thenReturn("City");
        when(request.getParameter("street")).thenReturn("Street");
        when(request.getParameter("postalcode")).thenReturn("Postal Code");
        when(request.getParameter("password")).thenReturn("Password1");
        when(request.getParameter("reppass")).thenReturn("Password1");
    }
}