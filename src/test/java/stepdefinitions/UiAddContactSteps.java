package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageobjects.AddEditContactPage;
import pageobjects.ContactListPage;
import scenariocontext.FormKey;
import scenariocontext.ScenarioContext;
import utils.ContactHelper;

import java.util.Map;

public class UiAddContactSteps {

    private static final Logger LOG = LogManager.getLogger(UiAddContactSteps.class);
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
        Map<FormKey, String> parsedContact = ContactHelper.parseContactDetails(contactDetails);
        ScenarioContext.saveContact(parsedContact);

        LOG.info("Adding a contact providing {}", parsedContact.toString());
        addEditContactPage.addEditContact(parsedContact);
        LOG.info("Contact is submitted.");
    }
}


