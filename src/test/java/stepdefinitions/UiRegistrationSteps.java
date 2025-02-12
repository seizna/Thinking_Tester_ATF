package stepdefinitions;

import driversetup.WebDriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AddEditContactPage;
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
    //TODO: Precondition refactor
    UiSharedSteps sharedSteps = new UiSharedSteps();


    @Given("User is on Add User page")
    public void accessAddUserPage() {
        sharedSteps.navigateToLoginPage();
        sharedSteps.checkUiElements("Login");
        loginPage.clickSignUpButton();
        sharedSteps.checkUserRedirectToExpectedPage("Add User");
        sharedSteps.checkUiElements("Add User");
        LOGGER.info("Add User page accessed.");
    }

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
}
