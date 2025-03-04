package pageobjects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static driversetup.WebDriverManager.*;

public class LoginPage {

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


    public LoginPage() {
        PageFactory.initElements(getDriver(), this);
    }

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

    public void clickSignUpButton() {
        signUpButton.click();
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

        WebElement[] loginElements = new WebElement[4];
        loginElements[0] = getEmail();
        loginElements[1] = getPassword();
        loginElements[2] = getSubmitButton();
        loginElements[3] = getSignUpButton();

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
}

