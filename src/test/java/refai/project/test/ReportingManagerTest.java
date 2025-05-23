package refai.project.test;

import refai.project.manager.ReportingManager;
import refai.project.model.FinancialReport;
import refai.project.model.ReportEntry;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReportingManagerTest {

    private final ReportingManager manager = new ReportingManager();

    @Test
    public void testGenerateMonthlyRevenueReport() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.MARCH, 15); // Arbitrary date in March
        Date testDate = cal.getTime();

        FinancialReport report = manager.generateMonthlyRevenueReport(testDate);
        assertNotNull(report);
        assertEquals("MONTHLY_REVENUE", report.getReportType());

        List<ReportEntry> entries = report.getEntries();
        assertEquals(4, entries.size());
        assertTrue(entries.stream().anyMatch(e -> e.getCategory().equals("Special Dishes")));
    }

    @Test
    public void testGenerateQuarterlyComparisonReport() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 5); // Within Q1
        Date testQuarter = cal.getTime();

        FinancialReport report = manager.generateQuarterlyComparisonReport(testQuarter);
        assertNotNull(report);
        assertEquals("QUARTERLY_COMPARISON", report.getReportType());
        assertEquals(4, report.getEntries().size());
    }

    @Test
    public void testGenerateCustomerSpendingReport() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.FEBRUARY, 1);
        Date start = cal.getTime();
        cal.set(2024, Calendar.FEBRUARY, 28);
        Date end = cal.getTime();

        FinancialReport report = manager.generateCustomerSpendingReport(start, end);
        assertNotNull(report);
        assertEquals("CUSTOMER_SPENDING", report.getReportType());

        List<ReportEntry> entries = report.getEntries();
        assertEquals(5, entries.size());
        assertTrue(entries.stream().anyMatch(e -> e.getCategory().equals("Marketing")));
    }
}
