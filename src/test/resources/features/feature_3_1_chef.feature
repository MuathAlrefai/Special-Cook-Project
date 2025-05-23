Feature: View Assigned Tasks – As a Chef
  As a chef,
  I want to receive notifications about my assigned cooking tasks
  So that I can prepare meals on time.

  Scenario: Chef receives notification for assigned grill task
    Given the kitchen manager has access to chef data
    And chef "Ahmad" has workload level "Low" and expertise "Grill"
    When the kitchen manager assigns a "Grill" task
    Then the system should assign the task to chef "Ahmad"

  Scenario: Chef with medium workload receives dessert task
    Given the kitchen manager has access to chef data
    And chef "Layla" has workload level "Medium" and expertise "Desserts"
    When the kitchen manager assigns a "Desserts" task
    Then the system should assign the task to chef "Layla"

  Scenario: Chef does not receive task when workload is high
    Given the kitchen manager has access to chef data
    And chef "Salem" has workload level "High" and expertise "Vegan"
    When the kitchen manager assigns a "Vegan" task
    Then the task list should be empty
