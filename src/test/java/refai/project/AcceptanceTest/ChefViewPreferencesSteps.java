package refai.project.AcceptanceTest;

import refai.project.context.TestContext;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
public class ChefViewPreferencesSteps 
{
	private final TestContext context;
	public ChefViewPreferencesSteps(TestContext context) 
	{
	    this.context = context;
	}


    @And("the chef wants to prepare a meal for {string}")
    public void the_chef_wants_to_prepare_a_meal_for(String name)
    {
    	assertNotNull(context.customerManager.getCustomer(name));
        assertNotNull(context.customer);
    }

    @When("the chef requests customer dietary preferences")
    public void the_chef_requests_customer_dietary_preferences()
    {
        assertNotNull(context.customer.getDietaryPreferences());
        assertNotNull(context.customer.getAllergies());
    }

    @Then("the system provides {string} and {string}")
    public void the_system_provides_and(String expectedPreferences, String expectedAllergies) 
    {
        assertEquals(expectedPreferences, context.customer.getDietaryPreferences());
        assertEquals(expectedAllergies, context.customer.getAllergies());
    }
}
