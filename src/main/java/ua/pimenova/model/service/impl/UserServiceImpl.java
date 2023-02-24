package ua.pimenova.model.service.impl;

import ua.pimenova.model.database.dao.UserDao;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.UserService;
import ua.pimenova.model.util.EncryptingUserPassword;

import java.util.List;

/**
 * Implementation of UserService interface
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class UserServiceImpl implements UserService {

    /** Contains UserDao field to work with UserDao */
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Calls DAO to get relevant entity by id
     * @param id - user id to find
     * @return - User entity
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public User getByID(int id) throws DaoException {
        return userDao.getByID(id);
    }

    /**
     * Calls DAO to get list of all entities from database
     * @return - list of all users
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public List<User> getAll() throws DaoException {
        return userDao.getAll();
    }

    /**
     * Calls DAO to create relevant entity
     * @return - User entity
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public User create(User user) throws DaoException {
        String encryptedPassword = EncryptingUserPassword.encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);
        return userDao.create(user);
    }

    /**
     * Calls DAO to update relevant entity
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public boolean update(User user) throws DaoException {
        return userDao.update(user);
    }

    /**
     * Calls DAO to delete relevant entity
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public boolean delete(User user) throws DaoException {
        return userDao.delete(user);
    }

    /**
     * Calls DAO to update specific field of relevant entity
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public boolean updatePassword(User user) throws DaoException {
        String encryptedPassword = EncryptingUserPassword.encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);
        return userDao.updatePassword(user);
    }

    /**
     * Calls DAO to get relevant entity by phone
     * @param phone - user phone to find
     * @return - User entity
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public User getByPhone(String phone) throws DaoException {
        return userDao.getByPhone(phone);
    }

    /**
     * Calls DAO to get relevant entity by email
     * @param email - user email to find
     * @return - User entity
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public User getByEmail(String email) throws DaoException {
        return userDao.getByEmail(email);
    }

    /**
     * Calls DAO to get relevant entity by email and password
     * @param email - user email to find
     * @param password - user password to find
     * @return - User entity
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public User getUserByEmailAndPassword(String email, String password) throws DaoException {
        String encryptedPassword = EncryptingUserPassword.encryptPassword(password);
        return userDao.getUserByEmailAndPassword(email, encryptedPassword);
    }

    /**
     * Calls DAO to set temporary password
     * @param user - user, which password must be updated
     * @param temporaryPassword - random password
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public boolean resetPassword(User user, String temporaryPassword) throws DaoException {
        String encryptedPassword = EncryptingUserPassword.encryptPassword(temporaryPassword);
        user.setPassword(encryptedPassword);
        return userDao.updatePassword(user);
    }
}
