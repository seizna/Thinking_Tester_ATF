package stepdefinitions;

import driversetup.WebDriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AddUserPage;
import pageobjects.LoginPage;
import scenariocontext.ContextKey;
import scenariocontext.ScenarioContext;

import static org.junit.Assert.assertTrue;

public class UiRegistrationSteps {

    private final WebDriver driver = WebDriverManager.getDriver();
    LoginPage loginPage = new LoginPage(driver);
    AddUserPage addUserPage = new AddUserPage(driver);

    @When("User registers with First Name: {}, Last Name: {}, Email: {} and Password: {}")
    public void registerNewUser(String firstName, String lastName, String email, String password) throws Exception {
        addUserPage.registerUser(firstName, lastName, email, password);

        ScenarioContext.setContext(ContextKey.FIRST_NAME, firstName);
        ScenarioContext.setContext(ContextKey.LAST_NAME, lastName);
        ScenarioContext.setContext(ContextKey.EMAIL, email);
        ScenarioContext.setContext(ContextKey.RAW_PASSWORD, password);
        ScenarioContext.setContext(ContextKey.ENCRYPTED_PASSWORD, DbSteps.encryptAesKey(password));
        System.setProperty("registeredUserEmail", email);
        System.setProperty("registeredUserPassword", password);
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
                throw new IllegalStateException("Unexpected value: " + pageName);
        }
        assertTrue("Validation message is not displayed", validationMessageDisplayed.isDisplayed());
        Assert.assertEquals("Unexpected validation message", validationMessage, validationMessageDisplayed.getText());
    }
}
