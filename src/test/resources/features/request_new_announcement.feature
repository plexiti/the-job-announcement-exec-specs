Feature: Request New Announcement

  A manager of a business unit has the need of a new co-worker in his/her department.
  He needs to describe his need for a new employee and suggest a title for the new position.
  Once he requests a new job announcement, the Human Resources department takes care of
  specifying the requirements for the position.

  Scenario: As a department manager, I can request a new job announcement
    Given I am logged in as "Gonzo the Great"
    Then I should see a button "Request new announcement" enabled

  Scenario: As an employee who is not a department manager, I cannot request a new job announcement
    Given I am logged in as "Gonzo the Great"
    Then I should see a button "Request new announcement" disabled

  Scenario: As a department manager, I request a new job announcement
    Given I am logged in as "Gonzo the Great"
    Then I see a button "Request new announcement" enabled
