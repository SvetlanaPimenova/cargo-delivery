package ua.pimenova.controller.command.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;
import ua.pimenova.model.database.entity.User;

/**
 * ShowProfileCommand class. Accessible by any user. Returns different pages according to the user role.
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class ShowProfileCommand implements ICommand {

    /**
     * Checks user's role and returns corresponding page
     *
     * @param request - to get session
     * @param response - passed by application
     * @return profile page
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if(user.getRole() == User.Role.USER) {
            return Pages.USER_PROFILE;
        }
        return Pages.MANAGER_PROFILE;
    }
}
