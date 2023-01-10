package ua.pimenova.controller.command.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;

import java.io.IOException;

public class ShowSignUpPageCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return Pages.SIGNUP_PAGE;
    }
}
