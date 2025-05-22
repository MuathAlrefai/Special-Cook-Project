package refai.project.manager;

import refai.project.model.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NotificationManager {
    private Map<String, Notification> notifications = new HashMap<>();
    private Map<String, List<NotificationPreference>> userPreferences = new HashMap<>();

    // Method to create and schedule a notification
    public Notification scheduleNotification(String recipientId, String recipientType,
                                             String subject, String content, Date scheduledTime,
                                             String channel, String priority) {
        Notification notification = new Notification();
        notification.setRecipientId(recipientId);
        notification.setRecipientType(recipientType);
        notification.setSubject(subject);
        notification.setContent(content);
        notification.setScheduledTime(scheduledTime);
        notification.setChannel(channel);
        notification.setPriority(priority);

        notifications.put(notification.getNotificationId(), notification);
        return notification;
    }

    // Method to send a notification immediately
    public Notification sendImmediateNotification(String recipientId, String recipientType,
                                                  String subject, String content,
                                                  String channel, String priority) {
        Notification notification = scheduleNotification(
                recipientId, recipientType, subject, content, new Date(), channel, priority
        );

        sendNotification(notification);
        return notification;
    }

    // Method to send the actual notification
    public boolean sendNotification(Notification notification) {
        // In a real implementation, this would use different services to send the notification
        // based on the channel (email service, SMS service, etc.)

        // For test purposes, we just update the status and sent time
        notification.setStatus("SENT");
        notification.setSentTime(new Date());

        System.out.println("📣 Notification sent to " + notification.getRecipientId() +
                " via " + notification.getChannel() + ": " + notification.getSubject());

        return true;
    }

    // Method to check for upcoming notifications that need to be sent
    public List<Notification> processScheduledNotifications() {
        List<Notification> sentNotifications = new ArrayList<>();
        Date now = new Date();

        System.out.println("\n==== PROCESSING NOTIFICATIONS ====");
        System.out.println("Current time: " + formatDate(now));
        System.out.println("Total notifications to check: " + notifications.size());

        if (notifications.isEmpty()) {
            System.out.println("No notifications found to process!");
            return sentNotifications;
        }

        // Process all notifications with detailed logging
        for (Notification notification : new ArrayList<>(notifications.values())) {
            String id = notification.getNotificationId();
            String status = notification.getStatus();
            Date scheduledTime = notification.getScheduledTime();

            System.out.println("\nChecking notification: " + id);
            System.out.println("Status: " + status + ", Scheduled time: " + formatDate(scheduledTime));

            // Skip already sent notifications
            if ("SENT".equals(status)) {
                System.out.println("Already sent, skipping.");
                continue;
            }

            // Check if it's time to send
            boolean isReadyToSend = now.compareTo(scheduledTime) >= 0;
            System.out.println("Ready to send? " + isReadyToSend);

            if (isReadyToSend) {
                System.out.println("Sending notification to " + notification.getRecipientId() + " via " + notification.getChannel());

                // Mark as sent
                notification.setStatus("SENT");
                notification.setSentTime(now);
                sentNotifications.add(notification);

                System.out.println("📣 Notification sent to " + notification.getRecipientId() +
                        " via " + notification.getChannel() + ": " +
                        notification.getSubject());
            } else {
                System.out.println("Not yet time to send.");
            }
        }

        System.out.println("\nTotal notifications sent: " + sentNotifications.size());
        return sentNotifications;
    }


    // Method to calculate when to send reminders for a delivery
    public List<Notification> scheduleDeliveryReminders(Customer customer, Delivery delivery) {
        List<Notification> scheduledReminders = new ArrayList<>();
        Date deliveryTime = delivery.getScheduledTime();

        // Get customer preferences or use defaults
        List<NotificationPreference> prefs = userPreferences.getOrDefault(
                customer.getName(), getDefaultCustomerPreferences(customer.getName()));

        for (NotificationPreference pref : prefs) {
            if (pref.isEnabled()) {
                // Calculate when to send this reminder
                Calendar reminderTime = Calendar.getInstance();
                reminderTime.setTime(deliveryTime);
                reminderTime.add(Calendar.HOUR, -pref.getTimingHours());

                // Create reminder content
                String content = String.format(
                        "Reminder: Your meal delivery is scheduled for %s. Order ID: %s",
                        formatDate(deliveryTime),
                        delivery.getOrderId()
                );

                // Schedule the notification
                Notification reminder = scheduleNotification(
                        customer.getName(),
                        "CUSTOMER",
                        "Upcoming Meal Delivery Reminder",
                        content,
                        reminderTime.getTime(),
                        pref.getChannel(),
                        "NORMAL"
                );

                // Make sure the notification is properly stored in the notifications map
                notifications.put(reminder.getNotificationId(), reminder);
                scheduledReminders.add(reminder);
            }
        }

        return scheduledReminders;
    }

    // Method to schedule cooking task notifications
    public List<Notification> scheduleCookingTaskNotifications(Chef chef, CookingTask cookingTask) {
        List<Notification> taskNotifications = new ArrayList<>();

        if (chef == null || cookingTask == null) {
            System.out.println("ERROR: Cannot schedule notifications for null chef or task");
            return taskNotifications;
        }

        // Get task details
        Date cookingTime = cookingTask.getScheduledTime();
        String orderId = cookingTask.getOrderId();

        // Create notification content
        String content = String.format(
                "You have a cooking task scheduled for %s. Order ID: %s",
                formatDate(cookingTime),
                orderId
        );

        // Create immediate notification for testing purposes
        Calendar immediate = Calendar.getInstance();
        immediate.add(Calendar.MINUTE, -1);

        Notification immediateNotification = new Notification();
        immediateNotification.setNotificationId("NOTIF-IMM-" + System.currentTimeMillis());
        immediateNotification.setRecipientId(chef.getName());
        immediateNotification.setRecipientType("CHEF");
        immediateNotification.setSubject("Cooking Task Notification");
        immediateNotification.setContent(content);
        immediateNotification.setScheduledTime(immediate.getTime());
        immediateNotification.setChannel("EMAIL");
        immediateNotification.setPriority(cookingTask.isUrgent() ? "URGENT" : "NORMAL");
        immediateNotification.setStatus("PENDING");

        // Store in our map of notifications
        notifications.put(immediateNotification.getNotificationId(), immediateNotification);
        taskNotifications.add(immediateNotification);

        // Create 12-hour notification for regular tasks
        Calendar twelveHourBefore = Calendar.getInstance();
        twelveHourBefore.setTime(cookingTime);
        twelveHourBefore.add(Calendar.HOUR, -12);

        Notification twelveHourNotification = new Notification();
        twelveHourNotification.setNotificationId("NOTIF-12H-" + System.currentTimeMillis());
        twelveHourNotification.setRecipientId(chef.getName());
        twelveHourNotification.setRecipientType("CHEF");
        twelveHourNotification.setSubject("Upcoming Cooking Task Reminder");
        twelveHourNotification.setContent("Reminder: You have a cooking task in 12 hours. Order ID: " + orderId);
        twelveHourNotification.setScheduledTime(twelveHourBefore.getTime());
        twelveHourNotification.setChannel("SMS");
        twelveHourNotification.setStatus("PENDING");

        // Store in our map of notifications
        notifications.put(twelveHourNotification.getNotificationId(), twelveHourNotification);
        taskNotifications.add(twelveHourNotification);

        System.out.println("Created " + taskNotifications.size() + " notifications for chef " + chef.getName());
        return taskNotifications;
    }

    // Method to send daily schedule to chef
    public Notification sendDailySchedule(Chef chef, List<CookingTask> tasks, Date scheduleDate) {
        if (tasks == null || tasks.isEmpty()) {
            return null;
        }

        // Sort tasks by scheduled time
        tasks.sort(Comparator.comparing(CookingTask::getScheduledTime));

        // Create the schedule content
        StringBuilder content = new StringBuilder();
        content.append("Your cooking schedule for ").append(formatDateOnly(scheduleDate)).append(":\n\n");

        for (CookingTask task : tasks) {
            content.append("• ").append(formatTimeOnly(task.getScheduledTime()))
                    .append(" - Order ID: ").append(task.getOrderId())
                    .append(" (").append(task.getEstimatedDuration()).append(" min)");

            if (task.isUrgent()) {
                content.append(" [URGENT]");
            }

            content.append("\n");

            // Add preparation requirements if any
            if (!task.getPreparationRequirements().isEmpty()) {
                content.append("  Preparation: ");
                int count = 0;
                for (Map.Entry<String, String> req : task.getPreparationRequirements().entrySet()) {
                    if (count > 0) {
                        content.append(", ");
                    }
                    content.append(req.getKey());
                    count++;
                }
                content.append("\n");
            }
        }

        // Send the notification
        return sendImmediateNotification(
                chef.getName(),
                "CHEF",
                "Daily Cooking Schedule for " + formatDateOnly(scheduleDate),
                content.toString(),
                "EMAIL", // Daily schedules are typically sent by email
                "NORMAL"
        );
    }

    // Method to save user preferences
    // Method to save user preferences - Fix potential issues
    public void saveNotificationPreferences(String userId, String userType, List<NotificationPreference> preferences) {
        System.out.println("Saving preferences for user: " + userId);

        // Create a new list to avoid reference issues
        List<NotificationPreference> userPrefs = new ArrayList<>();

        for (NotificationPreference pref : preferences) {
            NotificationPreference newPref = new NotificationPreference(
                    userId, userType, pref.getChannel(), pref.isEnabled(), pref.getTimingHours()
            );
            userPrefs.add(newPref);
            System.out.println("- Preference: " + pref.getChannel() + ", Enabled: " + pref.isEnabled() +
                    ", Hours: " + pref.getTimingHours());
        }

        userPreferences.put(userId, userPrefs);
        System.out.println("Preferences saved. Total users with preferences: " + userPreferences.size());
    }

    //method to update a single notification preference
    public void updateNotificationPreference(String userId, String userType,
                                             String channel, boolean enabled, int timingHours) {
        System.out.println("Updating preference for user: " + userId + ", Channel: " + channel);

        List<NotificationPreference> prefs = userPreferences.computeIfAbsent(userId, k -> new ArrayList<>());

        //if no preferences exist for this user yet, create a new list
        if (prefs == null) {
            prefs = new ArrayList<>();
            userPreferences.put(userId, prefs);
        }

        // Look for existing preference
        boolean found = false;
        for (NotificationPreference pref : prefs) {
            if (pref.getChannel().equals(channel)) {
                pref.setEnabled(enabled);
                pref.setTimingHours(timingHours);
                found = true;
                System.out.println("- Updated existing preference");
                break;
            }
        }

        // If no existing preference found, add new one
        if (!found) {
            prefs.add(new NotificationPreference(userId, userType, channel, enabled, timingHours));
            System.out.println("- Added new preference");
        }

        System.out.println("Preference updated. Total preferences for user: " + prefs.size());
    }

    // Helper method for default customer preferences
    private List<NotificationPreference> getDefaultCustomerPreferences(String customerId) {
        List<NotificationPreference> defaults = new ArrayList<>();
        // Make sure we have a notification preference for exactly 24 hours
        defaults.add(new NotificationPreference(customerId, "CUSTOMER", "EMAIL", true, 24));
        defaults.add(new NotificationPreference(customerId, "CUSTOMER", "SMS", true, 12));
        defaults.add(new NotificationPreference(customerId, "CUSTOMER", "APP", true, 2));
        return defaults;
    }

    // Helper method for default chef preferences
    private List<NotificationPreference> getDefaultChefPreferences(String chefId) {
        List<NotificationPreference> defaults = new ArrayList<>();
        defaults.add(new NotificationPreference(chefId, "CHEF", "EMAIL", true, 12));
        defaults.add(new NotificationPreference(chefId, "CHEF", "SMS", true, 4));
        defaults.add(new NotificationPreference(chefId, "CHEF", "APP", true, 24));
        return defaults;
    }

    // Helper method to get hours between dates
    private long getHoursBetween(Date start, Date end) {
        long diffInMillies = Math.abs(end.getTime() - start.getTime());
        return TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    // Helper method to format dates
    private String formatDate(Date date) {
        if (date == null) return "null";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    private String formatDateOnly(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    private String formatTimeOnly(Date date) {
        return new SimpleDateFormat("HH:mm").format(date);
    }

    // Get all notifications for a user
    public List<Notification> getNotificationsForUser(String userId) {
        List<Notification> userNotifications = new ArrayList<>();
        for (Notification notification : notifications.values()) {
            if (userId.equals(notification.getRecipientId())) {
                userNotifications.add(notification);
            }
        }
        return userNotifications;
    }
    public void debugNotificationsState() {
        System.out.println("Current notifications in manager: " + notifications.size());
        for (Notification notification : notifications.values()) {
            System.out.println("ID: " + notification.getNotificationId() +
                    ", Status: " + notification.getStatus() +
                    ", Scheduled: " + notification.getScheduledTime());
        }
    }
    public void addTimedNotification(Notification notification) {
        if (notification == null) {
            System.out.println("Error: Cannot add null notification");
            return;
        }

        // Schedule the notification using existing methods
        Notification added = scheduleNotification(
                notification.getRecipientId(),
                notification.getRecipientType(),
                notification.getSubject(),
                notification.getContent(),
                notification.getScheduledTime(),
                notification.getChannel(),
                notification.getPriority()
        );

      
        if (added != null) {
            added.setStatus(notification.getStatus());

       
            added.setRequiresConfirmation(notification.isRequiresConfirmation());

          
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("Successfully added timed notification for " +
                    notification.getRecipientId() + " at " +
                    formatter.format(notification.getScheduledTime()));
        }
    }
   
    public void sendToManager(KitchenManager manager, Notification notification)
    {
        manager.addNotification(notification);

        notifications.put(notification.getNotificationId(), notification);

        notification.setStatus("SENT");
        notification.setSentTime(new Date());

        System.out.println("Notification sent to " + manager.getName() +
                " via " + notification.getChannel() + ": " +
                notification.getSubject());
    }


}