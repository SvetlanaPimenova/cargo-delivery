package ua.pimenova.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * LocaleFilter class. Sets and changes locale
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class LocaleFilter implements Filter {
    private String defaultLocale;
    private List<String> availableLocales;
    private static final Logger LOGGER = Logger.getLogger(LocaleFilter.class);

    /**
     * Sets default locale and list of the available locales
     * @param filterConfig passed by application
     */
    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("Locale Filter is initialized");
        defaultLocale = filterConfig.getInitParameter("defaultLocale");
        availableLocales = Arrays.asList(filterConfig.getInitParameter("availableLocales").split(" "));
    }

    /**
     * Checks if request contains locale parameter and sets locale to session as attribute if present.
     * Returns previous page in this case.
     * In other case checks if locale presents in session. If not check list of the available locales and sets either locale
     * from list or default locale. doFilter after that.
     * @param request passed by application
     * @param response passed by application
     * @param chain passed by application
     */
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
                    httpRequest.getSession().setAttribute("locale", new Locale(defaultLocale));
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
