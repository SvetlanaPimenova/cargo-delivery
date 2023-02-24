package ua.pimenova.controller.command.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;

/**
 * ShowResetPageCommand class. Accessible by any user
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class ShowResetPageCommand implements ICommand {
    /**
     * @param request - passed by application
     * @param response - passed by application
     * @return reset password page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Pages.RESET_PASSWORD_PAGE;
    }
}
