package stepdefinitions;

import driversetup.WebDriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AddUserPage;
import pageobjects.ContactListPage;
import pageobjects.LoginPage;
import utils.ConfigReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertTrue;

public class UiSharedSteps {

    private final Logger LOGGER = LogManager.getLogger(UiSharedSteps.class);
    private final WebDriver DRIVER = WebDriverManager.getDriver();
    LoginPage loginPage = new LoginPage(DRIVER);
    AddUserPage addUserPage = new AddUserPage(DRIVER);
    ContactListPage contactListPage = new ContactListPage(DRIVER);
    ConfigReader configReader = new ConfigReader();

    @Given("User navigates to the Login page")
    public void navigateToLoginPage() {
        DRIVER.navigate().to(configReader.getUrl("login.url"));
        LOGGER.info("Login page accessed.");
    }

    @And("User clicks [Sign up] button")
    public void clickSignUp() {
        loginPage.signUpButton();
        LOGGER.info("User clicked sign up button.");
    }

    @Then("User is redirected to {} page")
    public void checkUserRedirectToExpectedPage(String pageTitle) {
        String expectedPageTitle = getExpectedPageTitle(pageTitle);

        if (expectedPageTitle == null) {
            LOGGER.error("Unknown page title: {}", pageTitle);
            throw new IllegalArgumentException("Unknown page: " + pageTitle);
        }

        try {
            LOGGER.debug("Waiting for user to be redirected to '{}' page", expectedPageTitle);
            WebDriverManager.getWait().until(ExpectedConditions.titleIs(expectedPageTitle));
        } catch (TimeoutException ex) {
            LOGGER.error("Timeout waiting for user to be redirected to '{}' page", expectedPageTitle, ex);
        }

        String currentPageTitle = DRIVER.getTitle();
        Assert.assertEquals("User is not redirected to the expected page: " + expectedPageTitle, expectedPageTitle, currentPageTitle);
        LOGGER.info("User is successfully redirected to the expected page: {}.", currentPageTitle);

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

    @And("All UI elements are displayed on {} page")
    public void checkUiElements(String pageName) {
        Map<String, List<WebElement>> pageElements = getPageElements(pageName);

        if (pageElements.isEmpty()) {
            LOGGER.error("No UI elements defined for page: {}", pageName);
            throw new IllegalArgumentException("No UI elements defined for page: " + pageName);
        }

        pageElements.forEach((elementDescription, elementsList) -> {
            elementsList.forEach(element -> {
                if (!element.isDisplayed()) {
                    LOGGER.warn("{} is not displayed on '{}' page", elementDescription, pageName);
                } else {
                    LOGGER.debug("{} is displayed on '{}' page", elementDescription, pageName);
                }
                assertTrue(elementDescription + " is not displayed", element.isDisplayed());
            });
        });
        LOGGER.debug("All required UI elements are present on '{}' page.", pageName);
    }

    private Map<String, List<WebElement>> getPageElements(String pageName) {
        Map<String, List<WebElement>> elements = new HashMap<>();

        switch (pageName) {
            case "Login" -> {
                elements.put("Email field", List.of(loginPage.getEmail()));
                elements.put("Password field", List.of(loginPage.getPassword()));
                elements.put("Submit button", List.of(loginPage.getSubmitButton()));
                elements.put("Sign up button", List.of(loginPage.getSignUpButton()));
            }
            case "Contact List" -> {
                elements.put("Logout button", List.of(contactListPage.getLogoutButton()));
                elements.put("Add Contact button", List.of(contactListPage.getAddContactButton()));
                elements.put("Summary Table", List.of(contactListPage.getSummaryTable()));
            }
            case "Add User" -> {
                elements.put("First Name field", List.of(addUserPage.getFirstName()));
                elements.put("Last Name field", List.of(addUserPage.getLastName()));
                elements.put("Email field", List.of(addUserPage.getEmail()));
                elements.put("Password field", List.of(addUserPage.getPassword()));
                elements.put("Submit button", List.of(addUserPage.getSubmitButton()));
                elements.put("Cancel button", List.of(addUserPage.getCancelButton()));
            }
        }
        return elements;
    }
}
