package refai.project.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FinancialReport {
    private String reportId;
    private String reportType;
    private Date startDate;
    private Date endDate;
    private List<ReportEntry> entries;
    private String format;
    private Date generatedDate;

    public FinancialReport() {
        this.entries = new ArrayList<>();
        this.generatedDate = new Date();
        this.format = "CSV"; // Default format
    }

    public FinancialReport(String reportType, Date startDate, Date endDate) {
        this();
        this.reportType = reportType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reportId = "REP-" + System.currentTimeMillis();
    }

    // Getters and Setters
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<ReportEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ReportEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(ReportEntry entry) {
        this.entries.add(entry);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }
}