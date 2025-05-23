package refai.project.AcceptanceTest;

import refai.project.context.TestContext;
import refai.project.model.Chef;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class TaskAssignmentKitchenManagerSteps {

    private final TestContext context;

    public TaskAssignmentKitchenManagerSteps(TestContext context) {
        this.context = context;
    }

    @Given("the kitchen manager has access to chef data")
    public void the_kitchen_manager_has_access_to_chef_data() {
        assertNotNull(context.kitchenManager);
    }

    @Given("chef {string} has workload level {string} and expertise {string}")
    public void chef_has_workload_and_expertise(String name, String workload, String expertise) {
        Chef chef = new Chef(name, workload, expertise);
        context.kitchenManager.addChef(chef);
    }

    @When("the kitchen manager assigns a {string} task")
    public void the_kitchen_manager_assigns_a_task(String taskType) {
        context.assignedChef = context.kitchenManager.assignTask(taskType);
    }

    @Then("the system should assign the task to chef {string}")
    public void the_system_should_assign_task_to(String expectedName) {
        assertNotNull(context.assignedChef);
        assertEquals(expectedName, context.assignedChef.getName(), "Assigned chef name mismatch");
    }

    @Then("not assign it to chef {string}")
    public void not_assign_task_to(String name) {
        assertNotNull(context.assignedChef);
        assertNotEquals(name, context.assignedChef.getName(), "Task should not be assigned to chef with high workload");
    }
}
