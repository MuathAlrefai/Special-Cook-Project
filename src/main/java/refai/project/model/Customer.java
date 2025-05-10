package refai.project.model;

import java.util.ArrayList;
import java.util.List;

public class Customer
{
    private List<String> orderHistory;
    private final String name;
    private final String customerId;
    private String dietaryPreferences;
    private String allergies;
    private List<String> customMealIngredients;

    // New fields for billing system
    private String email;
    private String phone;
    private String address;
    private String preferredInvoiceFormat;

    public Customer(String name)
    {
        if (name == null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException("*** Error: Invalid customer name.");
        }
        this.name = name;
        this.customerId = "CUST-" + System.currentTimeMillis();
        this.dietaryPreferences = "";
        this.allergies = "";
        this.orderHistory = new ArrayList<>();
        this.customMealIngredients = new ArrayList<>();
        this.preferredInvoiceFormat = "PDF"; // Default format
    }

    public String getName()
    {
        return name;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public String getDietaryPreferences()
    {
        return dietaryPreferences;
    }

    public String getAllergies()
    {
        return allergies;
    }

    public void setDietaryPreferences(String dietaryPreferences)
    {
        this.dietaryPreferences = dietaryPreferences;
    }

    public void setAllergies(String allergies)
    {
        this.allergies = allergies;
    }

    public void addOrder(String mealName)
    {
        orderHistory.add(mealName);
    }

    public List<String> getOrderHistory()
    {
        return new ArrayList<>(orderHistory);
    }

    public void setCustomMealIngredients(List<String> ingredients)
    {
        this.customMealIngredients = ingredients;
    }

    public List<String> getCustomMealIngredients()
    {
        return customMealIngredients;
    }

    // Billing system methods

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPreferredInvoiceFormat()
    {
        return preferredInvoiceFormat;
    }

    public void setPreferredInvoiceFormat(String preferredInvoiceFormat)
    {
        if (preferredInvoiceFormat != null && !preferredInvoiceFormat.trim().isEmpty()) {
            this.preferredInvoiceFormat = preferredInvoiceFormat;
        }
    }
}
