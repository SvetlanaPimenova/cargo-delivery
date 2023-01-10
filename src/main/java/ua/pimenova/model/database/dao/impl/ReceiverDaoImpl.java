package ua.pimenova.model.database.dao.impl;

import ua.pimenova.model.database.dao.HikariCPDataSource;
import ua.pimenova.model.database.dao.ReceiverDao;
import ua.pimenova.model.database.dao.SqlQuery;
import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiverDaoImpl implements ReceiverDao {
    private static ReceiverDaoImpl instance = null;
    private ReceiverDaoImpl() {}

    public static synchronized ReceiverDaoImpl getInstance() {
        if(instance == null) {
            instance = new ReceiverDaoImpl();
        }
        return instance;
    }

    @Override
    public Receiver getByID(int id) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.ReceiverQuery.SELECT_RECEIVER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet != null) {
                return getReceiver(resultSet);
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return null;
    }

    private Receiver getReceiver(ResultSet resultSet) throws SQLException {
        Receiver receiver = null;
        while (resultSet.next()) {
            receiver = new Receiver();
            receiver.setId(resultSet.getInt(Receiver.ReceiverFields.ID));
            receiver.setFirstname(resultSet.getString(Receiver.ReceiverFields.FIRSTNAME));
            receiver.setLastname(resultSet.getString(Receiver.ReceiverFields.LASTNAME));
            receiver.setPhone(resultSet.getString(Receiver.ReceiverFields.PHONE));
            receiver.setCity(resultSet.getString(Receiver.ReceiverFields.CITY));
            receiver.setStreet(resultSet.getString(Receiver.ReceiverFields.STREET));
            receiver.setPostal_code(resultSet.getString(Receiver.ReceiverFields.POSTAL_CODE));
        }
        return receiver;
    }

    @Override
    public List<Receiver> getAll() throws DaoException {
        List<Receiver> receivers = new ArrayList<>();
        try(Connection connection = HikariCPDataSource.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.ReceiverQuery.SELECT_ALL_RECEIVERS);
            receivers = getListOfReceivers(receivers, resultSet);
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return receivers;
    }

    private List<Receiver> getListOfReceivers(List<Receiver> receivers, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id  = resultSet.getInt(Receiver.ReceiverFields.ID);
            String firstName = resultSet.getString(Receiver.ReceiverFields.FIRSTNAME);
            String lastName = resultSet.getString(Receiver.ReceiverFields.LASTNAME);
            String phone = resultSet.getString(Receiver.ReceiverFields.PHONE);
            String city = resultSet.getString(Receiver.ReceiverFields.CITY);
            String street = resultSet.getString(Receiver.ReceiverFields.STREET);
            String postal_code = resultSet.getString(Receiver.ReceiverFields.POSTAL_CODE);
            receivers.add(new Receiver(id, firstName, lastName, phone, city, street, postal_code));
        }
        return receivers;
    }

    @Override
    public Receiver create(Receiver receiver) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.ReceiverQuery.ADD_RECEIVER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, receiver.getFirstname());
            statement.setString(2, receiver.getLastname());
            statement.setString(3, receiver.getPhone());
            statement.setString(4, receiver.getCity());
            statement.setString(5, receiver.getStreet());
            statement.setString(6, receiver.getPostal_code());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                receiver.setId(resultSet.getInt(1));
            }
            return receiver;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Receiver receiver) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.ReceiverQuery.UPDATE_RECEIVER)) {
            statement.setString(1, receiver.getFirstname());
            statement.setString(2, receiver.getLastname());
            statement.setString(3, receiver.getPhone());
            statement.setString(4, receiver.getCity());
            statement.setString(5, receiver.getStreet());
            statement.setString(6, receiver.getPostal_code());
            statement.setInt(7, receiver.getId());
            return  statement.executeUpdate() > 0;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Receiver receiver) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.ReceiverQuery.DELETE_RECEIVER)) {
            statement.setInt(1, receiver.getId());
            return  statement.executeUpdate() > 0;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public Receiver getByPhone(String phone) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.ReceiverQuery.SELECT_RECEIVER_BY_PHONE)) {
            statement.setString(1, phone);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet != null) {
                return getReceiver(resultSet);
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return null;
    }

    @Override
    public List<Receiver> getAllReceiversByCity(String city) throws DaoException {
        List<Receiver> receivers = new ArrayList<>();
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.ReceiverQuery.SELECT_RECEIVERS_BY_CITY)) {
            statement.setString(1, city);
            ResultSet resultSet = statement.executeQuery();
            receivers = getListOfReceivers(receivers, resultSet);
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return receivers;
    }
}
