package ua.pimenova.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.pimenova.controller.command.CommandFactory;
import ua.pimenova.controller.command.ICommand;

import java.io.IOException;

/**
 * Controller  class. Implements Front-controller pattern. Chooses command to execute and redirect or forward result.
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class Controller extends HttpServlet {

    /**
     * Calls and executes command and then forwards requestDispatcher
     * @param req comes from user
     * @param resp comes from user
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(processRequest(req, resp)).forward(req, resp);
    }

    /**
     * Calls and executes action and then sendRedirect for PRG pattern implementation
     * @param req comes from user
     * @param resp comes from user
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(processRequest(req, resp));
    }

    /**
     * Obtains path to use in doPost/doGet methods
     * @return path
     */
    private String processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CommandFactory commandFactory = CommandFactory.getFactory();
        ICommand command = commandFactory.getCommand(req);
        return command.execute(req, resp);
    }
}

