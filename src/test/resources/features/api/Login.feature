Feature: User Login

  @API
  Scenario: Check that registered user can perform successful authentication
    When User sends authentication request providing valid email and password
    Then Response returns status code 200
    And Response body contains user's email

  @API
  Scenario: Check that non-existent user cannot perform successful authentication
    When User sends authentication request providing invalid invalidUser@example.com email and invalidPassword password
    Then Response returns status code 401
