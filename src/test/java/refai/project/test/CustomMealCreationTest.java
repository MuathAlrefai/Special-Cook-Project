package refai.project.test;

import refai.project.manager.CustomerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomMealCreationTest 
{

    private CustomerManager manager;

    @BeforeEach
    public void setUp()
    {
        manager = new CustomerManager();
        manager.registerCustomer("Kamal");
    }

    @Test
    public void testCustomMealWithValidIngredients() 
    {
        List<String> ingredients = Arrays.asList("Chicken", "Rice", "Spices");
        boolean result = manager.requestCustomMeal("Kamal", ingredients);
        assertTrue(result);
        List<String> savedMeal = manager.getCustomMeal("Kamal");
        assertEquals(ingredients, savedMeal);
    }

    @Test
    public void testCustomMealWithUnavailableIngredient() 
    {
        List<String> ingredients = Arrays.asList("Chicken", "Chocolate");
        boolean result = manager.requestCustomMeal("Kamal", ingredients);
        assertFalse(result);
        List<String> savedMeal = manager.getCustomMeal("Kamal");
        assertTrue(savedMeal == null || savedMeal.isEmpty());
    }

    @Test
    public void testCustomMealWithEmptyIngredients()
    {
        boolean result = manager.requestCustomMeal("Kamal", Arrays.asList());
        assertFalse(result);
        assertTrue(manager.isEmptyIngredientsProvided());
    }
}
