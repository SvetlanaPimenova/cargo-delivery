package ua.pimenova.model.database.dao.impl;

import ua.pimenova.model.database.dao.HikariCPDataSource;
import ua.pimenova.model.database.dao.SqlQuery;
import ua.pimenova.model.database.dao.UserDao;
import ua.pimenova.model.database.entity.User;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.util.EncryptingUserPassword;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static UserDaoImpl instance = null;

    private UserDaoImpl() {
    }

    public static synchronized UserDaoImpl getInstance() {
        if(instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public User getByID(int id) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.UsersQuery.SELECT_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet != null) {
                return getUser(resultSet);
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return null;
    }
    private User getUser(ResultSet resultSet) throws SQLException {
        User user = null;
        while (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt(User.UserFields.ID));
            user.setPassword(resultSet.getString(User.UserFields.PASSWORD));
            user.setFirstname(resultSet.getString(User.UserFields.FIRSTNAME));
            user.setLastname(resultSet.getString(User.UserFields.LASTNAME));
            user.setPhone(resultSet.getString(User.UserFields.PHONE));
            user.setEmail(resultSet.getString(User.UserFields.EMAIL));
            user.setAccount(resultSet.getInt(User.UserFields.ACCOUNT));
            user.setRole(User.Role.valueOf(resultSet.getString(User.UserFields.ROLE)));
            user.setCity(resultSet.getString(User.UserFields.CITY));
            user.setStreet(resultSet.getString(User.UserFields.STREET));
            user.setPostalCode(resultSet.getString(User.UserFields.POSTAL_CODE));
        }
        return user;
    }

    @Override
    public List<User> getAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try(Connection connection = HikariCPDataSource.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.UsersQuery.SELECT_ALL_USERS);
            users = getListOfUsers(users, resultSet);
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return users;
    }

    private List<User> getListOfUsers(List<User> users, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id  = resultSet.getInt(User.UserFields.ID);
            String password = resultSet.getString(User.UserFields.PASSWORD);
            String firstName = resultSet.getString(User.UserFields.FIRSTNAME);
            String lastName = resultSet.getString(User.UserFields.LASTNAME);
            String phone = resultSet.getString(User.UserFields.PHONE);
            String email = resultSet.getString(User.UserFields.EMAIL);
            int account  = resultSet.getInt(User.UserFields.ACCOUNT);
            User.Role role = User.Role.valueOf(resultSet.getString(User.UserFields.ROLE));
            String city = resultSet.getString(User.UserFields.CITY);
            String street = resultSet.getString(User.UserFields.STREET);
            String postalCode = resultSet.getString(User.UserFields.POSTAL_CODE);
            users.add(new User(id, password, firstName, lastName, phone, email, account, role, city, street, postalCode));
        }
        return users;
    }

    @Override
    public User create(User user) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.UsersQuery.ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getFirstname());
            statement.setString(3, user.getLastname());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getAccount());
            statement.setString(7, user.getRole().name());
            statement.setString(8, user.getCity());
            statement.setString(9, user.getStreet());
            statement.setString(10, user.getPostalCode());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(User user) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.UsersQuery.UPDATE_USER)) {
            statement.setString(1, user.getFirstname());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setInt(5, user.getAccount());
            statement.setString(6, user.getRole().name());
            statement.setString(7, user.getCity());
            statement.setString(8, user.getStreet());
            statement.setString(9, user.getPostalCode());
            statement.setInt(10, user.getId());
            return  statement.executeUpdate() > 0;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean updatePassword(User user) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.UsersQuery.UPDATE_USER_PASSWORD)) {
            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getId());
            return  statement.executeUpdate() > 0;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(User user) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.UsersQuery.DELETE_USER)) {
            statement.setInt(1, user.getId());
            return  statement.executeUpdate() > 0;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public User getByPhone(String phone) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.UsersQuery.SELECT_USER_BY_PHONE)) {
            statement.setString(1, phone);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet != null) {
                return getUser(resultSet);
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return null;
    }

    @Override
    public User getByEmail(String email) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.UsersQuery.SELECT_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet != null) {
                return getUser(resultSet);
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return null;
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.UsersQuery.SELECT_USER_BY_EMAIL_AND_PASSWORD)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet != null) {
                return getUser(resultSet);
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return null;
    }
}
