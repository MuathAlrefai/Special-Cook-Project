package refai.project.test;

import refai.project.manager.SupplierService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SupplierServiceTest {
    @Test
    void testFetchRealTimePrice() {
        SupplierService service = new SupplierService();
        service.setPrice("Tomatoes", 3.5);
        assertEquals(3.5, service.getPrice("Tomatoes"));
    }

    @Test
    void testPriceUnavailable() {
        SupplierService service = new SupplierService();
        assertNull(service.getPrice("Cabbage"));
        assertFalse(service.hasPrice("Cabbage"));
    }

    @Test
    void testUpdatePrice() {
        SupplierService service = new SupplierService();
        service.setPrice("Carrots", 2.0);
        service.setPrice("Carrots", 2.5);
        assertEquals(2.5, service.getPrice("Carrots"));
    }

    @Test
    void testAutoGeneratePurchaseOrder() {
        SupplierService supplierService = new SupplierService();
        supplierService.checkAndOrder("Milk", 1, 5);
        assertTrue(supplierService.hasPurchaseOrder("Milk"));
    }

    @Test
    void testNoPurchaseOrderWhenSufficientStock() {
        SupplierService supplierService = new SupplierService();
        supplierService.checkAndOrder("Eggs", 10, 5);
        assertFalse(supplierService.hasPurchaseOrder("Eggs"));
    }

    @Test
    void testMultiplePurchaseOrders() {
        SupplierService supplierService = new SupplierService();
        supplierService.checkAndOrder("Butter", 2, 5);
        supplierService.checkAndOrder("Flour", 1, 4);
        assertTrue(supplierService.hasPurchaseOrder("Butter"));
        assertTrue(supplierService.hasPurchaseOrder("Flour"));
    }
}
