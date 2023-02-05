package ua.pimenova.controller.command.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;

import java.util.Locale;

/**
 * LogoutCommand class. Accessible by any user. Allows to log out of the web app
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class LogoutCommand implements ICommand {

    /**
     * Invalidates session. Saves locale and sets to new session so language will not change for user
     *
     * @param request - to get session
     * @param response - passed by application
     * @return home page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            Locale currentLocale = (Locale) session.getAttribute("locale");
            session.invalidate();
            request.getSession(true).setAttribute("locale", currentLocale);
        }
        return Pages.PAGE_INDEX;
    }
}
