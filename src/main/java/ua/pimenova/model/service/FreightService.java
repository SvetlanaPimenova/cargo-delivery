package ua.pimenova.model.service;

import ua.pimenova.model.database.entity.Freight;
import ua.pimenova.model.exception.DaoException;

import java.util.List;

/**
 * FreightService interface.
 * Implement all methods in the specific FreightServiceImpl class.
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public interface FreightService {

    /**
     * Calls DAO to get relevant entity by id
     * @param id - freight id to find
     * @return - Freight entity
     * @throws DaoException - is wrapper for SQLException
     */
    Freight getByID(int id) throws DaoException;

    /**
     * Calls DAO to get list of all entities from database
     * @return - list of all freights
     * @throws DaoException - is wrapper for SQLException
     */
    List<Freight> getAll() throws DaoException;

    /**
     * Calls DAO to create relevant entity
     * @param freight - entity, that must be added to the database
     * @return - Freight entity
     * @throws DaoException - is wrapper for SQLException
     */
    Freight create(Freight freight) throws DaoException;

    /**
     * Calls DAO to update relevant entity
     * @param freight - entity, that must be updated
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    boolean update(Freight freight) throws DaoException;

    /**
     * Calls DAO to delete relevant entity
     * @param freight - entity, that must be deleted
     * @return - boolean value
     * @throws DaoException - is wrapper for SQLException
     */
    boolean delete(Freight freight) throws DaoException;

    /**
     * Calls DAO to get list of all relevant entities from database by freight type
     * @param type - can be goods/glass/compact
     * @return - list of freights
     * @throws DaoException - is wrapper for SQLException
     */
    List<Freight> getAllFreightsByType(Freight.FreightType type) throws DaoException;
}
