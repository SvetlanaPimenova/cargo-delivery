package ua.pimenova.model.database.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import ua.pimenova.model.database.dao.impl.FreightDaoImpl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class to configure and obtain HikariDataSource. Use it to connect to database
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class HikariCPDataSource {
    private static final String URL_PROPERTY = "jdbcUrl";
    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";
    private static final String CACHE_PREPARED_STATEMENT = "cachePrepStmts";
    private static final String CACHE_SIZE = "prepStmtCacheSize";
    private static final String CACHE_SQL_LIMIT = "prepStmtCacheSqlLimit";
    private static final String DRIVER = "driver";

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private static final Logger LOGGER = Logger.getLogger(HikariCPDataSource.class);

    static {
        Properties properties = getProperties();
        config.setJdbcUrl(properties.getProperty(URL_PROPERTY));
        config.setUsername(properties.getProperty(USER_NAME));
        config.setPassword(properties.getProperty(PASSWORD));
        config.setDriverClassName(properties.getProperty(DRIVER));
        config.addDataSourceProperty(CACHE_PREPARED_STATEMENT, properties.getProperty(CACHE_PREPARED_STATEMENT));
        config.addDataSourceProperty(CACHE_SIZE, properties.getProperty(CACHE_SIZE));
        config.addDataSourceProperty(CACHE_SQL_LIMIT, properties.getProperty(CACHE_SQL_LIMIT));
        ds = new HikariDataSource(config);
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        String connectionFile = "database.properties";
        try(InputStream resource = HikariCPDataSource.class.getClassLoader()
                .getResourceAsStream(connectionFile)) {
            properties.load(resource);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return properties;
    }

    private HikariCPDataSource() {}

    /**
     * Class to obtain Connection
     * @return - Connection from pool
     * @throws SQLException - unhandled exception
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
