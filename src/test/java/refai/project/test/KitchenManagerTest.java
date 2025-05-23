package refai.project.test;

import refai.project.manager.KitchenManager;
import refai.project.model.Chef;
import refai.project.model.Notification;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KitchenManagerTest {

    @Test
    void testDefaultConstructor() {
        KitchenManager manager = new KitchenManager();
        assertEquals("Default Kitchen", manager.getName());
    }

    @Test
    void testAddAndGetChefs() {
        KitchenManager manager = new KitchenManager("Main Kitchen");
        Chef chef = new Chef("Amal");
        manager.addChef(chef);

        List<Chef> chefs = manager.getChefs();
        assertEquals(1, chefs.size());
        assertEquals("Amal", chefs.get(0).getName());
    }

    @Test
    void testAssignTaskWithExpertChef() {
        KitchenManager manager = new KitchenManager("Task Kitchen");
        Chef chef = new Chef("Sona", "Low", "Dessert");
        chef.setAvailable(true);
        manager.addChef(chef);

        Chef assigned = manager.assignTask("Dessert");
        assertNotNull(assigned);
        assertEquals("Sona", assigned.getName());
        assertEquals("Medium", assigned.getWorkload());
    }

    @Test
    void testAssignTaskWithFallback() {
        KitchenManager manager = new KitchenManager();
        Chef chef = new Chef("Omar", "Low", "Grill");
        chef.setAvailable(true);
        manager.addChef(chef);

        Chef assigned = manager.assignTask("UnknownType");
        assertNotNull(assigned);
        assertEquals("Omar", assigned.getName());
        assertEquals("Medium", assigned.getWorkload());
    }

    @Test
    void testAssignTaskWithNoAvailableChef() {
        KitchenManager manager = new KitchenManager();
        Chef chef = new Chef("Muath", "High", "Soup");
        chef.setAvailable(true);
        manager.addChef(chef);

        Chef assigned = manager.assignTask("Soup");
        assertNull(assigned);
    }

    @Test
    void testAddAndGetNotifications() {
        KitchenManager manager = new KitchenManager("Notif Kitchen");

        Notification notif = new Notification();
        notif.setContent("New Order");

        manager.addNotification(notif);

        List<Notification> list = manager.getNotifications();
        assertEquals(1, list.size());
        assertEquals("New Order", list.get(0).getContent());
    }
}
