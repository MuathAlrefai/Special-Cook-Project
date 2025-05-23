package refai.project.test;

import org.junit.jupiter.api.Test;
import refai.project.manager.PurchaseOrder;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseOrderTest {
    @Test
    void constructorAndGetters_shouldWorkCorrectly() {
        PurchaseOrder po = new PurchaseOrder("Organic Tomatoes", 50);

        assertEquals("Organic Tomatoes", po.getItem());
        assertEquals(50, po.getQuantity());
    }

    @Test
    void equalsAndHashCode_shouldWorkCorrectly() {
        PurchaseOrder po1 = new PurchaseOrder("Organic Tomatoes", 50);
        PurchaseOrder po2 = new PurchaseOrder("Organic Tomatoes", 50);

        assertEquals(po1, po2);
        assertEquals(po1.hashCode(), po2.hashCode());
    }
}
