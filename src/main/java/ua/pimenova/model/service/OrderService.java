package ua.pimenova.model.service;

import ua.pimenova.model.database.entity.ExtraOptions;
import ua.pimenova.model.database.entity.Order;
import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;

import java.util.Date;
import java.util.List;

public interface OrderService {
    Order getByID(int id) throws DaoException;
    List<Order> getAll() throws DaoException;
    List<Order> getAll(String query) throws DaoException;
    Order create(Order order) throws DaoException;
    boolean update(Order order) throws DaoException;
    boolean delete(Order order) throws DaoException;
    List<Order> getAllOrdersByDate(Date date) throws DaoException;
    List<Order> getAllOrdersByReceiver(Receiver receiver) throws DaoException;
    List<Order> getAllOrdersBySender(User user) throws DaoException;
    List<Order> getAllOrdersByCityFrom(String city) throws DaoException;
    int getNumberOfRows(String query) throws DaoException;
}
