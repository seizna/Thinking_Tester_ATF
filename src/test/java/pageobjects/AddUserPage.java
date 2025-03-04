package pageobjects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static driversetup.WebDriverManager.getDriver;
import static driversetup.WebDriverManager.getWait;

public class AddUserPage {

    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(id = "error")
    private WebElement validationMessage;

    public AddUserPage() {
        PageFactory.initElements(getDriver(), this);
    }


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

    public boolean areAllAddUserElementsDisplayed() {

        WebElement[] addUserElements = new WebElement[6];
        addUserElements[0] = getFirstName();
        addUserElements[1] = getLastName();
        addUserElements[2] = getEmail();
        addUserElements[3] = getPassword();
        addUserElements[4] = getSubmitButton();
        addUserElements[5] = getCancelButton();

        for (WebElement addUserElement : addUserElements) {
            if (addUserElement == null || !addUserElement.isDisplayed()) {
                return false;
            }
        }
        return true;
    }

    public void registerUser(String firstName, String lastName, String email, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);
        clickSubmitButton();
    }
}

