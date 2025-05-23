package refai.project;

import refai.project.manager.*;
import refai.project.manager.KitchenManager;
import refai.project.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class App {
    public static void main(String[] args) {
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("Special Cook Personal Management System");
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");

        //initialize managers
        CustomerManager customerManager = new CustomerManager();
        KitchenManager kitchenManager = new KitchenManager("Head Kitchen");
        KitchenStaffManager chefManager = new KitchenStaffManager();
        NotificationManager notificationManager = new NotificationManager();
        InventoryManager inventoryManager = new InventoryManager(notificationManager);
        DeliveryManager deliveryManager = new DeliveryManager();
        InvoiceManager invoiceManager = new InvoiceManager();
        ReportingManager reportingManager = new ReportingManager();

        //register customers
        System.out.println("Registering customers...");
        customerManager.registerCustomer("Amal");
        customerManager.registerCustomer("Basim");
        customerManager.updatePreferences("Amal", "Vegetarian", "Dairy,Peanuts");
        customerManager.updateContactInfo("Amal", "amal@example.com", "555-123-4567", "123 Main St");
        customerManager.updateContactInfo("Basim", "basim@example.com", "555-789-0123", "456 Oak Ave");

        //register chefs
        System.out.println("\nRegistering chefs...");
        Chef chef1 = chefManager.registerChef("Gordon");
        Chef chef2 = chefManager.registerChef("GordonJr");
        kitchenManager.addChef(chef1);
        kitchenManager.addChef(chef2);

        //add ingredients to inventory
        System.out.println("\nStocking inventory...");
        inventoryManager.addIngredient("Chicken", 30);
        inventoryManager.addIngredient("Rice", 100);
        inventoryManager.addIngredient("Spices", 25);
        inventoryManager.addIngredient("Tofu", 20);
        inventoryManager.addIngredient("Broccoli", 20);
        inventoryManager.setThreshold("Chicken", 40);
        inventoryManager.setThreshold("Broccoli", 20);
        inventoryManager.setThreshold("Spices", 30);

        //check low stock and notify manager
        System.out.println("\nChecking inventory levels...");
        List<String> lowStock = inventoryManager.checkLowStockIngredients();
        if (!lowStock.isEmpty()) {
            System.out.println("Low stock items: " + lowStock);
            inventoryManager.notifyLowStock(kitchenManager);
        }

        //place customer orders
        System.out.println("\nProcessing customer orders...");
        customerManager.placeOrder("Amal", "Tofu Stir Fry");
        customerManager.placeOrder("Basim", "Chicken Rice Bowl");

        //request custom meal
        List<String> customIngredients = new ArrayList<>(Arrays.asList("Tofu", "Broccoli", "Rice"));
        customerManager.requestCustomMeal("Amal", customIngredients);

        //schedule cooking tasks
        System.out.println("\nScheduling cooking tasks...");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            //schedule regular cooking tasks
            CookingTask task1 = chefManager.scheduleCookingTask("Gordon", "ORDER-1", "2025-05-23 10:00:00");
            chefManager.addPreparationRequirement(task1.getTaskId(), "Veggie Bowl", "Prep vegetables 30 min before");

            CookingTask task2 = chefManager.scheduleCookingTask("GordonJr", "ORDER-2", "2025-05-23 11:30:00");
            chefManager.addPreparationRequirement(task2.getTaskId(), "Sauce preparation", "Prepare special sauce");

            //create an urgent task
            System.out.println("\nCreating urgent cooking task...");
            CookingTask urgentTask = chefManager.createUrgentTask("ORDER-URGENT");
            if (urgentTask != null) {
                System.out.println("Urgent task assigned to: " + urgentTask.getChefId());
            }

            //send daily schedule to chefs
            System.out.println("\nSending daily schedules to chefs...");
            Date scheduleDate = sdf.parse("2025-05-23 00:00:00");
            List<CookingTask> gordonTasks = chefManager.getTasksForDate("Gordon", scheduleDate);
            notificationManager.sendDailySchedule(chef1, gordonTasks, scheduleDate);

            //schedule deliveries
            System.out.println("\nScheduling deliveries...");
            Delivery delivery1 = deliveryManager.scheduleDelivery("Amal", "ORDER-1", "2025-05-23 12:30:00");
            Delivery delivery2 = deliveryManager.scheduleDelivery("Basim", "ORDER-2", "2025-05-23 13:45:00");

            //schedule delivery reminders
            System.out.println("\nSetting up delivery reminders...");
            Customer amal = customerManager.getCustomer("Amal");
            notificationManager.scheduleDeliveryReminders(amal, delivery1);

            //generate and send invoices
            System.out.println("\nGenerating invoices...");
            Order order1 = new Order("ORDER-1", "Amal");
            order1.addItem(new OrderItem("ITEM-1", "Tofu Stir Fry", 1, 15.99));

            Order order2 = new Order("ORDER-2", "Basim");
            order2.addItem(new OrderItem("ITEM-2", "Chicken Rice Bowl", 1, 12.99));

            Invoice invoice1 = invoiceManager.generateInvoice("Amal", "ORDER-1", order1);
            Invoice invoice2 = invoiceManager.generateInvoice("Basim", "ORDER-2", order2);

            //add invoices to customer records
            customerManager.addInvoiceToCustomer("Amal", invoice1);
            customerManager.addInvoiceToCustomer("Basim", invoice2);

            //send invoices
            invoiceManager.sendInvoice(invoice1, amal);

            //generate financial report
            System.out.println("\nGenerating financial reports...");
            FinancialReport monthlyReport = reportingManager.generateMonthlyRevenueReport(new Date());
            System.out.println("Monthly report generated with " + monthlyReport.getEntries().size() + " entries");

            //process scheduled notifications
            System.out.println("\nProcessing scheduled notifications...");
            notificationManager.processScheduledNotifications();

        } catch (ParseException e) {
            System.out.println("Error with date parsing: " + e.getMessage());
        }

        System.out.println("\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("System operations completed successfully");
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
    }
}