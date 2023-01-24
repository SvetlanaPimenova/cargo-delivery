package ua.pimenova.controller.command;

import jakarta.servlet.http.HttpServletRequest;

public class CommandUtil {

    private CommandUtil() {}

    public static boolean isMethodPost(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("post");
    }

    public static void getAttributeFromSessionToRequest(HttpServletRequest request, String attribute) {
        String attributeValue = (String) request.getSession().getAttribute(attribute);
        if (attributeValue != null) {
            request.setAttribute(attribute, attributeValue);
            request.getSession().removeAttribute(attribute);
        }
    }

    public static String getUrlAttribute(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("url");
    }

    public static String getURL(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String requestURL = request.getRequestURL().toString();
        return requestURL.replace(servletPath, "");
    }
}
