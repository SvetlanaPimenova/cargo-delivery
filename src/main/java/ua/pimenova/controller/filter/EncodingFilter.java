package ua.pimenova.controller.filter;

import jakarta.servlet.*;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * EncodingFilter class. Sets encoding for the view
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class EncodingFilter implements Filter {

    private String code;
    private static final Logger LOGGER = Logger.getLogger(EncodingFilter.class);

    /**
     * Sets default encoding
     * @param filterConfig passed by application
     */
    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("Encoding Filter is initialized");
        code = filterConfig.getInitParameter("encoding");
    }

    /**
     * Sets default encoding for any values from user.
     * @param request passed by application
     * @param response passed by application
     * @param chain passed by application
     */
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
