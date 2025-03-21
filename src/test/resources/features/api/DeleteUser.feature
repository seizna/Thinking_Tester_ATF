Feature: Delete User

  @API
  Scenario: Verify that registered user can be successfully deleted
    Given User sends authentication request providing valid email and password
    When User sends a delete user request
    Then Response returns status code 200
    And User with email under test is removed from DB

  @API
  Scenario: Verify delete user action without authentication
    Given User does not send authentication request
    When User sends a delete user request
    Then Response returns status code 401
    And Response body contains Please authenticate. message
