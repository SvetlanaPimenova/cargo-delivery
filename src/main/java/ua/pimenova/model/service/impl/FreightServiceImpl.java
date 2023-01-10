package ua.pimenova.model.service.impl;

import ua.pimenova.model.database.dao.FreightDao;
import ua.pimenova.model.database.entity.Freight;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.FreightService;

import java.util.List;

public class FreightServiceImpl implements FreightService {
    private FreightDao freightDao;

    public FreightServiceImpl(FreightDao freightDao) {
        this.freightDao = freightDao;
    }

    @Override
    public Freight getByID(int id) throws DaoException {
        return freightDao.getByID(id);
    }

    @Override
    public List<Freight> getAll() throws DaoException {
        return freightDao.getAll();
    }

    @Override
    public Freight create(Freight freight) throws DaoException {
        return freightDao.create(freight);
    }

    @Override
    public boolean update(Freight freight) throws DaoException {
        return freightDao.update(freight);
    }

    @Override
    public boolean delete(Freight freight) throws DaoException {
        return freightDao.delete(freight);
    }

    @Override
    public List<Freight> getAllFreightsByType(Freight.FreightType type) throws DaoException {
        return freightDao.getAllFreightsByType(type);
    }
}
