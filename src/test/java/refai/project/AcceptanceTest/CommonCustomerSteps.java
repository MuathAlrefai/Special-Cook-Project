package refai.project.AcceptanceTest;

import refai.project.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertNotNull;

public class CommonCustomerSteps 
{

	private final TestContext context;

	public CommonCustomerSteps(TestContext context)
	{
	    this.context = context;
	}

    @Given("the customer {string} is registered")
    public void the_customer_is_registered(String name) 
    {
        context.customerManager.registerCustomer(name);
        
        context.customer = context.customerManager.getCustomer(name);
        assertNotNull(context.customer);
    }
    
    @When("the customer updates preferences to {string} and allergies to {string}")
    public void the_customer_updates_preferences_to_and_allergies_to(String preferences, String allergies)
    {
        assertNotNull(context.customer);
        context.customerManager.updatePreferences(context.customer.getName(), preferences, allergies);
    }

}
