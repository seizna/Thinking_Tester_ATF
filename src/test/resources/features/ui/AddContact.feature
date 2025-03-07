Feature: Add Contact

  Background:
    Given User is on Add Contact page

  @UI @TakeScreenshot
  Scenario Outline: Check that user can add a contact providing valid data
    When User adds contact providing <valid Contact Details>
    Then User is redirected to Contact List page
    And <New contact> is displayed in contacts summary table
    Examples:
      | valid Contact Details                                                                                              | New contact |
      | Jane, Doe, , , , , , , , ,                                                                                         | Jane Doe    |
      | John, Doe, , test@email.com, 1234567890, , , , Chisinau, 2045, Moldova                                             | John Doe    |
      | John, Smith, 2003-11-26, test@email.com, 1234567890, Test Drive 1, Test Drive 2, Chisinau, Chisinau, 2045, Moldova | John Smith  |

  @UI @TakeScreenshot
  Scenario Outline: Verify system validates user input on Add Contact page
    When User adds contact providing <invalid Contact Details>
    Then <Validation message> is displayed on Add Contact page
    Examples:
      | invalid Contact Details                                                             | Validation message                                                                                                                                 |
      | , , , , , , , , , ,                                                                 | Contact validation failed: firstName: Path `firstName` is required., lastName: Path `lastName` is required.                                        |
      | first name above 20 c, last name, , , , , , , , ,                                   | Contact validation failed: firstName: Path `firstName` (`first name above 20 c`) is longer than the maximum allowed length (20).                   |
      | first name, last name above 20 ch, , , , , , , , ,                                  | Contact validation failed: lastName: Path `lastName` (`last name above 20 ch`) is longer than the maximum allowed length (20).                     |
      | first name, last name, 2000, , , , , , , ,                                          | Contact validation failed: birthdate: Birthdate is invalid                                                                                         |
      | first name, last name, 2050-11-26, , , , , , , ,                                    | Contact validation failed: birthdate: Birthdate is invalid                                                                                         |
      | first name, last name, 1111-11-11, , , , , , , ,                                    | Contact validation failed: birthdate: Birthdate is invalid                                                                                         |
      | first name, last name, , test, , , , , , ,                                          | Contact validation failed: email: Email is invalid                                                                                                 |
      | first name, last name, , , 800551234567891, , , , , ,                               | Contact validation failed: phone: Phone number is invalid                                                                                          |
      | first name, last name, , , 0000000000000016, , , , , ,                              | Contact validation failed: phone: Path `phone` (`0000000000000016`) is longer than the maximum allowed length (15).                                |
      | first name, last name, , , , Test Drive 123 is way above 40 characters, , , , ,     | Contact validation failed: street1: Path `street1` (`Test Drive 123 is way above 40 characters`) is longer than the maximum allowed length (40).   |
      | first name, last name, , , , , Test Drive 234 is way above 40 characters, , , ,     | Contact validation failed: street2: Path `street2` (`Test Drive 234 is way above 40 characters`) is longer than the maximum allowed length (40).   |
      | first name, last name, , , , , , This city name is way above 40 characters, , ,     | Contact validation failed: city: Path `city` (`This city name is way above 40 characters`) is longer than the maximum allowed length (40).         |
      | first name, last name, , , , , , , Max allowed value reached, ,                     | Contact validation failed: stateProvince: Path `stateProvince` (`Max allowed value reached`) is longer than the maximum allowed length (20).       |
      | first name, last name, , , , , , , , 0,                                             | Contact validation failed: postalCode: Postal code is invalid                                                                                      |
      | first name, last name, , , , , , , , 12345678910,                                   | Contact validation failed: postalCode: Path `postalCode` (`12345678910`) is longer than the maximum allowed length (10).                           |
      | first name, last name, , , , , , , , , Country name is way above allowed max length | Contact validation failed: country: Path `country` (`Country name is way above allowed max length`) is longer than the maximum allowed length (40). |
