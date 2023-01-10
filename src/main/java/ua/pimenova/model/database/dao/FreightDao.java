package ua.pimenova.model.database.dao;

import ua.pimenova.model.database.entity.Freight;
import ua.pimenova.model.exception.DaoException;

import java.util.List;

public interface FreightDao extends Dao<Freight> {
    List<Freight> getAllFreightsByType(Freight.FreightType type) throws DaoException;
}
