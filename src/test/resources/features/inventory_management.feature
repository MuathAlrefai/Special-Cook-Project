Feature: Inventory and Supplier Management
  As a kitchen manager, I want to track ingredient stock levels in real time so that I can prevent shortages and ensure continuous operations.
  As a system, I want to automatically suggest restocking when ingredients are low so that kitchen managers can take action promptly.

   # 4.1 Track available ingredients and suggest restocking
  Scenario: Ingredient quantity updated after usage
    Given the inventory has "Tomatoes" with quantity 50
    When 10 "Tomatoes" are used
    Then the inventory should show 40 "Tomatoes"

  Scenario: Ingredient quantity updated after restocking
    Given the inventory has "Onions" with quantity 20
    When 30 "Onions" are restocked
    Then the inventory should show 50 "Onions"

  Scenario: View current stock of an ingredient
    Given the inventory has "Cheese" with quantity 15
    When I check the stock for "Cheese"
    Then it should display 15 units of "Cheese"

  Scenario: System suggests restocking when stock is low
    Given the inventory has "Lettuce" with quantity 4
    When the threshold for "Lettuce" is 5
    Then the system should suggest restocking "Lettuce"

  Scenario: System does not suggest restocking when stock is sufficient
    Given the inventory has "Salt" with quantity 10
    When the threshold for "Salt" is 5
    Then the system should not suggest restocking "Salt"

  Scenario: System suggests restocking multiple low-stock items
    Given the inventory has "Carrots" with quantity 2 and threshold 5
    And the inventory has "Potatoes" with quantity 3 and threshold 4
    When the system checks for low-stock items
    Then it should suggest restocking "Carrots" and "Potatoes"


