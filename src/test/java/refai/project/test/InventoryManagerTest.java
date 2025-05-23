package refai.project.test;

import refai.project.manager.InventoryManager;
import refai.project.manager.KitchenManager;
import refai.project.manager.NotificationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryManagerTest {

    private InventoryManager inventoryManager;

    @BeforeEach
    public void setup() {
        inventoryManager = new InventoryManager(new NotificationManager());
    }

    @Test
    public void testGetItemsToRestock() {
        inventoryManager.addIngredient("Tomato", 2);
        inventoryManager.setThreshold("Tomato", 5);
        Map<String, Integer> restocks = inventoryManager.getItemsToRestock();
        assertTrue(restocks.containsKey("Tomato"));
    }

    @Test
    public void testCheckLowStockIngredients() {
        inventoryManager.addIngredient("Onion", 1);
        inventoryManager.setThreshold("Onion", 4);
        List<String> lowStock = inventoryManager.checkLowStockIngredients();
        assertEquals(1, lowStock.size());
        assertEquals("Onion", lowStock.get(0));
    }

    @Test
    public void testNotifyLowStock_singleItem() {
        KitchenManager kitchenManager = new KitchenManager();
        kitchenManager.setName("Chef Amal");

        inventoryManager.addIngredient("Cheese", 1);
        inventoryManager.setThreshold("Cheese", 3);

        // Assert that cheese is identified as needing restocking
        assertTrue(inventoryManager.shouldRestock("Cheese"));

        // Assert that cheese appears in the low stock ingredients list
        List<String> lowStockItems = inventoryManager.checkLowStockIngredients();
        assertTrue(lowStockItems.contains("Cheese"));
        assertEquals(1, lowStockItems.size());

        inventoryManager.notifyLowStock(kitchenManager);
    }

    @Test
    public void testNotifyLowStock_multipleItems() {
        KitchenManager kitchenManager = new KitchenManager();
        kitchenManager.setName("Chef Amal");

        inventoryManager.addIngredient("Salt", 0);
        inventoryManager.addIngredient("Pepper", 1);
        inventoryManager.setThreshold("Salt", 2);
        inventoryManager.setThreshold("Pepper", 3);

        assertTrue(inventoryManager.shouldRestock("Salt"));
        assertTrue(inventoryManager.shouldRestock("Pepper"));

        // Assert that both ingredients appear in the low stock ingredients list
        List<String> lowStockItems = inventoryManager.checkLowStockIngredients();
        assertTrue(lowStockItems.contains("Salt"));
        assertTrue(lowStockItems.contains("Pepper"));
        assertEquals(2, lowStockItems.size());


        inventoryManager.notifyLowStock(kitchenManager);
    }

    @Test
    public void testCreateMultipleItemMessage_format() {
        inventoryManager.addIngredient("Oil", 1);
        inventoryManager.setThreshold("Oil", 4);

        List<String> low = List.of("Oil");
        String message = invokeCreateMultipleItemMessage(low);
        assertTrue(message.contains("Oil"));
        assertTrue(message.contains("Please restock"));
    }

    private String invokeCreateMultipleItemMessage(List<String> items) {
        try {
        	Method method = InventoryManager.class.getDeclaredMethod("createMultipleItemMessage", List.class);
            method.setAccessible(true);
            return (String) method.invoke(inventoryManager, items);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void testRestockSuggestions() {
        inventoryManager.addIngredient("Rice", 2);
        inventoryManager.setThreshold("Rice", 5);

        Map<String, Boolean> suggestions = inventoryManager.restockSuggestions();
        assertTrue(suggestions.containsKey("Rice"));
        assertTrue(suggestions.get("Rice"));
    }
    
    @Test
    public void testUseIngredientExceedsAvailable() {
        inventoryManager.addIngredient("Flour", 3);
        inventoryManager.useIngredient("Flour", 10);
        assertEquals(0, inventoryManager.getQuantity("Flour"));
    }


    @Test
    public void testGetQuantityForUnknownIngredient() {
        assertEquals(0, inventoryManager.getQuantity("UnknownItem"));
    }

    @Test
    public void testSetNotificationManagerAfterConstruction() {
        InventoryManager manager = new InventoryManager(); 
        manager.setNotificationManager(new NotificationManager());

        KitchenManager kitchenManager = new KitchenManager("Chef Amal");
        manager.addIngredient("Yogurt", 1);
        manager.setThreshold("Yogurt", 3);


        // Assert that Yogurt is identified as needing restocking
        assertTrue(manager.shouldRestock("Yogurt"));

        // Assert that Yogurt appears in the low stock ingredients list
        List<String> lowStockItems = manager.checkLowStockIngredients();
        assertTrue(lowStockItems.contains("Yogurt"));
        assertEquals(1, lowStockItems.size());

        manager.notifyLowStock(kitchenManager);
    }

}
