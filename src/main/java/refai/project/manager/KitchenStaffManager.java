package refai.project.manager;

import refai.project.model.Chef;
import refai.project.model.CookingTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class KitchenStaffManager {
    private Map<String, Chef> chefs;
    private Map<String, CookingTask> tasks;
    private Map<String, Map<String, String>> preparationRequirements;
    private SimpleDateFormat dateFormat;

    public KitchenStaffManager() {
        this.chefs = new HashMap<>();
        this.tasks = new HashMap<>();
        this.preparationRequirements = new HashMap<>();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public Chef registerChef(String name) {
        //create a chef with default workload and expertise
        Chef chef = new Chef(name);
        chefs.put(name, chef);
        System.out.println("Chef " + name + " registered successfully");
        return chef;
    }

    public Chef registerChef(String name, String expertise, String workload) {
        Chef chef = new Chef(name, workload, expertise);
        chefs.put(name, chef);
        System.out.println("Chef " + name + " registered with expertise: " + expertise);
        return chef;
    }

    public CookingTask scheduleCookingTask(String chefName, String orderId, String dateTimeString)
            throws ParseException {
        Chef chef = chefs.get(chefName);
        if (chef == null) {
            System.out.println("Chef not found: " + chefName);
            return null;
        }

        Date cookingTime = dateFormat.parse(dateTimeString);
        CookingTask task = new CookingTask(chefName, orderId, cookingTime);
        String taskId = task.getTaskId();

        tasks.put(taskId, task);
        chef.increaseWorkload();

        System.out.println("Task scheduled for " + chefName + ": " + orderId);
        return task;
    }

    public void addPreparationRequirement(String taskId, String requirement, String details) {
        CookingTask task = tasks.get(taskId);
        if (task == null) {
            System.out.println("Task not found: " + taskId);
            return;
        }

        if (!preparationRequirements.containsKey(taskId)) {
            preparationRequirements.put(taskId, new HashMap<>());
        }

        preparationRequirements.get(taskId).put(requirement, details);
        System.out.println("Added preparation requirement to task " + taskId + ": " + requirement);
    }

    public CookingTask createUrgentTask(String orderId) {
        //find chef with the lowest workload
        Chef selectedChef = null;
        for (Chef chef : chefs.values()) {
            if (chef.isAvailable() && (selectedChef == null ||
                    chef.getWorkload().equals("Low") && !selectedChef.getWorkload().equals("Low"))) {
                selectedChef = chef;
            }
        }

        if (selectedChef == null) {
            System.out.println("No available chef for urgent task");
            return null;
        }

        CookingTask urgentTask = new CookingTask(selectedChef.getName(), orderId, new Date());
        urgentTask.setUrgent(true);
        String taskId = urgentTask.getTaskId();

        tasks.put(taskId, urgentTask);
        selectedChef.increaseWorkload();

        System.out.println("Urgent task " + orderId + " assigned to " + selectedChef.getName());
        return urgentTask;
    }

    public List<CookingTask> getTasksForDate(String chefName, Date date) {
        List<CookingTask> chefTasks = new ArrayList<>();
        Calendar requestedDate = Calendar.getInstance();
        requestedDate.setTime(date);

        for (CookingTask task : tasks.values()) {
            if (task.getChefId().equals(chefName)) {
                Calendar taskDate = Calendar.getInstance();
                taskDate.setTime(task.getCookingTime());

                if (taskDate.get(Calendar.YEAR) == requestedDate.get(Calendar.YEAR) &&
                        taskDate.get(Calendar.MONTH) == requestedDate.get(Calendar.MONTH) &&
                        taskDate.get(Calendar.DAY_OF_MONTH) == requestedDate.get(Calendar.DAY_OF_MONTH)) {
                    chefTasks.add(task);
                }
            }
        }

        return chefTasks;
    }

    public Chef getChef(String chefName) {
        return chefs.get(chefName);
    }

    public Map<String, String> getPreparationRequirements(String taskId) {
        return preparationRequirements.getOrDefault(taskId, new HashMap<>());
    }
}