Feature: Custom Meal Creation – As a Customer
  Customers can select ingredients to customize their meals based on their taste and dietary needs.

  Scenario: Customer requests a simple custom meal
    Given the customer "Omar Salem" is registered
    When the customer requests a meal with "Beef" and "Tomato"
    Then the system should store the custom meal for "Omar Salem"

  Scenario: Customer requests a custom meal including their allergen
    Given the customer "Noor Hasan" is registered
    And the customer updates preferences to "Standard" and allergies to "Peanuts"
    When the customer requests a meal with "Chicken" and "Peanuts"
    Then the system should reject the meal due to allergy conflict

  Scenario: Customer requests a custom meal with no ingredients
    Given the customer "Mahmoud Yaseen" is registered
    When the customer tries to select no ingredients
		Then the system should reject the request due to empty selection

