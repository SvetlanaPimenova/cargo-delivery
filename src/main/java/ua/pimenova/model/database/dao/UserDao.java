package ua.pimenova.model.database.dao;

import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;

import java.util.List;

/**
 * UserDao interface.
 * Implement methods due to database type
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public interface UserDao extends Dao<User> {

    /**
     * Obtains relevant entity by phone
     * @param phone - user phone to find
     * @return - User entity
     * @throws DaoException - is wrapper for SQLException
     */
    User getByPhone(String phone) throws DaoException;

    /**
     * Obtains relevant entity by email
     * @param email - user email to find
     * @return - User entity
     * @throws DaoException - is wrapper for SQLException
     */
    User getByEmail(String email) throws DaoException;

    /**
     * Updates specific field of relevant entity
     * @param user - user, which password must be updated
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    boolean updatePassword(User user) throws DaoException;

    /**
     * Obtains relevant entity by email and password
     * @param email - user email to find
     * @param password - user password to find
     * @return - User entity
     * @throws DaoException - is wrapper for SQLException
     */
    User getUserByEmailAndPassword(String email, String password) throws DaoException;
}
