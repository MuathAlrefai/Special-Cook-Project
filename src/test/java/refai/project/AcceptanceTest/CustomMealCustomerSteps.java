package refai.project.AcceptanceTest;

import refai.project.context.TestContext;
import io.cucumber.java.en.*;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CustomMealCustomerSteps 
{
    private final TestContext context;

    public CustomMealCustomerSteps(TestContext context)
    {
        this.context = context;
    }



    @When("the customer selects the ingredients {string} and {string}")
    public void the_customer_selects_the_ingredients_and(String ingredient1, String ingredient2)
    {
        List<String> ingredients = Arrays.asList(ingredient1, ingredient2);
        context.requestSuccessful = context.customerManager.requestCustomMeal(context.customer.getName(), ingredients);

        System.out.println("Ingredients received: " + ingredients);

        if (!context.requestSuccessful)
        {
            if (context.customerManager.hasAllergyConflict())
            {
                System.out.println("Ingredient conflicts with allergy: " + ingredient2);
            }
            else if (context.customerManager.hasIncompatibleCombination())
            {
                System.out.println("Rejected due to incompatible ingredients.");
            }
            else if (context.customerManager.isIngredientUnavailable())
            {
                System.out.println("Ingredient not available: " + ingredient1 + " or " + ingredient2);
            }
        }
        else
        {
            System.out.println("Custom meal saved for " + context.customer.getName());
        }
    }

    @When("the customer requests a meal with {string} and {string}")
    public void the_customer_requests_a_meal_with_and(String ingredient1, String ingredient2)
    {
        List<String> ingredients = Arrays.asList(ingredient1, ingredient2);
        context.requestSuccessful = context.customerManager.requestCustomMeal(context.customer.getName(), ingredients);

        if (context.requestSuccessful) {
            System.out.println("Custom meal stored: " + ingredients);
        }
    }

    @Then("the system should store the custom meal for {string}")
    public void the_system_should_store_the_custom_meal_for(String name)
    {
        List<String> customMeal = context.customerManager.getCustomMeal(name);
        assertNotNull(customMeal);
        assertFalse(customMeal.isEmpty());
        System.out.println("Custom meal stored: " + customMeal);
    }

    @When("the customer tries to select no ingredients")
    public void the_customer_tries_to_select_no_ingredients() 
    {
        context.requestSuccessful = context.customerManager.requestCustomMeal(context.customer.getName(), List.of());
    }

    @Then("the system should reject the request due to empty selection")
    public void the_system_should_reject_the_request_due_to_empty_selection() 
    {
        assertFalse(context.requestSuccessful);
        assertTrue(context.customerManager.isEmptyIngredientsProvided());
    }

    @When("the customer selects an incompatible combination of Milk and Fish")
    public void the_customer_selects_an_incompatible_combination_of_milk_and_fish()
    {
        List<String> ingredients = Arrays.asList("Milk", "Fish");
        context.requestSuccessful = context.customerManager.requestCustomMeal(context.customer.getName(), ingredients);
    }

    @Then("the system should reject the meal due to allergy conflict")
    public void the_system_should_reject_the_meal_due_to_allergy_conflict()
    {
        assertFalse(context.requestSuccessful);
        assertTrue(context.customerManager.hasAllergyConflict());
    }
}
