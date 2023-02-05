package ua.pimenova.model.service.impl;

import ua.pimenova.model.database.dao.ReceiverDao;
import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.ReceiverService;

import java.util.List;

/**
 * Implementation of ReceiverService interface
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class ReceiverServiceImpl implements ReceiverService {

    /** Contains ReceiverDao field to work with ReceiverDao */
    private ReceiverDao receiverDao;

    public ReceiverServiceImpl(ReceiverDao receiverDao) {
        this.receiverDao = receiverDao;
    }

    /**
     * Calls DAO to get relevant entity by id
     * @param id - receiver id to find
     * @return - Receiver entity
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public Receiver getByID(int id) throws DaoException {
        return receiverDao.getByID(id);
    }

    /**
     * Calls DAO to get list of all entities from database
     * @return - list of all receivers
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public List<Receiver> getAll() throws DaoException {
        return receiverDao.getAll();
    }

    /**
     * Calls DAO to create relevant entity
     * @return - Receiver entity
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public Receiver create(Receiver receiver) throws DaoException {
        return receiverDao.create(receiver);
    }

    /**
     * Calls DAO to update relevant entity
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public boolean update(Receiver receiver) throws DaoException {
        return receiverDao.update(receiver);
    }

    /**
     * Calls DAO to delete relevant entity
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public boolean delete(Receiver receiver) throws DaoException {
        return receiverDao.delete(receiver);
    }

    /**
     * Calls DAO to get relevant entity by phone
     * @param phone - receiver phone to find
     * @return - Receiver entity
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public Receiver getByPhone(String phone) throws DaoException {
        return receiverDao.getByPhone(phone);
    }

    /**
     * Calls DAO to get list of all relevant entities from database by city
     * @return - list of receivers
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public List<Receiver> getAllReceiversByCity(String city) throws DaoException {
        return receiverDao.getAllReceiversByCity(city);
    }
}
