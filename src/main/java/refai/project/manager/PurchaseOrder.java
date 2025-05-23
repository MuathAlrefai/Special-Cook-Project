package refai.project.manager;

import java.util.Objects;

public class PurchaseOrder {
    private String item;
    private int quantity;

    public PurchaseOrder(String item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrder that = (PurchaseOrder) o;
        return getQuantity() == that.getQuantity() &&
                Objects.equals(getItem(), that.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItem(), getQuantity());
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}
