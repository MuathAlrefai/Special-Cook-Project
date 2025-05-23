package refai.project.AcceptanceTest;

import refai.project.context.TestContext;
import refai.project.model.Chef;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class TaskNotificationChefSteps {

    private final TestContext context;

    public TaskNotificationChefSteps(TestContext context) {
        this.context = context;
    }

    @Given("the kitchen manager system is active")
    public void the_kitchen_manager_system_is_active() {
        assertNotNull(context.kitchenManager);
    }

    @And("chef {string} is added with workload level {string} and expertise {string}")
    public void chef_is_added_with_workload_level_and_expertise(String name, String workload, String expertise) {
        Chef chef = new Chef(name, workload, expertise);
        context.kitchenManager.addChef(chef);
    }

    @When("chef {string} checks their assigned tasks")
    public void chef_checks_their_assigned_tasks(String name) {
        //simulate task assignment, same as Feature definition
        context.assignedChef = context.kitchenManager.assignTask(name);
    }

    @Then("the task list should include {string}")
    public void the_task_list_should_include(String taskType) {
        assertNotNull(context.assignedChef);
        assertEquals(taskType.toLowerCase(), context.assignedChef.getExpertise().toLowerCase());
        assertNotEquals("High", context.assignedChef.getWorkload());
    }

    @Then("the task list should be empty")
    public void the_task_list_should_be_empty() {
        assertNull(context.assignedChef);
    }
}
