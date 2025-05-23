package refai.project.test;

import refai.project.manager.CustomerManager;
import refai.project.model.Customer;
import refai.project.model.Invoice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerManagerTest {

    private CustomerManager manager;

    @BeforeEach
    void setUp() {
        manager = new CustomerManager();
    }

    @Test
    void testRegisterCustomerValid() {
        boolean result = manager.registerCustomer("Muhammad");
        assertTrue(result);
        assertNotNull(manager.getCustomer("Muhammad"));
    }

    @Test
    void testRegisterCustomerDuplicate() {
        manager.registerCustomer("Muhammad");
        boolean result = manager.registerCustomer("Muhammad");
        assertFalse(result);
    }

    @Test
    void testUpdatePreferences() {
        manager.registerCustomer("Salameh");
        boolean updated = manager.updatePreferences("Salameh", "Vegan", "Milk");
        assertTrue(updated);

        String[] prefs = manager.getCustomerPrefrences("Salameh");
        assertEquals("Vegan", prefs[0]);
        assertEquals("Milk", prefs[1]);
    }

    @Test
    void testPlaceOrderAndHistory() {
        manager.registerCustomer("Rizik");
        assertTrue(manager.placeOrder("Rizik", "Chicken"));
        List<String> history = manager.getOrderHistory("Rizik");
        assertEquals(1, history.size());
        assertEquals("Chicken", history.get(0));
    }

    @Test
    void testReorderMealSuccess() {
        manager.registerCustomer("Sona");
        manager.placeOrder("Sona", "Pasta");
        boolean result = manager.reorderMeal("Sona", "Pasta");
        assertTrue(result);
        assertEquals(2, manager.getOrderHistory("Sona").size());
    }

    @Test
    void testReorderMealFailure() {
        manager.registerCustomer("Daghlas");
        boolean result = manager.reorderMeal("Daghlas", "Burger");
        assertFalse(result);
    }
    
    @Test
    void testRequestCustomMeal_AllValid() {
        manager.registerCustomer("Lama");
        manager.updatePreferences("Lama", "Vegetarian", ""); 

        List<String> ingredients = List.of("Rice", "Carrots", "Tofu");
        boolean result = manager.requestCustomMeal("Lama", ingredients);

        assertTrue(result);
        assertEquals(ingredients, manager.getCustomMeal("Lama"));
    }
    
    @Test
    void testRequestCustomMeal_UnavailableIngredient() {
        manager.registerCustomer("Omar");

        List<String> ingredients = List.of("Dragonfruit");
        boolean result = manager.requestCustomMeal("Omar", ingredients);

        assertFalse(result);
        assertTrue(manager.isIngredientUnavailable());
    }

    @Test
    void testRequestCustomMeal_WithAllergy() {
        manager.registerCustomer("Jamal");
        manager.updatePreferences("Jamal", "", "Milk");

        List<String> ingredients = List.of("Rice", "Milk");
        boolean result = manager.requestCustomMeal("Jamal", ingredients);

        assertFalse(result);
        assertTrue(manager.hasAllergyConflict());
    }
    
    @Test
    void testRequestCustomMeal_IncompatibleCombo() {
        manager.registerCustomer("Latifa");

        List<String> ingredients = List.of("Milk", "Fish");
        boolean result = manager.requestCustomMeal("Latifa", ingredients);

        assertFalse(result);
        assertTrue(manager.hasIncompatibleCombination());
    }
    
    @Test
    void testRequestCustomMeal_EmptyIngredients() {
        manager.registerCustomer("Abed");

        boolean result = manager.requestCustomMeal("Abed", new ArrayList<>());
        assertFalse(result);
        assertTrue(manager.isEmptyIngredientsProvided());
    }

    @Test
    void testSuggestAlternatives_AllergyAndUnavailable() {
        manager.registerCustomer("Lamar");
        manager.updatePreferences("Lamar", "", "Peanuts");

        List<String> ingredients = List.of("Peanuts", "Dragonfruit");
        Map<String, List<String>> suggestions = manager.suggestAlternatives("Lamar", ingredients);

        assertTrue(suggestions.containsKey("Peanuts"));
        assertTrue(suggestions.containsKey("Dragonfruit"));

        assertEquals(List.of("Sunflower Butter", "Pumpkin Seeds"), suggestions.get("Peanuts"));
        assertEquals(List.of("Apple", "Pear"), suggestions.get("Dragonfruit"));
    }
    
    @Test
    void testSuggestAlternatives_NoSuggestions() {
        manager.registerCustomer("Sameera");
        manager.updatePreferences("Sameera", "", "");

        List<String> ingredients = List.of("Rice", "Carrots"); 
        Map<String, List<String>> suggestions = manager.suggestAlternatives("Sameera", ingredients);

        assertTrue(suggestions.isEmpty());
    }

    
    @Test
    void testSuggestAlternatives_CustomerNotFound() {
        Map<String, List<String>> result = manager.suggestAlternatives("Avoider", List.of("Fish"));
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testUpdateContactInfo() {
        manager.registerCustomer("Alaa");
        boolean updated = manager.updateContactInfo("Alaa", "alaa@email.com", "05999999", "Nablus");
        assertTrue(updated);
    }
    @Test
    void testUpdateContactInfo_CustomerNotFound()
    {
        boolean updated = manager.updateContactInfo("Avoider", "Avoider@email.com", "000", "Void");
        assertFalse(updated);
    }
    
    @Test
    void testAddInvoiceToCustomer() 
    {
        manager.registerCustomer("Ibrahim");
        Invoice invoice = new Invoice("Ibrahim", "ORDER001");
        invoice.setTotalAmount(45.0);
        invoice.setStatus("PAID");
        invoice.setFormat("PDF");
        manager.addInvoiceToCustomer("Ibrahim", invoice);

        List<Invoice> invoices = manager.getCustomerInvoices("Ibrahim");
        assertEquals(1, invoices.size());
        assertEquals("ORDER001", invoices.get(0).getOrderId());
    }

    
    @Test
    void testAddInvoiceToCustomer_CustomerNotFound() 
    {
        Invoice invoice = new Invoice("Avoider", "ORDER404");
        invoice.setTotalAmount(99.0);

        manager.addInvoiceToCustomer("Avoider", invoice); 
        List<Invoice> result = manager.getCustomerInvoices("Avoider");

        assertTrue(result.isEmpty());
    }
    
    @Test
    void testSetPreferredInvoiceFormat() {
        manager.registerCustomer("Hana");
        boolean result = manager.setPreferredInvoiceFormat("Hana", "HTML");
        assertTrue(result);

        Customer hana = manager.getCustomer("Hana");
        assertEquals("HTML", hana.getPreferredInvoiceFormat());
    }
    
    @Test
    void testSetPreferredInvoiceFormat_CustomerNotFound() {
        boolean result = manager.setPreferredInvoiceFormat("Fake", "PDF");
        assertFalse(result);
    }
    
    @Test
    void testGetCustomerInvoices_Empty() {
        manager.registerCustomer("Muhammad");
        List<Invoice> result = manager.getCustomerInvoices("Muhammad");
        assertTrue(result.isEmpty());
    }

}
