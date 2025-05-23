package refai.project.AcceptanceTest;

import refai.project.context.TestContext;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class CustomMealSystemSteps 
{
	private final TestContext context;
	public CustomMealSystemSteps(TestContext context)
	{
		this.context=context;
	}
	
	@Given("the customer {string} is logged in and ready to customize a meal")
    public void the_customer_is_logged_in_and_ready_to_customize_a_meal(String name) 
    {
        context.customerManager.registerCustomer(name);
        context.customer = context.customerManager.getCustomer(name);
        assertNotNull(context.customer);
    }
	
	 @Then("the system should reject the request due to incompatibility")
	 public void the_system_should_reject_the_request_due_to_incompatibility() 
	 {
	     assertFalse(context.requestSuccessful);
	     assertTrue(context.customerManager.hasIncompatibleCombination());
	     System.out.println("Rejected due to incompatible ingredients.");
	 }
	@Then("the system should reject the request due to unavailable ingredients")
	public void the_system_should_reject_the_request_due_to_unavailable_ingredients() 
	{
		 assertFalse(context.requestSuccessful);
		 System.out.println("Rejected due to unavailable ingredient.");
	}
	 @Then("the system should reject the request due to allergy conflict")
	 public void the_system_should_reject_the_request_due_to_allergy_conflict()
	 {
	     assertFalse(context.requestSuccessful);
	     System.out.println("Rejected due to allergy conflict.");
	 }
	 
	

}
