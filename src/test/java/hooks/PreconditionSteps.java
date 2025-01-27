package hooks;

import driversetup.WebDriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AddUserPage;
import pageobjects.ContactListPage;
import pageobjects.LoginPage;
import scenariocontext.ContextKey;
import scenariocontext.ScenarioContext;
import stepdefinitions.DbSteps;
import utils.ConfigReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertTrue;

public class PreconditionSteps {

    private final WebDriver driver = WebDriverManager.getDriver();
    LoginPage loginPage = new LoginPage(driver);
    AddUserPage addUserPage = new AddUserPage(driver);
    ContactListPage contactListPage = new ContactListPage(driver);
    ConfigReader configReader = new ConfigReader();
    DbSteps dbSteps = new DbSteps();

    @Given("User navigates to the Login page")
    public void navigateToLoginPage() {
        driver.navigate().to(configReader.getUrl("login.url"));
        System.out.println("Login page accessed");
    }

    @And("User clicks [Sign up] button")
    public void clickSignUp() {
        loginPage.signUpButton();
    }

    @And("User is redirected to {} page")
    public void checkUserRedirectToExpectedPage(String pageTitle) {
        checkRedirect(pageTitle);
        if (pageTitle.equals("Contact List")) {
            dbSteps.insertRegisteredUser(ScenarioContext.getContext(ContextKey.EMAIL).toString()
                    , ScenarioContext.getContext(ContextKey.ENCRYPTED_PASSWORD).toString()
                    , ScenarioContext.getContext(ContextKey.FIRST_NAME).toString()
                    , ScenarioContext.getContext(ContextKey.LAST_NAME).toString());
        }
    }

    public void checkRedirect(String pageTitle) {
        String expectedPageTitle = getExpectedPageTitle(pageTitle);

        if (expectedPageTitle == null) {
            throw new IllegalArgumentException("Unknown page: " + pageTitle);
        }

        try {
            WebDriverManager.getWait().until(ExpectedConditions.titleIs(expectedPageTitle));
        } catch (TimeoutException ex) {
            System.err.println("Timeout waiting for user to be redirected to " + expectedPageTitle);
        }

        String currentPageTitle = driver.getTitle();
        Assert.assertEquals("User is not redirected to the expected page: " + expectedPageTitle, expectedPageTitle, currentPageTitle);
        System.out.println("User is redirected to the expected page: " + currentPageTitle);
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
            throw new IllegalArgumentException("No UI elements defined for page: " + pageName);
        }

        pageElements.forEach((elementDescription, elementsList) -> {
            elementsList.forEach(element -> {
                assertTrue(elementDescription + " is not displayed", element.isDisplayed());
            });
        });

        System.out.println(pageName + " page has all UI elements in place");
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
