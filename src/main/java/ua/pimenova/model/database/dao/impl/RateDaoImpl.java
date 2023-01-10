package ua.pimenova.model.database.dao.impl;

import ua.pimenova.model.database.dao.HikariCPDataSource;
import ua.pimenova.model.database.dao.RateDao;
import ua.pimenova.model.database.dao.SqlQuery;
import ua.pimenova.model.database.entity.Rate;
import ua.pimenova.model.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RateDaoImpl implements RateDao {
    @Override
    public Rate getByID(int id) throws DaoException {
        return null;
    }

    @Override
    public List<Rate> getAll() throws DaoException {
        List<Rate> rates = new ArrayList<>();
        try(Connection connection = HikariCPDataSource.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.RateQuery.SELECT_ALL_RATE);
            rates = getListOfRates(rates, resultSet);
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return rates;
    }

    private List<Rate> getListOfRates(List<Rate> rates, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            double weight = resultSet.getDouble(Rate.RateFields.WEIGHT);
            int fare = resultSet.getInt(Rate.RateFields.FARE);
            rates.add(new Rate(weight, fare));
        }
        return rates;
    }

    @Override
    public Rate create(Rate rate) throws DaoException {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlQuery.RateQuery.ADD_RATE);){
            statement.setDouble(1, rate.getWeight());
            statement.setInt(2, rate.getFare());
            statement.executeUpdate();
            return rate;
        } catch (SQLException e) {
            //           logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Rate rate) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.RateQuery.UPDATE_RATE)) {
            statement.setInt(1, rate.getFare());
            statement.setDouble(2, rate.getWeight());
            return  statement.executeUpdate() > 0;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Rate rate) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.RateQuery.DELETE_RATE)) {
            statement.setDouble(1, rate.getWeight());
            return  statement.executeUpdate() > 0;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public Rate getRateByWeight(double weight) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.RateQuery.SELECT_RATE_BY_WEIGHT)) {
            statement.setDouble(1, weight);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet != null) {
                return getRate(resultSet);
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return null;
    }
    private Rate getRate(ResultSet resultSet) throws SQLException {
        Rate rate = new Rate();
        while (resultSet.next()) {
            rate.setWeight(resultSet.getDouble(Rate.RateFields.WEIGHT));
            rate.setFare(resultSet.getInt(Rate.RateFields.FARE));
        }
        return rate;
    }
}
