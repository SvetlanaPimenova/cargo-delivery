package ua.pimenova.model.database.builder;

import ua.pimenova.model.database.entity.ExtraOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class QueryBuilder {
    private final List<String> filters = new ArrayList<>();
    private String sortParameter;
    private String order;
    private int currentPage = 1;
    private int offset = 0;
    private int recordsPerPage = 4;

    public QueryBuilder() {}

    public QueryBuilder setUserIdFilter(int userId) {
        filters.add("sender_info = " + userId);
        return this;
    }
    public QueryBuilder setDeliveryFilter(String deliveryFilter) {
        if (deliveryFilter != null && deliveryFilter.equals("to_the_branch")) {
            filters.add("delivery_type_id=" + ExtraOptions.DeliveryType.TO_THE_BRANCH.getId());
        } else if (deliveryFilter != null && deliveryFilter.equals("courier")) {
            filters.add("delivery_type_id=" + ExtraOptions.DeliveryType.COURIER.getId());
        }
        return this;
    }
    public QueryBuilder setFreightTypeFilter(String freightFilter) {
        if (freightFilter != null && !freightFilter.equals("")) {
            filters.add("`name` = \"" + freightFilter + "\"" );
        }
        return this;
    }

    public QueryBuilder setPaymentFilter(String paymentFilter) {
        if(paymentFilter != null && !paymentFilter.equals("")) {
            filters.add("payment_status = \"" + paymentFilter.toUpperCase() + "\"");
        }
        return this;
    }

    public QueryBuilder setExecutionFilter(String executionFilter) {
        if(executionFilter != null && !executionFilter.equals("")) {
            filters.add("execution_status = \"" + executionFilter.toUpperCase() + "\"");
        }
        return this;
    }

    public QueryBuilder setSortParameter(String sortParameter) {
        if (sortParameter != null && !sortParameter.equals("") ) {
            String[] splited = sortParameter.split("_");
            if(splited[0].equalsIgnoreCase("cost")) {
                this.sortParameter = "total_cost";
            } else if(splited[0].equalsIgnoreCase("date")) {
                this.sortParameter = "date";
            }
            this.order = splited[1].toUpperCase();
        }
        return this;
    }

    public QueryBuilder setLimits(String page, String records) {
        if (page != null) {
            currentPage = Integer.parseInt(page);
        }
        if (records != null) {
            recordsPerPage = Integer.parseInt(records);
        }
        offset = (currentPage - 1)*recordsPerPage;
        return this;
    }

    public String getQuery() {
        return getFilterQuery() + getSortQuery() + getLimitQuery();
    }

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
