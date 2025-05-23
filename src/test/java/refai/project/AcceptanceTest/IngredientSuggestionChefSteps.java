package refai.project.AcceptanceTest;

import refai.project.context.TestContext;
import io.cucumber.java.en.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientSuggestionChefSteps {

    private final TestContext context;

    public IngredientSuggestionChefSteps(TestContext context)
    {
        this.context = context;
    }

    @Given("the chef is preparing a meal for {string}")
    public void the_chef_is_preparing_a_meal_for(String customerName) 
    {
        context.customer = context.customerManager.getCustomer(customerName);
        assertNotNull(context.customer, "Chef must have a valid customer to prepare a meal for.");
    }

    @When("the chef selects the ingredients {string} and {string} for {string}")
    public void the_chef_selects_the_ingredients_and_for(String ingredient1, String ingredient2, String customerName)
    {
        List<String> ingredients = Arrays.asList(ingredient1, ingredient2);
        context.selectedIngredients = ingredients;
        context.suggestedAlternatives = context.customerManager.suggestAlternatives(customerName, ingredients);
    }

    @Then("the system should suggest alternatives for {string}")
    public void the_system_should_suggest_alternatives_for(String ingredient) 
    {
        assertNotNull(context.suggestedAlternatives, "Expected alternatives to be initialized.");
        assertTrue(context.suggestedAlternatives.containsKey(ingredient), "Expected alternatives for: " + ingredient);
        assertFalse(context.suggestedAlternatives.get(ingredient).isEmpty(), "Expected non-empty suggestions for: " + ingredient);
    }

    @Then("the system should suggest allergy-safe alternatives for {string}")
    public void the_system_should_suggest_allergy_safe_alternatives_for(String ingredient) 
    {
        assertNotNull(context.suggestedAlternatives, "Expected suggestions not to be null.");
        assertTrue(context.suggestedAlternatives.containsKey(ingredient), "Expected allergy-safe alternatives for: " + ingredient);
    }

    @Then("the system should not suggest any alternatives")
    public void the_system_should_not_suggest_any_alternatives()
    {
        if (context.suggestedAlternatives == null)
        {
            context.suggestedAlternatives = new HashMap<>();
        }
        assertTrue(context.suggestedAlternatives.isEmpty(), "Expected no alternatives.");
    }
}
