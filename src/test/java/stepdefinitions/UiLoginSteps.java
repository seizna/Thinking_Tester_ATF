package stepdefinitions;

import database.DbActions;
import database.Users;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageobjects.LoginPage;
import utils.EncryptionUtils;

public class UiLoginSteps {

    private static final Logger LOG = LogManager.getLogger(UiLoginSteps.class);
    LoginPage loginPage = new LoginPage();
    UiSharedSteps sharedSteps = new UiSharedSteps();
    DbActions dbActions = new DbActions();
    Users user = new Users();

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
}
