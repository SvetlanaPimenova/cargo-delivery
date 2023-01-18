package ua.pimenova.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.entity.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityFilterTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    FilterConfig filterConfig = mock(FilterConfig.class);
    FilterChain chain = mock(FilterChain.class);
    HttpSession session = mock(HttpSession.class);

    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    private static final String USER = "createOrder orders pageCreate deleteOrder updateOrder_user update_page account top_up transaction bill_pdf";
    private static final String MANAGER = "reports packages updateStatus updateShipment_page";
    private static final String COMMON = "calculate login logout signup home profile update signup_page pdf error";

    @BeforeEach
    void setDefault() {
        when(filterConfig.getInitParameter("manager")).thenReturn(MANAGER);
        when(filterConfig.getInitParameter("user")).thenReturn(USER);
        when(filterConfig.getInitParameter("common")).thenReturn(COMMON);
        when(request.getSession()).thenReturn(session);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/managerCommands.csv")
    void testAccessAllowedToManagerCommands(String command) throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/delivery/" + command);
        when(session.getAttribute("userRole")).thenReturn(User.Role.MANAGER);
        SecurityFilter filter = new SecurityFilter();
        filter.init(filterConfig);
        doNothing().when(chain).doFilter(request, response);
        filter.doFilter(request, response, chain);
        verify(request, never()).setAttribute("errorMessage", "You do not have permission to access the requested resource");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/managerCommands.csv")
    void testAccessDeniedToManagerCommands(String command) throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/delivery/" + command);
        when(session.getAttribute("userRole")).thenReturn(User.Role.USER);
        SecurityFilter filter = new SecurityFilter();
        filter.init(filterConfig);
        when(request.getRequestDispatcher(Pages.PAGE_ERROR)).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);
        filter.doFilter(request, response, chain);
        verify(request).setAttribute("errorMessage", "You do not have permission to access the requested resource");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/userCommands.csv")
    void testAccessAllowedToUserCommands(String command) throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/delivery/" + command);
        when(session.getAttribute("userRole")).thenReturn(User.Role.USER);
        SecurityFilter filter = new SecurityFilter();
        filter.init(filterConfig);
        doNothing().when(chain).doFilter(request, response);
        filter.doFilter(request, response, chain);
        verify(request, never()).setAttribute("errorMessage", "You do not have permission to access the requested resource");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/userCommands.csv")
    void testAccessDeniedToUserCommands(String command) throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/delivery/" + command);
        when(session.getAttribute("userRole")).thenReturn(User.Role.MANAGER);
        SecurityFilter filter = new SecurityFilter();
        filter.init(filterConfig);
        when(request.getRequestDispatcher(Pages.PAGE_ERROR)).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);
        filter.doFilter(request, response, chain);
        verify(request).setAttribute("errorMessage", "You do not have permission to access the requested resource");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/commonCommands.csv")
    void testAccessAllowedToCommonCommandsUser(String command) throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/delivery/" + command);
        when(session.getAttribute("userRole")).thenReturn(User.Role.USER);
        SecurityFilter filter = new SecurityFilter();
        filter.init(filterConfig);
        doNothing().when(chain).doFilter(request, response);
        filter.doFilter(request, response, chain);
        verify(request, never()).setAttribute("errorMessage", "You do not have permission to access the requested resource");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/commonCommands.csv")
    void testAccessAllowedToCommonCommandsManager(String command) throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/delivery/" + command);
        when(session.getAttribute("userRole")).thenReturn(User.Role.MANAGER);
        SecurityFilter filter = new SecurityFilter();
        filter.init(filterConfig);
        doNothing().when(chain).doFilter(request, response);
        filter.doFilter(request, response, chain);
        verify(request, never()).setAttribute("errorMessage", "You do not have permission to access the requested resource");
    }
}