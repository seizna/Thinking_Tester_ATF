package pageobjects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static driversetup.WebDriverManager.getDriver;
import static driversetup.WebDriverManager.getWait;

public class AddEditContactPage {

    @FindBy(css = "header h1")
    private WebElement header;

    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(css = "label[for='firstName']")
    private WebElement firstNameAsterisk;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(css = "label[for='lastName']")
    private WebElement lastNameAsterisk;

    @FindBy(id = "birthdate")
    private WebElement dateOfBirth;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "phone")
    private WebElement phone;

    @FindBy(id = "street1")
    private WebElement address1;

    @FindBy(id = "street2")
    private WebElement address2;

    @FindBy(id = "city")
    private WebElement city;

    @FindBy(id = "stateProvince")
    private WebElement stateOrProvince;

    @FindBy(id = "postalCode")
    private WebElement postalCode;

    @FindBy(id = "country")
    private WebElement country;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(id = "logout")
    private WebElement logoutButton;

    @FindBy(id = "error")
    private WebElement validationMessage;


    public AddEditContactPage() {
        PageFactory.initElements(getDriver(), this);
    }


    private void clearAndSet(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    public void setFirstName(String firstName) {
        clearAndSet(this.firstName, firstName);
    }

    public void setLastName(String lastName) {
        clearAndSet(this.lastName, lastName);
    }

    public void setDateOfBirth(String dateOfBirth) {
        clearAndSet(this.dateOfBirth, dateOfBirth);
    }

    public void setEmail(String email) {
        clearAndSet(this.email, email);
    }

    public void setPhone(String phone) {
        clearAndSet(this.phone, phone);
    }

    public void setAddress1(String address1) {
        clearAndSet(this.address1, address1);
    }

    public void setAddress2(String address2) {
        clearAndSet(this.address2, address2);
    }

    public void setCity(String city) {
        clearAndSet(this.city, city);
    }

    public void setStateOrProvince(String stateOrProvince) {
        clearAndSet(this.stateOrProvince, stateOrProvince);
    }

    public void setPostalCode(String postalCode) {
        clearAndSet(this.postalCode, postalCode);
    }

    public void setCountry(String country) {
        clearAndSet(this.country, country);
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public void clickCancelButton() {
        cancelButton.click();
    }

    public void clickLogoutButton() {
        logoutButton.click();
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

    public boolean areAllContactElementsDisplayed() {
        WebElement[] contactElements = {header, firstName, firstNameAsterisk, lastName, lastNameAsterisk, dateOfBirth, email, phone,
                address1, address2, city, stateOrProvince, postalCode, country, submitButton, cancelButton, logoutButton};
        for (WebElement contactElement : contactElements) {
            if (contactElement == null || !contactElement.isDisplayed()) {
                return false;
            }
        }
        return true;
    }

    public void addEditContact(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
        clickSubmitButton();
    }

    public void addEditContact(String firstName, String lastName, String dateOfBirth, String email, String phone,
                               String streetAddr1, String streetAddr2, String city, String stateOrProvince,
                               String postalCode, String country) {

        setFirstName(firstName);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);
        setEmail(email);
        setPhone(phone);
        setAddress1(streetAddr1);
        setAddress2(streetAddr2);
        setCity(city);
        setStateOrProvince(stateOrProvince);
        setPostalCode(postalCode);
        setCountry(country);
        clickSubmitButton();
    }
}