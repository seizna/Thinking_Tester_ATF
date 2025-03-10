package stepdefinitions;

import driversetup.BrowserActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import pageobjects.*;
import utils.ConfigReader;
import utils.Page;

public class UiSharedSteps {

    private final Logger LOG = LogManager.getLogger(UiSharedSteps.class);
    LoginPage loginPage = new LoginPage();
    AddUserPage addUserPage = new AddUserPage();
    ContactListPage contactListPage = new ContactListPage();
    AddEditContactPage addEditContactPage = new AddEditContactPage();
    ContactDetailsPage contactDetailsPage = new ContactDetailsPage();
    BrowserActions browserActions = new BrowserActions();


    @Given("User navigates to the Login page")
    public void navigateToLoginPage() {
        try {
            String expectedTitle = "Contact List App";
            browserActions.navigateTo(ConfigReader.getProperty("login.url"));
            String actualTitle = browserActions.getPageTitle();
            Assert.assertEquals("Login page title mismatch.", expectedTitle, actualTitle);
        } catch (Exception ex) {
            LOG.error("Failed to navigate to Login page:" + ex.getMessage());
            throw ex;
        }
    }

    @Then("User is redirected to {} page")
    public void checkUserRedirectToExpectedPage(String scenarioPage) {
        Page page = Page.fromScenarioKey(scenarioPage);

        if (page == null) {
            LOG.error("Unknown page identifier: {}", scenarioPage);
            throw new IllegalArgumentException("Unknown page: " + scenarioPage);
        }

        String expectedPageTitle = page.getPageTitle();
        String expectedPageUrlKey = page.getPageUrlKey();

        try {
            if (expectedPageTitle != null && !expectedPageTitle.isEmpty()) {
                LOG.debug("Waiting for page title to be '{}'", expectedPageTitle);
                browserActions.waitForExpectedPageTitle(expectedPageTitle);
                String currentPageTitle = browserActions.getPageTitle();
                Assert.assertEquals("User is not redirected to the expected page: " + expectedPageTitle, expectedPageTitle, currentPageTitle);
            } else {
                String expectedPageUrl = ConfigReader.getProperty(expectedPageUrlKey);

                LOG.debug("Waiting for URL to be '{}'", expectedPageUrl);
                browserActions.waitForExpectedPageUrl(expectedPageUrl);
                String currentPageUrl = browserActions.getPageUrl();
                Assert.assertEquals("User is not redirected to the expected page: " + currentPageUrl, expectedPageUrl, currentPageUrl);
            }
        } catch (TimeoutException ex) {
            LOG.error("Timeout waiting for user to be redirected to '{}' page", expectedPageTitle, ex);
            throw ex;
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

        Assert.assertEquals("Unexpected validation message", expectedValidationMessage, actualValidationMessage);
        LOG.debug("Expected validation message '{}' matches the actual message displayed {}'.", expectedValidationMessage, actualValidationMessage);
    }
}
