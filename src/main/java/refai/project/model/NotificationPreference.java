package refai.project.model;

public class NotificationPreference {
    private String userId;
    private String userType; 
    private String channel;
    private boolean enabled;
    private int timingHours; 

    public NotificationPreference(String userId, String userType, String channel, boolean enabled, int timingHours) {
        this.userId = userId;
        this.userType = userType;
        this.channel = channel;
        this.enabled = enabled;
        this.timingHours = timingHours;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getTimingHours() {
        return timingHours;
    }

    public void setTimingHours(int timingHours) {
        this.timingHours = timingHours;
    }
}