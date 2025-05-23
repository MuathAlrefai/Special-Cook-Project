Feature: Validate ingredient combinations – As a System

  Scenario: System rejects incompatible ingredient combination
    Given the customer "Omar Salem" is logged in and ready to customize a meal
    When the customer selects the ingredients "Milk" and "Fish"
    Then the system should reject the request due to incompatibility

  Scenario: System rejects unavailable ingredient
    Given the customer "Sara Khaled" is logged in and ready to customize a meal
    When the customer selects the ingredients "Dragonfruit" and "Beef"
    Then the system should reject the request due to unavailable ingredients

  Scenario: System rejects ingredient that conflicts with allergy
    Given the customer "Noor Hasan" is registered
    And the customer updates preferences to "Standard" and allergies to "Peanuts"
    Given the customer "Noor Hasan" is logged in and ready to customize a meal
    When the customer selects the ingredients "Chicken" and "Peanuts"
    Then the system should reject the request due to allergy conflict
