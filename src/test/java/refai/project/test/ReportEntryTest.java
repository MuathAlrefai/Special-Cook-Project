package refai.project.test;

import refai.project.model.ReportEntry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReportEntryTest {

    @Test
    public void testConstructorAndGetters() {
        ReportEntry entry = new ReportEntry("Sales", "January report", 200.0, 100.0);
        assertEquals("Sales", entry.getCategory());
        assertEquals("January report", entry.getDescription());
        assertEquals(200.0, entry.getValue());
        assertEquals(100.0, entry.getComparisonValue());
        assertEquals(100.0, entry.getGrowthPercentage());
    }

    @Test
    public void testSettersAndGrowthCalculation() {
        ReportEntry entry = new ReportEntry();
        entry.setCategory("Revenue");
        entry.setDescription("Q1 Revenue");
        entry.setComparisonValue(50.0);
        entry.setValue(100.0); 

        assertEquals("Revenue", entry.getCategory());
        assertEquals("Q1 Revenue", entry.getDescription());
        assertEquals(100.0, entry.getValue());
        assertEquals(50.0, entry.getComparisonValue());
        assertEquals(100.0, entry.getGrowthPercentage());
    }

    @Test
    public void testZeroComparisonValue() {
        ReportEntry entry = new ReportEntry();
        entry.setValue(200.0);
        entry.setComparisonValue(0); 
        assertEquals(0.0, entry.getGrowthPercentage());
    }
}
