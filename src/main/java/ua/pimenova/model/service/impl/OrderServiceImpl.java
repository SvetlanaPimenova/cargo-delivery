package ua.pimenova.model.service.impl;

import ua.pimenova.model.database.dao.OrderDao;
import ua.pimenova.model.database.entity.ExtraOptions;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.OrderService;

import java.util.Date;
import java.util.List;

/**
 * Implementation of OrderService interface
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class OrderServiceImpl implements OrderService {

    /** Contains OrderDao field to work with OrderDao */
    private OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Calls DAO to get relevant entity by id
     * @param id - order id to find
     * @return - Order entity
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public Order getByID(int id) throws DaoException {
        return orderDao.getByID(id);
    }

    /**
     * Calls DAO to get list of all entities from database
     * @return - list of all orders
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public List<Order> getAll() throws DaoException {
        return orderDao.getAll();
    }

    /**
     * Calls DAO to get sorted and limited list of all entities from database
     * @param query - should contain filters, order, limits for pagination
     * @return - sorted and limited list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public List<Order> getAll(String query) throws DaoException {
        return orderDao.getAll(query);
    }

    /**
     * Calls DAO to create relevant entity
     * @return - Order entity
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public Order create(Order order) throws DaoException {
        return orderDao.create(order);
    }

    /**
     * Calls DAO to update relevant entity
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public boolean update(Order order) throws DaoException {
        return orderDao.update(order);
    }

    /**
     * Calls DAO to delete relevant entity
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public boolean delete(Order order) throws DaoException {
        return orderDao.delete(order);
    }

    /**
     * Calls DAO to get list of all relevant entities from database by date
     * @return - list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public List<Order> getAllOrdersByDate(Date date) throws DaoException {
        return orderDao.getAllOrdersByDate(date);
    }

    /**
     * Calls DAO to get list of all relevant entities from database by receiver
     * @return - list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public List<Order> getAllOrdersByReceiver(Receiver receiver) throws DaoException {
        return orderDao.getAllOrdersByReceiver(receiver);
    }

    /**
     * Calls DAO to get list of all relevant entities from database by sender
     * @return - list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public List<Order> getAllOrdersBySender(User user) throws DaoException {
        return orderDao.getAllOrdersBySender(user);
    }

    /**
     * Calls DAO to get list of all relevant entities from database by "city from"
     * @return - list of orders
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public List<Order> getAllOrdersByCityFrom(String city) throws DaoException {
        return orderDao.getAllOrdersByCityFrom(city);
    }

    /**
     * Obtains number of all records matching filter
     * @param query - should contain 'where' to specify query
     * @return - number of records
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public int getNumberOfRows(String query) throws DaoException {
        return orderDao.getNumberOfRows(query);
    }
}
