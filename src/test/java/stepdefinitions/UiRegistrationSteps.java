package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageobjects.AddUserPage;
import pageobjects.LoginPage;
import scenariocontext.ContextKey;
import scenariocontext.ScenarioContext;
import utils.EncryptionUtils;

import java.util.List;
import java.util.Map;

public class UiRegistrationSteps {

    private static final Logger LOG = LogManager.getLogger(UiRegistrationSteps.class);
    LoginPage loginPage = new LoginPage();
    AddUserPage addUserPage = new AddUserPage();
    UiSharedSteps sharedSteps = new UiSharedSteps();


    @Given("User is on Add User page")
    public void accessAddUserPage() {
        sharedSteps.navigateToLoginPage();
        sharedSteps.checkUiElements("Login");
        loginPage.clickSignUpButton();
        sharedSteps.checkUserRedirectToExpectedPage("Add User");
        sharedSteps.checkUiElements("Add User");
        LOG.info("Add User page accessed and has all required elements.");
    }

    @When("User registers with First Name: {}, Last Name: {}, Email: {} and Password: {}")
    public void registerNewUser(String firstName, String lastName, String email, String password) throws Exception {
        boolean isEmailUnique = email.equals("uniqueEmail");

        if (isEmailUnique) {
            LOG.info("Attempting to register a new user with valid data.");
            email = "user_" + System.currentTimeMillis() + "@example.com";
            LOG.debug("Generated unique email: {}", email);
            LOG.info("Submitting registration form for user with email: {}.", email);
        } else {
            LOG.info("Attempting to register a new user with invalid data.");
        }

        addUserPage.registerUser(firstName, lastName, email, password);

        if (isEmailUnique) {
            LOG.info("User registration completed with success.");
            ScenarioContext.setContext(ContextKey.USER_FIRST_NAME, firstName);
            ScenarioContext.setContext(ContextKey.USER_LAST_NAME, lastName);
            ScenarioContext.setContext(ContextKey.USER_EMAIL, email);
            ScenarioContext.setContext(ContextKey.USER_RAW_PASSWORD, password);
            ScenarioContext.setContext(ContextKey.USER_ENCRYPTED_PASSWORD, EncryptionUtils.encryptAesKey(password));
        } else {
            LOG.error("User registration failed.");
        }
    }

    @When("User submits the registration form with the following data")
    public void submitUserRegistrationForm(DataTable registrationDetails) throws Exception {
        LOG.info("Attempting to register a new user.");
        List<Map<String, String>> registrationDataList = registrationDetails.asMaps(String.class, String.class);
        for (Map<String, String> registrationData : registrationDataList) {
            String firstName = registrationData.get("firstName");
            String lastName = registrationData.get("lastName");
            String email = registrationData.get("email");
            String password = registrationData.get("password");

            if ("uniqueEmail".equals(email)) {
                email = "user_" + System.currentTimeMillis() + "@example.com";
                LOG.debug("Generated unique email for registration: {}", email);
            }

            LOG.info("Submitting registration form for user with the following email: {}.", email);
            addUserPage.registerUser(firstName, lastName, email, password);

            LOG.info("User is successfully registered.");
            ScenarioContext.setContext(ContextKey.USER_FIRST_NAME, firstName);
            ScenarioContext.setContext(ContextKey.USER_LAST_NAME, lastName);
            ScenarioContext.setContext(ContextKey.USER_EMAIL, email);
            ScenarioContext.setContext(ContextKey.USER_RAW_PASSWORD, password);
            ScenarioContext.setContext(ContextKey.USER_ENCRYPTED_PASSWORD, EncryptionUtils.encryptAesKey(password));
        }
    }
}
