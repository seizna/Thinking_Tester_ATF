package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageobjects.AddEditContactPage;
import pageobjects.ContactListPage;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class UiAddContactSteps {

    private final Logger LOG = LogManager.getLogger(UiAddContactSteps.class);
    UiSharedSteps sharedSteps = new UiSharedSteps();
    UiLoginSteps loginSteps = new UiLoginSteps();
    ContactListPage contactListPage = new ContactListPage();
    AddEditContactPage addEditContactPage = new AddEditContactPage();

    @Given("User is on Add Contact page")
    public void accessAddContactPage() throws Exception {
        sharedSteps.navigateToLoginPage();
        loginSteps.loginUserWithValidCredentials();
        sharedSteps.checkUiElements("Contact List");
        contactListPage.clickAddContactButton();
        sharedSteps.checkUserRedirectToExpectedPage("Add Contact");
        sharedSteps.checkUiElements("Add Contact");
        LOG.info("Add Contact page accessed and has all required elements.");
    }

    @When("User adds contact providing {}")
    public void addNewContact(String contactDetails) {
        String[] details = contactDetails.split(",", -1);

        String firstName = details[0].trim();
        String lastName = details[1].trim();
        String dateOfBirth = details[2].trim();
        String email = details[3].trim();
        String phone = details[4].trim();
        String streetAddr1 = details[5].trim();
        String streetAddr2 = details[6].trim();
        String city = details[7].trim();
        String stateOrProvince = details[8].trim();
        String postalCode = details[9].trim();
        String country = details[10].trim();

        boolean allOptionalFieldsEmpty = Arrays.stream(details, 2, details.length)
                .allMatch(String::isBlank);

        if (allOptionalFieldsEmpty) {
            LOG.info("Adding a contact providing required fields only.");
            addEditContactPage.addContact(firstName, lastName);
            LOG.info("Contact '{}' is submitted.", firstName + " " + lastName);
        } else {
            LOG.info("Adding a contact providing required and optional fields.");
            addEditContactPage.addContact(firstName, lastName, dateOfBirth, email, phone,
                    streetAddr1, streetAddr2, city, stateOrProvince,
                    postalCode, country);
            LOG.info("Contact is submitted with the following name '{}' and optional info: '{}'.",
                    firstName + " " + lastName,
                    dateOfBirth + " " + email + " " + phone + " " + streetAddr1 + " " + streetAddr2 + " " + city + " " + stateOrProvince + " " + postalCode + " " + country);
        }
    }

    @Then("{} is added to contacts summary table")
    public void checkCreatedContactInSummary(String contactName) {
        assertTrue("Contact is not displayed in contacts summary table.", contactListPage.isSpecificContactDisplayed(contactName));
        LOG.info("New contact with name '{}' is displayed in contacts summary table.", contactName);
    }
}


