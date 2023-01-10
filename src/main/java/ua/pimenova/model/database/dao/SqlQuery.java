package ua.pimenova.model.database.dao;

public class SqlQuery {
    public static class FreightQuery {
        public static final String ADD_FREIGHT = "INSERT INTO freights (id, weight, length, width, height, estimated_cost, freight_type_id) "
                + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
        public static final String SELECT_ALL_FREIGHTS = "SELECT f.id, weight, length, width, height, estimated_cost, `name` FROM freights AS f "
                + "JOIN freight_type AS ft ON f.freight_type_id = ft.id";
        public static final String SELECT_FREIGHT_BY_ID = "SELECT f.id, weight, length, width, height, estimated_cost, `name` FROM freights AS f "
                + "JOIN freight_type AS ft ON f.freight_type_id = ft.id WHERE f.id = ?";
        public static final String SELECT_FREIGHTS_BY_TYPE = "SELECT f.id, weight, length, width, height, estimated_cost, `name` FROM freights AS f "
                + "JOIN freight_type AS ft ON f.freight_type_id = ft.id WHERE `name` = ?";
        public static final String UPDATE_FREIGHT = "UPDATE freights SET weight = ?, length = ?, width = ?, height = ?, estimated_cost = ?, "
                + "freight_type_id = ? WHERE id = ?";
        public static final String DELETE_FREIGHT = "DELETE FROM freights WHERE id = ?";
    }

    public static class UsersQuery {
        public static final String ADD_USER = "INSERT INTO users (id, password, firstname, lastname, phone, `e-mail`, account, role, city, street, postal_code) "
                + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        public static final String SELECT_ALL_USERS = "SELECT * FROM users WHERE `role` = 'USER'";
        public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
        public static final String SELECT_USER_BY_PHONE = "SELECT * FROM users WHERE phone = ?";
        public static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE `e-mail` = ?";
        public static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE `e-mail` = ? " +
                "AND `password` = ?";
        public static final String UPDATE_USER = "UPDATE users SET firstname = ?, lastname = ?, phone = ?, `e-mail` = ?, account = ?, role = ?, "
                + "city = ?, street = ?, postal_code = ? WHERE id = ?";
        public static final String UPDATE_USER_PASSWORD = "UPDATE users SET password = ? WHERE id = ?";
        public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    }

