package test;

import refai.project.context.TestContext;
import refai.project.model.Chef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskAssignmentKitchenManagerStepsTest 
{

    private TestContext context;

    @BeforeEach
    public void setUp()
    {
        context = new TestContext();
    }

    @Test
    public void testAssignTaskToChefWithExpertise()
    {
        Chef chef = new Chef("Milad", "Low", "Grill");
        context.kitchenManager.addChef(chef);
        context.assignedChef = context.kitchenManager.assignTask("Grill");
        assertNotNull(context.assignedChef);
        assertEquals("Milad", context.assignedChef.getName());
    }

    @Test
    public void testFallbackAssignmentWhenNoMatchingExpertise()
    {
        Chef chef = new Chef("Salem", "Low", "Dessert");
        context.kitchenManager.addChef(chef);
        context.assignedChef = context.kitchenManager.assignTask("Vegan");
        assertNotNull(context.assignedChef);
        assertEquals("Salem", context.assignedChef.getName());
    }

    @Test
    public void testNoAssignmentWhenAllHighWorkload() 
    {
        Chef chef = new Chef("Salem", "High", "Grill");
        context.kitchenManager.addChef(chef);
        context.assignedChef = context.kitchenManager.assignTask("Grill");
        assertNull(context.assignedChef);
    }
}
