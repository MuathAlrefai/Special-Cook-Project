Feature: Notifications and Alerts System

# Customer Reminder Scenarios
Scenario: Customer receives reminder for upcoming meal delivery
  Given a customer "John Doe" has a scheduled meal delivery on "2025-05-12 18:00:00"
  When the system checks for upcoming deliveries
  Then the customer should receive a delivery reminder notification
  And the notification should contain the delivery date and time

Scenario: Customer receives multiple reminders for a special order
  Given a customer "Emma Smith" has a special order delivery scheduled on "2025-05-15 19:30:00"
  And the customer has opted for multiple reminders
  When the system processes reminder schedules
  Then the customer should receive a first reminder 48 hours before delivery
  And the customer should receive a second reminder 24 hours before delivery
  And the customer should receive a final reminder 2 hours before delivery

Scenario: Customer customizes notification preferences
  Given a customer "Robert Johnson" has an account with the system
  When the customer updates notification preferences to:
    | channel | enabled | timing_hours |
    | email   | true    | 24           |
    | SMS     | true    | 12           |
    | app     | false   | 0            |
  Then the system should save the customer's notification preferences
  And future reminders should be sent according to these preferences

# Chef Notification Scenarios
Scenario: Chef gets notified of a scheduled cooking task
  Given a chef "Chef Gordon" is assigned to prepare an order for "2025-05-11 16:00:00"
  When the system processes cooking schedules
  Then the chef should receive a cooking task notification
  And the notification should include the order details
  And the notification should be sent 12 hours before the cooking time

Scenario: Chef receives daily schedule of all cooking tasks
  Given a chef "Chef Julia" has multiple cooking tasks on "2025-05-13"
  And the tasks are scheduled at different times throughout the day
  When the daily schedule is generated at "05:00:00"
  Then the chef should receive a consolidated schedule notification
  And the notification should list all tasks in chronological order
  And the notification should include preparation requirements for each task
