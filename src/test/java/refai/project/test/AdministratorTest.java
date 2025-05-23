package refai.project.test;

import refai.project.model.Administrator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdministratorTest {

    @Test
    public void testConstructorAndGetters() {
        Administrator admin = new Administrator("ADMIN01", "Refai", "Manager");

        assertEquals("ADMIN01", admin.getAdminId());
        assertEquals("Refai", admin.getName());
        assertEquals("Manager", admin.getRole());
    }

    @Test
    public void testSetters() {
        Administrator admin = new Administrator();
        admin.setAdminId("ADMIN02");
        admin.setName("Milad");
        admin.setRole("Supervisor");

        assertEquals("ADMIN02", admin.getAdminId());
        assertEquals("Milad", admin.getName());
        assertEquals("Supervisor", admin.getRole());
    }
}
