package refai.project.model;

import java.util.ArrayList;
import java.util.List;

public class    KitchenManager {
    private String name;
    private String email;
    private List<Notification> notifications;

    public KitchenManager(String name) {
        this.name = name;
        this.email = name.toLowerCase().replace(" ", ".") + "@restaurant.com";
        this.notifications = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    public void clearNotifications() {
        this.notifications.clear();
    }
}