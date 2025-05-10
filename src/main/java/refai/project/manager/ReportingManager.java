package refai.project.manager;

import refai.project.model.FinancialReport;
import refai.project.model.ReportEntry;

import java.util.Calendar;
import java.util.Date;

public class ReportingManager {

    public FinancialReport generateMonthlyRevenueReport(Date month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = cal.getTime();

        FinancialReport report = new FinancialReport("MONTHLY_REVENUE", startDate, endDate);

        // In a real implementation, this would query a database for actual revenue data
        // For now, we'll add some sample data
        report.addEntry(new ReportEntry("Special Dishes", "Gourmet Meals", 45000.00));
        report.addEntry(new ReportEntry("Special Dishes", "Family Specials", 78000.00));
        report.addEntry(new ReportEntry("Beverages", "Specialty Drinks", 23500.00));
        report.addEntry(new ReportEntry("Desserts", "Premium Desserts", 31200.00));

        return report;
    }

    public FinancialReport generateQuarterlyComparisonReport(Date currentQuarter) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentQuarter);

        // Calculate current quarter start and end
        int currentMonth = cal.get(Calendar.MONTH);
        int currentQuarterStartMonth = currentMonth - (currentMonth % 3);

        cal.set(Calendar.MONTH, currentQuarterStartMonth);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date currentStartDate = cal.getTime();

        cal.add(Calendar.MONTH, 3);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date currentEndDate = cal.getTime();

        // Calculate previous quarter
        cal.setTime(currentStartDate);
        cal.add(Calendar.MONTH, -3);
        Date previousStartDate = cal.getTime();

        FinancialReport report = new FinancialReport("QUARTERLY_COMPARISON", previousStartDate, currentEndDate);

        // Sample data with comparison values and growth
        report.addEntry(new ReportEntry("Special Dishes", "Gourmet Meals", 145000.00, 120000.00));
        report.addEntry(new ReportEntry("Special Dishes", "Family Specials", 238000.00, 195000.00));
        report.addEntry(new ReportEntry("Beverages", "Specialty Drinks", 73500.00, 85000.00));
        report.addEntry(new ReportEntry("Desserts", "Premium Desserts", 112000.00, 98000.00));

        return report;
    }

    public FinancialReport generateCustomerSpendingReport(Date startDate, Date endDate) {
        FinancialReport report = new FinancialReport("CUSTOMER_SPENDING", startDate, endDate);

        // Sample data for customer segments
        report.addEntry(new ReportEntry("Premium", "High-value customers", 450000.00));
        report.addEntry(new ReportEntry("Standard", "Regular customers", 280000.00));
        report.addEntry(new ReportEntry("Occasional", "Infrequent buyers", 75000.00));

        // Add marketing opportunities as entries
        ReportEntry opportunity1 = new ReportEntry("Marketing", "Loyalty program for Premium customers", 0);
        opportunity1.setDescription("Potential for 15% revenue increase with exclusive offers");

        ReportEntry opportunity2 = new ReportEntry("Marketing", "Re-engagement campaign for Occasional buyers", 0);
        opportunity2.setDescription("Potential for 25% conversion to Standard tier");

        report.addEntry(opportunity1);
        report.addEntry(opportunity2);

        return report;
    }
}