package refai.project.manager;

import refai.project.model.FinancialReport;
import refai.project.model.ReportEntry;

import java.util.Calendar;
import java.util.Date;

public class ReportingManager {
    //constants for category names
    private static final String CATEGORY_SPECIAL_DISHES = "Special Dishes";
    private static final String CATEGORY_BEVERAGES = "Beverages";
    private static final String CATEGORY_DESSERTS = "Desserts";
    private static final String CATEGORY_PREMIUM = "Premium";
    private static final String CATEGORY_STANDARD = "Standard";
    private static final String CATEGORY_OCCASIONAL = "Occasional";
    private static final String CATEGORY_MARKETING = "Marketing";

    //constants for entry names
    private static final String ENTRY_GOURMET_MEALS = "Gourmet Meals";
    private static final String ENTRY_FAMILY_SPECIALS = "Family Specials";
    private static final String ENTRY_SPECIALTY_DRINKS = "Specialty Drinks";
    private static final String ENTRY_PREMIUM_DESSERTS = "Premium Desserts";
    private static final String ENTRY_HIGH_VALUE = "High-value customers";
    private static final String ENTRY_REGULAR = "Regular customers";
    private static final String ENTRY_INFREQUENT = "Infrequent buyers";
    private static final String ENTRY_LOYALTY_PROGRAM = "Loyalty program for Premium customers";
    private static final String ENTRY_REENGAGEMENT = "Re-engagement campaign for Occasional buyers";

    public FinancialReport generateMonthlyRevenueReport(Date month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = cal.getTime();

        FinancialReport report = new FinancialReport("MONTHLY_REVENUE", startDate, endDate);

        report.addEntry(new ReportEntry(CATEGORY_SPECIAL_DISHES, ENTRY_GOURMET_MEALS, 45000.00));
        report.addEntry(new ReportEntry(CATEGORY_SPECIAL_DISHES, ENTRY_FAMILY_SPECIALS, 78000.00));
        report.addEntry(new ReportEntry(CATEGORY_BEVERAGES, ENTRY_SPECIALTY_DRINKS, 23500.00));
        report.addEntry(new ReportEntry(CATEGORY_DESSERTS, ENTRY_PREMIUM_DESSERTS, 31200.00));

        return report;
    }

    public FinancialReport generateQuarterlyComparisonReport(Date currentQuarter) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentQuarter);

        int currentMonth = cal.get(Calendar.MONTH);
        int currentQuarterStartMonth = currentMonth - (currentMonth % 3);

        cal.set(Calendar.MONTH, currentQuarterStartMonth);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date currentStartDate = cal.getTime();

        cal.add(Calendar.MONTH, 3);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date currentEndDate = cal.getTime();

        cal.setTime(currentStartDate);
        cal.add(Calendar.MONTH, -3);
        Date previousStartDate = cal.getTime();

        FinancialReport report = new FinancialReport("QUARTERLY_COMPARISON", previousStartDate, currentEndDate);

        report.addEntry(new ReportEntry(CATEGORY_SPECIAL_DISHES, ENTRY_GOURMET_MEALS, 145000.00, 120000.00));
        report.addEntry(new ReportEntry(CATEGORY_SPECIAL_DISHES, ENTRY_FAMILY_SPECIALS, 238000.00, 195000.00));
        report.addEntry(new ReportEntry(CATEGORY_BEVERAGES, ENTRY_SPECIALTY_DRINKS, 73500.00, 85000.00));
        report.addEntry(new ReportEntry(CATEGORY_DESSERTS, ENTRY_PREMIUM_DESSERTS, 112000.00, 98000.00));

        return report;
    }

    public FinancialReport generateCustomerSpendingReport(Date startDate, Date endDate) {
        FinancialReport report = new FinancialReport("CUSTOMER_SPENDING", startDate, endDate);

        report.addEntry(new ReportEntry(CATEGORY_PREMIUM, ENTRY_HIGH_VALUE, 450000.00));
        report.addEntry(new ReportEntry(CATEGORY_STANDARD, ENTRY_REGULAR, 280000.00));
        report.addEntry(new ReportEntry(CATEGORY_OCCASIONAL, ENTRY_INFREQUENT, 75000.00));

        ReportEntry opportunity1 = new ReportEntry(CATEGORY_MARKETING, ENTRY_LOYALTY_PROGRAM, 0);
        opportunity1.setDescription("Potential for 15% revenue increase with exclusive offers");

        ReportEntry opportunity2 = new ReportEntry(CATEGORY_MARKETING, ENTRY_REENGAGEMENT, 0);
        opportunity2.setDescription("Potential for 25% conversion to Standard tier");

        report.addEntry(opportunity1);
        report.addEntry(opportunity2);

        return report;
    }
}