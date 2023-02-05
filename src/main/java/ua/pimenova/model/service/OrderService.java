package ua.pimenova.model.service;

import ua.pimenova.model.database.entity.ExtraOptions;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;

import java.util.Date;
import java.util.List;

/**
 * OrderService interface.
 * Implement all methods in the specific OrderServiceImpl class.
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public interface OrderService {

    /**
     * Calls DAO to get relevant entity by id
     * @param id - order id to find
     * @return - Order entity
     * @throws DaoException - is wrapper for SQLException
     */
    Order getByID(int id) throws DaoException;

    /**
     * Calls DAO to get list of all entities from database
     * @return - list of all orders
     * @throws DaoException - is wrapper for SQLException
     */
    List<Order> getAll() throws DaoException;

    /**
     * Calls DAO to get sorted and limited list of all entities from database
     * @param query - should contain filters, order, limits for pagination
     * @return - sorted and limited list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    List<Order> getAll(String query) throws DaoException;

    /**
     * Calls DAO to create relevant entity
     * @param order - entity, that must be added to the database
     * @return - Order entity
     * @throws DaoException - is wrapper for SQLException
     */
    Order create(Order order) throws DaoException;

    /**
     * Calls DAO to update relevant entity
     * @param order - entity, that must be updated
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    boolean update(Order order) throws DaoException;

    /**
     * Calls DAO to delete relevant entity
     * @param order - entity, that must be deleted
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    boolean delete(Order order) throws DaoException;

    /**
     * Calls DAO to get list of all relevant entities from database by date
     * @param date - order date to find
     * @return - list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    List<Order> getAllOrdersByDate(Date date) throws DaoException;

    /**
     * Calls DAO to get list of all relevant entities from database by receiver
     * @param receiver - order receiver to find
     * @return - list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    List<Order> getAllOrdersByReceiver(Receiver receiver) throws DaoException;

    /**
     * Calls DAO to get list of all relevant entities from database by sender
     * @param user - order sender to find
     * @return - list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    List<Order> getAllOrdersBySender(User user) throws DaoException;

    /**
     * Calls DAO to get list of all relevant entities from database by "city from"
     * @param city - city to find
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
