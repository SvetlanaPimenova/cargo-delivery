package ua.pimenova.controller.command;

import jakarta.servlet.http.HttpServletRequest;

/**
 * CommandUtil class. Contains utils methods to use in commands.
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class CommandUtil {

    private CommandUtil() {}

    /**
     * Checks if method is POST method
     * @param request passed by command
     * @return true if POST method
     */
    public static boolean isMethodPost(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("post");
    }

    /**
     * Transfers sessions attributes to request (only string). Delete then
     * @param request passed by command
     */
    public static void getAttributeFromSessionToRequest(HttpServletRequest request, String attribute) {
        String attributeValue = (String) request.getSession().getAttribute(attribute);
        if (attributeValue != null) {
            request.setAttribute(attribute, attributeValue);
            request.getSession().removeAttribute(attribute);
        }
    }

    /**
     * Used in GET commands to redirect to concrete page
     * @param request passed by command
     * @return page to redirect
     */
    public static String getUrlAttribute(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("url");
    }

    /**
     * Obtain Web App domain address. Common usage - email sender
     * @param request passed by command
     * @return - Web App domain address
     */
    public static String getURL(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String requestURL = request.getRequestURL().toString();
        return requestURL.replace(servletPath, "");
    }
}
