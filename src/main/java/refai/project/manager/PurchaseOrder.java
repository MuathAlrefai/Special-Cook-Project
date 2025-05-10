package refai.project.manager;

public class PurchaseOrder {
    private String item;
    private int quantity;

    public PurchaseOrder(String item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
