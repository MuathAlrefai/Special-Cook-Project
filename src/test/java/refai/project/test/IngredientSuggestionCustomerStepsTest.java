package refai.project.test;

import refai.project.manager.CustomerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientSuggestionCustomerStepsTest
{

    private CustomerManager customerManager;

    @BeforeEach
    public void setUp() 
    {
        customerManager = new CustomerManager();
    }

    @Test
    public void testSuggestAlternativesForUnavailableIngredient() 
    {
        customerManager.registerCustomer("Muhammad Alrefai");
        List<String> ingredients = Arrays.asList("Dragonfruit", "Beef");
        Map<String, List<String>> result = customerManager.suggestAlternatives("Muhammad Alrefai", ingredients);
        assertTrue(result.containsKey("Dragonfruit"));
        assertFalse(result.get("Dragonfruit").isEmpty());
    }

    @Test
    public void testSuggestAlternativesForAllergyConflict() 
    {
        customerManager.registerCustomer("Muath Alrefai");
        customerManager.updatePreferences("Muath Alrefai", "Standard", "Milk");
        List<String> ingredients = Arrays.asList("Milk", "Chicken");
        Map<String, List<String>> result = customerManager.suggestAlternatives("Muath Alrefai", ingredients);
        assertTrue(result.containsKey("Milk"));
        assertFalse(result.get("Milk").isEmpty());
    }

    @Test
    public void testNoAlternativesForValidIngredients() 
    {
        customerManager.registerCustomer("Lara Croft");
        customerManager.updatePreferences("Lara Croft", "Standard", "None");
        List<String> ingredients = Arrays.asList("Rice", "Chicken");
        Map<String, List<String>> result = customerManager.suggestAlternatives("Lara Croft", ingredients);
        assertTrue(result.isEmpty());
    }
    @Test
    public void testSuggestAlternativesForDragonfruit() {
        customerManager.registerCustomer("TestUser");
        List<String> ingredients = Arrays.asList("Dragonfruit", "Beef");
        Map<String, List<String>> result = customerManager.suggestAlternatives("TestUser", ingredients);

        assertNotNull(result);
        assertTrue(result.containsKey("Dragonfruit"));
        assertFalse(result.get("Dragonfruit").isEmpty());
    }

}