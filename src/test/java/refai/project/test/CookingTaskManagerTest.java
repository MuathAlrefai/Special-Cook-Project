package refai.project.test;

import refai.project.manager.CookingTaskManager;
import refai.project.model.Chef;
import refai.project.model.CookingTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CookingTaskManagerTest {

    private CookingTaskManager manager;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    public void setUp() {
        manager = new CookingTaskManager();
    }

    @Test
    public void testRegisterAndGetChef() {
        Chef chef = manager.registerChef("Muhammad");
        assertNotNull(chef);
        assertEquals("Muhammad", chef.getName());

        Chef fetched = manager.getChef("Muhammad");
        assertEquals(chef, fetched);
    }

    @Test
    public void testScheduleCookingTaskSuccess() throws Exception {
        manager.registerChef("Kareem");
        String time = "2025-05-15 12:18:56";
        CookingTask task = manager.scheduleCookingTask("Kareem", "ORDER001", time);

        assertNotNull(task);
        assertEquals("Kareem", task.getChefId());
        assertEquals("ORDER001", task.getOrderId());
    }

    @Test
    public void testScheduleCookingTaskInvalidDate() {
        CookingTask task = manager.scheduleCookingTask("Ali", "ORDER002", "invalid date");
        assertNull(task);
    }

    @Test
    public void testGetCookingTask() throws Exception {
        manager.registerChef("Sona");
        CookingTask task = manager.scheduleCookingTask("Sona", "ORDER003", "2025-05-15 12:18:56");

        CookingTask fetched = manager.getCookingTask(task.getTaskId());
        assertNotNull(fetched);
        assertEquals("ORDER003", fetched.getOrderId());
    }

    @Test
    public void testGetTasksForChef() throws Exception 
    {
        manager.registerChef("Hashem");

        manager.scheduleCookingTask("Hashem", "ORDER001", "2025-05-15 10:00:00");

        Thread.sleep(10); //simple stop to change taskID
        manager.scheduleCookingTask("Hashem", "ORDER002", "2025-05-16 12:00:00");

        List<CookingTask> tasks = manager.getTasksForChef("Hashem");
        assertEquals(2, tasks.size());
    }



    @Test
    public void testGetTasksForDate() throws Exception 
    {
        manager.registerChef("Salameh");

        manager.scheduleCookingTask("Salameh", "ORD100", "2025-05-15 12:18:56");
        manager.scheduleCookingTask("Salameh", "ORD101", "2025-05-16 12:18:56");

        Date targetDate = new SimpleDateFormat("yyyy-MM-dd").parse("2025-05-15");

        List<CookingTask> tasks = manager.getTasksForDate("Salameh", targetDate);
        assertEquals(1, tasks.size());
        assertEquals("ORD100", tasks.get(0).getOrderId());
    }


    @Test
    public void testCreateUrgentTask() {
        Chef chef = manager.registerChef("Sara");
        chef.setAvailable(true);

        CookingTask task = manager.createUrgentTask("ORDER999");
        assertNotNull(task);
        assertTrue(task.isUrgent());
        assertEquals("Sara", task.getChefId());
    }

    @Test
    public void testCreateUrgentTaskNoAvailableChef() 
    {
        CookingTaskManager manager = new CookingTaskManager();
        Chef chef = manager.registerChef("BusyChef");
        chef.increaseWorkload(); 
        chef.increaseWorkload(); 
        CookingTask task = manager.createUrgentTask("ORDER001");
        assertNull(task); 
    }



    @Test
    public void testAddPreparationRequirement() throws Exception {
        manager.registerChef("Hamzeh");
        CookingTask task = manager.scheduleCookingTask("Hamzeh", "ORDER555", "2025-05-15 12:18:56");
        manager.addPreparationRequirement(task.getTaskId(), "Chicken", "Marinate overnight");

        assertTrue(task.getPreparationRequirements().containsKey("Chicken"));
        assertEquals("Marinate overnight", task.getPreparationRequirements().get("Chicken"));
    }
}
