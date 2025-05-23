package refai.project.test;

import refai.project.manager.NotificationManager;
import refai.project.manager.KitchenManager;

import refai.project.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationManagerTest {

    private NotificationManager manager;

    @BeforeEach
    public void setUp() {
        manager = new NotificationManager();
    }

    @Test
    public void testScheduleNotification() {
        Notification notif = manager.scheduleNotification("USER1", "CUSTOMER", "Test", "Content",
                new Date(), "EMAIL", "NORMAL");
        assertNotNull(notif);
        assertEquals("CUSTOMER", notif.getRecipientType());
    }

    @Test
    public void testSendImmediateNotification() {
        Notification notif = manager.sendImmediateNotification("USER1", "CHEF", "Now", "Go", "SMS", "URGENT");
        assertEquals("SENT", notif.getStatus());
    }

    @Test
    public void testProcessScheduledNotifications_empty() {
        List<Notification> result = manager.processScheduledNotifications();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testScheduleDeliveryReminders() {
        Customer c = new Customer("Lamar");
        Delivery d = new Delivery("Lamar", "ORDER999", new Date(System.currentTimeMillis() + 50000));
        List<Notification> reminders = manager.scheduleDeliveryReminders(c, d);
        assertFalse(reminders.isEmpty());
    }

    @Test
    public void testScheduleCookingTaskNotifications() {
        Chef chef = new Chef("Murshid");
        CookingTask task = new CookingTask(chef.getName(), "ORDER456", new Date(System.currentTimeMillis() + 3600000));
        List<Notification> result = manager.scheduleCookingTaskNotifications(chef, task);
        assertEquals(2, result.size());
    }

    @Test
    public void testSaveAndUpdatePreferences() {
        List<NotificationPreference> prefs = new ArrayList<>();
        prefs.add(new NotificationPreference("Lamar", "CUSTOMER", "APP", true, 3));
        manager.saveNotificationPreferences("Lamar", "CUSTOMER", prefs);

        manager.updateNotificationPreference("Lamar", "CUSTOMER", "EMAIL", true, 5);

        Delivery d = new Delivery("Lamar", "ORDER999", new Date(System.currentTimeMillis() + 50000));
        List<Notification> scheduled = manager.scheduleDeliveryReminders(new Customer("Lamar"), d);
        assertFalse(scheduled.isEmpty());
    }

    @Test
    public void testGetNotificationsForUser() {
        manager.sendImmediateNotification("Muhammad", "CHEF", "Alert", "Hurry!", "EMAIL", "URGENT");
        List<Notification> result = manager.getNotificationsForUser("Muhammad");
        assertEquals(1, result.size());
    }

    @Test
    public void testAddTimedNotification_valid() {
        Notification notif = new Notification();
        notif.setRecipientId("TestUser");
        notif.setRecipientType("CHEF");
        notif.setSubject("Timed");
        notif.setContent("Check");
        notif.setScheduledTime(new Date(System.currentTimeMillis() + 10000));
        notif.setChannel("EMAIL");
        notif.setPriority("NORMAL");
        notif.setStatus("PENDING");
        notif.setRequiresConfirmation(true);

        manager.addTimedNotification(notif);

        List<Notification> result = manager.getNotificationsForUser("TestUser");
        assertEquals(1, result.size());
    }

    @Test
    public void testAddTimedNotification_null() {
        assertDoesNotThrow(() -> manager.addTimedNotification(null));
    }

    @Test
    public void testDebugNotificationsState() {
        //verify no notifications exist for the debug user before sending
        List<Notification> beforeNotifications = manager.getNotificationsForUser("DebugUser");
        assertEquals(0, beforeNotifications.size(), "Should have no notifications before sending");

        manager.debugNotificationsState();
        manager.sendImmediateNotification("DebugUser", "CHEF", "Test", "Now", "EMAIL", "NORMAL");
        manager.debugNotificationsState();

        //verify notification exists after sending
        List<Notification> afterNotifications = manager.getNotificationsForUser("DebugUser");
        assertEquals(1, afterNotifications.size(), "Should have one notification after sending");

        //verify the notification has the correct properties
        Notification notification = afterNotifications.get(0);
        assertEquals("SENT", notification.getStatus());
        assertEquals("Test", notification.getSubject());
    }

    @Test
    public void testSendToManager() {
        KitchenManager kitchenManager = new KitchenManager("Chef Kamal");

        Notification notification = new Notification();
        notification.setRecipientId("Chef Kamal");
        notification.setRecipientType("KITCHEN_MANAGER");
        notification.setSubject("Low Stock");
        notification.setContent("Salt is low");
        notification.setChannel("EMAIL");
        notification.setPriority("NORMAL");

        manager.sendToManager(kitchenManager, notification);

        List<Notification> notifications = kitchenManager.getNotifications();
        assertEquals(1, notifications.size());
        assertEquals("SENT", notifications.get(0).getStatus());
    }
    
    @Test
    public void testProcessScheduledNotifications_readyToSend() {
        Notification notif = manager.scheduleNotification(
                "ReadyUser", "CUSTOMER", "Soon", "Be ready",
                new Date(System.currentTimeMillis() - 1000), "APP", "NORMAL"
        );
        notif.setStatus("PENDING");

        List<Notification> result = manager.processScheduledNotifications();
        assertFalse(result.isEmpty());
        assertEquals("SENT", result.get(0).getStatus());
    }
    
    @Test
    public void testScheduleDeliveryReminders_disabledPrefs() {
        Customer c = new Customer("Hisham");
        List<NotificationPreference> prefs = List.of(
                new NotificationPreference("Hisham", "CUSTOMER", "EMAIL", false, 1)
        );
        manager.saveNotificationPreferences("Hisham", "CUSTOMER", prefs);

        Delivery d = new Delivery("Hisham", "ORDER200", new Date(System.currentTimeMillis() + 50000));
        List<Notification> result = manager.scheduleDeliveryReminders(c, d);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testScheduleCookingTaskNotifications_nullInputs() {
        List<Notification> r1 = manager.scheduleCookingTaskNotifications(null, null);
        assertTrue(r1.isEmpty());

        Chef chef = new Chef("Mira");
        List<Notification> r2 = manager.scheduleCookingTaskNotifications(chef, null);
        assertTrue(r2.isEmpty());
    }
    
    @Test
    public void testSendDailySchedule_emptyTasks() {
        Chef chef = new Chef("Omar");
        Notification result = manager.sendDailySchedule(chef, new ArrayList<>(), new Date());
        assertNull(result);
    }
    
    @Test
    public void testGetHoursBetween() throws Exception
    {
        Date now = new Date();
        Date later = new Date(now.getTime() + 3 * 60 * 60 * 1000); 
        java.lang.reflect.Method method = manager.getClass()
            .getDeclaredMethod("getHoursBetween", Date.class, Date.class);
        method.setAccessible(true);
        long hours = (long) method.invoke(manager, now, later);
        assertEquals(3L, hours);
    }


    @Test
    public void testSendNotificationDirectly() {
        Notification notif = new Notification();
        notif.setRecipientId("DirectUser");
        notif.setChannel("EMAIL");
        notif.setSubject("Direct Test");

        boolean result = manager.sendNotification(notif);
        assertTrue(result);
        assertEquals("SENT", notif.getStatus());
    }
    
    @Test
    public void testUpdatePreference_addNew() {
        manager.updateNotificationPreference("USERX", "CUSTOMER", "SMS", true, 8);
        Delivery d = new Delivery("USERX", "ORDER555", new Date(System.currentTimeMillis() + 3600000));
        List<Notification> result = manager.scheduleDeliveryReminders(new Customer("USERX"), d);
        assertFalse(result.isEmpty());
    }
    
    @Test
    public void testDefaultChefPreferences() {
        Chef chef = new Chef("ChefDefault");
        CookingTask task = new CookingTask(chef.getName(), "ORDERDEF", new Date(System.currentTimeMillis() + 7200000));
        List<Notification> result = manager.scheduleCookingTaskNotifications(chef, task);
        assertEquals(2, result.size()); 
    }
    
    @Test
    public void testAddTimedNotification_noConfirmation() {
        Notification notif = new Notification();
        notif.setRecipientId("NoConfirmUser");
        notif.setRecipientType("CHEF");
        notif.setSubject("NoConf");
        notif.setContent("None");
        notif.setScheduledTime(new Date(System.currentTimeMillis() + 5000));
        notif.setChannel("APP");
        notif.setPriority("NORMAL");
        notif.setStatus("PENDING");

        manager.addTimedNotification(notif);
        List<Notification> result = manager.getNotificationsForUser("NoConfirmUser");
        assertEquals(1, result.size());
    }

    @Test
    public void testSendDailySchedule_withUrgentAndPreparation() {
        Chef chef = new Chef("Chef Putin");

        CookingTask task = new CookingTask("Chef Putin", "ORDER888", new Date(System.currentTimeMillis() + 60000));
        task.setUrgent(true);
        Map<String, String> preparation = new HashMap<>();
        preparation.put("Chop onions", "yes");
        preparation.put("Preheat oven", "200C");
        task.setPreparationRequirements(preparation);

        List<CookingTask> tasks = new ArrayList<>();
        tasks.add(task);
        Date scheduleDate = new Date();

        Notification notif = manager.sendDailySchedule(chef, tasks, scheduleDate);

        assertNotNull(notif);
        assertEquals("Chef Putin", notif.getRecipientId());
        assertTrue(notif.getContent().contains("Chop onions"));
        assertTrue(notif.getContent().contains("Preheat oven"));
        assertTrue(notif.getContent().contains("[URGENT]"));
    }


    @Test
    public void testGetDefaultChefPreferences_viaScheduleCooking() {
        Chef chef = new Chef("Chef Medo");
        CookingTask task = new CookingTask("Chef Medo", "ORDER777", new Date(System.currentTimeMillis() + 7200000)); //+2 hours

        List<Notification> result = manager.scheduleCookingTaskNotifications(chef, task);

        assertEquals(2, result.size()); 
        assertTrue(result.get(0).getRecipientType().equals("CHEF"));
    }




}
