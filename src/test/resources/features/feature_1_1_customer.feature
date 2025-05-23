Feature: Store Dietary Preferences – As a Customer

  Scenario: Customer provides vegetarian preference with no allergies
    Given the customer "Omar Salem" is registered
    When the customer updates preferences to "Vegetarian" and allergies to ""
    Then the system should store "Vegetarian" and ""

  Scenario: Customer provides gluten-free preference and allergy to peanuts
    Given the customer "Lina Ahmed" is registered
    When the customer updates preferences to "Gluten-Free" and allergies to "Peanuts"
    Then the system should store "Gluten-Free" and "Peanuts"

  Scenario: Customer updates existing preferences to vegan with dairy allergy
    Given the customer "Sara Khaled" is registered
    When the customer updates preferences to "Vegan" and allergies to "Dairy"
    Then the system should store "Vegan" and "Dairy"
