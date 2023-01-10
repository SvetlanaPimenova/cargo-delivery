package ua.pimenova.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LocaleFilter implements Filter {
    private String defaultLocale;
    private List<String> availableLocales;

    @Override
    public void init(FilterConfig filterConfig) {
        defaultLocale = filterConfig.getInitParameter("defaultLocale");
        availableLocales = Arrays.asList(filterConfig.getInitParameter("availableLocales").split(" "));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String parameter = httpRequest.getParameter("locale");
        if (!isBlank(parameter)) {
            httpRequest.getSession().setAttribute("locale", new Locale(parameter));
        } else {
            Locale sessionLocale = (Locale) httpRequest.getSession().getAttribute("locale");
            if(sessionLocale == null) {
                Locale locale = httpRequest.getLocale();
                if(isCorrect(locale)) {
                    httpRequest.getSession().setAttribute("locale", locale);
                } else {
                    httpRequest.getSession().setAttribute("locale", defaultLocale);
                }
            }
        }
        chain.doFilter(request, response);

    }

    private boolean isCorrect(Locale locale) {
        return availableLocales.contains(locale.getLanguage());
    }

    private boolean isBlank(String locale) {
        return locale == null || locale.isEmpty();
    }
}
