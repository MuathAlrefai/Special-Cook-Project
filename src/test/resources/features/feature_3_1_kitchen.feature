Feature: Assign tasks to chefs based on workload and expertise – As a Kitchen Manager

  Scenario: Assign task to available chef with low workload
    Given the kitchen manager has access to chef data
    And chef "Ahmad" has workload level "Low" and expertise "Grill"
    When the kitchen manager assigns a "Grill Chicken" task
    Then the system should assign the task to chef "Ahmad"

  Scenario: Skip chef with high workload
    Given chef "Salem" has workload level "High" and expertise "Salad"
    And chef "Zain" has workload level "Low" and expertise "Salad"
    When the kitchen manager assigns a "Salad Preparation" task
    Then the system should assign the task to chef "Zain"
    And not assign it to chef "Salem"

  Scenario: Select chef with matching expertise
    Given chef "Layla" has workload level "Medium" and expertise "Desserts"
    And chef "Ziad" has workload level "Low" and expertise "Grill"
    When the kitchen manager assigns a "Chocolate Cake" task
    Then the system should assign the task to chef "Layla"
