package stepdefinitions;

import database.DbActions;
import database.Users;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageobjects.LoginPage;
import utils.EncryptionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UiLoginSteps {

    private static final Logger LOG = LogManager.getLogger(UiLoginSteps.class);
    LoginPage loginPage = new LoginPage();
    UiSharedSteps sharedSteps = new UiSharedSteps();
    DbActions dbActions = new DbActions();
    Users user = new Users();
    private final List<String> actualValidationMessages = new ArrayList<>();

    @Given("User is on Login page")
    public void accessLoginPage() {
        sharedSteps.navigateToLoginPage();
        sharedSteps.checkUiElements("Login");
        LOG.info("Login page accessed and has all required elements.");
    }

    @When("User logs in with valid email and password")
    public void loginUserWithValidCredentials() throws Exception {
        LOG.info("Attempting to login registered user.");
        user = dbActions.selectLastInsertedUser();
        LOG.info("User with the following email {} is retrieved from DB.", user.getEmail());
        String rawPassword = EncryptionUtils.decryptAesKey(user.getPassword());

        loginPage.loginUser(user.getEmail(), rawPassword);
        LOG.info("Logging user with the following credentials: {} and {}.", user.getEmail(), rawPassword);
    }

    @When("User attempts login with invalid {} and {}")
    public void loginUserWithInvalidCredentials(String email, String password) {
        LOG.info("Attempting to login user with invalid or missing data.");
        loginPage.loginUser(email, password);
        LOG.error("User login failed.");
    }

    @When("User attempts login with invalid credentials")
    public void loginUserWithInvalidCredentials(DataTable credentialsTable) {
        LOG.info("User attempts to login with invalid or missing data.");
        List<Map<String, String>> credentialsList = credentialsTable.asMaps(String.class, String.class);

        for (Map<String, String> credentials : credentialsList) {
            String email = credentials.get("email");
            String password = credentials.get("password");

            loginPage.clearLoginForm();
            if (email == null) {
                email = "";
            }
            loginPage.setEmail(email);
            if (password == null) {
                password = "";
            }
            loginPage.setPassword(password);
            loginPage.clickSubmitButton();

            String actualValidationMessage = loginPage.getValidationMessageText();
            actualValidationMessages.add(actualValidationMessage);
        }
    }

    @Then("Validation message is displayed")
    public void checkValidationMessages(DataTable expectedValidationMessagesTable) {
        List<Map<String, String>> listOfValidationMessages = expectedValidationMessagesTable.asMaps(String.class, String.class);
        List<String> expectedValidationMessageList = new ArrayList<>();

        for (Map<String, String> validationMessage : listOfValidationMessages) {
            expectedValidationMessageList.add(validationMessage.get("validationMessage"));
        }
        assertEquals("Unexpected validation message.", expectedValidationMessageList, actualValidationMessages);
        LOG.info("'{}' validation message is displayed.", actualValidationMessages.getFirst());
    }
}
