package stepdefinitions;

import driversetup.BrowserActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageobjects.ContactDetailsPage;
import pageobjects.ContactListPage;
import scenariocontext.FormKey;
import scenariocontext.ScenarioContext;
import utils.PageHelper;

import java.util.Map;

public class UiDeleteContactSteps {

    private static final Logger LOG = LogManager.getLogger(UiDeleteContactSteps.class);
    UiSharedSteps sharedSteps = new UiSharedSteps();
    UiLoginSteps loginSteps = new UiLoginSteps();
    ContactListPage contactListPage = new ContactListPage();
    ContactDetailsPage contactDetailsPage = new ContactDetailsPage();
    BrowserActions browserActions = new BrowserActions();


    @Given("User is on Contact Details page")
    public void accessContactDetailsPage() throws Exception {
        sharedSteps.navigateToLoginPage();
        loginSteps.loginUserWithValidCredentials();
        sharedSteps.checkUiElements("Contact List");
        contactListPage.selectExistingContact();
        LOG.info("Existing contact is selected from the summary table.");
        sharedSteps.checkUserRedirectToExpectedPage("Contact Details");
        sharedSteps.checkUiElements("Contact Details");
        LOG.info("Contact Details page accessed and has all required elements.");
    }

    @When("User clicks [Delete Contact] button on Contact Details page")
    public void clickDeleteContact() {
        Map<FormKey, String> contactDetails = PageHelper.parseContactDetailsForm(contactDetailsPage.getContactDetailsForm());
        ScenarioContext.saveContact(contactDetails);
        contactDetailsPage.clickDeleteContactButton();
    }

    @And("User confirms delete action by hitting [Ok] button on browser alert")
    public void confirmDeleteContactAction() {
        browserActions.acceptBrowserAlert();
    }

    @And("Deleted contact is not displayed in contacts summary table")
    public void checkDeletedContactInSummary() {
        Map<FormKey, String> contactDetails = ScenarioContext.getContact();
        contactListPage.isDeletedContactMissingInTable(contactDetails);
        LOG.info("Contact '{}' is not displayed in contacts summary table.", contactDetails.toString());
    }
}

