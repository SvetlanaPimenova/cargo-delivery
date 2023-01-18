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
import ua.pimenova.model.util.UserValidator;

import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.pimenova.controller.constants.Commands.*;

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
    @Mock
    UserValidator validator = new UserValidator(userService);
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        when(req.getSession()).thenReturn(session);
    }

    @AfterEach
    public void close() throws Exception {
        closeable.close();
    }

    @Test
    void testExecuteGet() throws ServletException, IOException {
        setGetRequest(req);

        String path = command.execute(req, resp);

        assertEquals(SHOW_SIGNUP_PAGE, path);
        verify(req).setAttribute(eq("errorMessage"), eq("error"));
        verify(session).removeAttribute(eq("errorMessage"));
        assertEquals(user, session.getAttribute("user"));
    }

    @Test
    void testSuccessfulExecutePost() throws IncorrectFormatException, DaoException, ServletException, IOException {
        setPostRequest(req);

//        when(userService.create(user)).thenReturn(user);
//        when(session.getAttribute(eq("user"))).thenReturn(user);
//        when(session.getAttribute(eq("userRole"))).thenReturn(user.getRole());
//        when(session.getAttribute(eq("url"))).thenReturn(PROFILE);

        doNothing().when(validator).validate(isA(User.class), isA(HttpServletRequest.class));
        when(userService.create(user)).thenReturn(user);
        when(req.getContextPath()).thenReturn("/delivery/");

        String path = command.execute(req, resp);

//        assertEquals(session.getAttribute("user"), user);
//        assertEquals(session.getAttribute("userRole"), user.getRole());
//        assertEquals(session.getAttribute("url"), PROFILE);
        verify(session).setAttribute(eq("user"), eq(user));
        verify(session).setAttribute(eq("userRole"), eq(user.getRole()));
        verify(session).setAttribute(eq("url"), eq(PROFILE));
        assertEquals(req.getContextPath() + SIGN_UP, path);
    }

    private void setPostRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("post");
        mockingUser();
    }

    private void setGetRequest(HttpServletRequest request) {
        when(request.getMethod()).thenReturn("get");
        when(session.getAttribute(eq("errorMessage"))).thenReturn(new IncorrectFormatException("error").getMessage());
        when(session.getAttribute(eq("user"))).thenReturn(user);
        when(session.getAttribute(eq("url"))).thenReturn(SHOW_SIGNUP_PAGE);
    }

    private void mockingUser() {
        when(req.getParameter("firstname")).thenReturn("Ivan");
        when(req.getParameter("lastname")).thenReturn("Ivanov");
        when(req.getParameter("email")).thenReturn("user@gmail.com");
        when(req.getParameter("phone")).thenReturn("+380111111111");
        when(req.getParameter("city")).thenReturn("City");
        when(req.getParameter("street")).thenReturn("Street");
        when(req.getParameter("postalcode")).thenReturn("Postal Code");
        when(req.getParameter("password")).thenReturn("Password1");
        when(req.getParameter("reppass")).thenReturn("Password1");
    }
}