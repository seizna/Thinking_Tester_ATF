package pageobjects;

import driversetup.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage {

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "submit")
    private static WebElement submitButton;

    @FindBy(id = "signup")
    private WebElement signUpButton;

    @FindBy(id = "error")
    private WebElement validationMessage;


    public WebElement getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.sendKeys(email);
    }

    public WebElement getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.sendKeys(password);
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public WebElement getSignUpButton() {
        return signUpButton;
    }

    public void signUpButton() {
        signUpButton.click();
    }

    public WebElement getValidationMessage() {
        return validationMessage;
    }

    public WebElement waitForValidationMessage() {
        return WebDriverManager.getWait().until(ExpectedConditions.visibilityOf(validationMessage));
    }
}

