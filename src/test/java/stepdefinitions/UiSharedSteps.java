package stepdefinitions;

import driversetup.BrowserActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.TimeoutException;
import pageobjects.*;
import scenariocontext.FormKey;
import scenariocontext.ScenarioContext;
import utils.ConfigReader;
import utils.PageKey;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UiSharedSteps {

    private static final Logger LOG = LogManager.getLogger(UiSharedSteps.class);
    LoginPage loginPage = new LoginPage();
    AddUserPage addUserPage = new AddUserPage();
    ContactListPage contactListPage = new ContactListPage();
    AddEditContactPage addEditContactPage = new AddEditContactPage();
    ContactDetailsPage contactDetailsPage = new ContactDetailsPage();
    BrowserActions browserActions = new BrowserActions();


    @Given("User navigates to the Login page")
    public void navigateToLoginPage() {
        try {
            browserActions.navigateTo(ConfigReader.getProperty("login.url"));
        } catch (InvalidArgumentException ex) {
            LOG.error("Failed to navigate to Login page. Make sure the login URL is valid. Exception details: {}", ex.getMessage());
            throw ex;
        }
    }

    @Then("User is redirected to {} page")
    public void checkUserRedirectToExpectedPage(String scenarioPage) {
        PageKey pageKey = PageKey.fromScenarioKey(scenarioPage);

        if (pageKey == null) {
            LOG.error("Unknown page identifier: {}", scenarioPage);
            throw new IllegalArgumentException("Unknown page: " + scenarioPage);
        }

        String expectedPageTitle = pageKey.getPageTitle();
        String expectedPageUrlKey = pageKey.getPageUrlKey();

        try {
            if (expectedPageTitle != null && !expectedPageTitle.isEmpty()) {
                LOG.debug("Waiting for page title to be '{}'", expectedPageTitle);
                browserActions.waitForExpectedPageTitle(expectedPageTitle);
                String currentPageTitle = browserActions.getPageTitle();
                assertEquals("User is not redirected to the expected page: " + expectedPageTitle, expectedPageTitle, currentPageTitle);
            } else {
                String expectedPageUrl = ConfigReader.getProperty(expectedPageUrlKey);

                LOG.debug("Waiting for URL to be '{}'", expectedPageUrl);
                browserActions.waitForExpectedPageUrl(expectedPageUrl);
                String currentPageUrl = browserActions.getPageUrl();
                assertEquals("User is not redirected to the expected page: " + currentPageUrl, expectedPageUrl, currentPageUrl);
            }
        } catch (TimeoutException ex) {
            LOG.error("Timeout waiting for user to be redirected to '{}' page", pageKey, ex);
        }
    }

    public void checkUiElements(String pageName) {
        boolean arePageElementsDisplayed = false;

        switch (pageName) {
            case "Login":
                arePageElementsDisplayed = loginPage.areAllLoginElementsDisplayed();
                break;
            case "Contact List":
                arePageElementsDisplayed = contactListPage.areAllContactListElementsDisplayed();
                break;
            case "Add User":
                arePageElementsDisplayed = addUserPage.areAllAddUserElementsDisplayed();
                break;
            case "Add Contact", "Edit Contact":
                arePageElementsDisplayed = addEditContactPage.areAllContactElementsDisplayed();
                break;
            case "Contact Details":
                arePageElementsDisplayed = contactDetailsPage.areAllContactDetailsElementsDisplayed();
                break;
        }
        if (arePageElementsDisplayed) {
            LOG.debug("All required UI elements are present on '{}' page.", pageName);
        } else {
            LOG.error("Missing required UI element(s) on '{}' page.", pageName);
        }
    }

    @Then("{} is displayed in contacts summary table")
    public void checkContactInSummary(String contactName) {
        Map<FormKey, String> parsedContact = ScenarioContext.getContact();
        contactListPage.isSpecificContactDisplayed(parsedContact);
        LOG.info("Contact with name '{}' is displayed in contacts summary table.", contactName);
    }

    @Then("{} is displayed on {} page")
    public void checkValidationMessage(String expectedValidationMessage, String pageName) {
        String actualValidationMessage;

        switch (pageName) {
            case "Login":
                actualValidationMessage = loginPage.getValidationMessageText();
                break;
            case "Add User":
                actualValidationMessage = addUserPage.getValidationMessageText();
                break;
            case "Add Contact", "Edit Contact":
                actualValidationMessage = addEditContactPage.getValidationMessageText();
                break;
            default:
                LOG.error("Unexpected page name: {}", pageName);
                throw new IllegalStateException("Unexpected value: " + pageName);
        }
        if (actualValidationMessage.isEmpty()) {
            LOG.warn("Validation message is NOT displayed on '{}' page.", pageName);
        } else {
            LOG.info("Validation message '{}' is displayed on '{}' page.", expectedValidationMessage, pageName);
        }

        assertEquals("Unexpected validation message", expectedValidationMessage, actualValidationMessage);
        LOG.debug("Expected validation message '{}' matches the actual message displayed {}'.", expectedValidationMessage, actualValidationMessage);
    }
}
