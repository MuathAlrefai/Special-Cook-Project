Feature: Suggest Ingredient Alternatives – As a Chef
  As a chef,
  I want to be notified of unavailable or unsafe ingredients for a customer,
  So that I can adjust the meal and suggest appropriate alternatives.

  Scenario: Chef identifies unavailable ingredients while preparing a meal
    Given the customer "Lina Ahmed" is registered
    And the customer updates preferences to "Standard" and allergies to "None"
    And the chef is preparing a meal for "Lina Ahmed"
    When the chef selects the ingredients "Dragonfruit" and "Fish" for "Lina Ahmed"
    Then the system should suggest alternatives for "Dragonfruit"

  Scenario: Chef detects an ingredient that conflicts with the customer’s allergy
    Given the customer "Fadi Naser" is registered
    And the customer updates preferences to "Vegan" and allergies to "Milk"
    And the chef is preparing a meal for "Fadi Naser"
    When the chef selects the ingredients "Milk" and "Rice" for "Fadi Naser"
    Then the system should suggest allergy-safe alternatives for "Milk"

  Scenario: Chef selects fully valid and available ingredients
    Given the customer "Mahmoud Yaseen" is registered
    And the customer updates preferences to "Vegetarian" and allergies to "None"
    And the chef is preparing a meal for "Mahmoud Yaseen"
    When the chef selects the ingredients "Tofu" and "Carrots" for "Mahmoud Yaseen"
    Then the system should not suggest any alternatives
