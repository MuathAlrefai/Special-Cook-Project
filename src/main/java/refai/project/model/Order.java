package refai.project.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String orderId;
    private String customerId;
    private List<OrderItem> items;
    private double totalAmount;
    private String status;
    private Date orderDate;
    private Date modifiedDate;

    public Order() {
        this.items = new ArrayList<>();
        this.orderDate = new Date();
    }

    public Order(String orderId, String customerId) {
        this();
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = "PENDING";
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
        calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        this.totalAmount = items.stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
        calculateTotalAmount();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}