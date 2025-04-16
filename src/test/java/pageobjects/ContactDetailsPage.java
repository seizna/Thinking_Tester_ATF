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

    public ContactDetailsPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public void clickEditContactButton() {
        this.editContactButton.click();
    }

    public void clickDeleteContactButton() {
        this.deleteContactButton.click();
    }

    public void clickReturnToContactListButton() {
        this.returnToContactListButton.click();
    }

    public WebElement getContactDetailsForm() {
        return this.contactDetailsForm;
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
