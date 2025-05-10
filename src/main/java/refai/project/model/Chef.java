package refai.project.model;

import java.util.ArrayList;
import java.util.List;

public class Chef {

    private final String name;
    private String workload;
    private final String expertise;
    private List<NotificationPreference> notificationPreferences;
    private boolean available;

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
        this(name, "Low", "General");
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
            case "Low":
                workload = "Medium";
                break;
            case "Medium":
                workload = "High";
                break;
        }
    }

    public boolean isAvailable() {
        return !"High".equalsIgnoreCase(workload) && available;
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

        // If preference doesn't exist, add it
        notificationPreferences.add(new NotificationPreference(name, "CHEF", channel, enabled, timingHours));
    }
}
