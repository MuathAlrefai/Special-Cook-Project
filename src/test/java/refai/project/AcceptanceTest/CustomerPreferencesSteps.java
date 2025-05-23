package refai.project.AcceptanceTest;

import static org.junit.Assert.assertEquals;

import refai.project.context.TestContext;
import io.cucumber.java.en.Then;

public class CustomerPreferencesSteps
{
	private final TestContext context;

	public CustomerPreferencesSteps(TestContext context)
	{
	    this.context = context;
	}


	    @Then("the system should store {string} and {string}")
	    public void the_system_should_store_and(String preferences, String allergies) 
	    {
	        assertEquals(preferences, context.customer.getDietaryPreferences());
	        assertEquals(allergies, context.customer.getAllergies());
	    }
}
