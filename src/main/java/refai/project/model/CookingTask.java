package refai.project.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CookingTask {
    private String taskId;
    private String orderId;
    private String chefId;
    private Date scheduledTime;
    private int estimatedDuration; // In minutes
    private String status; // "SCHEDULED", "IN_PROGRESS", "COMPLETED", "CANCELLED"
    private boolean isUrgent;
    private Map<String, String> preparationRequirements;

    public CookingTask() {
        this.taskId = "TASK-" + System.currentTimeMillis();
        this.status = "SCHEDULED";
        this.isUrgent = false;
        this.preparationRequirements = new HashMap<>();
        this.estimatedDuration = 60; // Default 60 minutes
    }

    public CookingTask(String chefId, String orderId, Date scheduledTime) {
        this();
        this.chefId = chefId;
        this.orderId = orderId;
        this.scheduledTime = scheduledTime;
    }

    public CookingTask(String chefId, String orderId, String scheduledTimeStr) throws ParseException {
        this();
        this.chefId = chefId;
        this.orderId = orderId;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.scheduledTime = formatter.parse(scheduledTimeStr);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChefId() {
        return chefId;
    }

    public void setChefId(String chefId) {
        this.chefId = chefId;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }

    public Map<String, String> getPreparationRequirements() {
        return preparationRequirements;
    }

    public void setPreparationRequirements(Map<String, String> preparationRequirements) {
        this.preparationRequirements = preparationRequirements;
    }

    public void addPreparationRequirement(String item, String details) {
        this.preparationRequirements.put(item, details);
    }
}