package ua.pimenova.model.database.dao;

import ua.pimenova.model.database.entity.ExtraOptions;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;

import java.util.Date;
import java.util.List;

/**
 * OrderDao interface.
 * Implement methods due to database type
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public interface OrderDao extends Dao<Order> {

    /**
     * Obtains list of all relevant entities from database by date
     * @param date - order date to find
     * @return - list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    List<Order> getAllOrdersByDate(Date date) throws DaoException;

    /**
     * Obtains sorted and limited list of all entities from database
     * @param query - should contain filters, order, limits for pagination
     * @return - sorted and limited list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    List<Order> getAll(String query) throws DaoException;

    /**
     * Obtains list of all relevant entities from database by receiver
     * @param receiver - order receiver to find
     * @return - list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    List<Order> getAllOrdersByReceiver(Receiver receiver) throws DaoException;

    /**
     * Obtains list of all relevant entities from database by sender
     * @param user - order sender to find
     * @return - list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    List<Order> getAllOrdersBySender(User user) throws DaoException;

    /**
     * Obtains list of all relevant entities from database by "city from"
     * @param city - order city to find
     * @return - list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    List<Order> getAllOrdersByCityFrom(String city) throws DaoException;

    /**
     * Obtains number of all records matching filter
     * @param query - should contain 'where' to specify query
     * @return - number of records
     * @throws DaoException - is wrapper for SQLException
     */
    int getNumberOfRows(String query) throws DaoException;
}
