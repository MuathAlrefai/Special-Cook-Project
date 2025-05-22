package test;

import refai.project.manager.CustomerManager;
import refai.project.model.Customer;
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
        manager.registerCustomer("Muath");
    }

    @Test
    public void testCustomMealWithValidIngredients()
    {
        List<String> ingredients = Arrays.asList("Beef", "Rice", "Spices");
        boolean result = manager.requestCustomMeal("Muath", ingredients);
        assertTrue(result);
        List<String> savedMeal = manager.getCustomMeal("Muath");
        assertEquals(ingredients, savedMeal);
    }

    @Test
    public void testCustomMealWithUnavailableIngredient()
    {
        List<String> ingredients = Arrays.asList("Beef", "Honey");
        boolean result = manager.requestCustomMeal("Muath", ingredients);
        assertFalse(result);
        List<String> savedMeal = manager.getCustomMeal("Muath");
        assertTrue(savedMeal == null || savedMeal.isEmpty());
    }

    @Test
    public void testCustomMealWithEmptyIngredients()
    {
        boolean result = manager.requestCustomMeal("Muath", Arrays.asList());
        assertFalse(result);
        assertTrue(manager.isEmptyIngredientsProvided());
    }
}
