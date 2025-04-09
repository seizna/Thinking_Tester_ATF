Feature: Delete Contact

  Background:
    Given User is on Contact Details page

  @UI @TakeScreenshot
  Scenario: Check that user can delete an existing contact
    When User clicks [Delete Contact] button and confirms delete action by hitting [Ok] button on browser alert
    Then User is redirected to Contact List page
    And Deleted contact is not displayed in contacts summary table