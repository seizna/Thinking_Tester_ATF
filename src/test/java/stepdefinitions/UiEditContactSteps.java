package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageobjects.AddEditContactPage;
import pageobjects.ContactDetailsPage;
import pageobjects.ContactListPage;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class UiEditContactSteps {

    private final Logger LOG = LogManager.getLogger(UiEditContactSteps.class);
    UiSharedSteps sharedSteps = new UiSharedSteps();
    UiLoginSteps loginSteps = new UiLoginSteps();
    ContactListPage contactListPage = new ContactListPage();
    ContactDetailsPage contactDetailsPage = new ContactDetailsPage();
    AddEditContactPage addEditContactPage = new AddEditContactPage();

    @Given("User is on Contact List page")
    public void accessContactListPage() throws Exception {
        sharedSteps.navigateToLoginPage();
        loginSteps.loginUserWithValidCredentials();
        sharedSteps.checkUiElements("Contact List");
        LOG.info("Contact List page accessed and has all required elements.");
    }

    @And("At least one contact exists in contacts summary table")
    public void checkExistingContactInSummary() {
        assertTrue("Contacts summary table is empty.", contactListPage.hasAtLeastOneContact());
        LOG.info("Summary table has at least one contact.");
    }

    @And("User selects existing contact to view the Contact Details")
    public void selectExistingContact() {
        contactListPage.selectExistingContact();
        LOG.info("Existing contact is selected from the summary table.");
    }

    @And("User clicks [Edit Contact] button on Contact Details page")
    public void clickEditContact() {
        contactDetailsPage.clickEditContactButton();
        LOG.info("User clicks Edit Contact button on Contact Details page.");
    }

    @When("User updates contact providing {}")
    public void updateExistingContact(String contactDetails) {
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
            LOG.info("Updating contact's required fields.");
            addEditContactPage.addEditContact(firstName, lastName);
            LOG.info("Contact name is updated to {}.", firstName + " " + lastName);
        } else {
            LOG.info("Updating contact's required and optional fields.");
            addEditContactPage.addEditContact(firstName, lastName, dateOfBirth, email, phone,
                    streetAddr1, streetAddr2, city, stateOrProvince,
                    postalCode, country);
            LOG.info("Contact is submitted with the following name '{}' and optional info: '{}'.",
                    firstName + " " + lastName,
                    dateOfBirth + " " + email + " " + phone + " " + streetAddr1 + " " + streetAddr2 + " " + city + " " + stateOrProvince + " " + postalCode + " " + country);
        }
    }

    @And("User clicks [Return to Contact List] button on Contact Details page")
    public void clickReturnToContactList() {
        contactDetailsPage.clickReturnToContactListButton();
        LOG.info("User clicks Return to Contact List button on Contact Details page.");
    }
}
