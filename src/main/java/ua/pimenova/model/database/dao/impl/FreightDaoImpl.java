package ua.pimenova.model.database.dao.impl;

import ua.pimenova.model.database.dao.FreightDao;
import ua.pimenova.model.database.dao.HikariCPDataSource;
import ua.pimenova.model.database.dao.SqlQuery;
import ua.pimenova.model.database.entity.Freight;
import ua.pimenova.model.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FreightDaoImpl implements FreightDao {
    private static FreightDaoImpl instance = null;

    private FreightDaoImpl() {}

    public static synchronized FreightDaoImpl getInstance() {
        if(instance == null) {
            instance = new FreightDaoImpl();
        }
        return instance;
    }
    @Override
    public Freight getByID(int id) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.FreightQuery.SELECT_FREIGHT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet != null) {
                return getFreight(resultSet);
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return null;
    }

    private Freight getFreight(ResultSet resultSet) throws SQLException {
        Freight freight = null;
        while (resultSet.next()) {
            freight = new Freight();
            freight.setId(resultSet.getInt(Freight.FreightFields.ID));
            freight.setWeight(resultSet.getDouble(Freight.FreightFields.WEIGHT));
            freight.setLength(resultSet.getDouble(Freight.FreightFields.LENGTH));
            freight.setWidth(resultSet.getDouble(Freight.FreightFields.WIDTH));
            freight.setHeight(resultSet.getDouble(Freight.FreightFields.HEIGHT));
            freight.setEstimatedCost(resultSet.getInt(Freight.FreightFields.ESTIMATED_COST));
            freight.setType(Freight.FreightType.valueOf(resultSet.getString(Freight.FreightFields.FREIGHT_TYPE_NAME).toUpperCase()));
        }
        return freight;
    }

    @Override
    public List<Freight> getAll() throws DaoException {
        List<Freight> freights = new ArrayList<>();
        try(Connection connection = HikariCPDataSource.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.FreightQuery.SELECT_ALL_FREIGHTS);
            freights = getListOfFreights(freights, resultSet);
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return freights;
    }

    @Override
    public Freight create(Freight freight) throws DaoException {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.FreightQuery.ADD_FREIGHT, Statement.RETURN_GENERATED_KEYS);){
            statement.setDouble(1, freight.getWeight());
            statement.setDouble(2, freight.getLength());
            statement.setDouble(3, freight.getWidth());
            statement.setDouble(4, freight.getHeight());
            statement.setInt(5, freight.getEstimatedCost());
            statement.setInt(6, freight.getType().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                freight.setId(resultSet.getInt(1));
            }
            return freight;
        } catch (SQLException e) {
            //           logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Freight freight) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.FreightQuery.UPDATE_FREIGHT)) {
            statement.setDouble(1, freight.getWeight());
            statement.setDouble(2, freight.getLength());
            statement.setDouble(3, freight.getWidth());
            statement.setDouble(4, freight.getHeight());
            statement.setInt(5, freight.getEstimatedCost());
            statement.setInt(6, freight.getType().getId());
            statement.setInt(7, freight.getId());
            return  statement.executeUpdate() > 0;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Freight freight) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.FreightQuery.DELETE_FREIGHT)) {
            statement.setInt(1, freight.getId());
            return  statement.executeUpdate() > 0;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public List<Freight> getAllFreightsByType(Freight.FreightType type) throws DaoException {
        List<Freight> freights = new ArrayList<>();
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.FreightQuery.SELECT_FREIGHTS_BY_TYPE)) {
            statement.setString(1, type.toString().toLowerCase());
            ResultSet resultSet = statement.executeQuery();
            freights = getListOfFreights(freights, resultSet);
        } catch(SQLException e) {
            throw new DaoException(e);
        }
        return freights;
    }

    private List<Freight> getListOfFreights(List<Freight> freights, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id  = resultSet.getInt(Freight.FreightFields.ID);
            double weight = resultSet.getDouble(Freight.FreightFields.WEIGHT);
            double length = resultSet.getDouble(Freight.FreightFields.LENGTH);
            double width = resultSet.getDouble(Freight.FreightFields.WIDTH);
            double height = resultSet.getDouble(Freight.FreightFields.HEIGHT);
            int estimatedCost = resultSet.getInt(Freight.FreightFields.ESTIMATED_COST);
            Freight.FreightType type = Freight.FreightType.valueOf(resultSet.getString(Freight.FreightFields.FREIGHT_TYPE_NAME).toUpperCase());
            freights.add(new Freight(id, weight, length, width, height, estimatedCost, type));
        }
        return freights;
    }
}
