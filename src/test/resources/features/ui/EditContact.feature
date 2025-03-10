Feature: Edit Contact

#  Background:
#    Given User is on Contact List page
#    And At least one contact exists in contacts summary table
#    And User selects existing contact to view the Contact Details
#    And User clicks [Edit Contact] button on Contact Details page

  Background:
    Given User is on Edit Contact page

  @UI @TakeScreenshot
  Scenario Outline: Check that user can update contact's info
    When User updates contact providing <valid Contact Details>
    And User clicks [Return to Contact List] button on Contact Details page
    Then <Updated contact> is displayed in contacts summary table
    Examples:
      | valid Contact Details                                                                                              | Updated contact                  |
      | UpdatedFirstName, UpdatedLastName, , , , , , , , ,                                                                 | UpdatedFirstName UpdatedLastName |
      | John, Doe, , test@email.com, 1234567890, , , , Chisinau, 2045, Moldova                                             | John Doe                         |
      | John, Smith, 2003-11-26, test@email.com, 1234567890, Test Drive 1, Test Drive 2, Chisinau, Chisinau, 2045, Moldova | John Smith                       |

  @UI @TakeScreenshot
  Scenario Outline: Verify system validates user input on Edit Contact page
    When User updates contact providing <invalid Contact Details>
    Then <Validation message> is displayed on Edit Contact page
    Examples:
      | invalid Contact Details                                                             | Validation message                                                                                                                          |
      | , , , , , , , , , ,                                                                 | Validation failed: lastName: Path `lastName` is required., firstName: Path `firstName` is required.                                         |
      | first name above 20 c, last name, , , , , , , , ,                                   | Validation failed: firstName: Path `firstName` (`first name above 20 c`) is longer than the maximum allowed length (20).                    |
      | first name, last name above 20 ch, , , , , , , , ,                                  | Validation failed: lastName: Path `lastName` (`last name above 20 ch`) is longer than the maximum allowed length (20).                      |
      | first name, last name, 2000, , , , , , , ,                                          | Validation failed: birthdate: Birthdate is invalid                                                                                          |
      | first name, last name, 2050-11-26, , , , , , , ,                                    | Validation failed: birthdate: Birthdate is invalid                                                                                          |
      | first name, last name, 1111-11-11, , , , , , , ,                                    | Validation failed: birthdate: Birthdate is invalid                                                                                          |
      | first name, last name, , test, , , , , , ,                                          | Validation failed: email: Email is invalid                                                                                                  |
      | first name, last name, , , 800551234567891, , , , , ,                               | Validation failed: phone: Phone number is invalid                                                                                           |
      | first name, last name, , , 0000000000000016, , , , , ,                              | Validation failed: phone: Path `phone` (`0000000000000016`) is longer than the maximum allowed length (15).                                 |
      | first name, last name, , , , Test Drive 123 is way above 40 characters, , , , ,     | Validation failed: street1: Path `street1` (`Test Drive 123 is way above 40 characters`) is longer than the maximum allowed length (40).    |
      | first name, last name, , , , , Test Drive 234 is way above 40 characters, , , ,     | Validation failed: street2: Path `street2` (`Test Drive 234 is way above 40 characters`) is longer than the maximum allowed length (40).    |
      | first name, last name, , , , , , This city name is way above 40 characters, , ,     | Validation failed: city: Path `city` (`This city name is way above 40 characters`) is longer than the maximum allowed length (40).          |
      | first name, last name, , , , , , , Max allowed value reached, ,                     | Validation failed: stateProvince: Path `stateProvince` (`Max allowed value reached`) is longer than the maximum allowed length (20).        |
      | first name, last name, , , , , , , , 0,                                             | Validation failed: postalCode: Postal code is invalid                                                                                       |
      | first name, last name, , , , , , , , 12345678910,                                   | Validation failed: postalCode: Path `postalCode` (`12345678910`) is longer than the maximum allowed length (10).                            |
      | first name, last name, , , , , , , , , Country name is way above allowed max length | Validation failed: country: Path `country` (`Country name is way above allowed max length`) is longer than the maximum allowed length (40). |
