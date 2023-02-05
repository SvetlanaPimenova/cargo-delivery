package ua.pimenova.model.service;

import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.exception.DaoException;

import java.util.List;

/**
 * ReceiverService interface.
 * Implement all methods in the specific ReceiverServiceImpl class.
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public interface ReceiverService {

    /**
     * Calls DAO to get relevant entity by id
     * @param id - receiver id to find
     * @return - Receiver entity
     * @throws DaoException - is wrapper for SQLException
     */
    Receiver getByID(int id) throws DaoException;

    /**
     * Calls DAO to get list of all entities from database
     * @return - list of all receivers
     * @throws DaoException - is wrapper for SQLException
     */
    List<Receiver> getAll() throws DaoException;

    /**
     * Calls DAO to create relevant entity
     * @param receiver - entity, that must be added to the database
     * @return - Receiver entity
     * @throws DaoException - is wrapper for SQLException
     */
    Receiver create(Receiver receiver) throws DaoException;

    /**
     * Calls DAO to update relevant entity
     * @param receiver - entity, that must be updated
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    boolean update(Receiver receiver) throws DaoException;

    /**
     * Calls DAO to delete relevant entity
     * @param receiver - entity, that must be deleted
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    boolean delete(Receiver receiver) throws DaoException;

    /**
     * Calls DAO to get relevant entity by phone
     * @param phone - receiver phone to find
     * @return - Receiver entity
     * @throws DaoException - is wrapper for SQLException
     */
    Receiver getByPhone(String phone) throws DaoException;

    /**
     * Calls DAO to get list of all relevant entities from database by city
     * @param city - city to find
     * @return - list of receivers
     * @throws DaoException - is wrapper for SQLException
     */
    List<Receiver> getAllReceiversByCity(String city) throws DaoException;
}
