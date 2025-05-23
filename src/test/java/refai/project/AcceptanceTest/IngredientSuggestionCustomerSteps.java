package refai.project.AcceptanceTest;

import refai.project.context.TestContext;
import io.cucumber.java.en.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientSuggestionCustomerSteps 
{

    private final TestContext context;

    public IngredientSuggestionCustomerSteps(TestContext context)
    {
        this.context = context;
    }

    @And("the customer is logged in and ready to customize a meal")
    public void the_customer_is_logged_in_and_ready_to_customize_a_meal() 
    {
        assertNotNull(context.customer);
    }

    @When("the customer selects the ingredients {string} and {string} for suggestion")
    public void the_customer_selects_ingredients_for_suggestion(String ingredient1, String ingredient2) 
    {
        List<String> ingredients = Arrays.asList(ingredient1, ingredient2);
        context.selectedIngredients = ingredients;
        assertNotNull(context.customer, "Customer should be initialized before suggesting alternatives");
        context.suggestedAlternatives =context.customerManager.suggestAlternatives(context.customer.getName(), ingredients);
    }


    
}
