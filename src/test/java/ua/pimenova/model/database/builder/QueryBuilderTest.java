package ua.pimenova.model.database.builder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ua.pimenova.model.database.entity.ExtraOptions;

import static org.junit.jupiter.api.Assertions.*;

class QueryBuilderTest {

    QueryBuilder queryBuilder = new QueryBuilder();

    @Test
    void testSetUserIdFilter() {
        String query = queryBuilder.setUserIdFilter(10).getQuery();
        assertTrue(query.contains("WHERE sender_info = " + 10 + " "));
    }

    @Test
    void testSetUserIdFilterNoFilter() {
        String query = queryBuilder.getQuery();
        assertFalse(query.contains("WHERE sender_info = "));
    }

    @ParameterizedTest
    @ValueSource(strings = {"courier", "to_the_branch"})
    void testSetDeliveryFilter(String deliveryFilter) {
        String query = queryBuilder.setDeliveryFilter(deliveryFilter).getQuery();
        assertTrue(query.contains("WHERE delivery_type_id=" + ExtraOptions.DeliveryType.valueOf(deliveryFilter.toUpperCase()).getId()));
    }

    @Test
    void testSetDeliveryFilterNoFilter() {
        String query = queryBuilder.getQuery();
        assertFalse(query.contains("WHERE delivery_type_id="));
    }

    @ParameterizedTest
    @ValueSource(strings = {"goods", "glass", "compact"})
    void testSetFreightTypeFilter(String freightFilter) {
        String query = queryBuilder.setFreightTypeFilter(freightFilter).getQuery();
        assertTrue(query.contains("WHERE `name` = \"" + freightFilter + "\""));
    }

    @Test
    void testSetFreightTypeFilterNoFilter() {
        String query = queryBuilder.getQuery();
        assertFalse(query.contains("WHERE `name` ="));
    }

    @ParameterizedTest
    @ValueSource(strings = {"paid", "unpaid"})
    void testSetPaymentFilter(String paymentFilter) {
        String query = queryBuilder.setPaymentFilter(paymentFilter).getQuery();
        assertTrue(query.contains("WHERE payment_status = \"" + paymentFilter.toUpperCase() + "\""));
    }

    @Test
    void testSetPaymentFilterNoFilter() {
        String query = queryBuilder.getQuery();
        assertFalse(query.contains("WHERE payment_status ="));
    }

    @ParameterizedTest
    @ValueSource(strings = {"in_processing", "formed", "sent", "arrived_at_destination", "delivered"})
    void testSetExecutionFilter(String executionFilter) {
        String query = queryBuilder.setExecutionFilter(executionFilter).getQuery();
        assertTrue(query.contains("WHERE execution_status = \"" + executionFilter.toUpperCase() + "\""));
    }

    @Test
    void testSetExecutionFilterNoFilter() {
        String query = queryBuilder.getQuery();
        assertFalse(query.contains("WHERE execution_status ="));
    }

    @Test
    void testSetSortParameterDateAsc() {
       String query = queryBuilder.setSortParameter("date_asc").getQuery();
       assertTrue(query.contains("ORDER BY date ASC"));
    }

    @Test
    void testSetSortParameterDateDesc() {
        String query = queryBuilder.setSortParameter("date_desc").getQuery();
        assertTrue(query.contains("ORDER BY date DESC"));
    }

    @Test
    void testSetSortParameterCostAsc() {
        String query = queryBuilder.setSortParameter("cost_asc").getQuery();
        assertTrue(query.contains("ORDER BY total_cost ASC"));
    }

    @Test
    void testSetSortParameterCostDesc() {
        String query = queryBuilder.setSortParameter("cost_desc").getQuery();
        assertTrue(query.contains("ORDER BY total_cost DESC"));
    }

    @Test
    void testSetLimitsNoLimits() {
        String query = queryBuilder.getQuery();
        assertTrue(query.contains("LIMIT 0, 4"));
    }

    @Test
    void testSetLimits() {
        String query = queryBuilder.setLimits("3", "8").getQuery();
        assertTrue(query.contains("LIMIT 16, 8"));
    }

    @Test
    void testSetWrongLimits() {
        String query = queryBuilder.setLimits("a", "b").getQuery();
        assertTrue(query.contains("LIMIT 0, 4"));
    }

    @Test
    void testSetNegativeLimits() {
        String query = queryBuilder.setLimits("-5", "-10").getQuery();
        assertTrue(query.contains("LIMIT 0, 4"));
    }

    @Test
    void testGetQuery() {
        String check = " WHERE sender_info = 10 AND delivery_type_id=1 AND `name` = \"glass\" "
        + "AND payment_status = \"PAID\" AND execution_status = \"SENT\"  ORDER BY total_cost DESC LIMIT 3, 3";
        String query = queryBuilder
                .setUserIdFilter(10)
                .setDeliveryFilter("to_the_branch")
                .setFreightTypeFilter("glass")
                .setPaymentFilter("paid")
                .setExecutionFilter("sent")
                .setSortParameter("cost_desc")
                .setLimits("2", "3")
                .getQuery();
        assertEquals(check, query);
    }

    @Test
    void getRecordQuery() {
        String check = " WHERE sender_info = 10 AND delivery_type_id=2 AND `name` = \"goods\" "
                + "AND payment_status = \"PAID\" AND execution_status = \"FORMED\" ";
        String query = queryBuilder
                .setUserIdFilter(10)
                .setDeliveryFilter("courier")
                .setFreightTypeFilter("goods")
                .setPaymentFilter("paid")
                .setExecutionFilter("formed")
                .getRecordQuery();
        assertEquals(check, query);
    }
}