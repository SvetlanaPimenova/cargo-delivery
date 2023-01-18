package ua.pimenova.controller.filter;

import jakarta.servlet.*;
import org.apache.log4j.Logger;

import java.io.IOException;

public class EncodingFilter implements Filter {

    private String code;
    private static final Logger LOGGER = Logger.getLogger(EncodingFilter.class);
    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("Encoding Filter is initialized");
        code = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(code != null) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        code = null;
    }
}
