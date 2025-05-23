package refai.project.test;

import refai.project.model.InvoiceItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvoiceItemTest {

    @Test
    public void testDefaultConstructor() {
        InvoiceItem item = new InvoiceItem();
        assertNull(item.getProductId());
        assertNull(item.getProductName());
        assertEquals(0, item.getQuantity());
        assertEquals(0.0, item.getUnitPrice());
        assertEquals(0.0, item.getTotalPrice());
    }

    @Test
    public void testParameterizedConstructor() {
        InvoiceItem item = new InvoiceItem("PRODUCT001", "Burger", 3, 5.0);
        assertEquals("PRODUCT001", item.getProductId());
        assertEquals("Burger", item.getProductName());
        assertEquals(3, item.getQuantity());
        assertEquals(5.0, item.getUnitPrice());
        assertEquals(15.0, item.getTotalPrice());
    }

    @Test
    public void testSettersAndGetters() {
        InvoiceItem item = new InvoiceItem();

        item.setProductId("PRODUCT002");
        item.setProductName("Pizza");
        item.setQuantity(2);
        item.setUnitPrice(7.5);

        assertEquals("PRODUCT002", item.getProductId());
        assertEquals("Pizza", item.getProductName());
        assertEquals(2, item.getQuantity());
        assertEquals(7.5, item.getUnitPrice());
        assertEquals(15.0, item.getTotalPrice());
    }

    @Test
    public void testTotalPriceRecalculation() {
        InvoiceItem item = new InvoiceItem("PRODUCT003", "Soda", 1, 2.0);

        item.setQuantity(5);
        assertEquals(10.0, item.getTotalPrice());

        item.setUnitPrice(3.0);
        assertEquals(15.0, item.getTotalPrice());
    }
}
