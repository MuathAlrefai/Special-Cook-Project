package refai.project.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Delivery {
    private String deliveryId;
    private String orderId;
    private String customerId;
    private Date scheduledTime;
    private String status; // "SCHEDULED", "IN_PROGRESS", "DELIVERED", "CANCELLED"
    private boolean isSpecialOrder;

    public Delivery() {
        this.deliveryId = "DEL-" + System.currentTimeMillis();
        this.status = "SCHEDULED";
        this.isSpecialOrder = false;
    }

    public Delivery(String customerId, String orderId, Date scheduledTime) {
        this();
        this.customerId = customerId;
        this.orderId = orderId;
        this.scheduledTime = scheduledTime;
    }

    public Delivery(String customerId, String orderId, String scheduledTimeStr) throws ParseException {
        this();
        this.customerId = customerId;
        this.orderId = orderId;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.scheduledTime = formatter.parse(scheduledTimeStr);
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

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

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSpecialOrder() {
        return isSpecialOrder;
    }

    public void setSpecialOrder(boolean specialOrder) {
        isSpecialOrder = specialOrder;
    }
}