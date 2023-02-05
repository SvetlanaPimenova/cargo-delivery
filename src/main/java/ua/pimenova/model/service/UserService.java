package ua.pimenova.model.service;

import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;

import java.util.List;

/**
 * UserService interface.
 * Implement all methods in the specific UserServiceImpl class.
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public interface UserService {

    /**
     * Calls DAO to get relevant entity by id
     * @param id - user id to find
     * @return - User entity
     * @throws DaoException - is wrapper for SQLException
     */
    User getByID(int id) throws DaoException;

    /**
     * Calls DAO to get list of all entities from database
     * @return - list of all users
     * @throws DaoException - is wrapper for SQLException
     */
    List<User> getAll() throws DaoException;

    /**
     * Calls DAO to create relevant entity
     * @param user - entity, that must be added to the database
     * @return - User entity
     * @throws DaoException - is wrapper for SQLException
     */
    User create(User user) throws DaoException;

    /**
     * Calls DAO to update relevant entity
     * @param user - entity, that must be updated
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    boolean update(User user) throws DaoException;

    /**
     * Calls DAO to delete relevant entity
     * @param user - entity, that must be deleted
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    boolean delete(User user) throws DaoException;

    /**
     * Calls DAO to update specific field of relevant entity
     * @param user - user, which password must be updated
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    boolean updatePassword(User user) throws DaoException;

    /**
     * Calls DAO to get relevant entity by phone
     * @param phone - user phone to find
     * @return - User entity
     * @throws DaoException - is wrapper for SQLException
     */
    User getByPhone(String phone) throws DaoException;

    /**
     * Calls DAO to get relevant entity by email
     * @param email - user email to find
     * @return - User entity
     * @throws DaoException - is wrapper for SQLException
     */
    User getByEmail(String email) throws DaoException;

    /**
     * Calls DAO to get relevant entity by email and password
     * @param email - user email to find
     * @param password - user password to find
     * @return - User entity
     * @throws DaoException - is wrapper for SQLException
     */
    User getUserByEmailAndPassword(String email, String password) throws DaoException;
}
