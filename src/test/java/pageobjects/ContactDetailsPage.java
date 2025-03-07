package pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static driversetup.WebDriverManager.getDriver;

public class ContactDetailsPage {

    @FindBy(css = "header h1")
    private WebElement header;

    @FindBy(id = "edit-contact")
    private WebElement editContactButton;

    @FindBy(id = "delete")
    private WebElement deleteContactButton;

    @FindBy(id = "return")
    private WebElement returnToContactListButton;

    @FindBy(id = "logout")
    private WebElement logoutButton;

    @FindBy(id = "contactDetails")
    private WebElement contactDetailsForm;

    @FindBy(id = "firstName")
    private WebElement firstName;

    @FindBy(id = "lastName")
    private WebElement lastName;


    public ContactDetailsPage() {
        PageFactory.initElements(getDriver(), this);
    }


    public boolean isHeaderDisplayed() {
        return header.isDisplayed();
    }

    public String getHeaderText() {
        if (isHeaderDisplayed()) {
            return header.getText();
        } else {
            return "";
        }
    }

    public void clickEditContactButton() {
        editContactButton.click();
    }

    public void clickDeleteContactButton() {
        deleteContactButton.click();
    }

    public void clickReturnToContactListButton() {
        returnToContactListButton.click();
    }

    public void clickLogoutButton() {
        logoutButton.click();
    }

    public boolean areAllContactDetailsElementsDisplayed() {
        WebElement[] contactDetailsElements = {header, editContactButton, deleteContactButton, returnToContactListButton, logoutButton, contactDetailsForm};
        for (WebElement contactDetailsElement : contactDetailsElements) {
            if (contactDetailsElement == null || !contactDetailsElement.isDisplayed()) {
                return false;
            }
        }
        return true;
    }
}