    public static class ReceiverQuery {
        public static final String ADD_RECEIVER = "INSERT INTO receivers (id, firstname, lastname, phone, city, street, postal_code) "
                + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
        public static final String SELECT_ALL_RECEIVERS = "SELECT * FROM receivers";
        public static final String SELECT_RECEIVER_BY_ID = "SELECT * FROM receivers WHERE receivers.id = ?";
        public static final String SELECT_RECEIVER_BY_PHONE = "SELECT * FROM receivers WHERE phone = ?";
        public static final String SELECT_RECEIVERS_BY_CITY = "SELECT * FROM receivers WHERE city = ?";
        public static final String UPDATE_RECEIVER = "UPDATE receivers SET firstname = ?, lastname = ?, phone = ?, city = ?, "
                + "street = ?, postal_code = ? WHERE id = ?";
        public static final String DELETE_RECEIVER = "DELETE FROM receivers WHERE id = ?";
    }
    public static class RateQuery {
        public static final String ADD_RATE = "INSERT INTO rate (weight, fare) VALUES (?, ?)";
        public static final String SELECT_ALL_RATE = "SELECT * FROM rate";
        public static final String SELECT_RATE_BY_WEIGHT = "SELECT * FROM rate WHERE weight = ?";
        public static final String UPDATE_RATE = "UPDATE rate SET fare = ? WHERE weight = ?";
        public static final String DELETE_RATE = "DELETE FROM rate WHERE weight = ?";
    }
    public static class OrdersQuery {
        public static final String ADD_ORDER = "INSERT INTO orders (id, date, city_from, freights_id, total_cost, delivery_type_id, receiver_info, sender_info, payment_status, execution_status) "
                + "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        public static final String SELECT_ALL_ORDERS = "SELECT o.id, o.date, o.city_from, f.id AS \"f.id\", f.weight, f.length, f.width, f.height, f.estimated_cost, ft.`name`, "
                + "o.total_cost, o.delivery_type_id, r.id AS \"r.id\", r.firstname AS \"r.firstname\", r.lastname AS \"r.lastname\", r.phone AS \"r.phone\", r.city AS \"r.city\", r.street AS \"r.street\", r.postal_code AS \"r.postal_code\", o.sender_info, u.password, u.firstname AS \"u.firstname\", "
                + "u.lastname AS \"u.lastname\", u.phone AS \"u.phone\", u.`e-mail`, u.account, u.role, u.city AS \"u.city\", u.street AS \"u.street\", u.postal_code AS \"u.postal_code\", o.payment_status, o.execution_status FROM orders AS o JOIN freights AS f ON o.freights_id = f.id "
                + "JOIN freight_type AS ft ON f.freight_type_id = ft.id JOIN receivers AS r ON o.receiver_info = r.id " +
                "JOIN users AS u ON o.sender_info = u.id";
        public static final String SELECT_ORDER_BY_ID = "SELECT o.id, o.date, o.city_from, f.id AS \"f.id\", f.weight, f.length, f.width, f.height, f.estimated_cost, ft.`name`, "
                + "o.total_cost, o.delivery_type_id, r.id AS \"r.id\", r.firstname AS \"r.firstname\", r.lastname AS \"r.lastname\", r.phone AS \"r.phone\", r.city AS \"r.city\", r.street AS \"r.street\", r.postal_code AS \"r.postal_code\", o.sender_info, u.password, u.firstname AS \"u.firstname\", "
                + "u.lastname AS \"u.lastname\", u.phone AS \"u.phone\", u.`e-mail`, u.account, u.role, u.city AS \"u.city\", u.street AS \"u.street\", u.postal_code AS \"u.postal_code\", o.payment_status, o.execution_status FROM orders AS o JOIN freights AS f ON o.freights_id = f.id "
                + "JOIN freight_type AS ft ON f.freight_type_id = ft.id JOIN receivers AS r ON o.receiver_info = r.id " +
                "JOIN users AS u ON o.sender_info = u.id WHERE o.id = ?";
        public static final String SELECT_ALL_ORDERS_BY_CITY_FROM = "SELECT o.id, o.date, o.city_from, f.id AS \"f.id\", f.weight, f.length, f.width, f.height, f.estimated_cost, ft.`name`, "
                + "o.total_cost, o.delivery_type_id, r.id AS \"r.id\", r.firstname AS \"r.firstname\", r.lastname AS \"r.lastname\", r.phone AS \"r.phone\", r.city AS \"r.city\", r.street AS \"r.street\", r.postal_code AS \"r.postal_code\", o.sender_info, u.password, u.firstname AS \"u.firstname\", "
                + "u.lastname AS \"u.lastname\", u.phone AS \"u.phone\", u.`e-mail`, u.account, u.role, u.city AS \"u.city\", u.street AS \"u.street\", u.postal_code AS \"u.postal_code\", o.payment_status, o.execution_status FROM orders AS o JOIN freights AS f ON o.freights_id = f.id "
                + "JOIN freight_type AS ft ON f.freight_type_id = ft.id JOIN receivers AS r ON o.receiver_info = r.id " +
                "JOIN users AS u ON o.sender_info = u.id WHERE o.city_from = ?";
        public static final String SELECT_ALL_ORDERS_BY_DATE = "SELECT o.id, o.date, o.city_from, f.id AS \"f.id\", f.weight, f.length, f.width, f.height, f.estimated_cost, ft.`name`, "
                + "o.total_cost, o.delivery_type_id, r.id AS \"r.id\", r.firstname AS \"r.firstname\", r.lastname AS \"r.lastname\", r.phone AS \"r.phone\", r.city AS \"r.city\", r.street AS \"r.street\", r.postal_code AS \"r.postal_code\", o.sender_info, u.password, u.firstname AS \"u.firstname\", "
                + "u.lastname AS \"u.lastname\", u.phone AS \"u.phone\", u.`e-mail`, u.account, u.role, u.city AS \"u.city\", u.street AS \"u.street\", u.postal_code AS \"u.postal_code\", o.payment_status, o.execution_status FROM orders AS o JOIN freights AS f ON o.freights_id = f.id "
                + "JOIN freight_type AS ft ON f.freight_type_id = ft.id JOIN receivers AS r ON o.receiver_info = r.id " +
                "JOIN users AS u ON o.sender_info = u.id WHERE o.date = ?";
        public static final String SELECT_ALL_ORDERS_BY_RECEIVER = "SELECT o.id, o.date, o.city_from, f.id AS \"f.id\", f.weight, f.length, f.width, f.height, f.estimated_cost, ft.`name`, "
                + "o.total_cost, o.delivery_type_id, r.id AS \"r.id\", r.firstname AS \"r.firstname\", r.lastname AS \"r.lastname\", r.phone AS \"r.phone\", r.city AS \"r.city\", r.street AS \"r.street\", r.postal_code AS \"r.postal_code\", o.sender_info, u.password, u.firstname AS \"u.firstname\", "
                + "u.lastname AS \"u.lastname\", u.phone AS \"u.phone\", u.`e-mail`, u.account, u.role, u.city AS \"u.city\", u.street AS \"u.street\", u.postal_code AS \"u.postal_code\", o.payment_status, o.execution_status FROM orders AS o JOIN freights AS f ON o.freights_id = f.id "
                + "JOIN freight_type AS ft ON f.freight_type_id = ft.id JOIN receivers AS r ON o.receiver_info = r.id " +
                "JOIN users AS u ON o.sender_info = u.id WHERE r.id = ?";
        public static final String SELECT_ALL_ORDERS_BY_SENDER = "SELECT o.id, o.date, o.city_from, f.id AS \"f.id\", f.weight, f.length, f.width, f.height, f.estimated_cost, ft.`name`, "
                + "o.total_cost, o.delivery_type_id, r.id AS \"r.id\", r.firstname AS \"r.firstname\", r.lastname AS \"r.lastname\", r.phone AS \"r.phone\", r.city AS \"r.city\", r.street AS \"r.street\", r.postal_code AS \"r.postal_code\", o.sender_info, u.password, u.firstname AS \"u.firstname\", "
                + "u.lastname AS \"u.lastname\", u.phone AS \"u.phone\", u.`e-mail`, u.account, u.role, u.city AS \"u.city\", u.street AS \"u.street\", u.postal_code AS \"u.postal_code\", o.payment_status, o.execution_status FROM orders AS o JOIN freights AS f ON o.freights_id = f.id "
                + "JOIN freight_type AS ft ON f.freight_type_id = ft.id JOIN receivers AS r ON o.receiver_info = r.id " +
                "JOIN users AS u ON o.sender_info = u.id WHERE u.id = ?";
        public static final String UPDATE_ORDER = "UPDATE orders SET delivery_type_id = ?, total_cost = ?, payment_status = ?, execution_status = ? WHERE id = ?";
        public static final String DELETE_ORDER = "DELETE FROM orders WHERE id = ?";
        public static final String NUMBER_OF_ROWS = "SELECT COUNT(id) FROM orders";
    }
}
