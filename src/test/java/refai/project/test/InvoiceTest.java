package refai.project.test;

import refai.project.model.Invoice;
import refai.project.model.InvoiceItem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {

    @Test
    public void testInvoiceSettersAndGetters() {
        Invoice invoice = new Invoice();

        invoice.setInvoiceId("INVOICE-999");
        invoice.setCustomerId("CUSTOMER-1");
        invoice.setOrderId("ORDER-1");
        invoice.setTotalAmount(250.75);

        List<InvoiceItem> items = new ArrayList<>();
        invoice.setItems(items);

        invoice.setStatus("PAID");
        invoice.setFormat("PDF");

        Date created = new Date();
        Date modified = new Date(created.getTime() + 10000);
        invoice.setCreatedDate(created);
        invoice.setModifiedDate(modified);

        assertEquals("INVOICE-999", invoice.getInvoiceId());
        assertEquals("CUSTOMER-1", invoice.getCustomerId());
        assertEquals("ORDER-1", invoice.getOrderId());
        assertEquals(250.75, invoice.getTotalAmount());
        assertEquals(items, invoice.getItems());
        assertEquals("PAID", invoice.getStatus());
        assertEquals("PDF", invoice.getFormat());
        assertEquals(created, invoice.getCreatedDate());
        assertEquals(modified, invoice.getModifiedDate());
    }
}
