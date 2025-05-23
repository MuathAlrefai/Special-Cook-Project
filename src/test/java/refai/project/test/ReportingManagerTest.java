package refai.project.test;

import org.junit.jupiter.api.Test;
import refai.project.manager.ReportingManager;
import refai.project.model.FinancialReport;
import refai.project.model.ReportEntry;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportingManagerTest {
    private ReportingManager reportingManager = new ReportingManager();

    @Test
    void generateMonthlyRevenueReport_shouldReturnValidReport() {
        Date testDate = new Date();
        FinancialReport report = reportingManager.generateMonthlyRevenueReport(testDate);

        assertNotNull(report);
        assertEquals("MONTHLY_REVENUE", report.getReportType());
        assertEquals(4, report.getEntries().size());

        List<ReportEntry> entries = report.getEntries();
        assertTrue(entries.stream().anyMatch(e -> e.getCategory().equals("Special Dishes")));
    }

    @Test
    void generateQuarterlyComparisonReport_shouldContainComparisonData() {
        Date testDate = new Date();
        FinancialReport report = reportingManager.generateQuarterlyComparisonReport(testDate);

        assertNotNull(report);
        assertEquals("QUARTERLY_COMPARISON", report.getReportType());
        assertEquals(4, report.getEntries().size());

        ReportEntry firstEntry = report.getEntries().get(0);
        assertTrue(firstEntry.getValue() > 0);
        assertTrue(firstEntry.getComparisonValue() > 0);
    }

    @Test
    void generateCustomerSpendingReport_shouldIncludeMarketingOpportunities() {
        Calendar cal = Calendar.getInstance();
        Date startDate = cal.getTime();
        cal.add(Calendar.MONTH, 6);
        Date endDate = cal.getTime();

        FinancialReport report = reportingManager.generateCustomerSpendingReport(startDate, endDate);

        assertNotNull(report);
        assertEquals("CUSTOMER_SPENDING", report.getReportType());
        assertEquals(5, report.getEntries().size()); // 3 customer segments + 2 opportunities

        long marketingEntries = report.getEntries().stream()
                .filter(e -> e.getCategory().equals("Marketing"))
                .count();
        assertEquals(2, marketingEntries);
    }

    @Test
    void reportDates_shouldBeCorrectForMonthlyReport() {
        Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.MAY, 15);
        Date testDate = cal.getTime();

        FinancialReport report = reportingManager.generateMonthlyRevenueReport(testDate);

        cal.set(2023, Calendar.MAY, 1);
        Date expectedStart = cal.getTime();
        cal.set(2023, Calendar.MAY, 31);
        Date expectedEnd = cal.getTime();

        assertEquals(expectedStart, report.getStartDate());
        assertEquals(expectedEnd, report.getEndDate());
    }
}