package refai.project.manager;

import refai.project.model.Customer;
import refai.project.model.Invoice;
import refai.project.model.InvoiceItem;
import refai.project.model.Order;
import refai.project.model.OrderItem;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InvoiceManager {
    private Map<String, Invoice> invoices = new HashMap<>();

    public Invoice generateInvoice(String customerId, String orderId, Order order) {
        Invoice invoice = new Invoice(customerId, orderId);

        //convert OrderItems to InvoiceItems
        for (OrderItem orderItem : order.getItems()) {
            InvoiceItem invoiceItem = new InvoiceItem(
                    orderItem.getProductId(),
                    orderItem.getProductName(),
                    orderItem.getQuantity(),
                    orderItem.getUnitPrice()
            );
            invoice.getItems().add(invoiceItem);
        }

        invoice.setTotalAmount(order.getTotalAmount());
        invoices.put(invoice.getInvoiceId(), invoice);

        return invoice;
    }

    public Invoice updateInvoice(String invoiceId, Order modifiedOrder) {
        Invoice invoice = invoices.get(invoiceId);

        if (invoice != null) {
            //update invoice items based on modified order
            invoice.setItems(modifiedOrder.getItems().stream()
                    .map(orderItem -> new InvoiceItem(
                            orderItem.getProductId(),
                            orderItem.getProductName(),
                            orderItem.getQuantity(),
                            orderItem.getUnitPrice()
                    ))
                    .collect(Collectors.toList()));

            invoice.setTotalAmount(modifiedOrder.getTotalAmount());
            invoice.setStatus("UPDATED");
            invoice.setModifiedDate(new Date());

            return invoice;
        }

        return null;
    }

    public boolean sendInvoice(Invoice invoice, Customer customer) {
        //in a real implementation, this would send the invoice via email or other means
        //set the format based on customer preference
        invoice.setFormat(customer.getPreferredInvoiceFormat());

        //only change the status to SENT if it's not already UPDATED
        if (!"UPDATED".equals(invoice.getStatus())) {
            invoice.setStatus("SENT");
        }

        //return true to indicate successful sending
        return true;
    }

    public Invoice getInvoice(String invoiceId) {
        return invoices.get(invoiceId);
    }
}