Feature: User Registration
  
  Background:
    Given User is on Add User page

  @UI @DB @TakeScreenshot
  Scenario: Successful User Registration
    When User registers with First Name: Jane, Last Name: Doe, Email: uniqueEmail and Password: TestUserRegistration1!
    Then User is redirected to Contact List page
    And User with email under test is inserted in DB

  @UI @DB @TakeScreenshot
  Scenario: Check that user is able to register successfully using valid data
    When User submits the registration form with the following data
      | firstName | lastName | email       | password               |
      | John      | Doe      | uniqueEmail | TestUserRegistration1! |
    Then User is redirected to Contact List page
    And User with email under test is inserted in DB

  @UI @TakeScreenshot
  Scenario Outline: Verify system validates user input on Add User page
    When User registers with First Name: <First Name>, Last Name: <Last Name>, Email: <Email> and Password: <Password>
    Then <Validation message> is displayed on Add User page
    Examples:
      | First Name             | Last Name              | Email             | Password                                                                                              | Validation message                                                                                                                                                                                           |
      |                        |                        |                   |                                                                                                       | User validation failed: firstName: Path `firstName` is required., lastName: Path `lastName` is required., email: Email is invalid, password: Path `password` is required.                                    |
      |                        |                        | Jane.Doe@sena.com |                                                                                                       | User validation failed: firstName: Path `firstName` is required., lastName: Path `lastName` is required., password: Path `password` is required.                                                             |
      | Jane                   | Doe                    | Jane.Doe@sena.com | Jane!!                                                                                                | User validation failed: password: Path `password` (`Jane!!`) is shorter than the minimum allowed length (7).                                                                                                 |
      | Jane                   | Doe                    | Jane.Doe@sena.com | TestUserRegistrationTestUserRegistrationTestUserRegistrationTestUserRegistrationTestUserRegistration! | User validation failed: password: Path `password` (`TestUserRegistrationTestUserRegistrationTestUserRegistrationTestUserRegistrationTestUserRegistration!`) is longer than the maximum allowed length (100). |
      | Jane                   | Doe                    | Jane.Doe@sena.com | JaneDoe                                                                                               | Email address is already in use                                                                                                                                                                              |
      | CheckMaximumCharacters | Doe                    | Jane.Doe@sena.com | JaneDoe                                                                                               | User validation failed: firstName: Path `firstName` (`CheckMaximumCharacters`) is longer than the maximum allowed length (20).                                                                               |
      | Jane                   | CheckMaximumCharacters | Jane.Doe@sena.com | JaneDoe                                                                                               | User validation failed: lastName: Path `lastName` (`CheckMaximumCharacters`) is longer than the maximum allowed length (20).                                                                                 |


