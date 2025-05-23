Feature: Track Past Orders – As a Customer

  Scenario: Customer views their order history
    Given the customer "Mahmoud Yaseen" is registered
    And the customer has placed the meals "Grilled Chicken", "Pasta Alfredo"
    When the customer requests their order history
    Then the system should return the list ["Grilled Chicken", "Pasta Alfredo"]

  Scenario: Customer reorders a previously placed meal
    Given the customer "Lina Ahmed" is registered
    And the customer has placed the meal "Veggie Burger"
    When the customer reorders "Veggie Burger"
    Then the system should confirm the reorder and update history with "Veggie Burger"

  Scenario: Customer tries to reorder a meal they never ordered
    Given the customer "Omar Salem" is registered
    When the customer reorders "Sushi"
    Then the system should reject the reorder due to missing history
