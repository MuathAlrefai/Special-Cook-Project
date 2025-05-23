package refai.project.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class StockNotification extends Notification {
    private List<String> lowStockIngredients;

    public StockNotification(String recipientId, String recipientType,
                             String subject, String content, String channel) {
        super();
        this.setNotificationId("STOCK-" + UUID.randomUUID().toString());
        this.setRecipientId(recipientId);
        this.setRecipientType(recipientType);
        this.setSubject(subject);
        this.setContent(content);
        this.setScheduledTime(new Date()); 
        this.setChannel(channel);
        this.setPriority("HIGH");
        this.setStatus("PENDING");
        this.lowStockIngredients = new ArrayList<>();
    }

    public List<String> getLowStockIngredients() {
        return lowStockIngredients;
    }

    public void addLowStockIngredient(String ingredient) {
        this.lowStockIngredients.add(ingredient);
    }

    public void setLowStockIngredients(List<String> lowStockIngredients) {
        this.lowStockIngredients = lowStockIngredients;
    }
}