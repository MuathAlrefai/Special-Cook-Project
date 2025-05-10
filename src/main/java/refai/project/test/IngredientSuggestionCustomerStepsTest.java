package refai.project.test;

import refai.project.manager.CustomerManager;
import refai.project.model.Customer;
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
        customerManager.registerCustomer("Muhammad");
        List<String> ingredients = Arrays.asList("Dragonfruit", "Beef");
        Map<String, List<String>> result = customerManager.suggestAlternatives("Muhammad", ingredients);
        assertTrue(result.containsKey("Dragonfruit"));
        assertFalse(result.get("Dragonfruit").isEmpty());
    }

    @Test
    public void testSuggestAlternativesForAllergyConflict() 
    {
        customerManager.registerCustomer("Malek");
        customerManager.updatePreferences("Malek", "Standard", "Milk");
        List<String> ingredients = Arrays.asList("Milk", "Chicken");
        Map<String, List<String>> result = customerManager.suggestAlternatives("Malek", ingredients);
        assertTrue(result.containsKey("Milk"));
        assertFalse(result.get("Milk").isEmpty());
    }

    @Test
    public void testNoAlternativesForValidIngredients() 
    {
        customerManager.registerCustomer("Messi");
        customerManager.updatePreferences("Messi", "Standard", "None");
        List<String> ingredients = Arrays.asList("Rice", "Chicken");
        Map<String, List<String>> result = customerManager.suggestAlternatives("Messi", ingredients);
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