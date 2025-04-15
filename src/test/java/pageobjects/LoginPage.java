package pageobjects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static driversetup.WebDriverManager.getDriver;
import static driversetup.WebDriverManager.getWait;

public class LoginPage {

    @FindBy(css = "h1")
    private WebElement header;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "signup")
    private WebElement signUpButton;

    @FindBy(id = "error")
    private WebElement validationMessage;

    public LoginPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public void setEmail(String email) {
        this.email.sendKeys(email);
    }

    public void setPassword(String password) {
        this.password.sendKeys(password);
    }

    public void clickSubmitButton() {
        this.submitButton.click();
    }

    public void clickSignUpButton() {
        this.signUpButton.click();
    }

    public boolean isValidationMessageDisplayed() {
        try {
            return getWait().until(ExpectedConditions.visibilityOf(validationMessage)).isDisplayed();
        } catch (TimeoutException | NoSuchElementException ex) {
            return false;
        }
    }

    public String getValidationMessageText() {
        if (isValidationMessageDisplayed()) {
            return validationMessage.getText();
        } else {
            return "";
        }
    }

    public boolean areAllLoginElementsDisplayed() {
        WebElement[] loginElements = {header, email, password, submitButton, signUpButton};
        for (WebElement loginElement : loginElements) {
            if (loginElement == null || !loginElement.isDisplayed()) {
                return false;
            }
        }
        return true;
    }

    public void loginUser(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickSubmitButton();
    }

    public void clearLoginForm(){
        email.clear();
        password.clear();
    }
}

