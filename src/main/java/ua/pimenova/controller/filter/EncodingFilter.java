package ua.pimenova.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.pimenova.controller.listener.InitListener;

import java.io.IOException;

public class EncodingFilter implements Filter {

    private String code;
    private static final Logger logger = LoggerFactory.getLogger(EncodingFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Encoding Filter is initialized");
        code = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();
        if(code != null && !code.equalsIgnoreCase(codeRequest)) {
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
