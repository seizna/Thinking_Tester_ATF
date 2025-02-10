Feature: User Login

  @UI @DB @TakeScreenshot
  Scenario: Verify user is able to log in successfully using valid credentials
    Given User navigates to the Login page
    And All UI elements are displayed on Login page
    When User logs in with valid email and password
    Then User is redirected to Contact List page

  @UI @TakeScreenshot
  Scenario Outline: Verify that system rejects login requests with invalid credentials
    Given User navigates to the Login page
    And All UI elements are displayed on Login page
    When User attempts login with invalid <Email> and <Password>
    Then <Validation message> is displayed on Login page
    Examples:
      | Email                | Password | Validation message             |
      |                      |          | Incorrect username or password |
      | seiz.nadea@gmail.com |          | Incorrect username or password |
      |                      | QAZws1!  | Incorrect username or password |
      | seiz.nadea           | QAZws1!  | Incorrect username or password |

  @API
  Scenario: Check that existing user can perform successful authentication
    When User sends authentication request providing valid email and password
    Then Response returns status code 200

  @API
  Scenario: Check that non-existent user cannot perform successful authentication
    When User sends authentication request providing invalid invalidUser@example.com email and invalidPassword password
    Then Response returns status code 401