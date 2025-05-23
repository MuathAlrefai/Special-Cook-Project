package refai.project.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import refai.project.manager.KitchenStaffManager;
import refai.project.model.Chef;
import refai.project.model.CookingTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class KitchenStaffManagerTest {
    private KitchenStaffManager manager;
    private SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp() {
        manager = new KitchenStaffManager();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    void registerChef_shouldRegisterChefWithDefaultValues() {
        Chef chef = manager.registerChef("Gordon");
        assertNotNull(chef);
        assertEquals("Gordon", chef.getName());
    }

    @Test
    void registerChef_shouldRegisterChefWithExpertiseAndWorkload() {
        Chef chef = manager.registerChef("Jamie", "Italian", "Medium");
        assertNotNull(chef);
        assertEquals("Jamie", chef.getName());
        assertEquals("Italian", chef.getExpertise());
        assertEquals("Medium", chef.getWorkload());
    }

    @Test
    void scheduleCookingTask_shouldReturnTaskForValidChef() throws ParseException {
        manager.registerChef("Gordon");
        CookingTask task = manager.scheduleCookingTask("Gordon", "ORDER-123", "2023-05-23 12:00:00");
        assertNotNull(task);
        assertEquals("Gordon", task.getChefId());
        assertEquals("ORDER-123", task.getOrderId());
    }

    @Test
    void scheduleCookingTask_shouldReturnNullForInvalidChef() throws ParseException {
        CookingTask task = manager.scheduleCookingTask("Unknown", "ORDER-123", "2023-05-23 12:00:00");
        assertNull(task);
    }

    @Test
    void addPreparationRequirement_shouldAddRequirementForValidTask() throws ParseException {
        manager.registerChef("Gordon");
        CookingTask task = manager.scheduleCookingTask("Gordon", "ORDER-123", "2023-05-23 12:00:00");
        manager.addPreparationRequirement(task.getTaskId(), "Prep veggies", "Chop all vegetables");

        Map<String, String> requirements = manager.getPreparationRequirements(task.getTaskId());
        assertEquals("Chop all vegetables", requirements.get("Prep veggies"));
    }

    @Test
    void addPreparationRequirement_shouldNotAddForInvalidTask() {
        manager.addPreparationRequirement("invalid-task-id", "Prep veggies", "Chop all vegetables");
        // Verify no exception thrown
    }

    @Test
    void createUrgentTask_shouldAssignToAvailableChef() {
        manager.registerChef("Gordon", "General", "Low");
        manager.registerChef("Jamie", "Italian", "High");

        CookingTask task = manager.createUrgentTask("URGENT-ORDER");
        assertNotNull(task);
        assertEquals("Gordon", task.getChefId());
        assertTrue(task.isUrgent());
    }

    @Test
    void createUrgentTask_shouldReturnNullWhenNoChefsAvailable() {
        assertNull(manager.createUrgentTask("URGENT-ORDER"));
    }

    @Test
    void getTasksForDate_shouldReturnTasksForSpecificDate() throws ParseException {
        manager.registerChef("Gordon");
        CookingTask task1 = manager.scheduleCookingTask("Gordon", "ORDER-1", "2023-05-23 12:00:00");
        CookingTask task2 = manager.scheduleCookingTask("Gordon", "ORDER-2", "2023-05-24 12:00:00");

        Date testDate = dateFormat.parse("2023-05-23 00:00:00");
        List<CookingTask> tasks = manager.getTasksForDate("Gordon", testDate);

        assertEquals(1, tasks.size());
        assertEquals(task1.getTaskId(), tasks.get(0).getTaskId());
    }

    @Test
    void getTasksForDate_shouldReturnEmptyListForNoTasks() throws ParseException {
        Date testDate = dateFormat.parse("2023-05-23 00:00:00");
        List<CookingTask> tasks = manager.getTasksForDate("Gordon", testDate);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void getChef_shouldReturnChefWhenExists() {
        manager.registerChef("Gordon");
        Chef chef = manager.getChef("Gordon");
        assertNotNull(chef);
    }

    @Test
    void getChef_shouldReturnNullWhenNotExists() {
        assertNull(manager.getChef("Unknown"));
    }

    @Test
    void getPreparationRequirements_shouldReturnEmptyMapForInvalidTask() {
        Map<String, String> requirements = manager.getPreparationRequirements("invalid-task-id");
        assertTrue(requirements.isEmpty());
    }

    @Test
    void scheduleCookingTask_shouldIncreaseChefWorkload() throws ParseException {
        Chef chef = manager.registerChef("Gordon", "General", "Low");
        manager.scheduleCookingTask("Gordon", "ORDER-123", "2023-05-23 12:00:00");
        // Assuming increaseWorkload() changes the workload status
        assertNotEquals("Low", chef.getWorkload());
    }

    @Test
    void createUrgentTask_shouldPreferLowWorkloadChefs() {
        manager.registerChef("Gordon", "General", "Low");
        manager.registerChef("Jamie", "Italian", "Medium");

        CookingTask task = manager.createUrgentTask("URGENT-ORDER");
        assertEquals("Gordon", task.getChefId());
    }

    @Test
    void getTasksForDate_shouldIgnoreOtherChefsTasks() throws ParseException {
        manager.registerChef("Gordon");
        manager.registerChef("Jamie");
        manager.scheduleCookingTask("Gordon", "ORDER-1", "2023-05-23 12:00:00");
        manager.scheduleCookingTask("Jamie", "ORDER-2", "2023-05-23 12:00:00");

        Date testDate = dateFormat.parse("2023-05-23 00:00:00");
        List<CookingTask> tasks = manager.getTasksForDate("Gordon", testDate);

        assertEquals(1, tasks.size());
        assertEquals("ORDER-1", tasks.get(0).getOrderId());
    }
}