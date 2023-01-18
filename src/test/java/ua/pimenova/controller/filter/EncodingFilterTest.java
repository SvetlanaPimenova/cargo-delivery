package ua.pimenova.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class EncodingFilterTest {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    FilterConfig filterConfig = mock(FilterConfig.class);
    FilterChain chain = mock(FilterChain.class);

    @Test
    void testDoFilter() throws ServletException, IOException {
        when(filterConfig.getInitParameter("encoding")).thenReturn("UTF-8");

        EncodingFilter filter = new EncodingFilter();
        filter.init(filterConfig);
        filter.doFilter(request, response, chain);
        verify(request).setCharacterEncoding("UTF-8");
        verify(response).setCharacterEncoding("UTF-8");
    }

}