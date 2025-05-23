package refai.project.test;

import refai.project.manager.PurchaseOrder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PurchaseOrderTest {

    @Test
    void testConstructorAndGetters() {
        PurchaseOrder order = new PurchaseOrder("Rice", 5);

        assertEquals("Rice", order.getItem());
        assertEquals(5, order.getQuantity());
    }
}
