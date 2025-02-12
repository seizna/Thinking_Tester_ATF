package pageobjects;

import driversetup.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddUserPage {

    public AddUserPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "submit")
    private static WebElement submitButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(id = "error")
    private WebElement validationMessage;


    public WebElement getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.sendKeys(firstName);
    }

    public WebElement getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.sendKeys(lastName);
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

    public WebElement getCancelButton() {
        return cancelButton;
    }

    public void clickCancelButton() {
        cancelButton.click();
    }

    public WebElement getValidationMessage() {
        return validationMessage;
    }

    public WebElement waitForValidationMessage() {
        return WebDriverManager.getWait().until(ExpectedConditions.visibilityOf(validationMessage));
    }

    public void registerUser(String firstName, String lastName, String email, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);

        clickSubmitButton();
    }
}

