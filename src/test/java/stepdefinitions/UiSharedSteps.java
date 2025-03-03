package stepdefinitions;

import driversetup.BrowserActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import pageobjects.AddEditContactPage;
import pageobjects.AddUserPage;
import pageobjects.ContactListPage;
import pageobjects.LoginPage;
import utils.ConfigReader;
import java.util.HashMap;
import java.util.Map;

public class UiSharedSteps {

    private final Logger LOG = LogManager.getLogger(UiSharedSteps.class);
    LoginPage loginPage = new LoginPage();
    AddUserPage addUserPage = new AddUserPage();
    ContactListPage contactListPage = new ContactListPage();
    AddEditContactPage addEditContactPage = new AddEditContactPage();
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
    public void checkUserRedirectToExpectedPage(String scenarioPageTitle) {
        String expectedPageTitle = getExpectedPageTitle(scenarioPageTitle);

        if (expectedPageTitle == null) {
            LOG.error("Unknown page title: {}", scenarioPageTitle);
            throw new IllegalArgumentException("Unknown page: " + scenarioPageTitle);
        }

        try {
            LOG.debug("Waiting for page title to be '{}'", expectedPageTitle);
            browserActions.waitForPageToLoad(expectedPageTitle);
        } catch (TimeoutException ex) {
            LOG.error("Timeout waiting for user to be redirected to '{}' page", expectedPageTitle, ex);
        }

        String currentPageTitle = browserActions.getPageTitle();
        Assert.assertEquals("User is not redirected to the expected page: " + expectedPageTitle, expectedPageTitle, currentPageTitle);
    }

    private String getExpectedPageTitle(String pageTitle) {
        Map<String, String> expectedPageMap = new HashMap<>();
        expectedPageMap.put("Contact List App", "Contact List App");
        expectedPageMap.put("Add User", "Add User");
        expectedPageMap.put("Contact List", "My Contacts");
        expectedPageMap.put("Add Contact", "Add Contact");
        expectedPageMap.put("Contact Details", "Contact Details");

        return expectedPageMap.get(pageTitle);
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
            case "Add Contact":
                arePageElementsDisplayed = addEditContactPage.areAllContactElementsDisplayed();
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
            case "Add Contact":
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
