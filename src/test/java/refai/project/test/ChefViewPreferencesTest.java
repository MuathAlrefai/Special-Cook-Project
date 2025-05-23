package refai.project.test;

import refai.project.manager.CustomerManager;
import refai.project.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChefViewPreferencesTest
{
	 private CustomerManager customerManager;

	    @BeforeEach
	    void setup() {
	        customerManager = new CustomerManager();
	    }

	    @Test
	    void testViewVegetarianCustomer() {
	        String name = "Jamal Khaled";
	        customerManager.registerCustomer(name);
	        customerManager.updatePreferences(name, "Vegetarian", "None");
	        Customer customer = customerManager.getCustomer(name);
	        assertEquals("Vegetarian", customer.getDietaryPreferences());
	        assertEquals("None", customer.getAllergies());
	    }

	    @Test
	    void testViewCustomerWithSeafoodAllergy() {
	        String name = "Abed Salameh";
	        customerManager.registerCustomer(name);
	        customerManager.updatePreferences(name, "Standard", "Seafood");
	        Customer customer = customerManager.getCustomer(name);
	        assertEquals("Standard", customer.getDietaryPreferences());
	        assertEquals("Seafood", customer.getAllergies());
	    }

	    @Test
	    void testViewVeganCustomerWithMultipleAllergies() {
	        String name = "Ahmed Jummah";
	        customerManager.registerCustomer(name);
	        customerManager.updatePreferences(name, "Vegan", "Dairy,Eggs");
	        Customer customer = customerManager.getCustomer(name);
	        assertEquals("Vegan", customer.getDietaryPreferences());
	        assertEquals("Dairy,Eggs", customer.getAllergies());
	    }
}
