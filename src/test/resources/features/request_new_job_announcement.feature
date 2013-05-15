Feature: Request New Job Announcement

  A manager of a business unit has the need of a new co-worker in his/her department.
  He needs to describe his "need" for a new employee and suggest a "title" for the new position.
  Once he requests a new job announcement, the Human Resources department takes care of
  specifying the requirements for the position.

  Scenario: As a department manager, I can request a new job announcement
    Given I am logged in as "Gonzo The Great"
    When I request a new job announcement
    And I use the job announcement title "Java Developer"
    And I use the job announcement need
      """
      We would need a Java developer with some experience.
      From the top of my head I think we would need at least the following:
      - 10+ years Java experience
      - good soft skills
      - experience with BPMN
      """
    Then A new job announcement with these attributes exists
    Given I am logged in as "Fozzie The Bear"
    Then A job announcement with these attributes exists that he can work on

  Scenario: Not being a department manager, I cannot request a new job announcement
    Given I am logged in as "Fozzie The Bear"
    Then I cannot request a new job announcement

