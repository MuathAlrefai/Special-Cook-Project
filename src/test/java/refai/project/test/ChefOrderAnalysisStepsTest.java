package refai.project.test;

import refai.project.manager.CustomerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class ChefOrderAnalysisStepsTest 
{
	 private CustomerManager customerManager;
	 private final String customerName = "Muhammad Alrefai";

	 @BeforeEach
	 void setup() 
	 {
		 customerManager = new CustomerManager();
		 customerManager.registerCustomer(customerName);
	 }

	 @Test
	 void testViewCustomerOrderHistory()
	 {
		 customerManager.placeOrder(customerName, "Grilled Chicken");
	     customerManager.placeOrder(customerName, "Vegetable Soup");

	     List<String> history = customerManager.getOrderHistory(customerName);
	     assertNotNull(history);
	     assertEquals(2, history.size());
	     assertTrue(history.contains("Grilled Chicken"));
	     assertTrue(history.contains("Vegetable Soup"));
	  }

	  @Test
	  void testSuggestPersonalizedPlanBasedOnRepeatedMeals() 
	  {
		  for (int i = 0; i < 3; i++) 
	      {
			  customerManager.placeOrder(customerName, "Pasta");
	      }

		  List<String> history = customerManager.getOrderHistory(customerName);
	      long count = history.stream().filter(m -> m.equals("Pasta")).count();
	      assertTrue(count >= 2, "Expected at least two orders of the meal for suggestion");
	  }

	  @Test
	  void testViewOrderHistoryOfCustomerWithNoOrders() 
	  {
		  List<String> history = customerManager.getOrderHistory(customerName);
	      assertNotNull(history);
	      assertTrue(history.isEmpty(), "Expected no orders in history");
	  }
}
