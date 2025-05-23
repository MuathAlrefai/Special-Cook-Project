Feature: Analyze Orders and Trends – As a System Administrator

  Scenario: Admin retrieves order history for a specific customer
    Given the customer "Mahmoud Yaseen" is registered
    And the customer has placed the meals "Pasta", "Grilled Chicken"
    When the system administrator retrieves the order history for "Mahmoud Yaseen"
   Then the system should return the order list "Pasta", "Grilled Chicken"


  Scenario: Admin analyzes most ordered meals across customers
    Given the customer "Lina Ahmed" is registered
    And the customer has ordered "Burger" three times
    And the customer "Fadi Naser" is registered
    And the customer has ordered "Burger" two times
    When the system administrator analyzes global meal trends
    Then the system should identify "Burger" as a top ordered meal

  Scenario: Admin retrieves empty order history for a new customer
    Given the customer "Omar Salem" is registered
    When the system administrator retrieves the order history for "Omar Salem"
    Then the system should indicate that no orders are available
