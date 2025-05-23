package refai.project.test;

import refai.project.model.CookingTask;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CookingTaskTest
{

    @Test
    public void testDefaultConstructor()
    {
        CookingTask task = new CookingTask();
        assertNotNull(task.getTaskId());
        assertEquals("SCHEDULED", task.getStatus());
        assertFalse(task.isUrgent());
        assertEquals(60, task.getEstimatedDuration());
        assertNotNull(task.getPreparationRequirements());
        assertTrue(task.getPreparationRequirements().isEmpty());
    }

    @Test
    public void testConstructorWithDate() 
    {
        Date date = new Date();
        CookingTask task = new CookingTask("chef1", "order1", date);
        assertEquals("chef1", task.getChefId());
        assertEquals("order1", task.getOrderId());
        assertEquals(date, task.getScheduledTime());
    }

    @Test
    public void testConstructorWithStringDate() throws Exception 
    {
        String dateStr = "2025-05-16 12:00:00";
        CookingTask task = new CookingTask("chef2", "order2", dateStr);
        assertEquals("chef2", task.getChefId());
        assertEquals("order2", task.getOrderId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        assertEquals(format.parse(dateStr), task.getScheduledTime());
    }

    @Test
    public void testSettersAndGetters() 
    {
        CookingTask task = new CookingTask();

        task.setTaskId("T-001");
        task.setOrderId("ORD-1");
        task.setChefId("Chef-A");
        Date date = new Date();
        task.setScheduledTime(date);
        task.setEstimatedDuration(90);
        task.setStatus("IN_PROGRESS");
        task.setUrgent(true);

        assertEquals("T-001", task.getTaskId());
        assertEquals("ORD-1", task.getOrderId());
        assertEquals("Chef-A", task.getChefId());
        assertEquals(date, task.getScheduledTime());
        assertEquals(90, task.getEstimatedDuration());
        assertEquals("IN_PROGRESS", task.getStatus());
        assertTrue(task.isUrgent());
    }

    @Test
    public void testPreparationRequirements() 
    {
        CookingTask task = new CookingTask();

        task.addPreparationRequirement("Meat", "Defrost");
        task.addPreparationRequirement("Potatoes", "Peel");

        Map<String, String> map = task.getPreparationRequirements();
        assertEquals(2, map.size());
        assertEquals("Defrost", map.get("Meat"));

        Map<String, String> newMap = new HashMap<>();
        newMap.put("Onions", "Slice");
        task.setPreparationRequirements(newMap);

        assertEquals(1, task.getPreparationRequirements().size());
        assertEquals("Slice", task.getPreparationRequirements().get("Onions"));
    }
}
