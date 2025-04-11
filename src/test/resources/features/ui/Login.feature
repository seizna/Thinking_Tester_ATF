Feature: User Login

  Background:
    Given User is on Login page

  @UI @DB @TakeScreenshot
  Scenario: Verify registered user is able to log in successfully using valid credentials
    When User logs in with valid email and password
    Then User is redirected to Contact List page

  @UI @TakeScreenshot
  Scenario Outline: Verify that system rejects login requests with invalid credentials
    When User attempts login with invalid <Email> and <Password>
    Then <Validation message> is displayed on Login page
    Examples:
      | Email                | Password | Validation message             |
      |                      |          | Incorrect username or password |
      | seiz.nadea@gmail.com |          | Incorrect username or password |
      |                      | QAZws1!  | Incorrect username or password |
      | seiz.nadea           | QAZws1!  | Incorrect username or password |


