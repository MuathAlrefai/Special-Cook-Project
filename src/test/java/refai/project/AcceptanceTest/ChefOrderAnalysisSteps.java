package refai.project.AcceptanceTest;

import refai.project.context.TestContext;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ChefOrderAnalysisSteps {

    private final TestContext context;

    public ChefOrderAnalysisSteps(TestContext context) {
        this.context = context;
    }

    @And("the customer has ordered {string} and {string}")
    public void the_customer_has_ordered_and(String meal1, String meal2) {
        assertTrue(context.customerManager.placeOrder(context.customer.getName(), meal1));
        assertTrue(context.customerManager.placeOrder(context.customer.getName(), meal2));
    }

    @When("the chef views the order history for {string}")
    public void the_chef_views_the_order_history_for(String name) {
        context.customer = context.customerManager.getCustomer(name);
        assertNotNull(context.customer);
    }

    @Then("the system should show the orders {string} and {string}")
    public void the_system_should_show_the_orders_and(String meal1, String meal2) {
        List<String> history = context.customer.getOrderHistory();
        assertNotNull(history);
        assertTrue(history.contains(meal1));
        assertTrue(history.contains(meal2));
    }

    @And("the customer has ordered {string} three times")
    public void the_customer_has_ordered_three_times(String meal) {
        for (int i = 0; i < 3; i++) {
            assertTrue(context.customerManager.placeOrder(context.customer.getName(), meal));
        }
    }

    @When("the chef analyzes order history for {string}")
    public void the_chef_analyzes_order_history_for(String name) {
        context.customer = context.customerManager.getCustomer(name);
        assertNotNull(context.customer);
    }

    @Then("the system should suggest a personalized plan including {string}")
    public void the_system_should_suggest_a_personalized_plan_including(String meal) {
        List<String> history = context.customer.getOrderHistory();
        long count = history.stream().filter(m -> m.equals(meal)).count();
        assertTrue(count >= 2, "Expected at least two orders of the meal for suggestion");
    }

    @Then("the system should show that there are no orders available")
    public void the_system_should_show_that_there_are_no_orders_available() {
        List<String> history = context.customer.getOrderHistory();
        assertNotNull(history);
        assertTrue(history.isEmpty(), "Expected no orders in history");
    }
}
