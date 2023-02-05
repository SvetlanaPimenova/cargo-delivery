package ua.pimenova.model.service.impl;

import ua.pimenova.model.database.dao.FreightDao;
import ua.pimenova.model.database.entity.Freight;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.FreightService;

import java.util.List;

/**
 * Implementation of FreightService interface
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class FreightServiceImpl implements FreightService {
    /** Contains FreightDao field to work with FreightDao */
    private FreightDao freightDao;

    public FreightServiceImpl(FreightDao freightDao) {
        this.freightDao = freightDao;
    }

    /**
     * Calls DAO to get relevant entity by id
     * @param id - freight id to find
     * @return - Freight entity
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public Freight getByID(int id) throws DaoException {
        return freightDao.getByID(id);
    }

    /**
     * Calls DAO to get list of all entities from database
     * @return - list of all freights
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public List<Freight> getAll() throws DaoException {
        return freightDao.getAll();
    }

    /**
     * Calls DAO to create relevant entity
     * @return - Freight entity
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public Freight create(Freight freight) throws DaoException {
        return freightDao.create(freight);
    }

    /**
     * Calls DAO to update relevant entity
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public boolean update(Freight freight) throws DaoException {
        return freightDao.update(freight);
    }

    /**
     * Calls DAO to delete relevant entity
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public boolean delete(Freight freight) throws DaoException {
        return freightDao.delete(freight);
    }

    /**
     * Calls DAO to get list of all relevant entities from database by freight type
     * @param type - can be goods/glass/compact
     * @return - list of freights
     * @throws DaoException - is wrapper for SQLException
     */
    @Override
    public List<Freight> getAllFreightsByType(Freight.FreightType type) throws DaoException {
        return freightDao.getAllFreightsByType(type);
    }
}
