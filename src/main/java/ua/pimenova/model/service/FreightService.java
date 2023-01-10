package ua.pimenova.model.service;

import ua.pimenova.model.database.entity.Freight;
import ua.pimenova.model.exception.DaoException;

import java.util.List;

public interface FreightService {
    Freight getByID(int id) throws DaoException;
    List<Freight> getAll() throws DaoException;
    Freight create(Freight freight) throws DaoException;
    boolean update(Freight freight) throws DaoException;
    boolean delete(Freight freight) throws DaoException;
    List<Freight> getAllFreightsByType(Freight.FreightType type) throws DaoException;
}
