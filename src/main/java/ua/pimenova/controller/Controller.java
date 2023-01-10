package ua.pimenova.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.pimenova.controller.command.CommandFactory;
import ua.pimenova.controller.command.ICommand;

import java.io.IOException;

public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(processRequest(req, resp)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(processRequest(req, resp));
    }

    private String processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CommandFactory commandFactory = CommandFactory.getFactory();
        ICommand command = commandFactory.getCommand(req);
        return command.execute(req, resp);
//        RequestDispatcher dispatcher = req.getRequestDispatcher(page);
//        if (!page.equals("redirect")) {
//            dispatcher.forward(req, resp);
//        }
    }
}

