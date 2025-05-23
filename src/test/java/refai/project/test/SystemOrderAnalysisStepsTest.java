package refai.project.test;

import refai.project.manager.CustomerManager;
import refai.project.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class SystemOrderAnalysisStepsTest
{
	  private CustomerManager customerManager;

	    @BeforeEach
	    void setup() 
	    {
	        customerManager = new CustomerManager();
	    }

	    @Test
	    void testAnalyzeMealFrequency() 
	    {
	        customerManager.registerCustomer("Muhammad Alrefai");
	        customerManager.placeOrder("Muhammad Alrefai", "Grilled Chicken");
	        customerManager.placeOrder("Muhammad Alrefai", "Grilled Chicken");
	        customerManager.placeOrder("Muhammad Alrefai", "Pasta");
	        customerManager.placeOrder("Muhammad Alrefai", "Grilled Chicken");

	        Map<String, Integer> frequencies = getMealFrequencies(customerManager.getCustomer("Muhammad Alrefai").getOrderHistory());
	        assertEquals(3, frequencies.get("Grilled Chicken"));
	        assertEquals(1, frequencies.get("Pasta"));
	    }

	    @Test
	    void testNoOrdersReturnsEmptyAnalysis() 
	    {
	        customerManager.registerCustomer("Fadi Naser");
	        Customer customer = customerManager.getCustomer("Fadi Naser");
	        List<String> history = customer.getOrderHistory();
	        Map<String, Integer> frequencies = getMealFrequencies(history);
	        assertTrue(frequencies.isEmpty());
	    }

	    @Test
	    void testMultipleCustomersTrendAnalysis() 
	    {
	        customerManager.registerCustomer("Lara Croft");
	        customerManager.registerCustomer("Muhammad Alrefai");
	        customerManager.placeOrder("Lara Croft", "Pasta");
	        customerManager.placeOrder("Lara Croft", "Pasta");
	        customerManager.placeOrder("Muhammad Alrefai", "Pasta");
	        customerManager.placeOrder("Muhammad Alrefai", "Grilled Chicken");

	        Map<String, Integer> globalTrend = new HashMap<>();
	        for (String name : new String[]{"Lara Croft", "Muhammad Alrefai"})
	        {
	            List<String> orders = customerManager.getCustomer(name).getOrderHistory();
	            for (String meal : orders) 
	            {
	                globalTrend.put(meal, globalTrend.getOrDefault(meal, 0) + 1);
	            }
	        }

	        assertEquals(3, globalTrend.get("Pasta"));
	        assertEquals(1, globalTrend.get("Grilled Chicken"));
	    }

	    private Map<String, Integer> getMealFrequencies(List<String> orders)
	    {
	        Map<String, Integer> freq = new HashMap<>();
	        for (String meal : orders)
	        {
	            freq.put(meal, freq.getOrDefault(meal, 0) + 1);
	        }
	        return freq;
	    }
}
