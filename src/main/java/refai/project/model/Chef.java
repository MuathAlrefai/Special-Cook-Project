package refai.project.model;

import java.util.ArrayList;
import java.util.List;

public class Chef {

    private final String name;
    private String workload;
    private final String expertise;
    private List<NotificationPreference> notificationPreferences;
    private boolean available;

    //constants for workload levels
    private static final String WORKLOAD_LOW = "Low";
    private static final String WORKLOAD_MEDIUM = "Medium";
    private static final String WORKLOAD_HIGH = "High";

    public Chef(String name, String workload, String expertise) {
        this.name = name;
        this.workload = workload;
        this.expertise = expertise;
        this.available = true;
        this.notificationPreferences = new ArrayList<>();

        this.notificationPreferences.add(new NotificationPreference(name, "CHEF", "EMAIL", true, 12));
        this.notificationPreferences.add(new NotificationPreference(name, "CHEF", "SMS", true, 4));
        this.notificationPreferences.add(new NotificationPreference(name, "CHEF", "APP", true, 24));
    }

    public Chef(String name) {
        this(name, WORKLOAD_LOW, "General");
    }

    public String getName() {
        return name;
    }

    public String getWorkload() {
        return workload;
    }

    public String getExpertise() {
        return expertise;
    }

    public void increaseWorkload() {
        switch (workload) {
            case WORKLOAD_LOW:
                workload = WORKLOAD_MEDIUM;
                break;
            case WORKLOAD_MEDIUM:
                workload = WORKLOAD_HIGH;
                break;
            case WORKLOAD_HIGH:
                //already at high workload, no change needed
                break;
            default:
                //if workload is not recognized, set to medium as a default
                workload = WORKLOAD_MEDIUM;
                break;
        }
    }

    public boolean isAvailable() {
        return !WORKLOAD_HIGH.equalsIgnoreCase(workload) && available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<NotificationPreference> getNotificationPreferences() {
        return notificationPreferences;
    }

    public void setNotificationPreferences(List<NotificationPreference> notificationPreferences) {
        this.notificationPreferences = notificationPreferences;
    }

    public void updateNotificationPreference(String channel, boolean enabled, int timingHours) {
        for (NotificationPreference pref : notificationPreferences) {
            if (pref.getChannel().equals(channel)) {
                pref.setEnabled(enabled);
                pref.setTimingHours(timingHours);
                return;
            }
        }

        //if preference doesn't exist, add it
        notificationPreferences.add(new NotificationPreference(name, "CHEF", channel, enabled, timingHours));
    }
}