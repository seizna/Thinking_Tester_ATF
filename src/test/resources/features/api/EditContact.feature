Feature: Edit Contact

  @API
  Scenario: Check that registered user can update contact details
    Given User sends authentication request providing valid email and password
    And Contact's under test ID is retrieved from Contact List
    When User sends a patch contact request providing any valid info
    Then Response returns status code 200
    And Response body contains updated contact info