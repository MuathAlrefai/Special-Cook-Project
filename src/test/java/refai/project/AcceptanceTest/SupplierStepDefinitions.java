package refai.project.AcceptanceTest;

import refai.project.manager.SupplierService;
import refai.project.manager.InventoryManager;

import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.en.*;

import java.util.Map;

public class SupplierStepDefinitions {

    private SupplierService supplierService = new SupplierService();
    private InventoryManager inventoryManager = new InventoryManager();
    private Double fetchedPrice;
    private boolean priceAvailable;

    @Given("the supplier provides a price of {double} for {string}")
    public void supplier_provides_price(double price, String item) {
        supplierService.setPrice(item, price);
    }

    @When("I request the current price for {string}")
    public void request_price(String item) {
        fetchedPrice = supplierService.getPrice(item);
        priceAvailable = supplierService.hasPrice(item);
    }

    @Then("the system should return {double} as the current price")
    public void system_returns_price(double expected) {
        assertEquals(expected, fetchedPrice);
    }

    @Given("no supplier price is available for {string}")
    public void no_price_available(String item) {
        //no action needed, as price is not set
    }

    @Then("the system should indicate that price data is unavailable")
    public void system_indicates_no_price() {
        assertFalse(priceAvailable);
        assertNull(fetchedPrice);
    }

    @When("the supplier updates the price for {string} to {double}")
    public void update_supplier_price(String item, double newPrice) {
        supplierService.setPrice(item, newPrice);
        fetchedPrice = supplierService.getPrice(item);
    }

    @Then("the system should return {double} as the updated price")
    public void system_returns_updated_price(double expectedPrice) {
        assertEquals(expectedPrice, fetchedPrice);
    }


    @When("the system checks stock levels")
    public void the_system_checks_stock_levels() {
        Map<String, Integer> itemsToRestock = inventoryManager.getItemsToRestock();
        for (Map.Entry<String, Integer> entry : itemsToRestock.entrySet()) {
            String item = entry.getKey();
            int currentQuantity = entry.getValue();
            int threshold = inventoryManager.getThreshold(item);
            int quantityToOrder = threshold - currentQuantity;
            if (quantityToOrder > 0) {
                supplierService.checkAndOrder(item, quantityToOrder, threshold);
            }
        }
    }

    @Then("it should automatically generate a purchase order for {string}")
    public void should_generate_order(String item) {
        assertTrue(supplierService.hasPurchaseOrder(item), "Purchase order for " + item + " not generated");
    }

    @Then("it should not generate a purchase order for {string}")
    public void should_not_generate_order(String item) {
        assertFalse(supplierService.hasPurchaseOrder(item), "Unexpected purchase order for " + item);
    }

    @Then("it should generate purchase orders for {string} and {string}")
    public void it_should_generate_purchase_orders_for_two_items(String item1, String item2) {
        assertTrue(supplierService.hasPurchaseOrder(item1), "Expected purchase order for " + item1);
        assertTrue(supplierService.hasPurchaseOrder(item2), "Expected purchase order for " + item2);
    }
}
