package ua.pimenova.controller.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Command interface. Implement it to create new commands
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public interface ICommand {

    /**
     * Obtains path to sendRedirect or forward in front-controller. Edits request and response if needed.
     *
     * @param request passed by controller
     * @param response passed by controller
     * @return path to return to front-controller
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
