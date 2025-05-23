package refai.project.AcceptanceTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import refai.project.manager.KitchenManager;

import refai.project.model.Notification;
import refai.project.manager.InventoryManager;
import refai.project.manager.NotificationManager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class LowStockNotificationsStepDefinitions {
    private KitchenManager kitchenManager;
    private NotificationManager notificationManager;
    private InventoryManager inventoryManager;

    @Given("a kitchen manager {string} is registered in the system")
    public void aKitchenManagerIsRegisteredInTheSystem(String managerName) {
        notificationManager = new NotificationManager();
        kitchenManager = new KitchenManager(managerName);
        inventoryManager = new InventoryManager(notificationManager);

        System.out.println("Kitchen manager " + managerName + " registered");
    }

    @And("ingredient {string} has a current stock of {string} kg")
    public void ingredientHasACurrentStockOfKg(String ingredientName, String stock) {
        inventoryManager.addIngredient(ingredientName, Integer.parseInt(stock));
        System.out.println("Added ingredient " + ingredientName +
                " with stock " + stock + " kg");
    }

    @And("the minimum threshold for {string} is set to {string} kg")
    public void theMinimumThresholdForIsSetToKg(String ingredientName, String threshold) {
        inventoryManager.setThreshold(ingredientName, Integer.parseInt(threshold));
        System.out.println("Set minimum threshold for " + ingredientName +
                " to " + threshold + " kg");
    }

    @And("ingredient {string} has a current stock of {string} kg with minimum threshold of {string} kg")
    public void ingredientHasACurrentStockOfKgWithMinimumThresholdOfKg(
            String ingredientName, String stock, String threshold) {
        inventoryManager.addIngredient(ingredientName, Integer.parseInt(stock));
        inventoryManager.setThreshold(ingredientName, Integer.parseInt(threshold));

        System.out.println("Added ingredient " + ingredientName +
                " with stock " + stock + " kg" +
                " and threshold " + threshold + " kg");
    }

    @And("ingredient {string} has a current stock of {string} liters with minimum threshold of {string} liters")
    public void ingredientHasACurrentStockOfLitersWithMinimumThresholdOfLiters(
            String ingredientName, String stock, String threshold) {
        inventoryManager.addIngredient(ingredientName, Integer.parseInt(stock));
        inventoryManager.setThreshold(ingredientName, Integer.parseInt(threshold));

        System.out.println("Added ingredient " + ingredientName +
                " with stock " + stock + " liters" +
                " and threshold " + threshold + " liters");
    }

    @When("the system checks the inventory levels")
    public void theSystemChecksTheInventoryLevels() {
        System.out.println("Checking inventory levels...");
        inventoryManager.notifyLowStock(kitchenManager);
    }

    @Then("the kitchen manager should receive a low stock notification for {string}")
    public void theKitchenManagerShouldReceiveALowStockNotificationFor(String ingredientName) {
        List<Notification> notifications = kitchenManager.getNotifications();

        System.out.println("Checking for low stock notification for " + ingredientName);
        System.out.println("Total notifications: " + notifications.size());

        boolean foundNotification = false;
        for (Notification notification : notifications) {
            System.out.println("Notification subject: " + notification.getSubject());
            System.out.println("Notification content: " + notification.getContent());

            if (notification.getSubject().contains(ingredientName) ||
                    notification.getContent().contains(ingredientName)) {
                foundNotification = true;
                break;
            }
        }

        assertTrue(foundNotification,
                "Kitchen manager should have received notification for " + ingredientName);
    }

    @Then("no low stock notification should be sent")
    public void noLowStockNotificationShouldBeSent() {
        List<Notification> notifications = kitchenManager.getNotifications();

        System.out.println("Checking that no low stock notifications were sent");
        System.out.println("Total notifications: " + notifications.size());

        boolean foundStockNotification = false;
        for (Notification notification : notifications) {
            if (notification.getSubject().contains("Low Stock") ||
                    notification.getSubject().contains("low stock")) {
                foundStockNotification = true;
                System.out.println("Found low stock notification: " + notification.getSubject());
                break;
            }
        }

        assertFalse(foundStockNotification, "No low stock notifications should have been sent");
    }

    @Then("the kitchen manager should receive a consolidated notification for multiple low stock ingredients")
    public void theKitchenManagerShouldReceiveAConsolidatedNotificationForMultipleLowStockIngredients() {
        List<Notification> notifications = kitchenManager.getNotifications();

        System.out.println("Checking for consolidated low stock notification");
        System.out.println("Total notifications: " + notifications.size());

        boolean foundConsolidatedNotification = false;
        for (Notification notification : notifications) {
            System.out.println("Notification subject: " + notification.getSubject());

            if (notification.getSubject().contains("Multiple Low Stock")) {
                foundConsolidatedNotification = true;
                System.out.println("Found consolidated notification: " + notification.getSubject());
                System.out.println("Content: " + notification.getContent());
                break;
            }
        }

        assertTrue(foundConsolidatedNotification,
                "Kitchen manager should have received a consolidated notification");

        //there should be only one notification for all ingredients
        assertEquals(1, notifications.size(),
                "There should be exactly one notification for all low stock ingredients");
    }

    @And("the notification should include {string}, {string}, and {string}")
    public void theNotificationShouldIncludeAnd(String ing1, String ing2, String ing3) {
        Notification notification = kitchenManager.getNotifications().get(0);
        String content = notification.getContent();

        System.out.println("Checking notification content includes all ingredients");

        assertTrue(content.contains(ing1),
                "Notification should include " + ing1);
        assertTrue(content.contains(ing2),
                "Notification should include " + ing2);
        assertTrue(content.contains(ing3),
                "Notification should include " + ing3);
    }
}