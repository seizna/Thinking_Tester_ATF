package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageobjects.AddEditContactPage;
import pageobjects.ContactDetailsPage;
import pageobjects.ContactListPage;
import scenariocontext.FormKey;
import scenariocontext.ScenarioContext;
import utils.ContactHelper;

import java.util.Map;

public class UiEditContactSteps {

    private static final Logger LOG = LogManager.getLogger(UiEditContactSteps.class);
    UiSharedSteps sharedSteps = new UiSharedSteps();
    UiLoginSteps loginSteps = new UiLoginSteps();
    ContactListPage contactListPage = new ContactListPage();
    ContactDetailsPage contactDetailsPage = new ContactDetailsPage();
    AddEditContactPage addEditContactPage = new AddEditContactPage();

    @Given("User is on Edit Contact page")
    public void accessEditContactPage() throws Exception {
        sharedSteps.navigateToLoginPage();
        loginSteps.loginUserWithValidCredentials();
        sharedSteps.checkUiElements("Contact List");
        contactListPage.selectExistingContact();
        LOG.info("Existing contact is selected from the summary table.");
        sharedSteps.checkUserRedirectToExpectedPage("Contact Details");
        sharedSteps.checkUiElements("Contact Details");
        contactDetailsPage.clickEditContactButton();
        LOG.info("User enters Edit mode by clicking [Edit Contact] button.");
        sharedSteps.checkUserRedirectToExpectedPage("Edit Contact");
        sharedSteps.checkUiElements("Edit Contact");
        LOG.info("Edit Contact page accessed and has all required elements.");
    }

    @When("User updates contact providing {}")
    public void updateExistingContact(String contactDetails) {
        Map<FormKey, String> parsedContact = ContactHelper.parseContactDetails(contactDetails);
        ScenarioContext.saveContact(parsedContact);

        LOG.info("Updating a contact providing {}", parsedContact.toString());
        addEditContactPage.addEditContact(parsedContact);
        LOG.info("Contact is submitted.");
    }

    @And("User clicks [Return to Contact List] button on Contact Details page")
    public void clickReturnToContactList() {
        contactDetailsPage.clickReturnToContactListButton();
        LOG.info("User clicks [Return to Contact List] button on Contact Details page.");
    }
}
