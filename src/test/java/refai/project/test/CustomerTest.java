package refai.project.test;

import refai.project.model.Customer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void testValidCustomerCreation()
    {
        Customer customer = new Customer("Ameer");

        assertEquals("Ameer", customer.getName());
        assertNotNull(customer.getCustomerId());
        assertEquals("", customer.getDietaryPreferences());
        assertEquals("", customer.getAllergies());
        assertEquals("PDF", customer.getPreferredInvoiceFormat());
        assertTrue(customer.getOrderHistory().isEmpty());
        assertTrue(customer.getCustomMealIngredients().isEmpty());
    }

    @Test
    public void testInvalidCustomerNameThrowsException()
    {
        assertThrows(IllegalArgumentException.class, () -> new Customer(null));
        assertThrows(IllegalArgumentException.class, () -> new Customer(""));
        assertThrows(IllegalArgumentException.class, () -> new Customer("   "));
    }

    @Test
    public void testSettersAndGetters()
    {
        Customer customer = new Customer("Lena");
        customer.setDietaryPreferences("Vegan");
        customer.setAllergies("Nuts");
        customer.setPhone("0591234567");
        customer.setAddress("Nablus");
        customer.setEmail("lena@mail.com");
        customer.setPreferredInvoiceFormat("HTML");

        assertEquals("Vegan", customer.getDietaryPreferences());
        assertEquals("Nuts", customer.getAllergies());
        assertEquals("0591234567", customer.getPhone());
        assertEquals("Nablus", customer.getAddress());
        assertEquals("lena@mail.com", customer.getEmail());
        assertEquals("HTML", customer.getPreferredInvoiceFormat());
    }

    @Test
    public void testPreferredInvoiceFormatFallback() 
    {
        Customer customer = new Customer("Sami");
        customer.setPreferredInvoiceFormat("TEXT");
        assertEquals("TEXT", customer.getPreferredInvoiceFormat());

        customer.setPreferredInvoiceFormat("   "); 
        assertEquals("TEXT", customer.getPreferredInvoiceFormat());

        customer.setPreferredInvoiceFormat(null); 
        assertEquals("TEXT", customer.getPreferredInvoiceFormat());
    }

    @Test
    public void testOrderHistoryAndCustomMealIngredients() 
    {
        Customer customer = new Customer("Fadi");

        customer.addOrder("Burger");
        customer.addOrder("Pizza");
        List<String> history = customer.getOrderHistory();

        assertEquals(2, history.size());
        assertTrue(history.contains("Burger"));
        assertTrue(history.contains("Pizza"));

        customer.setCustomMealIngredients(Arrays.asList("Tomato", "Lettuce"));
        List<String> ingredients = customer.getCustomMealIngredients();

        assertEquals(2, ingredients.size());
        assertTrue(ingredients.contains("Tomato"));
    }
}
