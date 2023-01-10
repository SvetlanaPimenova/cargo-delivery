package ua.pimenova.controller.command.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;

import java.util.Locale;

public class LogoutCommand implements ICommand {
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
