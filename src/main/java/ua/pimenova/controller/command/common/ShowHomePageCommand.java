package ua.pimenova.controller.command.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.controller.constants.Pages;

public class ShowHomePageCommand implements ICommand {
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Pages.PAGE_INDEX;
    }

}
