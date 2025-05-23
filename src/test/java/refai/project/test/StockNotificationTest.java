package refai.project.test;

import refai.project.model.StockNotification;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StockNotificationTest {

    @Test
    public void testStockNotificationBehavior() {
        StockNotification notif = new StockNotification(
                "Chef123", "CHEF", "Low Stock Alert",
                "Some ingredients are running low!", "EMAIL"
        );

        assertEquals("CHEF", notif.getRecipientType());
        assertEquals("HIGH", notif.getPriority());
        assertEquals("PENDING", notif.getStatus());
        assertTrue(notif.getLowStockIngredients().isEmpty());

        notif.addLowStockIngredient("Tomato");
        notif.addLowStockIngredient("Onion");

        List<String> ingredients = notif.getLowStockIngredients();
        assertEquals(2, ingredients.size());
        assertTrue(ingredients.contains("Tomato"));
        assertTrue(ingredients.contains("Onion"));

        List<String> newList = Arrays.asList("Garlic", "Pepper");
        notif.setLowStockIngredients(newList);
        assertEquals(2, notif.getLowStockIngredients().size());
        assertEquals("Garlic", notif.getLowStockIngredients().get(0));
    }
}
