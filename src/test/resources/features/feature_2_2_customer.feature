Feature: Suggest Ingredient Alternatives – As a Customer

  Scenario: Customer selects an unavailable ingredient and system suggests alternatives
    Given the customer "Omar Salem" is registered
    And the customer is logged in and ready to customize a meal
   	When the customer selects the ingredients "Dragonfruit" and "Beef" for suggestion
   	Then the system should suggest alternatives for "Dragonfruit"

  Scenario: Customer selects ingredient that conflicts with allergy and system suggests safe alternatives
    Given the customer "Noor Hasan" is registered
    And the customer updates preferences to "Standard" and allergies to "Milk"
    And the customer is logged in and ready to customize a meal
    When the customer selects the ingredients "Dragonfruit" and "Beef" for suggestion
    Then the system should suggest allergy-safe alternatives for "Milk"

  Scenario: Customer selects a fully valid combination, no alternatives needed
    Given the customer "Mahmoud Yaseen" is registered
    And the customer updates preferences to "Standard" and allergies to "None"
    And the customer is logged in and ready to customize a meal
    When the customer selects the ingredients "Rice" and "Chicken"
    Then the system should not suggest any alternatives
