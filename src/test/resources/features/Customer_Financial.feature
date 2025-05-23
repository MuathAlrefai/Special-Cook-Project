Feature: Billing System

  # Customer Invoice Scenarios
  Scenario: Customer receives invoice after purchase completion
    Given a customer with ID "C123" has made a purchase with order ID "ORD456"
    And the purchase has been completed
    When the system generates an invoice for the order
    Then the customer should receive an invoice with details of the purchase
    And the invoice status should be "SENT"

  Scenario: Customer receives invoice in preferred format
    Given a customer with ID "C789" has made a purchase with order ID "ORD101"
    And the customer has set their preferred invoice format to "PDF"
    When the system generates an invoice for the order
    Then the customer should receive the invoice in PDF format
    And the invoice should contain all purchase details

  Scenario: Customer receives updated invoice after order modification
    Given a customer with ID "C456" has an existing invoice for order ID "ORD202"
    And the customer has modified their order
    When the system regenerates the invoice for the modified order
    Then the customer should receive an updated invoice
    And the invoice should be marked as "UPDATED"
    And the invoice should contain the modified order details

  # Financial Reporting Scenarios
  Scenario: System administrator generates monthly revenue report
    Given a system administrator with ID "A001" is logged in
    And there are completed transactions for the current month
    When the administrator requests a monthly revenue report
    Then the system should generate a report showing total revenue for the month
    And the report should be categorized by product categories
    And the report should be downloadable in CSV format

  Scenario: System administrator generates quarterly performance comparison report
    Given a system administrator with ID "A002" is logged in
    And transaction data exists for the current and previous quarters
    When the administrator requests a quarterly performance comparison report
    Then the system should generate a report comparing current quarter to previous quarter
    And the report should display growth percentages
    And the report should highlight top-performing products

  Scenario: System administrator generates customer spending analysis report
    Given a system administrator with ID "A003" is logged in
    And customer purchase data exists for the specified time period
    When the administrator requests a customer spending analysis report for the past 6 months
    Then the system should generate a report showing spending patterns by customer segment
    And the report should identify high-value customers
    And the report should suggest potential marketing opportunities