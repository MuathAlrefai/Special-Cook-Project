package refai.project.test;

import refai.project.manager.CustomerManager;
import refai.project.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomMealSystemStepsTest
{

    private CustomerManager customerManager;

    @BeforeEach
    public void setup() {
        customerManager = new CustomerManager();
    }

    @Test
    public void testValidCustomMealCombination() {
        customerManager.registerCustomer("Waleed");
        customerManager.updatePreferences("Waleed", "Standard", "None");

        List<String> ingredients = Arrays.asList("Chicken", "Tomato");
        boolean result = customerManager.requestCustomMeal("Waleed", ingredients);

        assertTrue(result, "System should accept valid combination");
        assertFalse(customerManager.hasIncompatibleCombination(), "No incompatibility should be detected");
    }

    @Test
    public void testIncompatibleCustomMealCombination() {
        customerManager.registerCustomer("Aseel");
        customerManager.updatePreferences("Aseel", "Standard", "");

        List<String> ingredients = Arrays.asList("Milk", "Fish");
        boolean result = customerManager.requestCustomMeal("Aseel", ingredients);

        assertFalse(result, "System should reject incompatible combination");
        assertTrue(customerManager.hasIncompatibleCombination(), "Incompatibility should be detected");
    }

    @Test
    public void testEmptyIngredientSelection() {
        customerManager.registerCustomer("Aya");
        customerManager.updatePreferences("Aya", "Vegetarian", "Peanuts");

        boolean result = customerManager.requestCustomMeal("Aya", Arrays.asList());

        assertFalse(result, "System should reject empty ingredient selection");
        assertTrue(customerManager.isEmptyIngredientsProvided(), "Empty input should be flagged");
    }
}
