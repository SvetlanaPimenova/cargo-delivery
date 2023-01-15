package ua.pimenova.controller.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(InitListener.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("Servlet context is initialized");
        initCommandFactory();
    }

    private void initCommandFactory() {
        try {
            Class.forName("ua.pimenova.controller.command.CommandFactory");
            logger.info("Command factory is initialized");
        } catch (ClassNotFoundException ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Servlet context is destroyed");
    }
}
