package ua.pimenova.model.service;

import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;

import java.util.List;

public interface UserService {
    User getByID(int id) throws DaoException;
    List<User> getAll() throws DaoException;
    User create(User user) throws DaoException;
    boolean update(User user) throws DaoException;
    boolean delete(User user) throws DaoException;

    boolean updatePassword(User user) throws DaoException;
    User getByPhone(String phone) throws DaoException;

    User getByEmail(String email) throws DaoException;
    User getUserByEmailAndPassword(String email, String password) throws DaoException;
}
