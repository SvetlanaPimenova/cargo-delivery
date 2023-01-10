package ua.pimenova.model.database.entity;

import java.util.Date;

public class Order {
    private int id;
    private Date orderDate;
    private String cityFrom;
    private Freight freight;
    private int totalCost;
    private ExtraOptions.DeliveryType deliveryType;
    private Receiver receiver;
    private User sender;
    private PaymentStatus paymentStatus;
    private ExecutionStatus executionStatus;


    public enum PaymentStatus {
        PAID,
        UNPAID
    }
    public enum ExecutionStatus {
        IN_PROCESSING,
        FORMED,
        SENT,
        ARRIVED_AT_DESTINATION,
        DELIVERED
    }

    public Order() {
    }

    public Order(int id, Date orderDate, String cityFrom, Freight freight, int totalCost, ExtraOptions.DeliveryType deliveryType, Receiver receiver, User sender, PaymentStatus paymentStatus, ExecutionStatus executionStatus) {
        this.id = id;
        this.orderDate = orderDate;
        this.cityFrom = cityFrom;
        this.freight = freight;
        this.totalCost = totalCost;
        this.deliveryType = deliveryType;
        this.paymentStatus = paymentStatus;
        this.executionStatus = executionStatus;
        this.receiver = receiver;
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public Freight getFreight() {
        return freight;
    }

    public void setFreight(Freight freight) {
        this.freight = freight;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public ExtraOptions.DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(ExtraOptions.DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate + "}";
    }

    public static class OrderFields {
        public static final String ID = "id";
        public static final String DATE = "date";
        public static final String CITY_FROM = "city_from";
        public static final String TOTAL_COST = "total_cost";
        public static final String DELIVERY_TYPE_ID = "delivery_type_id";
        public static final String PAYMENT_STATUS = "payment_status";
        public static final String EXECUTION_STATUS = "execution_status";
    }
}
