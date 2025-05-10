package refai.project.manager;

import refai.project.model.Chef;
import refai.project.model.CookingTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookingTaskManager {
    private Map<String, CookingTask> cookingTasks = new HashMap<>();
    private Map<String, Chef> chefs = new HashMap<>();

    // Register a chef in the system
    public Chef registerChef(String name) {
        Chef chef = new Chef(name);
        chefs.put(name, chef);
        return chef;
    }

    // Get a chef by name
    public Chef getChef(String name) {
        return chefs.get(name);
    }

    // Schedule a cooking task
    public CookingTask scheduleCookingTask(String chefId, String orderId, String scheduledTimeStr) {
        try {
            CookingTask task = new CookingTask(chefId, orderId, scheduledTimeStr);
            cookingTasks.put(task.getTaskId(), task);
            return task;
        } catch (ParseException e) {
            System.out.println("*** Error: Invalid date format - " + e.getMessage());
            return null;
        }
    }

    // Get a cooking task by ID
    public CookingTask getCookingTask(String taskId) {
        return cookingTasks.get(taskId);
    }

    // Get all cooking tasks for a chef
    public List<CookingTask> getTasksForChef(String chefId) {
        List<CookingTask> chefTasks = new ArrayList<>();

        for (CookingTask task : cookingTasks.values()) {
            if (chefId.equals(task.getChefId())) {
                chefTasks.add(task);
            }
        }

        return chefTasks;
    }

    // Get scheduled cooking tasks for a specific day
    public List<CookingTask> getTasksForDate(String chefId, Date date) {
        List<CookingTask> dayTasks = new ArrayList<>();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);

        int year1 = cal1.get(Calendar.YEAR);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);

        for (CookingTask task : cookingTasks.values()) {
            if (chefId.equals(task.getChefId())) {
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(task.getScheduledTime());

                int year2 = cal2.get(Calendar.YEAR);
                int day2 = cal2.get(Calendar.DAY_OF_YEAR);

                if (year1 == year2 && day1 == day2) {
                    dayTasks.add(task);
                }
            }
        }

        return dayTasks;
    }

    // Create an urgent cooking task
    public CookingTask createUrgentTask(String orderId) {
        // Find available chef
        Chef availableChef = null;
        for (Chef chef : chefs.values()) {
            if (chef.isAvailable()) {
                availableChef = chef;
                break;
            }
        }

        if (availableChef == null) {
            System.out.println("*** Error: No available chef found for urgent task.");
            return null;
        }

        // Schedule task for 30 minutes from now
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 30);

        CookingTask task = new CookingTask(availableChef.getName(), orderId, cal.getTime());
        task.setUrgent(true);
        task.setEstimatedDuration(45); // Urgent tasks typically take less time

        cookingTasks.put(task.getTaskId(), task);
        return task;
    }

    // Add preparation requirements to a task
    public void addPreparationRequirement(String taskId, String item, String details) {
        CookingTask task = cookingTasks.get(taskId);
        if (task != null) {
            task.addPreparationRequirement(item, details);
        }
    }
}