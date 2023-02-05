package ua.pimenova.controller.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.Logger;

/**
 * InitListener class
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class InitListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(InitListener.class);

    /**
     * Initializes CommandFactory class
     * @param servletContextEvent - passed by application
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("Servlet context is initialized");
        initCommandFactory();
    }

    private void initCommandFactory() {
        try {
            Class.forName("ua.pimenova.controller.command.CommandFactory");
            LOGGER.info("Command factory is initialized");
        } catch (ClassNotFoundException ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Servlet context is destroyed");
    }
}
