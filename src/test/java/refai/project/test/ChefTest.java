package refai.project.test;

import refai.project.model.Chef;
import refai.project.model.NotificationPreference;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChefTest {

    @Test
    public void testConstructorWithNameOnly() {
        Chef chef = new Chef("Muhammad");
        assertEquals("Muhammad", chef.getName());
        assertEquals("Low", chef.getWorkload());
        assertEquals("General", chef.getExpertise());
        assertTrue(chef.isAvailable());
        assertEquals(3, chef.getNotificationPreferences().size());
    }

    @Test
    public void testFullConstructor() {
        Chef chef = new Chef("Muath", "Medium", "BBQ");
        assertEquals("Muath", chef.getName());
        assertEquals("Medium", chef.getWorkload());
        assertEquals("BBQ", chef.getExpertise());
        assertTrue(chef.isAvailable());
    }

    @Test
    public void testIncreaseWorkload() {
        Chef chef = new Chef("Sameera", "Low", "Dessert");
        chef.increaseWorkload();
        assertEquals("Medium", chef.getWorkload());
        chef.increaseWorkload();
        assertEquals("High", chef.getWorkload());
    }

    @Test
    public void testAvailability() {
        Chef chef = new Chef("Jameel", "High", "Grill");
        assertFalse(chef.isAvailable());
        chef.setAvailable(false);
        assertFalse(chef.isAvailable());
    }

    @Test
    public void testUpdateExistingNotificationPreference() {
        Chef chef = new Chef("Ahmed");
        chef.updateNotificationPreference("EMAIL", false, 8);
        List<NotificationPreference> prefs = chef.getNotificationPreferences();
        NotificationPreference emailPref = prefs.stream()
                .filter(p -> p.getChannel().equals("EMAIL"))
                .findFirst()
                .orElse(null);
        assertNotNull(emailPref);
        assertFalse(emailPref.isEnabled());
        assertEquals(8, emailPref.getTimingHours());
    }

    @Test
    public void testAddNewNotificationPreference() {
        Chef chef = new Chef("Kamal");
        int before = chef.getNotificationPreferences().size();
        chef.updateNotificationPreference("WHATSAPP", true, 10);
        int after = chef.getNotificationPreferences().size();
        assertEquals(before + 1, after);

        NotificationPreference newPref = chef.getNotificationPreferences().stream()
                .filter(p -> p.getChannel().equals("WHATSAPP"))
                .findFirst()
                .orElse(null);
        assertNotNull(newPref);
        assertTrue(newPref.isEnabled());
        assertEquals(10, newPref.getTimingHours());
    }

    @Test
    public void testSetNotificationPreferencesDirectly() {
        Chef chef = new Chef("Sona");
        List<NotificationPreference> newPrefs = new ArrayList<>();
        newPrefs.add(new NotificationPreference("Sona", "CHEF", "APP", false, 5));

        chef.setNotificationPreferences(newPrefs);
        assertEquals(1, chef.getNotificationPreferences().size());
        assertEquals("APP", chef.getNotificationPreferences().get(0).getChannel());
    }
}
