package refai.project.manager;

import java.util.HashMap;
import java.util.Map;

public class SupplierService {
    // Use Map to store actual PurchaseOrder objects instead of Booleans
    private final Map<String, PurchaseOrder> purchaseOrders = new HashMap<>();

    // Example priceMap for reference (unchanged)
    private final Map<String, Double> priceMap = new HashMap<>();

    public void setPrice(String item, double price) {
        priceMap.put(item, price);
    }

    public Double getPrice(String item) {
        return priceMap.get(item);
    }

    public boolean hasPrice(String item) {
        return priceMap.containsKey(item);
    }

    // Check if a PurchaseOrder exists for an item
    public boolean hasPurchaseOrder(String item) {
        return purchaseOrders.containsKey(item);
    }

    // Main logic to check and order if necessary
    public void checkAndOrder(String item, int quantity, int threshold) {
        if (quantity < threshold) {
            int orderQuantity = threshold - quantity; // How much to order
            generatePurchaseOrder(item, orderQuantity);
        }
    }

    private void generatePurchaseOrder(String item, int orderQuantity) {
        purchaseOrders.computeIfAbsent(item, k -> new PurchaseOrder(item, orderQuantity));
    }

}
