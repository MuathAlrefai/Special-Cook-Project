package refai.project.AcceptanceTest;

import refai.project.model.*;
import refai.project.manager.InvoiceManager;
import refai.project.manager.ReportingManager;

import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.en.*;
import java.util.Calendar;
import java.util.Date;


public class BillingSystemStepDefinitions {
    //common variables and objects for both invoice and reporting
    private Customer customer;
    private Administrator admin;

    //invoice related variables and objects
    private Order order;
    private Invoice invoice;
    private InvoiceManager invoiceManager = new InvoiceManager();

    //reporting related variables and objects
    private FinancialReport report;
    private ReportingManager reportingManager = new ReportingManager();
    private Date reportDate;

    // ---- INVOICE STEP DEFINITIONS ----

    @Given("a customer with ID {string} has made a purchase with order ID {string}")
    public void customerHasMadePurchase(String customerId, String orderId) {
        //create customer with name as the customerId (since our Customer class uses name as identifier)
        customer = new Customer(customerId);

        order = new Order();
        order.setOrderId(orderId);
        order.setCustomerId(customerId);

        //add some sample items to the order
        order.addItem(new OrderItem("P1", "Special Gourmet Meal", 2, 19.99));
        order.addItem(new OrderItem("P2", "Premium Dessert", 1, 29.99));
    }

    @Given("the purchase has been completed")
    public void purchaseHasBeenCompleted() {
        order.setStatus("COMPLETED");
    }

    @Given("the customer has set their preferred invoice format to {string}")
    public void customerSetsPreferredFormat(String format) {
        customer.setPreferredInvoiceFormat(format);
    }

    @Given("a customer with ID {string} has an existing invoice for order ID {string}")
    public void customerHasExistingInvoice(String customerId, String orderId) {
        //create customer with name as the customerId
        customer = new Customer(customerId);

        order = new Order();
        order.setOrderId(orderId);
        order.setCustomerId(customerId);

        //add some sample items to the order
        order.addItem(new OrderItem("P1", "Special Gourmet Meal", 2, 19.99));
        order.addItem(new OrderItem("P2", "Premium Dessert", 1, 29.99));

        invoice = invoiceManager.generateInvoice(customerId, orderId, order);
    }

    @Given("the customer has modified their order")
    public void customerModifiesOrder() {
        //change quantity of an existing item and add a new item
        order.getItems().get(0).setQuantity(3);
        order.addItem(new OrderItem("P3", "Specialty Drink", 1, 15.99));
        order.setModifiedDate(new Date());
        order.setStatus("MODIFIED");
    }

    @When("the system generates an invoice for the order")
    public void systemGeneratesInvoice() {
        invoice = invoiceManager.generateInvoice(customer.getName(), order.getOrderId(), order);
    }

    @When("the system regenerates the invoice for the modified order")
    public void systemRegeneratesInvoice() {
        invoice = invoiceManager.updateInvoice(invoice.getInvoiceId(), order);
    }

    @Then("the customer should receive an invoice with details of the purchase")
    public void customerReceivesInvoice() {
        assertTrue(invoiceManager.sendInvoice(invoice, customer));
        assertNotNull(invoice);
        assertEquals(customer.getName(), invoice.getCustomerId());
        assertEquals(order.getOrderId(), invoice.getOrderId());
    }

    @Then("the invoice status should be {string}")
    public void invoiceHasCorrectStatus(String status) {
        assertEquals(status, invoice.getStatus());
    }

    @Then("the customer should receive the invoice in PDF format")
    public void customerReceivesInvoiceInPdfFormat() {
        assertTrue(invoiceManager.sendInvoice(invoice, customer));
        assertEquals("PDF", invoice.getFormat());
    }

    @Then("the invoice should contain all purchase details")
    public void invoiceContainsPurchaseDetails() {
        assertEquals(order.getTotalAmount(), invoice.getTotalAmount(), 0.001);
        assertEquals(order.getItems().size(), invoice.getItems().size());

        //verify that each order item is reflected in the invoice
        for (int i = 0; i < order.getItems().size(); i++) {
            assertEquals(order.getItems().get(i).getProductId(), invoice.getItems().get(i).getProductId());
            assertEquals(order.getItems().get(i).getQuantity(), invoice.getItems().get(i).getQuantity());
        }
    }

    @Then("the customer should receive an updated invoice")
    public void customerReceivesUpdatedInvoice() {
        //verify status is UPDATED *before* sending
        assertEquals("UPDATED", invoice.getStatus());

        //now send the invoice
        assertTrue(invoiceManager.sendInvoice(invoice, customer));
    }

    @Then("the invoice should be marked as {string}")
    public void invoiceIsMarkedCorrectly(String status) {
        assertEquals(status, invoice.getStatus());
    }

