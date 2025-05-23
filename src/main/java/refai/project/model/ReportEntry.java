package refai.project.model;

public class ReportEntry {
    private String category;
    private String description;
    private double value;
    private double comparisonValue;
    private double growthPercentage;

    public ReportEntry() {
    }

    public ReportEntry(String category, String description, double value) {
        this.category = category;
        this.description = description;
        this.value = value;
    }

    public ReportEntry(String category, String description, double value, double comparisonValue) {
        this(category, description, value);
        this.comparisonValue = comparisonValue;
        calculateGrowthPercentage();
    }

    private void calculateGrowthPercentage() {
        if (comparisonValue != 0) {
            this.growthPercentage = ((value - comparisonValue) / comparisonValue) * 100;
        }
    }

    //getters and setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
        calculateGrowthPercentage();
    }

    public double getComparisonValue() {
        return comparisonValue;
    }

    public void setComparisonValue(double comparisonValue) {
        this.comparisonValue = comparisonValue;
        calculateGrowthPercentage();
    }

    public double getGrowthPercentage() {
        return growthPercentage;
    }

}