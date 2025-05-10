package refai.project.test;
import refai.project.manager.InventoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest {
    private InventoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new InventoryManager();
    }

    @Test
    void testIngredientUsageUpdatesQuantity() {
        manager.addIngredient("Tomatoes", 50);
        manager.useIngredient("Tomatoes", 10);
        assertEquals(40, manager.getQuantity("Tomatoes"));
    }

    @Test
    void testRestockingUpdatesQuantity() {
        manager.addIngredient("Onions", 20);
        manager.addIngredient("Onions", 30);
        assertEquals(50, manager.getQuantity("Onions"));
    }

    @Test
    void testGetCurrentStock() {
        manager.addIngredient("Cheese", 15);
        assertEquals(15, manager.getQuantity("Cheese"));
    }

    @Test
    void testSuggestRestockWhenLow() {
        manager.addIngredient("Lettuce", 4);
        manager.setThreshold("Lettuce", 5);
        assertTrue(manager.shouldRestock("Lettuce"));
    }

    @Test
    void testNoRestockWhenStockSufficient() {
        manager.addIngredient("Salt", 10);
        manager.setThreshold("Salt", 5);
        assertFalse(manager.shouldRestock("Salt"));
    }

    @Test
    void testMultipleLowStockSuggestions() {
        manager.addIngredient("Carrots", 2);
        manager.addIngredient("Potatoes", 3);
        manager.setThreshold("Carrots", 5);
        manager.setThreshold("Potatoes", 4);
        Map<String, Boolean> suggestions = manager.restockSuggestions();
        assertTrue(suggestions.get("Carrots"));
        assertTrue(suggestions.get("Potatoes"));
    }
}

