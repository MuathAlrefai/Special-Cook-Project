package refai.project.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import refai.project.manager.CustomerManager;
import refai.project.manager.KitchenManager;
import refai.project.model.Customer;
import refai.project.model.Chef;

public class TestContext 
{
    public CustomerManager customerManager = new CustomerManager();
    public KitchenManager kitchenManager = new KitchenManager(); 
    public Customer customer;
    public boolean requestSuccessful;
    public Map<String, Object> sessionData = new HashMap<>();
    public List<String> orderList;
    public Map<String, Integer> trendAnalysis;
    public List<String> retrievedOrders;
    public String topMeal;
    public Map<String, List<String>> suggestedAlternatives;
    public List<String> selectedIngredients;
    public Chef assignedChef;

}
