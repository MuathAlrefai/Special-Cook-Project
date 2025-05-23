package refai.project.manager;

import refai.project.model.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NotificationManager {
    //notification message constants
    private static final String NOTIFICATION_SENT_PREFIX = "Notification sent to ";
    private static final String NOTIFICATION_VIA = " via ";
    private static final String NOTIFICATION_SUBJECT_SEPARATOR = ": ";

    //recipient type constants
    private static final String RECIPIENT_TYPE_CUSTOMER = "CUSTOMER";
    private static final String RECIPIENT_TYPE_CHEF = "CHEF";

    //priority constants
    private static final String PRIORITY_NORMAL = "NORMAL";
    private static final String PRIORITY_URGENT = "URGENT";

    //channel constants
    private static final String CHANNEL_EMAIL = "EMAIL";
    private static final String CHANNEL_SMS = "SMS";
    private static final String CHANNEL_APP = "APP";

    private Map<String, Notification> notifications = new HashMap<>();
    private Map<String, List<NotificationPreference>> userPreferences = new HashMap<>();

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

    public Notification sendImmediateNotification(String recipientId, String recipientType,
                                                  String subject, String content,
                                                  String channel, String priority) {
        Notification notification = scheduleNotification(
                recipientId, recipientType, subject, content, new Date(), channel, priority
        );

        sendNotification(notification);
        return notification;
    }

    public boolean sendNotification(Notification notification) {
        notification.setStatus("SENT");
        notification.setSentTime(new Date());

        System.out.println(NOTIFICATION_SENT_PREFIX + notification.getRecipientId() +
                NOTIFICATION_VIA + notification.getChannel() +
                NOTIFICATION_SUBJECT_SEPARATOR + notification.getSubject());

        return true;
    }

    public List<Notification> processScheduledNotifications() {
        List<Notification> sentNotifications = new ArrayList<>();
        Date now = new Date();

        System.out.println("\n*-*-*-* PROCESSING NOTIFICATIONS *-*-*-*");
        System.out.println("Current time: " + formatDate(now));
        System.out.println("Total notifications to check: " + notifications.size());

        if (notifications.isEmpty()) {
            System.out.println("No notifications found to process!");
            return sentNotifications;
        }

        for (Notification notification : new ArrayList<>(notifications.values())) {
            String id = notification.getNotificationId();
            String status = notification.getStatus();
            Date scheduledTime = notification.getScheduledTime();

            System.out.println("\nChecking notification: " + id);
            System.out.println("Status: " + status + ", Scheduled time: " + formatDate(scheduledTime));

            if ("SENT".equals(status)) {
                System.out.println("Already sent, skipping.");
                continue;
            }

            boolean isReadyToSend = now.compareTo(scheduledTime) >= 0;
            System.out.println("Ready to send? " + isReadyToSend);

            if (isReadyToSend) {
                System.out.println("Sending notification to " + notification.getRecipientId() +
                        NOTIFICATION_VIA + notification.getChannel());

                notification.setStatus("SENT");
                notification.setSentTime(now);
                sentNotifications.add(notification);

                System.out.println(NOTIFICATION_SENT_PREFIX + notification.getRecipientId() +
                        NOTIFICATION_VIA + notification.getChannel() +
                        NOTIFICATION_SUBJECT_SEPARATOR + notification.getSubject());
            } else {
                System.out.println("Not yet time to send.");
            }
        }

        System.out.println("\nTotal notifications sent: " + sentNotifications.size());
        return sentNotifications;
    }

    public List<Notification> scheduleDeliveryReminders(Customer customer, Delivery delivery) {
        List<Notification> scheduledReminders = new ArrayList<>();
        Date deliveryTime = delivery.getScheduledTime();

        List<NotificationPreference> prefs = userPreferences.getOrDefault(
                customer.getName(), getDefaultCustomerPreferences(customer.getName()));

        for (NotificationPreference pref : prefs) {
            if (pref.isEnabled()) {
                Calendar reminderTime = Calendar.getInstance();
                reminderTime.setTime(deliveryTime);
                reminderTime.add(Calendar.HOUR, -pref.getTimingHours());

                String content = String.format(
                        "Reminder: Your meal delivery is scheduled for %s. Order ID: %s",
                        formatDate(deliveryTime),
                        delivery.getOrderId()
                );

                Notification reminder = scheduleNotification(
                        customer.getName(),
                        RECIPIENT_TYPE_CUSTOMER,
                        "Upcoming Meal Delivery Reminder",
                        content,
                        reminderTime.getTime(),
                        pref.getChannel(),
                        PRIORITY_NORMAL
                );

                notifications.put(reminder.getNotificationId(), reminder);
                scheduledReminders.add(reminder);
            }
        }

        return scheduledReminders;
    }

    public List<Notification> scheduleCookingTaskNotifications(Chef chef, CookingTask cookingTask) {
        List<Notification> taskNotifications = new ArrayList<>();

        if (chef == null || cookingTask == null) {
            System.out.println("ERROR: Cannot schedule notifications for null chef or task");
            return taskNotifications;
        }

        Date cookingTime = cookingTask.getScheduledTime();
        String orderId = cookingTask.getOrderId();

        String content = String.format(
                "You have a cooking task scheduled for %s. Order ID: %s",
                formatDate(cookingTime),
                orderId
        );

        Calendar immediate = Calendar.getInstance();
        immediate.add(Calendar.MINUTE, -1);

        Notification immediateNotification = new Notification();
        immediateNotification.setNotificationId("NOTIF-IMM-" + System.currentTimeMillis());
        immediateNotification.setRecipientId(chef.getName());
        immediateNotification.setRecipientType(RECIPIENT_TYPE_CHEF);
        immediateNotification.setSubject("Cooking Task Notification");
        immediateNotification.setContent(content);
        immediateNotification.setScheduledTime(immediate.getTime());
        immediateNotification.setChannel(CHANNEL_EMAIL);
        immediateNotification.setPriority(cookingTask.isUrgent() ? PRIORITY_URGENT : PRIORITY_NORMAL);
        immediateNotification.setStatus("PENDING");

        notifications.put(immediateNotification.getNotificationId(), immediateNotification);
        taskNotifications.add(immediateNotification);

        Calendar twelveHourBefore = Calendar.getInstance();
        twelveHourBefore.setTime(cookingTime);
        twelveHourBefore.add(Calendar.HOUR, -12);

        Notification twelveHourNotification = new Notification();
        twelveHourNotification.setNotificationId("NOTIF-12H-" + System.currentTimeMillis());
        twelveHourNotification.setRecipientId(chef.getName());
        twelveHourNotification.setRecipientType(RECIPIENT_TYPE_CHEF);
        twelveHourNotification.setSubject("Upcoming Cooking Task Reminder");
        twelveHourNotification.setContent("Reminder: You have a cooking task in 12 hours. Order ID: " + orderId);
        twelveHourNotification.setScheduledTime(twelveHourBefore.getTime());
        twelveHourNotification.setChannel(CHANNEL_SMS);
        twelveHourNotification.setStatus("PENDING");

        notifications.put(twelveHourNotification.getNotificationId(), twelveHourNotification);
        taskNotifications.add(twelveHourNotification);

        System.out.println("Created " + taskNotifications.size() + " notifications for chef " + chef.getName());
        return taskNotifications;
    }

    public Notification sendDailySchedule(Chef chef, List<CookingTask> tasks, Date scheduleDate) {
        if (tasks == null || tasks.isEmpty()) {
            return null;
        }

        tasks.sort(Comparator.comparing(CookingTask::getScheduledTime));

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

        return sendImmediateNotification(
                chef.getName(),
                RECIPIENT_TYPE_CHEF,
                "Daily Cooking Schedule for " + formatDateOnly(scheduleDate),
                content.toString(),
                CHANNEL_EMAIL,
                PRIORITY_NORMAL
        );
    }

    public void saveNotificationPreferences(String userId, String userType, List<NotificationPreference> preferences) {
        System.out.println("Saving preferences for user: " + userId);

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

    public void updateNotificationPreference(String userId, String userType,
                                             String channel, boolean enabled, int timingHours) {
        System.out.println("Updating preference for user: " + userId + ", Channel: " + channel);

        List<NotificationPreference> prefs = userPreferences.get(userId);

        if (prefs == null) {
            prefs = new ArrayList<>();
            userPreferences.put(userId, prefs);
        }

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

        if (!found) {
            prefs.add(new NotificationPreference(userId, userType, channel, enabled, timingHours));
            System.out.println("- Added new preference");
        }

        System.out.println("Preference updated. Total preferences for user: " + prefs.size());
    }

    private List<NotificationPreference> getDefaultCustomerPreferences(String customerId) {
        List<NotificationPreference> defaults = new ArrayList<>();
        defaults.add(new NotificationPreference(customerId, RECIPIENT_TYPE_CUSTOMER, CHANNEL_EMAIL, true, 24));
        defaults.add(new NotificationPreference(customerId, RECIPIENT_TYPE_CUSTOMER, CHANNEL_SMS, true, 12));
        defaults.add(new NotificationPreference(customerId, RECIPIENT_TYPE_CUSTOMER, CHANNEL_APP, true, 2));
        return defaults;
    }

    private List<NotificationPreference> getDefaultChefPreferences(String chefId) {
        List<NotificationPreference> defaults = new ArrayList<>();
        defaults.add(new NotificationPreference(chefId, RECIPIENT_TYPE_CHEF, CHANNEL_EMAIL, true, 12));
        defaults.add(new NotificationPreference(chefId, RECIPIENT_TYPE_CHEF, CHANNEL_SMS, true, 4));
        defaults.add(new NotificationPreference(chefId, RECIPIENT_TYPE_CHEF, CHANNEL_APP, true, 24));
        return defaults;
    }

    private long getHoursBetween(Date start, Date end) {
        long diffInMillies = Math.abs(end.getTime() - start.getTime());
        return TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

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

    public void sendToManager(KitchenManager manager, Notification notification) {
        manager.addNotification(notification);

        notifications.put(notification.getNotificationId(), notification);

        notification.setStatus("SENT");
        notification.setSentTime(new Date());

        System.out.println(NOTIFICATION_SENT_PREFIX + manager.getName() +
                NOTIFICATION_VIA + notification.getChannel() +
                NOTIFICATION_SUBJECT_SEPARATOR + notification.getSubject());
    }
}