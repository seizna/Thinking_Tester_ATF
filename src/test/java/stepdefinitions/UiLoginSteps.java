package stepdefinitions;

import database.Users;
import driversetup.WebDriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pageobjects.LoginPage;

public class UiLoginSteps {

    private final Logger LOGGER = LogManager.getLogger(UiLoginSteps.class);
    private final WebDriver DRIVER = WebDriverManager.getDriver();
    LoginPage loginPage = new LoginPage(DRIVER);
    DbSteps dbSteps = new DbSteps();
    Users users = new Users();


    @When("User logs in with valid email and password")
    public void loginUserWithValidCredentials() throws Exception {
        LOGGER.info("Attempting to login last registered user.");
        users = dbSteps.selectLastInsertedUser();
        String rawPassword = dbSteps.decryptAesKey(users.getPassword());
        LOGGER.info("Logging user with the following credentials: {} and {}", users.getEmail(), rawPassword);
        loginPage.loginUser(users.getEmail(), rawPassword);
    }

    @When("User attempts login with invalid {} and {}")
    public void loginUserWithInvalidCredentials(String email, String password) {
        LOGGER.info("Attempting to login user with invalid data.");
        loginPage.loginUser(email, password);
        LOGGER.error("User login failed");
    }
}
