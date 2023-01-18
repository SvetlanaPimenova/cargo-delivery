package ua.pimenova.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Locale;

import static org.mockito.Mockito.*;

class LocaleFilterTest {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    FilterConfig filterConfig = mock(FilterConfig.class);
    FilterChain chain = mock(FilterChain.class);
    HttpSession session = mock(HttpSession.class);

    @BeforeEach
    void setDefault() {
        when(filterConfig.getInitParameter("defaultLocale")).thenReturn("en");
        when(filterConfig.getInitParameter("availableLocales")).thenReturn("en ua");
    }

    @Test
    void testNullLocale() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getLocale()).thenReturn(new Locale("en"));
        when(session.getAttribute("locale")).thenReturn(null);
        LocaleFilter filter = new LocaleFilter();
        filter.init(filterConfig);
        filter.doFilter(request, response, chain);
        verify(session).setAttribute("locale", new Locale("en"));
    }

    @Test
    void testParameterLocale() throws ServletException, IOException {
        when(request.getParameter("locale")).thenReturn("ua");
        when(request.getSession()).thenReturn(session);
        LocaleFilter filter = new LocaleFilter();
        filter.init(filterConfig);
        filter.doFilter(request, response, chain);
        verify(session).setAttribute("locale", new Locale("ua"));
    }

}