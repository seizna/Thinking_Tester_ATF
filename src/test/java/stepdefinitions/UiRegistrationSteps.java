package stepdefinitions;

import driversetup.WebDriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AddUserPage;
import pageobjects.LoginPage;
import scenariocontext.ContextKey;
import scenariocontext.ScenarioContext;

public class UiRegistrationSteps {

    private final Logger LOGGER = LogManager.getLogger(UiRegistrationSteps.class);
    private final WebDriver DRIVER = WebDriverManager.getDriver();
    LoginPage loginPage = new LoginPage(DRIVER);
    AddUserPage addUserPage = new AddUserPage(DRIVER);
    DbSteps dbSteps = new DbSteps();

    @When("User registers with First Name: {}, Last Name: {}, Email: {} and Password: {}")
    public void registerNewUser(String firstName, String lastName, String email, String password) throws Exception {

        if (email.equals("uniqueEmail")) {
            LOGGER.info("Attempting to register a new user with valid data.");
            email = "user_" + System.currentTimeMillis() + "@example.com";
            LOGGER.debug("Generated unique email: {}", email);
            LOGGER.info("Submitting registration form for user with email: {}", email);
        } else {
            LOGGER.info("Attempting to register a new user with invalid data.");
            LOGGER.error("User registration failed");
        }
        addUserPage.registerUser(firstName, lastName, email, password);
        ScenarioContext.setContext(ContextKey.FIRST_NAME, firstName);
        ScenarioContext.setContext(ContextKey.LAST_NAME, lastName);
        ScenarioContext.setContext(ContextKey.EMAIL, email);
        ScenarioContext.setContext(ContextKey.RAW_PASSWORD, password);
        ScenarioContext.setContext(ContextKey.ENCRYPTED_PASSWORD, dbSteps.encryptAesKey(password));
    }

    @Then("{} is displayed on {} page")
    public void checkValidationMessage(String validationMessage, String pageName) {
        WebElement validationMessageDisplayed;

        switch (pageName) {
            case "Login":
                validationMessageDisplayed = loginPage.waitForValidationMessage();
                break;
            case "Add User":
                validationMessageDisplayed = addUserPage.waitForValidationMessage();
                break;
            default:
                LOGGER.error("Unexpected page name: {}", pageName);
                throw new IllegalStateException("Unexpected value: " + pageName);
        }

        if (validationMessageDisplayed.isDisplayed()) {
            LOGGER.warn("Validation message is displayed on '{}' page.", pageName);
        } else {
            LOGGER.warn("Validation message is NOT displayed on '{}' page.", pageName);
        }

        Assert.assertEquals("Unexpected validation message", validationMessage, validationMessageDisplayed.getText());
        LOGGER.debug("Expected validation message '{}' matches the actual displayed message '{}'.", validationMessage, validationMessageDisplayed.getText());
    }
}
