Feature: Add Contact

  Background:
    Given User is on Add Contact page

  @UI
  Scenario Outline: Check that user can add a contact providing valid data in required fields only
    When User adds contact providing <valid Contact Details>
    Then User is redirected to Contact List page
    And <New contact> is added to contacts summary table
    Examples:
      | valid Contact Details                                                                                              | New contact |
      | Jane, Doe, , , , , , , , ,                                                                                         | Jane Doe    |
      | John, Doe, , test@email.com, 1234567890, , , , Chisinau, 2045, Moldova                                             | John Doe    |
      | John, Smith, 2003-11-26, test@email.com, 1234567890, Test Drive 1, Test Drive 2, Chisinau, Chisinau, 2045, Moldova | John Smith  |

  @UI
  Scenario Outline: Verify system validates user input on Add Contact page
    When User adds contact providing <invalid Contact Details>
    Then <Validation message> is displayed on Add Contact page
    Examples:
      | invalid Contact Details                                                                                                                  | Validation message                                                                                                               |
      | , , , , , , , , , ,                                                                                                                      | Contact validation failed: firstName: Path `firstName` is required., lastName: Path `lastName` is required.                      |
      | first name above 20 c, last name, 2003-11-26, test@email.com, 1234567890, Test Drive 1, Test Drive 2, Chisinau, Chisinau, 2045, Moldova  | Contact validation failed: firstName: Path `firstName` (`first name above 20 c`) is longer than the maximum allowed length (20). |
      | first name, last name above 20 ch, 2003-11-26, test@email.com, 1234567890, Test Drive 1, Test Drive 2, Chisinau, Chisinau, 2045, Moldova | Contact validation failed: lastName: Path `lastName` (`last name above 20 ch`) is longer than the maximum allowed length (20).   |
      | first name, last name, 2000, test@email.com, 1234567890, Test Drive 1, Test Drive 2, Chisinau, Chisinau, 2045, Moldova                   | Contact validation failed: birthdate: Birthdate is invalid                                                                       |
      | first name, last name, 2050-11-26, test@email.com, 1234567890, Test Drive 1, Test Drive 2, Chisinau, Chisinau, 2045, Moldova             | Contact validation failed: birthdate: Birthdate is invalid                                                                       |
      | first name, last name , 1111-11-11, test@email.com, 1234567890, Test Drive 1, Test Drive 2, Chisinau, Chisinau, 2045, Moldova            | Contact validation failed: birthdate: Birthdate is invalid                                                                       |
