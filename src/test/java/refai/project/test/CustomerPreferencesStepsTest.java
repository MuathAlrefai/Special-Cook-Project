package refai.project.test;

import refai.project.manager.CustomerManager;
import refai.project.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerPreferencesStepsTest 
{
	 private CustomerManager customerManager;

	    @BeforeEach
	    void setup() {
	        customerManager = new CustomerManager();
	    }

	    @Test
	    void testVegetarianNoAllergy() {
	        String name = "Muath Alrefai";
	        customerManager.registerCustomer(name);
	        customerManager.updatePreferences(name, "Vegetarian", "");
	        Customer customer = customerManager.getCustomer(name);
	        assertEquals("Vegetarian", customer.getDietaryPreferences());
	        assertEquals("", customer.getAllergies());
	    }

	    @Test
	    void testGlutenFreeWithPeanutsAllergy() {
	        String name = "Lara Croft";
	        customerManager.registerCustomer(name);
	        customerManager.updatePreferences(name, "Gluten-Free", "Peanuts");
	        Customer customer = customerManager.getCustomer(name);
	        assertEquals("Gluten-Free", customer.getDietaryPreferences());
	        assertEquals("Peanuts", customer.getAllergies());
	    }

	    @Test
	    void testUpdateToVeganWithDairyAllergy() {
	        String name = "Ahmed Salameh";
	        customerManager.registerCustomer(name);
	        customerManager.updatePreferences(name, "Vegan", "Dairy");
	        Customer customer = customerManager.getCustomer(name);
	        assertEquals("Vegan", customer.getDietaryPreferences());
	        assertEquals("Dairy", customer.getAllergies());
	    }
}
