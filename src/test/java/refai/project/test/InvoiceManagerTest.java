package refai.project.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import refai.project.manager.InvoiceManager;
import refai.project.model.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceManagerTest {
    private InvoiceManager invoiceManager;
    private Customer customer;
    private Order order;

    @BeforeEach
    void setUp() {
        invoiceManager = new InvoiceManager();
        customer = new Customer("CUST-001");
        customer.setPreferredInvoiceFormat("PDF");

        OrderItem item1 = new OrderItem("ITEM-1", "Tofu Stir Fry", 1, 15.99);
        OrderItem item2 = new OrderItem("ITEM-2", "Green Tea", 2, 3.50);
        order = new Order("ORDER-001", "CUST-001");
        order.setItems(Arrays.asList(item1, item2));
        // Total amount should be calculated as (15.99 + (2 * 3.50)) = 22.99
    }

    @Test
    void generateInvoice_shouldCreateValidInvoice() {
        Invoice invoice = invoiceManager.generateInvoice("CUST-001", "ORDER-001", order);

        assertNotNull(invoice);
        assertEquals("CUST-001", invoice.getCustomerId());
        assertEquals(2, invoice.getItems().size());
        assertEquals(22.99, invoice.getTotalAmount(), 0.001); // Using delta for double comparison
    }

    @Test
    void updateInvoice_shouldUpdateExistingInvoice() {
        Invoice original = invoiceManager.generateInvoice("CUST-001", "ORDER-001", order);

        // Modify order
        Order modifiedOrder = new Order("ORDER-001", "CUST-001");
        modifiedOrder.setItems(List.of(
                new OrderItem("ITEM-1", "Tofu Stir Fry", 2, 15.99) // Quantity changed to 2
        ));
        // New total should be (2 * 15.99) = 31.98

        Invoice updated = invoiceManager.updateInvoice(original.getInvoiceId(), modifiedOrder);

        assertNotNull(updated);
        assertEquals("UPDATED", updated.getStatus());
        assertEquals(1, updated.getItems().size());
        assertEquals(31.98, updated.getTotalAmount(), 0.001);
        assertNotNull(updated.getModifiedDate());
    }

    @Test
    void updateInvoice_shouldReturnNullForInvalidId() {
        Invoice result = invoiceManager.updateInvoice("INVALID-ID", order);
        assertNull(result);
    }

    @Test
    void sendInvoice_shouldSetFormatAndStatus() {
        Invoice invoice = invoiceManager.generateInvoice("CUST-001", "ORDER-001", order);

        boolean result = invoiceManager.sendInvoice(invoice, customer);

        assertTrue(result);
        assertEquals("PDF", invoice.getFormat());
        assertEquals("SENT", invoice.getStatus());
    }

    @Test
    void sendInvoice_shouldNotChangeUpdatedStatus() {
        Invoice invoice = invoiceManager.generateInvoice("CUST-001", "ORDER-001", order);
        invoice.setStatus("UPDATED");

        boolean result = invoiceManager.sendInvoice(invoice, customer);

        assertTrue(result);
        assertEquals("UPDATED", invoice.getStatus());
    }

    @Test
    void getInvoice_shouldReturnInvoiceWhenExists() {
        Invoice expected = invoiceManager.generateInvoice("CUST-001", "ORDER-001", order);
        Invoice actual = invoiceManager.getInvoice(expected.getInvoiceId());
        assertEquals(expected, actual);
    }

    @Test
    void getInvoice_shouldReturnNullWhenNotExists() {
        assertNull(invoiceManager.getInvoice("NON-EXISTENT"));
    }
}