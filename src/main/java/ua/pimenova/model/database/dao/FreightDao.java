package ua.pimenova.model.database.dao;

import ua.pimenova.model.database.entity.Freight;
import ua.pimenova.model.exception.DaoException;

import java.util.List;

/**
 * FreightDao interface.
 * Implement methods due to database type
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public interface FreightDao extends Dao<Freight> {


    /**
     * Obtains list of all relevant entities from database by freight type
     * @param type - can be goods/glass/compact
     * @return - list of freights
     * @throws DaoException - is wrapper for SQLException
     */
    List<Freight> getAllFreightsByType(Freight.FreightType type) throws DaoException;
}
