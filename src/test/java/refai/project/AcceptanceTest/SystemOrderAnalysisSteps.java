package refai.project.AcceptanceTest;

import refai.project.context.TestContext;
import io.cucumber.java.en.*;

import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class SystemOrderAnalysisSteps 
{

    private final TestContext context;
    private Map<String, Integer> mealFrequency = new HashMap<>();

    public SystemOrderAnalysisSteps(TestContext context) 
    {
        this.context = context;
    }

    @Given("the customer {string} has ordered the following meals:")
    public void the_customer_has_ordered_the_following_meals(String customerName, List<String> meals) 
    {
        context.customerManager.registerCustomer(customerName);
        for (String meal : meals)
        {
            context.customerManager.placeOrder(customerName, meal);
        }
    }

    @When("the administrator analyzes all order histories")
    public void the_administrator_analyzes_all_order_histories() 
    {
        mealFrequency.clear();
        for (String customerName : context.customerManager.getAllCustomers())
        {
            List<String> history = context.customerManager.getOrderHistory(customerName);
            for (String meal : history) {
                mealFrequency.put(meal, mealFrequency.getOrDefault(meal, 0) + 1);
            }
        }
    }

    @Then("the system should identify the most frequently ordered meal as {string}")
    public void the_system_should_identify_the_most_frequently_ordered_meal_as(String expectedMeal) 
    {
        String mostFrequentMeal = mealFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        assertEquals(expectedMeal, mostFrequentMeal);
    }

    @Then("the system should display the total orders for each customer")
    public void the_system_should_display_the_total_orders_for_each_customer() 
    {
        for (String customer : context.customerManager.getAllCustomers()) 
        {
            List<String> orders = context.customerManager.getOrderHistory(customer);
            assertNotNull(orders);
            System.out.println("🧾 " + customer + " has " + orders.size() + " orders: " + orders);
        }
    }

    @Then("the system should confirm no order history exists")
    public void the_system_should_confirm_no_order_history_exists()
    {
        for (String customer : context.customerManager.getAllCustomers()) 
        {
            List<String> history = context.customerManager.getOrderHistory(customer);
            assertTrue(history == null || history.isEmpty(), "Expected no orders for customer, but found some.");
        }
    }
    
    @When("the system administrator retrieves the order history for {string}")
    public void the_system_administrator_retrieves_the_order_history_for(String name) 
    {
        context.customer = context.customerManager.getCustomer(name);
        assertNotNull(context.customer);
        context.orderList = context.customerManager.getOrderHistory(name);
    }

    @Then("the system should return the order list {string}, {string}")
    public void the_system_should_return_the_order_list(String meal1, String meal2)
    {
        assertNotNull(context.orderList);
        assertEquals(2, context.orderList.size());
        assertTrue(context.orderList.contains(meal1));
        assertTrue(context.orderList.contains(meal2));
    }
    
    @Given("the customer has ordered {string} two times")
    public void the_customer_has_ordered_two_times(String meal)
    {
        for (int i = 0; i < 2; i++) 
        {
            context.customerManager.placeOrder(context.customer.getName(), meal);
        }
    }
    
    @When("the system administrator analyzes global meal trends")
    public void the_system_administrator_analyzes_global_meal_trends() 
    {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String customerName : context.customerManager.getAllCustomers()) 
        {
            List<String> history = context.customerManager.getOrderHistory(customerName);
            for (String meal : history) 
            {
                frequencyMap.put(meal, frequencyMap.getOrDefault(meal, 0) + 1);
            }
        }
        context.trendAnalysis = frequencyMap;
    }

    
    @Then("the system should identify {string} as a top ordered meal")
    public void the_system_should_identify_as_a_top_ordered_meal(String expectedMeal)
    {
        assertNotNull(context.trendAnalysis);
        int maxCount = Collections.max(context.trendAnalysis.values());
        assertEquals(maxCount, context.trendAnalysis.get(expectedMeal));
    }

    @Then("the system should indicate that no orders are available")
    public void the_system_should_indicate_that_no_orders_are_available() 
    {
        assertTrue(context.orderList == null || context.orderList.isEmpty(), "Expected no orders.");
    }



}
