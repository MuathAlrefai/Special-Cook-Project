package refai.project.AcceptanceTest;

import refai.project.manager.InventoryManager;
import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.en.*;

import java.util.Map;

public class InventoryStepDefinitions {

    private InventoryManager inventoryManager = new InventoryManager();
    private int resultQuantity;
    private Map<String, Boolean> restockResults;

    @Given("the inventory has {string} with quantity {int}")
    public void the_inventory_has_with_quantity(String item, Integer quantity) {
        inventoryManager.addIngredient(item, quantity);
    }

    @When("{int} {string} are used")
    public void item_is_used(int quantity, String item) {
        inventoryManager.useIngredient(item, quantity);
    }

    @When("{int} {string} are restocked")
    public void item_is_restocked(int quantity, String item) {
        inventoryManager.addIngredient(item, quantity);
    }

    @When("I check the stock for {string}")
    public void i_check_the_stock_for(String item) {
        resultQuantity = inventoryManager.getQuantity(item);
    }

    @Then("the inventory should show {int} {string}")
    public void the_inventory_should_show_quantity(int expectedQuantity, String item) {
        assertEquals(expectedQuantity, inventoryManager.getQuantity(item));
    }

    @Then("it should display {int} units of {string}")
    public void it_should_display_units_of_item(int expectedQuantity, String item) {
        assertEquals(expectedQuantity, resultQuantity);
    }

    @When("the threshold for {string} is {int}")
    public void the_threshold_for_is(String item, Integer threshold) {
        inventoryManager.setThreshold(item, threshold);
    }

    @Then("the system should suggest restocking {string}")
    public void the_system_should_suggest_restocking(String item) {
        assertTrue(inventoryManager.shouldRestock(item));
    }

    @Then("the system should not suggest restocking {string}")
    public void the_system_should_not_suggest_restocking(String item) {
        assertFalse(inventoryManager.shouldRestock(item));
    }

    @Given("the inventory has {string} with quantity {int} and threshold {int}")
    public void the_inventory_has_with_quantity_and_threshold(String item, int quantity, int threshold) {
        inventoryManager.addIngredient(item, quantity);
        inventoryManager.setThreshold(item, threshold);
    }

    @When("the system checks for low-stock items")
    public void the_system_checks_for_low_stock_items() {
        restockResults = inventoryManager.restockSuggestions();
    }

    @Then("it should suggest restocking {string} and {string}")
    public void it_should_suggest_restocking_multiple(String item1, String item2) {
        assertTrue(restockResults.getOrDefault(item1, false));
        assertTrue(restockResults.getOrDefault(item2, false));
    }
}