package refai.project.test;

import refai.project.model.Order;
import refai.project.model.OrderItem;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    public void testDefaultConstructor() {
        Order order = new Order();
        assertNotNull(order.getItems());
        assertNotNull(order.getOrderDate());
        assertNull(order.getOrderId());
        assertNull(order.getCustomerId());
        assertEquals(0.0, order.getTotalAmount());
    }

    @Test
    public void testParameterizedConstructor() {
        Order order = new Order("ORDER23", "CUSTOMER456");
        assertEquals("ORDER23", order.getOrderId());
        assertEquals("CUSTOMER456", order.getCustomerId());
        assertEquals("PENDING", order.getStatus());
    }

    @Test
    public void testSettersAndGetters() {
        Order order = new Order();

        order.setOrderId("ORDER999");
        assertEquals("ORDER999", order.getOrderId());

        order.setCustomerId("CUSTOMER888");
        assertEquals("CUSTOMER888", order.getCustomerId());

        order.setStatus("CONFIRMED");
        assertEquals("CONFIRMED", order.getStatus());

        Date now = new Date();
        order.setOrderDate(now);
        assertEquals(now, order.getOrderDate());

        Date mod = new Date();
        order.setModifiedDate(mod);
        assertEquals(mod, order.getModifiedDate());
    }

    @Test
    public void testAddItemAndTotalAmount() {
        Order order = new Order();

        OrderItem item1 = new OrderItem("P1", "Burger", 2, 5.0);
        OrderItem item2 = new OrderItem("P2", "Fries", 3, 2.0);

        order.addItem(item1);
        order.addItem(item2);

        assertEquals(2, order.getItems().size());
        assertEquals(2 * 5.0 + 3 * 2.0, order.getTotalAmount());
    }

    @Test
    public void testSetItemsAndRecalculateTotal() {
        Order order = new Order();

        List<OrderItem> itemList = new ArrayList<>();
        itemList.add(new OrderItem("P1", "Pizza", 1, 10.0));
        itemList.add(new OrderItem("P2", "Drink", 4, 2.5));

        order.setItems(itemList);

        assertEquals(2, order.getItems().size());
        assertEquals(10.0 + 4 * 2.5, order.getTotalAmount());
    }
}
