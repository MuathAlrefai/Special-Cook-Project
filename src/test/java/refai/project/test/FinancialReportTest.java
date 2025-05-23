package refai.project.test;

import refai.project.model.FinancialReport;
import refai.project.model.ReportEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialReportTest {

    private FinancialReport report;

    @BeforeEach
    public void setUp() {
        report = new FinancialReport("Monthly", new Date(), new Date());
    }

    @Test
    public void testInitialValues() {
        assertNotNull(report.getReportId());
        assertEquals("Monthly", report.getReportType());
        assertNotNull(report.getStartDate());
        assertNotNull(report.getEndDate());
        assertEquals("CSV", report.getFormat());
        assertNotNull(report.getGeneratedDate());
        assertTrue(report.getEntries().isEmpty());
    }

    @Test
    public void testSettersAndGetters() {
        report.setReportId("REP-123");
        report.setReportType("Quarterly");

        Date now = new Date();
        report.setStartDate(now);
        report.setEndDate(now);
        report.setFormat("PDF");
        report.setGeneratedDate(now);

        assertEquals("REP-123", report.getReportId());
        assertEquals("Quarterly", report.getReportType());
        assertEquals(now, report.getStartDate());
        assertEquals(now, report.getEndDate());
        assertEquals("PDF", report.getFormat());
        assertEquals(now, report.getGeneratedDate());
    }

    @Test
    public void testSetEntriesAndAddEntry() {
        ReportEntry entry1 = new ReportEntry("Sales", "Desc1", 100.0, 90.0);
        ReportEntry entry2 = new ReportEntry("Expenses", "Desc2", 50.0, 60.0);
        List<ReportEntry> entries = new ArrayList<>(List.of(entry1));

        report.setEntries(entries);
        assertEquals(1, report.getEntries().size());

        report.addEntry(entry2);
        assertEquals(2, report.getEntries().size());
        assertTrue(report.getEntries().contains(entry2));
    }
}
