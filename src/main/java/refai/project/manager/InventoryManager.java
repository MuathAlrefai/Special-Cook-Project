package refai.project.manager;


import refai.project.model.StockNotification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {

    private final Map<String, Integer> inventory = new HashMap<>();
    private final Map<String, Integer> thresholds = new HashMap<>();
    private NotificationManager notificationManager;

    public InventoryManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    public InventoryManager() {}

    public void addIngredient(String name, int quantity) {
        inventory.put(name, inventory.getOrDefault(name, 0) + quantity);
    }

    public void useIngredient(String name, int quantity) {
        inventory.put(name, Math.max(0, inventory.getOrDefault(name, 0) - quantity));
    }

    public int getQuantity(String name) {
        return inventory.getOrDefault(name, 0);
    }

    public void setThreshold(String name, int threshold) {
        thresholds.put(name, threshold);
    }

    public boolean shouldRestock(String name) {
        return getQuantity(name) < thresholds.getOrDefault(name, 0);
    }

    public Map<String, Boolean> restockSuggestions() {
        Map<String, Boolean> suggestions = new HashMap<>();
        for (String item : inventory.keySet()) {
            suggestions.put(item, shouldRestock(item));
        }
        return suggestions;
    }

    public Map<String, Integer> getItemsToRestock() {
        Map<String, Integer> restocks = new HashMap<>();
        for (String item : inventory.keySet()) {
            if (shouldRestock(item)) {
                restocks.put(item, inventory.get(item));
            }
        }
        return restocks;
    }

    public int getThreshold(String item) {
        return thresholds.getOrDefault(item, 0);
    }


    /**
     * Checks inventory and returns list of ingredients below threshold
     */
    public List<String> checkLowStockIngredients() {
        List<String> lowStock = new ArrayList<>();

        for (String ingredient : inventory.keySet()) {
            if (shouldRestock(ingredient)) {
                lowStock.add(ingredient);
            }
        }

        return lowStock;
    }

    /**
     * Notifies kitchen manager about low stock ingredients
     */
    public void notifyLowStock(KitchenManager manager) {
        if (notificationManager == null) {
            System.out.println("Warning: NotificationManager not set. Cannot send notifications.");
            return;
        }

        List<String> lowStock = checkLowStockIngredients();

        if (!lowStock.isEmpty()) {
            if (lowStock.size() == 1) {
                String ingredient = lowStock.get(0);
                StockNotification notification = new StockNotification(
                        manager.getName(),
                        "KITCHEN_MANAGER",
                        "Low Stock Alert: " + ingredient,
                        createSingleItemMessage(ingredient),
                        "EMAIL"
                );
                notification.addLowStockIngredient(ingredient);

                notificationManager.sendToManager(manager, notification);   
            } else {
                StockNotification notification = new StockNotification(
                        manager.getName(),
                        "KITCHEN_MANAGER",
                        "Multiple Low Stock Ingredients Alert",
                        createMultipleItemMessage(lowStock),
                        "EMAIL"
                );

                for (String ingredient : lowStock) {
                    notification.addLowStockIngredient(ingredient);
                }

                notificationManager.sendToManager(manager, notification); 
            }
        }
    }


    /**
     * Creates notification message for a single low stock ingredient
     */
    private String createSingleItemMessage(String ingredient) {
        return "The following ingredient is below the minimum threshold: " +
                ingredient + " - Current stock: " + getQuantity(ingredient) +
                " (Minimum: " + getThreshold(ingredient) + ")";
    }

    /**
     * Creates notification message for multiple low stock ingredients
     */
    private String createMultipleItemMessage(List<String> ingredients) {
        StringBuilder message = new StringBuilder("The following ingredients are below their minimum thresholds:\n\n");

        for (String ingredient : ingredients) {
            message.append("- ").append(ingredient)
                    .append(": Current stock: ").append(getQuantity(ingredient))
                    .append(" (Minimum: ").append(getThreshold(ingredient))
                    .append(")\n");
        }

        message.append("\nPlease restock these items as soon as possible.");
        return message.toString();
    }

    /**
     * Set notification manager after construction
     */
    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }
}