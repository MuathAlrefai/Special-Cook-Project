Feature: View Dietary Preferences – As a Chef

  Scenario: Chef views preferences of vegetarian customer
    Given the customer "Mahmoud Yaseen" is registered
    And the customer updates preferences to "Vegetarian" and allergies to "None"
    And the chef wants to prepare a meal for "Mahmoud Yaseen"
    When the chef requests customer dietary preferences
    Then the system provides "Vegetarian" and "None"

  Scenario: Chef views preferences of customer with seafood allergy
    Given the customer "Noor Hasan" is registered
    And the customer updates preferences to "Standard" and allergies to "Seafood"
    And the chef wants to prepare a meal for "Noor Hasan"
    When the chef requests customer dietary preferences
    Then the system provides "Standard" and "Seafood"

  Scenario: Chef views preferences of vegan customer with multiple allergies
    Given the customer "Fadi Naser" is registered
    And the customer updates preferences to "Vegan" and allergies to "Dairy,Eggs"
    And the chef wants to prepare a meal for "Fadi Naser"
    When the chef requests customer dietary preferences
    Then the system provides "Vegan" and "Dairy,Eggs"
