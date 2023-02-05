package ua.pimenova.controller.command.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;

/**
 * ShowAccountCommand class. Accessible by authorized user
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class ShowAccountCommand implements ICommand {

    /**
     * @param request - passed by application
     * @param response - passed by application
     * @return account page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Pages.ACCOUNT_PAGE;
    }
}
