package ua.pimenova.model.database.builder;

import ua.pimenova.model.database.entity.ExtraOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Defines all methods to build query to obtain sorted, ordered and limited list of entities
 *
 * @author Svetlana Pimenova
 * @version 1.0
 */
public class QueryBuilder {
    private final List<String> filters = new ArrayList<>();
    private String sortParameter;
    private String order;
    private int currentPage = 1;
    private int offset = 0;
    private int recordsPerPage = 4;

    public QueryBuilder() {}

    /**
     * Creates concrete filter for query
     * @param userId - user id for query
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setUserIdFilter(int userId) {
        filters.add("sender_info = " + userId);
        return this;
    }

    /**
     * Creates delivery type filter for query
     * @param deliveryFilter - can be 'to the branch' or 'courier'
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setDeliveryFilter(String deliveryFilter) {
        if (deliveryFilter != null && deliveryFilter.equals("to_the_branch")) {
            filters.add("delivery_type_id=" + ExtraOptions.DeliveryType.TO_THE_BRANCH.getId());
        } else if (deliveryFilter != null && deliveryFilter.equals("courier")) {
            filters.add("delivery_type_id=" + ExtraOptions.DeliveryType.COURIER.getId());
        }
        return this;
    }

    /**
     * Creates freight type filter for query
     * @param freightFilter - can be goods/glass/compact
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setFreightTypeFilter(String freightFilter) {
        if (freightFilter != null && !freightFilter.equals("")) {
            filters.add("`name` = \"" + freightFilter + "\"" );
        }
        return this;
    }

    /**
     * Creates payment status filter for query
     * @param paymentFilter can be either 'paid' or 'unpaid'
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setPaymentFilter(String paymentFilter) {
        if(paymentFilter != null && !paymentFilter.equals("")) {
            filters.add("payment_status = \"" + paymentFilter.toUpperCase() + "\"");
        }
        return this;
    }

    /**
     * Creates execution status filter for query
     * @param executionFilter - can be 'in processing'/'formed'/'sent'/'arrived at destination'/'delivered'
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setExecutionFilter(String executionFilter) {
        if(executionFilter != null && !executionFilter.equals("")) {
            filters.add("execution_status = \"" + executionFilter.toUpperCase() + "\"");
        }
        return this;
    }

    /**
     * Sets sort and order parameters
     * @param sortParameter - is split to define sort parameter and sorting order
     * @return QueryBuilder (as Builder pattern)
     */
    public QueryBuilder setSortParameter(String sortParameter) {
        if (sortParameter != null && !sortParameter.equals("") ) {
            String[] split = sortParameter.split("_");
            if(split[0].equalsIgnoreCase("cost")) {
                this.sortParameter = "total_cost";
            } else if(split[0].equalsIgnoreCase("date")) {
                this.sortParameter = "date";
            }
            this.order = split[1].toUpperCase();
        }
        return this;
    }

    /**
     * Sets limits for pagination
     * @param page - current page to calculate offset
     * @param records - number of records per page. Checks if valid, sets by default if not
     * @return
     */
    public QueryBuilder setLimits(String page, String records) {
        if (page != null && isPositiveInt(page)) {
            currentPage = Integer.parseInt(page);
        }
        if (records != null && isPositiveInt(records)) {
            recordsPerPage = Integer.parseInt(records);
        }
        offset = (currentPage - 1)*recordsPerPage;
        return this;
    }

    private boolean isPositiveInt(String intString) {
        try {
            int i = Integer.parseInt(intString);
            if (i < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * @return complete query to use in DAO to obtain list of entities
     */
    public String getQuery() {
        return getFilterQuery() + getSortQuery() + getLimitQuery();
    }

    /**
     * @return filter query to use in DAO to obtain number of records
     */
    public String getRecordQuery() {
        return getFilterQuery();
    }

    private String getFilterQuery() {
        if (filters.isEmpty()) {
            return "";
        }
        StringJoiner stringJoiner = new StringJoiner(" AND ", " WHERE ", " ");
        filters.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }

    private String getSortQuery() {
        if(sortParameter == null || sortParameter.equals("")) {
            return "";
        }
        return " ORDER BY " + sortParameter + " " + order;
    }

    private String getLimitQuery() {
        return " LIMIT " + offset + ", " + recordsPerPage;
    }
}
