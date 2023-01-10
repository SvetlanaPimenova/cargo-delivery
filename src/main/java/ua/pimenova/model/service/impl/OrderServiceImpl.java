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

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Order getByID(int id) throws DaoException {
        return orderDao.getByID(id);
    }

    @Override
    public List<Order> getAll() throws DaoException {
        return orderDao.getAll();
    }

    @Override
    public List<Order> getAll(String query) throws DaoException {
        return orderDao.getAll(query);
    }

    @Override
    public Order create(Order order) throws DaoException {
        return orderDao.create(order);
    }

    @Override
    public boolean update(Order order) throws DaoException {
        return orderDao.update(order);
    }

    @Override
    public boolean delete(Order order) throws DaoException {
        return orderDao.delete(order);
    }

    @Override
    public List<Order> getAllOrdersByDate(Date date) throws DaoException {
        return orderDao.getAllOrdersByDate(date);
    }

    @Override
    public List<Order> getAllOrdersByReceiver(Receiver receiver) throws DaoException {
        return orderDao.getAllOrdersByReceiver(receiver);
    }

    @Override
    public List<Order> getAllOrdersBySender(User user) throws DaoException {
        return orderDao.getAllOrdersBySender(user);
    }

    @Override
    public List<Order> getAllOrdersByCityFrom(String city) throws DaoException {
        return orderDao.getAllOrdersByCityFrom(city);
    }
    @Override
    public int getNumberOfRows(String query) throws DaoException {
        return orderDao.getNumberOfRows(query);
    }
}
