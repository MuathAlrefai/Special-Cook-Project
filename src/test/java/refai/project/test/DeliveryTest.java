package refai.project.test;

import refai.project.model.Delivery;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryTest {

    @Test
    public void testDefaultConstructor() {
        Delivery delivery = new Delivery();
        assertNotNull(delivery.getDeliveryId());
        assertEquals("SCHEDULED", delivery.getStatus());
        assertFalse(delivery.isSpecialOrder());
    }

    @Test
    public void testConstructorWithOrderIdAndDate() {
        Date now = new Date();
        Delivery delivery = new Delivery("ORDER001", now);
        assertEquals("ORDER001", delivery.getOrderId());
        assertEquals(now, delivery.getScheduledTime());
    }

    @Test
    public void testConstructorWithCustomerIdOrderIdDate() {
        Date now = new Date();
        Delivery delivery = new Delivery("CUSTOMER001", "ORDER002", now);
        assertEquals("CUSTOMER001", delivery.getCustomerId());
        assertEquals("ORDER002", delivery.getOrderId());
        assertEquals(now, delivery.getScheduledTime());
    }

    @Test
    public void testConstructorWithDateString() throws Exception {
        String dateStr = "2025-05-16 15:00:00";
        Delivery delivery = new Delivery("CUSTOMER002", "ORDER003", dateStr);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expectedDate = formatter.parse(dateStr);

        assertEquals("CUSTOMER002", delivery.getCustomerId());
        assertEquals("ORDER003", delivery.getOrderId());
        assertEquals(expectedDate, delivery.getScheduledTime());
    }

    @Test
    public void testSettersAndGetters() {
        Delivery delivery = new Delivery();
        delivery.setCustomerId("CUSTOMER123");
        delivery.setOrderId("ORDER456");
        delivery.setStatus("COMPLETED");

        Date date = new Date();
        delivery.setScheduledTime(date);
        delivery.setDeliveryId("DEL999");
        delivery.setSpecialOrder(true);

        assertEquals("CUSTOMER123", delivery.getCustomerId());
        assertEquals("ORDER456", delivery.getOrderId());
        assertEquals("COMPLETED", delivery.getStatus());
        assertEquals(date, delivery.getScheduledTime());
        assertEquals("DEL999", delivery.getDeliveryId());
        assertTrue(delivery.isSpecialOrder());
    }
}
