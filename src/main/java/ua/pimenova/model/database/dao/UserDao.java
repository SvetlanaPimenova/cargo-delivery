package ua.pimenova.model.database.dao;

import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;

import java.util.List;

public interface UserDao extends Dao<User> {
    User getByPhone(String phone) throws DaoException;
    User getByEmail(String email) throws DaoException;
    boolean updatePassword(User user) throws DaoException;
    User getUserByEmailAndPassword(String email, String password) throws DaoException;
}
