package refai.project.test;

import refai.project.model.KitchenManager;
import refai.project.model.Notification;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KitchenManagerModelTest {

    @Test
    void testConstructorAndGeneratedEmail() {
        KitchenManager manager = new KitchenManager("Jamal Qadah");
        assertEquals("Jamal Qadah", manager.getName());
        assertEquals("jamal.qadah@restaurant.com", manager.getEmail());
        assertNotNull(manager.getNotifications());
        assertTrue(manager.getNotifications().isEmpty());
    }

    @Test
    void testSetNameAndSetEmail() {
        KitchenManager manager = new KitchenManager("Test");
        manager.setName("New Name");
        manager.setEmail("new.name@restaurant.com");

        assertEquals("New Name", manager.getName());
        assertEquals("new.name@restaurant.com", manager.getEmail());
    }

    @Test
    void testAddNotification() {
        KitchenManager manager = new KitchenManager("Notifier");
        Notification notif = new Notification();
        notif.setSubject("Alert");
        notif.setContent("Fridge temperature high");
        notif.setChannel("SMS");

        manager.addNotification(notif);

        List<Notification> notifications = manager.getNotifications();
        assertEquals(1, notifications.size());
        assertEquals("Alert", notifications.get(0).getSubject());
        assertEquals("Fridge temperature high", notifications.get(0).getContent());
        assertEquals("SMS", notifications.get(0).getChannel());
    }

    @Test
    void testClearNotifications() {
        KitchenManager manager = new KitchenManager("Cleaner");

        Notification notif1 = new Notification();
        notif1.setSubject("Low Stock");
        manager.addNotification(notif1);

        Notification notif2 = new Notification();
        notif2.setSubject("Order Ready");
        manager.addNotification(notif2);

        assertEquals(2, manager.getNotifications().size());

        manager.clearNotifications();

        assertTrue(manager.getNotifications().isEmpty());
    }
}
