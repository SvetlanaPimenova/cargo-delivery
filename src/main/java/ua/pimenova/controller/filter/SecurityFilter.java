package ua.pimenova.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.entity.User;

import java.io.IOException;
import java.util.*;

/**
 * SecurityFilter class. Controls access to pages
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class SecurityFilter implements Filter {
    // commands access
    private static final Map<User.Role, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);

    /**
     * Fills accessMap with roles and pages
     * @param config passed by application
     */
    @Override
    public void init(FilterConfig config) {
        LOGGER.info("Security Filter is initialized");
        // roles
        accessMap.put(User.Role.MANAGER, asList(config.getInitParameter("manager")));
        accessMap.put(User.Role.USER, asList(config.getInitParameter("user")));

        // commons
        commons = asList(config.getInitParameter("common"));
    }

    /**
     * Checks user in session and then checks if user has access to page or command.
     * @param request passed by application
     * @param response passed by application
     * @param chain passed by application
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (accessAllowed(request)) {
            chain.doFilter(request, response);
        } else {
            String errorMessages = "You do not have permission to access the requested resource";
            request.setAttribute("errorMessage", errorMessages);
            request.getRequestDispatcher(Pages.PAGE_ERROR).forward(request, response);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

//        String commandName = request.getParameter("action");
        String requestURI = httpRequest.getRequestURI();
        String commandName = StringUtils.substringAfter(requestURI, "/delivery/");
        if (commandName == null || commandName.equalsIgnoreCase("")) {
            return true;
        }

        HttpSession session = httpRequest.getSession();
        if (session == null) {
            return true;
        }

        User.Role userRole = (User.Role) session.getAttribute("userRole");
        if(userRole == null) {
            return commons.contains(commandName);
        }
        return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
    }

    @Override
    public void destroy() {
    }

    private List<String> asList(String param) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(param);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }
}