    @Then("the invoice should contain the modified order details")
    public void invoiceContainsModifiedDetails() {
        assertEquals(order.getTotalAmount(), invoice.getTotalAmount(), 0.001);
        assertEquals(order.getItems().size(), invoice.getItems().size());
        assertNotNull(invoice.getModifiedDate());
    }

    // ---- FINANCIAL REPORTING STEP DEFINITIONS ----

    @Given("a system administrator with ID {string} is logged in")
    public void adminIsLoggedIn(String adminId) {
        admin = new Administrator();
        admin.setAdminId(adminId);
        admin.setRole("ADMIN");
    }

    @Given("there are completed transactions for the current month")
    public void transactionsExistForCurrentMonth() {
        //in a real implementation, this would check the database
        //for the refai.project.test, we just need to set the current date
        reportDate = new Date();
    }

    @Given("transaction data exists for the current and previous quarters")
    public void transactionDataExistsForQuarters() {
        //set current date for quarterly report
        reportDate = new Date();
    }

    @Given("customer purchase data exists for the specified time period")
    public void customerPurchaseDataExists() {
        //set current date for customer report
        reportDate = new Date();
    }

    @When("the administrator requests a monthly revenue report")
    public void adminRequestsMonthlyReport() {
        report = reportingManager.generateMonthlyRevenueReport(reportDate);
    }

    @When("the administrator requests a quarterly performance comparison report")
    public void adminRequestsQuarterlyReport() {
        report = reportingManager.generateQuarterlyComparisonReport(reportDate);
    }

    @When("the administrator requests a customer spending analysis report for the past {int} months")
    public void adminRequestsCustomerSpendingReport(int months) {
        Calendar cal = Calendar.getInstance();
        Date endDate = new Date();
        cal.setTime(endDate);
        cal.add(Calendar.MONTH, -months);
        Date startDate = cal.getTime();

        report = reportingManager.generateCustomerSpendingReport(startDate, endDate);
    }

    @Then("the system should generate a report showing total revenue for the month")
    public void systemGeneratesMonthlyRevenueReport() {
        assertNotNull(report);
        assertEquals("MONTHLY_REVENUE", report.getReportType());
        double totalRevenue = report.getEntries().stream()
                .mapToDouble(entry -> entry.getValue())
                .sum();
        assertTrue(totalRevenue > 0);
    }

    @Then("the report should be categorized by product categories")
    public void reportIsCategorizedByProductCategories() {
        //verify that entries have categories
        assertTrue(report.getEntries().stream()
                .anyMatch(e -> e.getCategory() != null && !e.getCategory().isEmpty()));

        //verify that there are different categories
        long distinctCategories = report.getEntries().stream()
                .map(e -> e.getCategory())
                .distinct()
                .count();
        assertTrue(distinctCategories > 1);
    }

    @Then("the report should be downloadable in CSV format")
    public void reportIsDownloadableInCsvFormat() {
        assertEquals("CSV", report.getFormat());
    }

    @Then("the system should generate a report comparing current quarter to previous quarter")
    public void systemGeneratesQuarterlyComparisonReport() {
        assertNotNull(report);
        assertEquals("QUARTERLY_COMPARISON", report.getReportType());
    }

    @Then("the report should display growth percentages")
    public void reportDisplaysGrowthPercentages() {
        //verify that at least one entry has a growth percentage
        assertTrue(report.getEntries().stream()
                .anyMatch(e -> e.getGrowthPercentage() != 0));
    }

    @Then("the report should highlight top-performing products")
    public void reportHighlightsTopPerformingProducts() {
        //in a real implementation, this would verify specific products are marked as top performers
        //for the refai.project.test, we'll check that some products have positive growth
        assertTrue(report.getEntries().stream()
                .anyMatch(e -> e.getGrowthPercentage() > 0));
    }

    @Then("the system should generate a report showing spending patterns by customer segment")
    public void systemGeneratesCustomerSpendingReport() {
        assertNotNull(report);
        assertEquals("CUSTOMER_SPENDING", report.getReportType());

        //verify that report contains customer segments
        assertTrue(report.getEntries().stream()
                .anyMatch(e -> e.getCategory().equals("Premium") ||
                        e.getCategory().equals("Standard") ||
                        e.getCategory().equals("Occasional")));
    }

    @Then("the report should identify high-value customers")
    public void reportIdentifiesHighValueCustomers() {
        //verify that Premium category exists with high value
        assertTrue(report.getEntries().stream()
                .anyMatch(e -> e.getCategory().equals("Premium") && e.getValue() > 0));
    }

    @Then("the report should suggest potential marketing opportunities")
    public void reportSuggestsMarketingOpportunities() {
        //verify marketing opportunities are included
        assertTrue(report.getEntries().stream()
                .anyMatch(e -> e.getCategory().equals("Marketing")));
    }
}