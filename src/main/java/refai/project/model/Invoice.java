package refai.project.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {
    private String invoiceId;
    private String customerId;
    private String orderId;
    private double totalAmount;
    private List<InvoiceItem> items;
    private String status;
    private String format;
    private Date createdDate;
    private Date modifiedDate;

    public Invoice() {
        this.items = new ArrayList<>();
        this.createdDate = new Date();
        this.status = "DRAFT";
    }

    public Invoice(String customerId, String orderId) {
        this();
        this.customerId = customerId;
        this.orderId = orderId;
        this.invoiceId = "INV-" + System.currentTimeMillis();
    }

    //getters and setters
    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
