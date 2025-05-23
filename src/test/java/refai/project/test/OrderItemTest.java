package refai.project.test;

import refai.project.model.OrderItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {

    @Test
    public void testConstructorAndGetters() {
        OrderItem item = new OrderItem("PRODUCT123", "Tomato", 5, 2.5);

        assertEquals("PRODUCT123", item.getProductId());
        assertEquals("Tomato", item.getProductName());
        assertEquals(5, item.getQuantity());
        assertEquals(2.5, item.getUnitPrice(), 0.001);
    }

    @Test
    public void testSetters() {
        OrderItem item = new OrderItem();

        item.setProductId("PRODUCT999");
        item.setProductName("Onion");
        item.setQuantity(10);
        item.setUnitPrice(3.75);

        assertEquals("PRODUCT999", item.getProductId());
        assertEquals("Onion", item.getProductName());
        assertEquals(10, item.getQuantity());
        assertEquals(3.75, item.getUnitPrice(), 0.001);
    }
}
