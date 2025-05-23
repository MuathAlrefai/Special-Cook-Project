package refai.project.test;

import refai.project.model.Ingredient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {

    @Test
    void testConstructorAndGetters() {
        Ingredient ingredient = new Ingredient("Salt", 10.0, 5.0, "kg");

        assertEquals("Salt", ingredient.getName());
        assertEquals(10.0, ingredient.getCurrentStock());
        assertEquals(5.0, ingredient.getMinimumThreshold());
        assertEquals("kg", ingredient.getUnit());
    }

    @Test
    void testSetters() {
        Ingredient ingredient = new Ingredient("Flour", 20.0, 8.0, "g");

        ingredient.setName("Sugar");
        ingredient.setCurrentStock(15.0);
        ingredient.setMinimumThreshold(12.0);
        ingredient.setUnit("kg");

        assertEquals("Sugar", ingredient.getName());
        assertEquals(15.0, ingredient.getCurrentStock());
        assertEquals(12.0, ingredient.getMinimumThreshold());
        assertEquals("kg", ingredient.getUnit());
    }

    @Test
    void testIsBelowThreshold() {
        Ingredient ing1 = new Ingredient("Oil", 3.0, 5.0, "L");
        assertTrue(ing1.isBelowThreshold());

        Ingredient ing2 = new Ingredient("Water", 10.0, 5.0, "L");
        assertFalse(ing2.isBelowThreshold());
    }

    @Test
    void testToString() {
        Ingredient ingredient = new Ingredient("Rice", 25.0, 10.0, "kg");
        String expected = "Rice: 25.0 kg (min: 10.0 kg)";
        assertEquals(expected, ingredient.toString());
    }
}
