package ua.pimenova.model.database.dao.impl;

import ua.pimenova.model.database.dao.HikariCPDataSource;
import ua.pimenova.model.database.dao.OrderDao;
import ua.pimenova.model.database.dao.SqlQuery;
import ua.pimenova.model.database.entity.*;
import ua.pimenova.model.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private static OrderDaoImpl instance = null;

    private FreightDaoImpl freightDao;
    private ReceiverDaoImpl receiverDao;

    private OrderDaoImpl() {
    }

    public static synchronized OrderDaoImpl getInstance() {
        if(instance == null) {
            instance = new OrderDaoImpl();
        }
        return instance;
    }

    @Override
    public Order getByID(int id) throws DaoException {
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.OrdersQuery.SELECT_ORDER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet != null) {
                return getOrder(resultSet);
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return null;
    }
    private Order getOrder(ResultSet resultSet) throws SQLException {
        Order order = null;
        while (resultSet.next()) {
            order = new Order();
            order.setId(resultSet.getInt(Order.OrderFields.ID));
            order.setOrderDate(resultSet.getDate(Order.OrderFields.DATE));
            order.setCityFrom(resultSet.getString(Order.OrderFields.CITY_FROM));
            order.setFreight(new Freight(resultSet.getInt("f.id"),
                    resultSet.getDouble(Freight.FreightFields.WEIGHT),
                    resultSet.getDouble(Freight.FreightFields.LENGTH),
                    resultSet.getDouble(Freight.FreightFields.WIDTH),
                    resultSet.getDouble(Freight.FreightFields.HEIGHT),
                    resultSet.getInt(Freight.FreightFields.ESTIMATED_COST),
                    Freight.FreightType.valueOf(resultSet.getString(Freight.FreightFields.FREIGHT_TYPE_NAME).toUpperCase())));
            order.setTotalCost(resultSet.getInt(Order.OrderFields.TOTAL_COST));
            order.setDeliveryType(ExtraOptions.DeliveryType.getTypeById(resultSet.getInt(Order.OrderFields.DELIVERY_TYPE_ID)));
            order.setReceiver(new Receiver(resultSet.getInt("r.id"),
                    resultSet.getString("r.firstname"),
                    resultSet.getString("r.lastname"),
                    resultSet.getString("r.phone"),
                    resultSet.getString("r.city"),
                    resultSet.getString("r.street"),
                    resultSet.getString("r.postal_code")));
            order.setSender(new User(resultSet.getInt("sender_info"),
                    resultSet.getString(User.UserFields.PASSWORD),
                    resultSet.getString("u.firstname"),
                    resultSet.getString("u.lastname"),
                    resultSet.getString("u.phone"),
                    resultSet.getString(User.UserFields.EMAIL),
                    resultSet.getInt(User.UserFields.ACCOUNT),
                    User.Role.valueOf(resultSet.getString(User.UserFields.ROLE)),
                    resultSet.getString("u.city"),
                    resultSet.getString("u.street"),
                    resultSet.getString("u.postal_code")));
            order.setPaymentStatus(Order.PaymentStatus.valueOf(resultSet.getString(Order.OrderFields.PAYMENT_STATUS)));
            order.setExecutionStatus(Order.ExecutionStatus.valueOf(resultSet.getString(Order.OrderFields.EXECUTION_STATUS)));
        }
        return order;
    }

    private List<Order> getListOfOrders(List<Order> orders, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int orderId = resultSet.getInt(Order.OrderFields.ID);
            Date orderDate  = resultSet.getDate(Order.OrderFields.DATE);
            String cityFrom = resultSet.getString(Order.OrderFields.CITY_FROM);
            Freight freight = new Freight(resultSet.getInt("f.id"),
                    resultSet.getDouble(Freight.FreightFields.WEIGHT),
                    resultSet.getDouble(Freight.FreightFields.LENGTH),
                    resultSet.getDouble(Freight.FreightFields.WIDTH),
                    resultSet.getDouble(Freight.FreightFields.HEIGHT),
                    resultSet.getInt(Freight.FreightFields.ESTIMATED_COST),
                    Freight.FreightType.valueOf(resultSet.getString(Freight.FreightFields.FREIGHT_TYPE_NAME).toUpperCase()));
            int totalCost = resultSet.getInt(Order.OrderFields.TOTAL_COST);
            ExtraOptions.DeliveryType type = ExtraOptions.DeliveryType.getTypeById(resultSet.getInt(Order.OrderFields.DELIVERY_TYPE_ID));
            Receiver receiver = new Receiver(resultSet.getInt("r.id"),
                    resultSet.getString("r.firstname"),
                    resultSet.getString("r.lastname"),
                    resultSet.getString("r.phone"),
                    resultSet.getString("r.city"),
                    resultSet.getString("r.street"),
                    resultSet.getString("r.postal_code"));
            User sender = new User(resultSet.getInt("sender_info"),
                    resultSet.getString(User.UserFields.PASSWORD),
                    resultSet.getString("u.firstname"),
                    resultSet.getString("u.lastname"),
                    resultSet.getString("u.phone"),
                    resultSet.getString(User.UserFields.EMAIL),
                    resultSet.getInt(User.UserFields.ACCOUNT),
                    User.Role.valueOf(resultSet.getString(User.UserFields.ROLE)),
                    resultSet.getString("u.city"),
                    resultSet.getString("u.street"),
                    resultSet.getString("u.postal_code"));
            Order.PaymentStatus paymentStatus = Order.PaymentStatus.valueOf(resultSet.getString(Order.OrderFields.PAYMENT_STATUS));
            Order.ExecutionStatus executionStatus = Order.ExecutionStatus.valueOf(resultSet.getString(Order.OrderFields.EXECUTION_STATUS));
            orders.add(new Order(orderId, orderDate, cityFrom, freight, totalCost, type, receiver, sender, paymentStatus, executionStatus));
        }
        return orders;
    }

    @Override
    public List<Order> getAll() throws DaoException {
        List<Order> orders = new ArrayList<>();

        try(Connection connection = HikariCPDataSource.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.OrdersQuery.SELECT_ALL_ORDERS);
            orders = getListOfOrders(orders, resultSet);
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public Order create(Order order) throws DaoException {
        Connection connection = null;
        try {
            connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.OrdersQuery.ADD_ORDER, Statement.RETURN_GENERATED_KEYS);
            connection.setAutoCommit(false);
            freightDao = FreightDaoImpl.getInstance();
            receiverDao = ReceiverDaoImpl.getInstance();
            statement.setDate(1, new java.sql.Date(order.getOrderDate().getTime()));
            statement.setString(2, order.getCityFrom());
            Freight freight = freightDao.create(order.getFreight());
            statement.setInt(3, freight.getId());
            statement.setInt(4, order.getTotalCost());
            statement.setInt(5, order.getDeliveryType().getId());
            String phone = order.getReceiver().getPhone();
            if(receiverDao.getByPhone(phone) != null) {
                Receiver receiver = receiverDao.getByPhone(phone);
                statement.setInt(6, receiver.getId());
            } else {
                Receiver receiver = receiverDao.create(order.getReceiver());
                statement.setInt(6, receiver.getId());
            }
            statement.setInt(7, order.getSender().getId());
            statement.setString(8, order.getPaymentStatus().name());
            statement.setString(9, order.getExecutionStatus().name());
            statement.executeUpdate();
            connection.commit();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                order.setId(resultSet.getInt(1));
            }
            return order;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
            throw new DaoException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
        }
    }

    @Override
    public boolean update(Order order) throws DaoException {
        Connection connection = null;
        try {
            connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.OrdersQuery.UPDATE_ORDER);
            connection.setAutoCommit(false);
            freightDao = FreightDaoImpl.getInstance();
            receiverDao = ReceiverDaoImpl.getInstance();
            freightDao.update(order.getFreight());
            receiverDao.update(order.getReceiver());
            statement.setInt(1, order.getDeliveryType().getId());
            statement.setInt(2, order.getTotalCost());
            statement.setString(3, order.getPaymentStatus().name());
            statement.setString(4, order.getExecutionStatus().name());
            statement.setInt(5, order.getId());
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
            throw new DaoException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
        }
    }

    @Override
    public boolean delete(Order order) throws DaoException {
        Connection connection = null;
        try {
            connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.OrdersQuery.DELETE_ORDER);
            connection.setAutoCommit(false);
            freightDao = FreightDaoImpl.getInstance();
            receiverDao = ReceiverDaoImpl.getInstance();
            freightDao.delete(order.getFreight());
            receiverDao.delete(order.getReceiver());
            statement.setInt(1, order.getId());
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
            throw new DaoException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
        }
    }

    @Override
    public List<Order> getAllOrdersByDate(Date date) throws DaoException {
        List<Order> orders = new ArrayList<>();
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.OrdersQuery.SELECT_ALL_ORDERS_BY_DATE)) {
            statement.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
            orders = getListOfOrders(orders, resultSet);
        } catch(SQLException e) {
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getAll(String query) throws DaoException {
        List<Order> orders = new ArrayList<>();

        try(Connection connection = HikariCPDataSource.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.OrdersQuery.SELECT_ALL_ORDERS + query);
            orders = getListOfOrders(orders, resultSet);
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrdersByReceiver(Receiver receiver) throws DaoException {
        List<Order> orders = new ArrayList<>();
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.OrdersQuery.SELECT_ALL_ORDERS_BY_RECEIVER)) {
            statement.setInt(1, receiver.getId());
            ResultSet resultSet = statement.executeQuery();
            orders = getListOfOrders(orders, resultSet);
        } catch(SQLException e) {
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrdersBySender(User user) throws DaoException {
        List<Order> orders = new ArrayList<>();
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.OrdersQuery.SELECT_ALL_ORDERS_BY_SENDER)) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            orders = getListOfOrders(orders, resultSet);
        } catch(SQLException e) {
            throw new DaoException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrdersByCityFrom(String city) throws DaoException {
        List<Order> orders = new ArrayList<>();
        try(Connection connection = HikariCPDataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlQuery.OrdersQuery.SELECT_ALL_ORDERS_BY_CITY_FROM)) {
            statement.setString(1, city);
            ResultSet resultSet = statement.executeQuery();
            orders = getListOfOrders(orders, resultSet);
        } catch(SQLException e) {
            throw new DaoException(e);
        }
        return orders;
    }
    @Override
    public int getNumberOfRows(String query) throws DaoException {
        int numOfRows = 0;
        try(Connection connection = HikariCPDataSource.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQuery.OrdersQuery.NUMBER_OF_ROWS + query);
            while (resultSet.next()) {
                numOfRows = resultSet.getInt(1);
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return numOfRows;
    }
}
