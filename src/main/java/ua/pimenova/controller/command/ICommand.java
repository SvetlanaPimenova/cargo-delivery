package ua.pimenova.controller.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ICommand {
    String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

}
