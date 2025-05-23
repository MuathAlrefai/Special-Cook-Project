package refai.project;

import refai.project.manager.*;
import refai.project.manager.KitchenManager;
import refai.project.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());
    
    //customer constants
    private static final String CUSTOMER_BASIM = "Basim";
    
    //chef constants
    private static final String CHEF_GORDON = "Gordon";
    
    //inventory constants
    private static final String INGREDIENT_BROCCOLI = "Broccoli";
    
    //order constants
    private static final String ORDER_1 = "ORDER-1";
    private static final String ORDER_2 = "ORDER-2";

    public static void main(String[] args) {
        logger.info("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        logger.info("Special Cook Personal Management System");
        logger.info("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");

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
        logger.info("Registering customers...");
        customerManager.registerCustomer("Amal");
        customerManager.registerCustomer(CUSTOMER_BASIM);
        customerManager.updatePreferences("Amal", "Vegetarian", "Dairy,Peanuts");
        customerManager.updateContactInfo("Amal", "amal@example.com", "555-123-4567", "123 Main St");
        customerManager.updateContactInfo(CUSTOMER_BASIM, "basim@example.com", "555-789-0123", "456 Oak Ave");

        //register chefs
        logger.info("\nRegistering chefs...");
        Chef chef1 = chefManager.registerChef(CHEF_GORDON);
        Chef chef2 = chefManager.registerChef("GordonJr");
        kitchenManager.addChef(chef1);
        kitchenManager.addChef(chef2);

        //add ingredients to inventory
        logger.info("\nStocking inventory...");
        inventoryManager.addIngredient("Chicken", 30);
        inventoryManager.addIngredient("Rice", 100);
        inventoryManager.addIngredient("Spices", 25);
        inventoryManager.addIngredient("Tofu", 20);
        inventoryManager.addIngredient(INGREDIENT_BROCCOLI, 20);
        inventoryManager.setThreshold("Chicken", 40);
        inventoryManager.setThreshold(INGREDIENT_BROCCOLI, 20);
        inventoryManager.setThreshold("Spices", 30);

        //check low stock and notify manager
        logger.info("\nChecking inventory levels...");
        List<String> lowStock = inventoryManager.checkLowStockIngredients();
        if (!lowStock.isEmpty()) {
            logger.info("Low stock items: " + lowStock);
            inventoryManager.notifyLowStock(kitchenManager);
        }

        //place customer orders
        logger.info("\nProcessing customer orders...");
        customerManager.placeOrder("Amal", "Tofu Stir Fry");
        customerManager.placeOrder(CUSTOMER_BASIM, "Chicken Rice Bowl");

        //request custom meal
        List<String> customIngredients = new ArrayList<>(Arrays.asList("Tofu", INGREDIENT_BROCCOLI, "Rice"));
        customerManager.requestCustomMeal("Amal", customIngredients);

        //schedule cooking tasks
        logger.info("\nScheduling cooking tasks...");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            //schedule regular cooking tasks
            CookingTask task1 = chefManager.scheduleCookingTask(CHEF_GORDON, ORDER_1, "2025-05-23 10:00:00");
            chefManager.addPreparationRequirement(task1.getTaskId(), "Veggie Bowl", "Prep vegetables 30 min before");

            CookingTask task2 = chefManager.scheduleCookingTask("GordonJr", ORDER_2, "2025-05-23 11:30:00");
            chefManager.addPreparationRequirement(task2.getTaskId(), "Sauce preparation", "Prepare special sauce");

            //create an urgent task
            logger.info("\nCreating urgent cooking task...");
            CookingTask urgentTask = chefManager.createUrgentTask("ORDER-URGENT");
            if (urgentTask != null) {
                logger.info("Urgent task assigned to: " + urgentTask.getChefId());
            }

            //send daily schedule to chefs
            logger.info("\nSending daily schedules to chefs...");
            Date scheduleDate = sdf.parse("2025-05-23 00:00:00");
            List<CookingTask> gordonTasks = chefManager.getTasksForDate(CHEF_GORDON, scheduleDate);
            notificationManager.sendDailySchedule(chef1, gordonTasks, scheduleDate);

            //schedule deliveries
            logger.info("\nScheduling deliveries...");
            Delivery delivery1 = deliveryManager.scheduleDelivery("Amal", ORDER_1, "2025-05-23 12:30:00");
            Delivery delivery2 = deliveryManager.scheduleDelivery(CUSTOMER_BASIM, ORDER_2, "2025-05-23 13:45:00");

            //schedule delivery reminders
            logger.info("\nSetting up delivery reminders...");
            Customer amal = customerManager.getCustomer("Amal");
            notificationManager.scheduleDeliveryReminders(amal, delivery1);

            //generate and send invoices
            logger.info("\nGenerating invoices...");
            Order order1 = new Order(ORDER_1, "Amal");
            order1.addItem(new OrderItem("ITEM-1", "Tofu Stir Fry", 1, 15.99));

            Order order2 = new Order(ORDER_2, CUSTOMER_BASIM);
            order2.addItem(new OrderItem("ITEM-2", "Chicken Rice Bowl", 1, 12.99));

            Invoice invoice1 = invoiceManager.generateInvoice("Amal", ORDER_1, order1);
            Invoice invoice2 = invoiceManager.generateInvoice(CUSTOMER_BASIM, ORDER_2, order2);

            //add invoices to customer records
            customerManager.addInvoiceToCustomer("Amal", invoice1);
            customerManager.addInvoiceToCustomer(CUSTOMER_BASIM, invoice2);

            //send invoices
            invoiceManager.sendInvoice(invoice1, amal);

            //generate financial report
            logger.info("\nGenerating financial reports...");
            FinancialReport monthlyReport = reportingManager.generateMonthlyRevenueReport(new Date());
            logger.info("Monthly report generated with " + monthlyReport.getEntries().size() + " entries");

            //process scheduled notifications
            logger.info("\nProcessing scheduled notifications...");
            notificationManager.processScheduledNotifications();

        } catch (ParseException e) {
            logger.info("Error with date parsing: " + e.getMessage());
        }

        logger.info("\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        logger.info("System operations completed successfully");
        logger.info("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
    }
}