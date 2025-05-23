package refai.project.AcceptanceTest;

import refai.project.context.TestContext;
import io.cucumber.java.en.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerOrderHistorySteps {

    private final TestContext context;

    public CustomerOrderHistorySteps(TestContext context) {
        this.context = context;
    }

    @And("the customer has placed the meals {string}, {string}")
    public void the_customer_has_placed_the_meals(String meal1, String meal2) {
        assertTrue(context.customerManager.placeOrder(context.customer.getName(), meal1));
        assertTrue(context.customerManager.placeOrder(context.customer.getName(), meal2));
    }

    @And("the customer has placed the meal {string}")
    public void the_customer_has_placed_the_meal(String meal) {
        assertTrue(context.customerManager.placeOrder(context.customer.getName(), meal));
    }

    @When("the customer requests their order history")
    public void the_customer_requests_their_order_history() {
        List<String> history = context.customerManager.getOrderHistory(context.customer.getName());
        assertNotNull(history);
        context.sessionData.put("orderHistory", history);
    }

    @Then("the system should return the list [{string}, {string}]")
    public void the_system_should_return_the_list(String meal1, String meal2) {
        List<String> history = (List<String>) context.sessionData.get("orderHistory");
        assertEquals(Arrays.asList(meal1, meal2), history);
    }

    @When("the customer reorders {string}")
    public void the_customer_reorders(String meal) {
        boolean result = context.customerManager.reorderMeal(context.customer.getName(), meal);
        context.sessionData.put("reorderResult", result);
    }

    @Then("the system should confirm the reorder and update history with {string}")
    public void the_system_should_confirm_the_reorder_and_update_history_with(String meal) {
        boolean result = (boolean) context.sessionData.get("reorderResult");
        assertTrue(result);
        List<String> history = context.customerManager.getOrderHistory(context.customer.getName());
        assertTrue(history.contains(meal));
        assertEquals(meal, history.get(history.size() - 1)); // last meal
    }

    @Then("the system should reject the reorder due to missing history")
    public void the_system_should_reject_the_reorder_due_to_missing_history() {
        boolean result = (boolean) context.sessionData.get("reorderResult");
        assertFalse(result);
    }
}
