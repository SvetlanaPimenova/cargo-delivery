package ua.pimenova.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.dao.impl.UserDaoImpl;
import ua.pimenova.model.database.entity.User;

import java.io.IOException;
import java.util.*;

/* @WebFilter(urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "user", value = "createOrder orders pageCreate deleteOrder updateOrder_user update_page account top_up transaction"),
                        @WebInitParam(name = "manager", value = "reports"),
                        @WebInitParam(name = "common", value = "calculate login logout signup home profile update signup_page")})
                        */

public class SecurityFilter implements Filter {
    // commands access
    private static Map<User.Role, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();

    @Override
    public void init(FilterConfig config) {
        // roles
        accessMap.put(User.Role.MANAGER, asList(config.getInitParameter("manager")));
        accessMap.put(User.Role.USER, asList(config.getInitParameter("user")));

        // commons
        commons = asList(config.getInitParameter("common"));
    }

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

        HttpSession session = httpRequest.getSession(false);
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

