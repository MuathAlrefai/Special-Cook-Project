package refai.project.model;

public class Ingredient {
    private String name;
    private double currentStock;
    private double minimumThreshold;
    private String unit;

    public Ingredient(String name, double currentStock, double minimumThreshold, String unit) {
        this.name = name;
        this.currentStock = currentStock;
        this.minimumThreshold = minimumThreshold;
        this.unit = unit;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(double currentStock) {
        this.currentStock = currentStock;
    }

    public double getMinimumThreshold() {
        return minimumThreshold;
    }

    public void setMinimumThreshold(double minimumThreshold) {
        this.minimumThreshold = minimumThreshold;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isBelowThreshold() {
        return currentStock < minimumThreshold;
    }

    @Override
    public String toString() {
        return name + ": " + currentStock + " " + unit + " (min: " + minimumThreshold + " " + unit + ")";
    }
}