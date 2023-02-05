package ua.pimenova.controller.command.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.pimenova.controller.command.ICommand;
import ua.pimenova.model.database.entity.ExtraOptions;
import ua.pimenova.model.database.entity.Freight;
import ua.pimenova.model.util.Calculator;
import static ua.pimenova.controller.command.CommandUtil.*;
import static ua.pimenova.controller.constants.Commands.*;

/**
 * CalculateCommand class. Accessible by any user. Allows to calculate approximate shipping cost. Implements PRG pattern
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class CalculateCommand implements ICommand {

    /**
     * Checks method and calls required implementation
     *
     * @param req - to get method, session and set all required attributes
     * @param resp - passed by application
     * @return path to redirect or forward by front-controller
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return isMethodPost(req) ? executePost(req) : executeGet(req);
    }

    private String executeGet(HttpServletRequest request) {
        getAttributeFromSession(request);
        getAttributeFromSessionToRequest(request, "result");
        return getUrlAttribute(request);
    }

    private void getAttributeFromSession(HttpServletRequest request) {
        getAttributeFromSessionToRequest(request, "cityfrom");
        getAttributeFromSessionToRequest(request, "cityto");
        getAttributeFromSessionToRequest(request, "freighttype");
        getAttributeFromSessionToRequest(request, "deliverytype");
        getAttributeFromSessionToRequest(request, "weight");
        getAttributeFromSessionToRequest(request, "length");
        getAttributeFromSessionToRequest(request, "width");
        getAttributeFromSessionToRequest(request, "height");
    }

    private String executePost(HttpServletRequest request) {
        String cityFrom = request.getParameter("cityfrom");
        String cityTo = request.getParameter("cityto");
        Freight.FreightType freightType = Freight.FreightType.valueOf(request.getParameter("freighttype").toUpperCase());
        ExtraOptions.DeliveryType deliveryType = ExtraOptions.DeliveryType.valueOf(request.getParameter("deliverytype").toUpperCase());
        double weight = Double.parseDouble(request.getParameter("weight"));
        int totalCost = Calculator.getTotalCost(cityFrom, cityTo, freightType,
                deliveryType, weight);
        settingAttributes(request);
        request.getSession().setAttribute("result", totalCost);
        request.getSession().setAttribute("url", HOME);
        return request.getContextPath() + HOME;
    }

    private void settingAttributes(HttpServletRequest request) {
        request.getSession().setAttribute("cityfrom", request.getParameter("cityfrom"));
        request.getSession().setAttribute("cityto", request.getParameter("cityto"));
        request.getSession().setAttribute("freighttype", request.getParameter("freighttype"));
        request.getSession().setAttribute("deliverytype", request.getParameter("deliverytype"));
        request.getSession().setAttribute("weight", request.getParameter("weight"));
        request.getSession().setAttribute("length", request.getParameter("length"));
        request.getSession().setAttribute("width", request.getParameter("width"));
        request.getSession().setAttribute("height", request.getParameter("height"));
    }
}
