package refai.project;

import refai.project.manager.CustomerManager;
import refai.project.model.Customer;

public class App 
{
    public static void main(String[] args) 
    {
        System.out.println(" ### Welcome to the Special Cook Project ### ");

        CustomerManager customerManager = new CustomerManager();

        //Register new customer
        System.out.println("\n *** Registering customer...");
        customerManager.registerCustomer("Muhammad Alrefai");
        System.out.println("\nCustomer registered successfully with name:" + customerManager.getAllCustomers().toString());

        //Update Preferences
        System.out.println("\n*** Updating preferences...");
        customerManager.updatePreferences("Muhammad Alrefai", "Meat", "Broccoli");
        System.out.println("\nCustomer preferences updated successfully!");

        //Show preferences to edit
        Customer customer = customerManager.getCustomer("Muhammad Alrefai");
        if (customer != null) {
            System.out.println("\n*** Customer preferences:");
            System.out.println("- Dietary: " + customer.getDietaryPreferences());
            System.out.println("- Allergies: " + customer.getAllergies());
        }

        //Add orders
        System.out.println("\n*** Placing orders...");
        customerManager.placeOrder("Muhammad Alrefai", "Kebab");
        customerManager.placeOrder("Muhammad Alrefai", "Shawarma");
        customerManager.placeOrder("Muhammad Alrefai", "Beef");
        System.out.println("\nOrder placed successfully as follows:" + customer.getOrderHistory().toString());

        //Show order history
        System.out.println("\n*** Showing Order History:");
        for (String order : customer.getOrderHistory()) {
            System.out.println("- " + order);
        }

        //Reorder a meal
        System.out.println("\n*** Reordering <Kebab>");
        boolean reordered = customerManager.reorderMeal("Muhammad Alrefai", "Kebab");
        if (reordered) {
            System.out.println("*** Meal reordered successfully!");
        } else {
            System.out.println("*** Failed to reorder meal.");
        }

        //Show updated order history
        System.out.println("\n*** Showing Updated Order History:");
        for (String order : customer.getOrderHistory()) {
            System.out.println("- " + order);
        }

        System.out.println("\n*** End of Project Demo. ***");
    }
}
