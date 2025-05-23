Feature: Low Stock Ingredient Notifications
  As a kitchen manager
  I want to receive alerts when stock levels are low
  So that I can reorder before running out of ingredients

  Scenario: Kitchen manager receives notification when ingredient is below threshold
    Given a kitchen manager "Manager Kim" is registered in the system
    And ingredient "Flour" has a current stock of "5" kg
    And the minimum threshold for "Flour" is set to "10" kg
    When the system checks the inventory levels
    Then the kitchen manager should receive a low stock notification for "Flour"

  Scenario: No notification when ingredient stock is above threshold
    Given a kitchen manager "Manager Lee" is registered in the system
    And ingredient "Sugar" has a current stock of "15" kg
    And the minimum threshold for "Sugar" is set to "10" kg
    When the system checks the inventory levels
    Then no low stock notification should be sent

  Scenario: Multiple low stock alerts are grouped into one notification
    Given a kitchen manager "Manager Park" is registered in the system
    And ingredient "Salt" has a current stock of "2" kg with minimum threshold of "5" kg
    And ingredient "Pepper" has a current stock of "1" kg with minimum threshold of "3" kg
    And ingredient "Olive Oil" has a current stock of "3" liters with minimum threshold of "5" liters
    When the system checks the inventory levels
    Then the kitchen manager should receive a consolidated notification for multiple low stock ingredients
    And the notification should include "Salt", "Pepper", and "Olive Oil"