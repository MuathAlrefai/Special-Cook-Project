package refai.project.test;

import refai.project.manager.InvoiceManager;
import refai.project.model.Customer;
import refai.project.model.Invoice;
import refai.project.model.Order;
import refai.project.model.OrderItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceManagerTest {

    @Test
    public void testGenerateInvoice() {
        InvoiceManager invoiceManager = new InvoiceManager();

        OrderItem item = new OrderItem("1", "Pizza", 2, 10.0);

        Order order = new Order("ORDER123", "CUSTOMER001");
        order.addItem(item); 

        Invoice invoice = invoiceManager.generateInvoice("CUSTOMER001", "ORDER123", order);

        assertNotNull(invoice);
        assertEquals("CUSTOMER001", invoice.getCustomerId());
        assertEquals("ORDER123", invoice.getOrderId());
        assertEquals(20.0, invoice.getTotalAmount(), 0.001);
        assertEquals(1, invoice.getItems().size());
    }
    
    @Test
    public void testUpdateInvoice_Success() {
        InvoiceManager invoiceManager = new InvoiceManager();

        Order originalOrder = new Order("ORDER1", "CUSTOMER001");
        originalOrder.addItem(new OrderItem("1", "Burger", 1, 8.0));
        Invoice invoice = invoiceManager.generateInvoice("CUSTOMER001", "ORDER1", originalOrder);
        String invoiceId = invoice.getInvoiceId();

        Order modifiedOrder = new Order("ORDER1", "CUSTOMER001");
        modifiedOrder.addItem(new OrderItem("2", "Juice", 2, 5.0)); 

        Invoice updatedInvoice = invoiceManager.updateInvoice(invoiceId, modifiedOrder);

        assertNotNull(updatedInvoice);
        assertEquals(10.0, updatedInvoice.getTotalAmount(), 0.001);
        assertEquals("UPDATED", updatedInvoice.getStatus());
        assertEquals(1, updatedInvoice.getItems().size());
        assertEquals("Juice", updatedInvoice.getItems().get(0).getProductName());
    }
    
    @Test
    public void testUpdateInvoice_NotFound() {
        InvoiceManager invoiceManager = new InvoiceManager();

        Order modifiedOrder = new Order("fakeOrder", "CUSTOMERX");
        modifiedOrder.addItem(new OrderItem("99", "Unknown", 1, 1.0));

        Invoice result = invoiceManager.updateInvoice("invalid-invoice-id", modifiedOrder);
        assertNull(result);
    }
    
    @Test
    public void testSendInvoice_StatusSent() {
        InvoiceManager invoiceManager = new InvoiceManager();

        Order order = new Order("ORDER1", "CUSTOMER001");
        order.addItem(new OrderItem("1", "Pizza", 1, 10.0));
        Invoice invoice = invoiceManager.generateInvoice("CUSTOMER001", "ORDER1", order);

        Customer customer = new Customer("CUSTOMER001");
        customer.setPreferredInvoiceFormat("PDF");

        boolean result = invoiceManager.sendInvoice(invoice, customer);

        assertTrue(result);
        assertEquals("PDF", invoice.getFormat());
        assertEquals("SENT", invoice.getStatus());
    }
    
    @Test
    public void testSendInvoice_StatusRemainsUpdated() {
        InvoiceManager invoiceManager = new InvoiceManager();

        Order order = new Order("ORDER1", "CUSTOMER002");
        order.addItem(new OrderItem("2", "Pasta", 2, 7.5));
        Invoice invoice = invoiceManager.generateInvoice("CUSTOMER002", "ORDER1", order);

        invoice.setStatus("UPDATED");

        Customer customer = new Customer("CUSTOMER002");
        customer.setPreferredInvoiceFormat("HTML");

        boolean result = invoiceManager.sendInvoice(invoice, customer);

        assertTrue(result);
        assertEquals("HTML", invoice.getFormat());
        assertEquals("UPDATED", invoice.getStatus()); 
    }
    
    @Test
    public void testGetInvoice() {
        InvoiceManager invoiceManager = new InvoiceManager();

        Order order = new Order("ORDERX", "CUSTOMER007");
        order.addItem(new OrderItem("7", "Soup", 1, 4.0));
        Invoice invoice = invoiceManager.generateInvoice("CUSTOMER007", "ORDERX", order);

        Invoice retrieved = invoiceManager.getInvoice(invoice.getInvoiceId());
        assertNotNull(retrieved);
        assertEquals(invoice.getInvoiceId(), retrieved.getInvoiceId());
    }





}
