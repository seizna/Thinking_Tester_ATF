package pageobjects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static driversetup.WebDriverManager.*;

public class AddEditContactPage {

    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(xpath = "//*[@id=\"add-contact\"]/p[1]/label[1]")
    private WebElement firstNameAsterisk;

    @FindBy(id = "lastName")
    private WebElement lastName;

    @FindBy(xpath = "//*[@id=\"add-contact\"]/p[1]/label[2]")
    private WebElement lastNameAsterisk;

    @FindBy(id = "birthdate")
    private WebElement dateOfBirth;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "phone")
    private WebElement phone;

    @FindBy(id = "street1")
    private WebElement streetAddr1;

    @FindBy(id = "street2")
    private WebElement streetAddr2;

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

    @FindBy(id = "error")
    private WebElement validationMessage;


    public AddEditContactPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public WebElement getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.sendKeys(firstName);
    }

    public void clearFirstName() {
        this.firstName.clear();
    }

    public WebElement getFirstNameAsterisk() {
        return firstNameAsterisk;
    }

    public WebElement getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.sendKeys(lastName);
    }

    public void clearLastName() {
        this.lastName.clear();
    }

    public WebElement getLastNameAsterisk() {
        return lastNameAsterisk;
    }

    public WebElement getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.sendKeys(dateOfBirth);
    }

    public WebElement getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.sendKeys(email);
    }

    public WebElement getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.sendKeys(phone);
    }

    public WebElement getStreetAddr1() {
        return streetAddr1;
    }

    public void setStreetAddr1(String streetAddr1) {
        this.streetAddr1.sendKeys(streetAddr1);
    }

    public WebElement getStreetAddr2() {
        return streetAddr2;
    }

    public void setStreetAddr2(String streetAddr2) {
        this.streetAddr2.sendKeys(streetAddr2);
    }

    public WebElement getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city.sendKeys(city);
    }

    public WebElement getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince.sendKeys(stateOrProvince);
    }

    public WebElement getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.sendKeys(postalCode);
    }

    public WebElement getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country.sendKeys(country);
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

    public boolean areAllContactElementsDisplayed() {

        WebElement[] contactElements = new WebElement[15];
        contactElements[0] = getFirstName();
        contactElements[1] = getFirstNameAsterisk();
        contactElements[2] = getLastName();
        contactElements[3] = getLastNameAsterisk();
        contactElements[4] = getDateOfBirth();
        contactElements[5] = getEmail();
        contactElements[6] = getPhone();
        contactElements[7] = getStreetAddr1();
        contactElements[8] = getStreetAddr2();
        contactElements[9] = getCity();
        contactElements[10] = getStateOrProvince();
        contactElements[11] = getPostalCode();
        contactElements[12] = getCountry();
        contactElements[13] = getSubmitButton();
        contactElements[14] = getCancelButton();

        for (WebElement contactElement : contactElements) {
            if (contactElement == null || !contactElement.isDisplayed()) {
                return false;
            }
        }
        return true;
    }

    public void addContact(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
        clickSubmitButton();
    }

    public void addContact(String firstName, String lastName, String dateOfBirth, String email, String phone,
                           String streetAddr1, String streetAddr2, String city, String stateOrProvince,
                           String postalCode, String country) {

        setFirstName(firstName);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);
        setEmail(email);
        setPhone(phone);
        setStreetAddr1(streetAddr1);
        setStreetAddr2(streetAddr2);
        setCity(city);
        setStateOrProvince(stateOrProvince);
        setPostalCode(postalCode);
        setCountry(country);
        clickSubmitButton();
    }

}