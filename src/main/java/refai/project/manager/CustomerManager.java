package refai.project.manager;

import refai.project.model.Customer;
import refai.project.model.Invoice;

import java.util.*;

public class CustomerManager {
    // Constants for error messages
    private static final String ERROR_CUSTOMER_NOT_FOUND = "**Error: Customer not found.";
    private static final String ERROR_INVALID_INPUT = "**Error: Invalid input provided.";
    private static final String ERROR_NO_INGREDIENTS = "**Error: No ingredients provided.";

    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, List<Invoice>> customerInvoices = new HashMap<>();

    private static final List<String> availableIngredients = Arrays.asList(
            "Chicken", "Rice", "Spices",
            "Tofu", "Broccoli", "Carrots",
            "Beef", "Milk", "Fish", "Tomato",
            "Peanuts"
    );

    private boolean incompatibleCombinationDetected = false;
    private boolean emptyIngredientsProvided = false;
    private boolean allergyConflictDetected = false;
    private boolean ingredientUnavailable = false;

    public boolean registerCustomer(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println(ERROR_INVALID_INPUT);
            return false;
        }
        if (customers.containsKey(name)) {
            System.out.println("**Customer already exists...");
            return false;
        }
        customers.put(name, new Customer(name));
        customerInvoices.put(name, new ArrayList<>());
        return true;
    }

    public boolean updatePreferences(String name, String dietaryPreferences, String allergies) {
        Customer customer = customers.get(name);
        if (customer == null) {
            System.out.println(ERROR_CUSTOMER_NOT_FOUND);
            return false;
        }
        customer.setDietaryPreferences(dietaryPreferences);
        customer.setAllergies(allergies);
        return true;
    }

    public Customer getCustomer(String name) {
        return customers.get(name);
    }

    public boolean isRegistered(String name) {
        return customers.containsKey(name);
    }

    public String[] getCustomerPrefrences(String name) {
        Customer customer = customers.get(name);
        if (customer == null) {
            System.out.println(ERROR_CUSTOMER_NOT_FOUND);
            return null;
        }
        return new String[]{customer.getDietaryPreferences(), customer.getAllergies()};
    }

    public boolean placeOrder(String customerName, String mealName) {
        Customer customer = customers.get(customerName);
        if (customer == null || mealName == null || mealName.trim().isEmpty()) {
            System.out.println(ERROR_INVALID_INPUT);
            return false;
        }
        customer.addOrder(mealName);
        return true;
    }

    public List<String> getOrderHistory(String customerName) {
        Customer customer = customers.get(customerName);
        if (customer == null) {
            System.out.println(ERROR_CUSTOMER_NOT_FOUND);
            return null;
        }
        return customer.getOrderHistory();
    }

    public boolean reorderMeal(String customerName, String mealName) {
        Customer customer = customers.get(customerName);
        if (customer == null || !customer.getOrderHistory().contains(mealName)) {
            System.out.println(ERROR_INVALID_INPUT);
            return false;
        }
        customer.addOrder(mealName);
        System.out.println("**Meal reordered successfully for " + customerName + ": " + mealName);
        return true;
    }

    public boolean requestCustomMeal(String customerName, List<String> ingredients) {
        Customer customer = customers.get(customerName);
        if (customer == null) {
            System.out.println(ERROR_CUSTOMER_NOT_FOUND);
            return false;
        }

        resetDetectionFlags();

        if (!validateIngredients(ingredients)) {
            return false;
        }

        if (checkIngredientAvailability(ingredients)) {
            return false;
        }

        if (checkAllergyConflicts(customer, ingredients)) {
            return false;
        }

        if (checkIncompatibleCombination(ingredients)) {
            return false;
        }

        customer.setCustomMealIngredients(ingredients);
        System.out.println("Custom meal saved for " + customerName);
        return true;
    }

    private boolean checkIngredientAvailability(List<String> ingredients) {
        for (String ingredient : ingredients) {
            if (!availableIngredients.contains(ingredient)) {
                System.out.println("**Ingredient not available: " + ingredient);
                ingredientUnavailable = true;
                return true;
            }
        }
        return false;
    }


    private void resetDetectionFlags() {
        incompatibleCombinationDetected = false;
        emptyIngredientsProvided = false;
        allergyConflictDetected = false;
        ingredientUnavailable = false;
    }

    private boolean validateIngredients(List<String> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            System.out.println(ERROR_NO_INGREDIENTS);
            emptyIngredientsProvided = true;
            return false;
        }
        return true;
    }

    private boolean checkAllergyConflicts(Customer customer, List<String> ingredients) {
        if (customer.getAllergies() == null || customer.getAllergies().isEmpty()) {
            return false;
        }

        List<String> allergyList = Arrays.asList(customer.getAllergies().split(","));
        for (String ingredient : ingredients) {
            for (String allergy : allergyList) {
                if (ingredient.equalsIgnoreCase(allergy.trim())) {
                    System.out.println("**Ingredient conflicts with allergy: " + ingredient);
                    allergyConflictDetected = true;
                    customer.setCustomMealIngredients(new ArrayList<>());
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIncompatibleCombination(List<String> ingredients) {
        if (ingredients.contains("Milk") && ingredients.contains("Fish")) {
            incompatibleCombinationDetected = true;
            return true;
        }
        return false;
    }

    public List<String> getCustomMeal(String customerName) {
        Customer customer = customers.get(customerName);
        if (customer == null) return null;
        return customer.getCustomMealIngredients();
    }

    public boolean hasIncompatibleCombination() {
        return incompatibleCombinationDetected;
    }

    public boolean isEmptyIngredientsProvided() {
        return emptyIngredientsProvided;
    }

    public boolean hasAllergyConflict() {
        return allergyConflictDetected;
    }

    public boolean isIngredientUnavailable() {
        return ingredientUnavailable;
    }

    public Set<String> getAllCustomers() {
        return customers.keySet();
    }

    public Map<String, List<String>> suggestAlternatives(String customerName, List<String> ingredients) {
        Map<String, List<String>> alternatives = new HashMap<>();
        Customer customer = customers.get(customerName);

        if (customer == null || ingredients == null || ingredients.isEmpty()) {
            return alternatives;
        }

        addAllergyAlternatives(customer, alternatives);
        addIngredientAlternatives(ingredients, alternatives);

        return alternatives;
    }

    private void addAllergyAlternatives(Customer customer, Map<String, List<String>> alternatives) {
        if (customer.getAllergies() == null || customer.getAllergies().isEmpty()) {
            return;
        }

        Arrays.stream(customer.getAllergies().split(","))
                .map(String::trim)
                .forEach(allergen -> {
                    List<String> alt = getSafeAlternatives(allergen);
                    if (!alt.isEmpty()) {
                        alternatives.put(allergen, alt);
                    }
                });
    }

    private void addIngredientAlternatives(List<String> ingredients, Map<String, List<String>> alternatives) {
        ingredients.stream()
                .filter(ingredient -> !availableIngredients.contains(ingredient))
                .forEach(ingredient -> {
                    List<String> alt = getAvailableAlternatives(ingredient);
                    if (!alt.isEmpty()) {
                        alternatives.put(ingredient, alt);
                    }
                });
    }

    private List<String> getSafeAlternatives(String allergen) {
        Map<String, List<String>> allergySafeAlternatives = new HashMap<>();
        allergySafeAlternatives.put("Milk", Arrays.asList("Soy Milk", "Almond Milk", "Oat Milk"));
        allergySafeAlternatives.put("Dairy", Arrays.asList("Coconut Milk", "Rice Milk"));
        allergySafeAlternatives.put("Eggs", Arrays.asList("Tofu", "Chia Seeds"));
        allergySafeAlternatives.put("Peanuts", Arrays.asList("Sunflower Butter", "Pumpkin Seeds"));
        return allergySafeAlternatives.getOrDefault(allergen, Collections.emptyList());
    }

    private List<String> getAvailableAlternatives(String ingredient) {
        Map<String, List<String>> ingredientAlternatives = new HashMap<>();
        ingredientAlternatives.put("Dragonfruit", Arrays.asList("Apple", "Pear"));
        ingredientAlternatives.put("Avocado", Arrays.asList("Zucchini", "Eggplant"));
        return ingredientAlternatives.getOrDefault(ingredient, Collections.emptyList());
    }

    public boolean updateContactInfo(String name, String email, String phone, String address) {
        Customer customer = customers.get(name);
        if (customer == null) {
            System.out.println(ERROR_CUSTOMER_NOT_FOUND);
            return false;
        }
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        return true;
    }

    public boolean setPreferredInvoiceFormat(String name, String format) {
        Customer customer = customers.get(name);
        if (customer == null) {
            System.out.println(ERROR_CUSTOMER_NOT_FOUND);
            return false;
        }
        customer.setPreferredInvoiceFormat(format);
        return true;
    }

    public void addInvoiceToCustomer(String name, Invoice invoice) {
        if (customers.containsKey(name)) {
            customerInvoices.computeIfAbsent(name, k -> new ArrayList<>()).add(invoice);
            System.out.println("**Invoice added to customer: " + name);
        } else {
            System.out.println(ERROR_CUSTOMER_NOT_FOUND);
        }
    }

    public List<Invoice> getCustomerInvoices(String name) {
        if (!customerInvoices.containsKey(name)) {
            System.out.println("**No invoices found for customer: " + name);
            return new ArrayList<>();
        }
        return customerInvoices.get(name);
    }
}