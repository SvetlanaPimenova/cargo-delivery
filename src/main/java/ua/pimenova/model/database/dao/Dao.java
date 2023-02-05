package ua.pimenova.model.database.dao;

import ua.pimenova.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Dao interface.
 * Implement methods due to database type
 *
 * @param <T> - specific entity, that matches relative table in database.
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public interface Dao<T> {
    /**
     * Obtains relevant entity from database by id
     *
     * @param id - entity's id to find
     * @return - entity
     * @throws DaoException - is wrapper for SQLException
     */
    T getByID(int id) throws DaoException;

    /**
     * Obtains list of all entities from database
     * @return - list of all entities
     * @throws DaoException - is wrapper for SQLException
     */
    List<T> getAll() throws DaoException;

    /**
     * Creates relevant entity
     * @param item - entity, that must be added to the database
     * @return - entity
     * @throws DaoException - is wrapper for SQLException
     */
    T create(T item) throws DaoException;

    /**
     * Updates relevant entity
     * @param item - entity, that must be updated
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    boolean update(T item) throws DaoException;

    /**
     * Deletes relevant entity
     * @param item - entity, that must be deleted
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    boolean delete(T item) throws DaoException;
}
