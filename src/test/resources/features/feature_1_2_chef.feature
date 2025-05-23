Feature: Track Orders and Suggest Plans – As a Chef

  Scenario: Chef views customer's full order history
    Given the customer "Omar Salem" is registered
    And the customer has ordered "Grilled Chicken" and "Vegetable Soup"
    When the chef views the order history for "Omar Salem"
    Then the system should show the orders "Grilled Chicken" and "Vegetable Soup"

  Scenario: Chef suggests a personalized plan based on repeated meals
    Given the customer "Lina Ahmed" is registered
    And the customer has ordered "Pasta" three times
    When the chef analyzes order history for "Lina Ahmed"
    Then the system should suggest a personalized plan including "Pasta"

  Scenario: Chef views order history of customer with no orders
    Given the customer "Fadi Naser" is registered
    When the chef views the order history for "Fadi Naser"
    Then the system should show that there are no orders available
