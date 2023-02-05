package ua.pimenova.model.database.dao;

import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.exception.DaoException;

import java.util.List;

/**
 * ReceiverDao interface.
 * Implement methods due to database type
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public interface ReceiverDao extends Dao<Receiver> {

    /**
     * Obtains relevant entity by phone
     * @param phone - receiver phone to find
     * @return - Receiver entity
     * @throws DaoException - is wrapper for SQLException
     */
    Receiver getByPhone(String phone) throws DaoException;

    /**
     * Obtains list of all relevant entities from database by city
     * @param city - city to find
     * @return - list of receivers
     * @throws DaoException - is wrapper for SQLException
     */
    List<Receiver> getAllReceiversByCity(String city) throws DaoException;
}
