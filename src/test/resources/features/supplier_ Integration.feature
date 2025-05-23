# 4.2 Integration with suppliers for real-time pricing and ordering
Feature: Supplier Integration

Scenario: Fetch real-time price for an ingredient
  Given the supplier provides a price of 3.5 for "Tomatoes"
  When I request the current price for "Tomatoes"
  Then the system should return 3.5 as the current price

Scenario: Fetch price for an ingredient with no supplier data
  Given no supplier price is available for "Cabbage"
  When I request the current price for "Cabbage"
  Then the system should indicate that price data is unavailable

Scenario: Update price when supplier changes rate
  Given the supplier provides a price of 2.0 for "Carrots"
  When the supplier updates the price for "Carrots" to 2.5
  Then the system should return 2.5 as the updated price

Scenario: Do not generate order if stock is not critically low
  Given the inventory has "Eggs" with quantity 10 and threshold 5
  When the system checks stock levels
  Then it should not generate a purchase order for "Eggs"
