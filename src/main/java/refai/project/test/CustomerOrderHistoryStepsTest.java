package refai.project.test;
import refai.project.manager.CustomerManager;
import refai.project.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerOrderHistoryStepsTest
{
	 private CustomerManager customerManager;
	    private final String customerName = "Messi";

	    @BeforeEach
	    void setup()
	    {
	        customerManager = new CustomerManager();
	        customerManager.registerCustomer(customerName);
	    }

	    @Test
	    void testCustomerPlacesNewOrderSuccessfully() 
	    {
	        boolean result = customerManager.placeOrder(customerName, "Broast Chicken");
	        assertTrue(result, "Customer should be able to place a new order.");
	        List<String> history = customerManager.getOrderHistory(customerName);
	        assertEquals(1, history.size());
	        assertEquals("Broast Chicken", history.get(0));
	    }

	    @Test
	    void testCustomerViewsOrderHistory()
	    {
	        customerManager.placeOrder(customerName, "Macaroni");
	        customerManager.placeOrder(customerName, "Soup");
	        List<String> history = customerManager.getOrderHistory(customerName);
	        assertEquals(2, history.size());
	        assertTrue(history.contains("Macaroni"));
	        assertTrue(history.contains("Soup"));
	    }

	    @Test
	    void testCustomerReordersPreviousMeal()
	    {
	        customerManager.placeOrder(customerName, "Fish Curry");
	        boolean result = customerManager.reorderMeal(customerName, "Fish Curry");
	        assertTrue(result, "Customer should be able to reorder a previously ordered meal.");
	        List<String> history = customerManager.getOrderHistory(customerName);
	        assertEquals(2, history.size());
	        assertEquals("Fish Curry", history.get(0));
	        assertEquals("Fish Curry", history.get(1));
	    }

	    @Test
	    void testReorderFailsIfMealNotInHistory() 
	    {
	        boolean result = customerManager.reorderMeal(customerName, "Tacos");
	        assertFalse(result, "Reordering should fail if the meal was never ordered.");
	    }
}
