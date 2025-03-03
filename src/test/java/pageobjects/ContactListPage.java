package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static driversetup.WebDriverManager.getDriver;

public class ContactListPage {

    @FindBy(id = "logout")
    private WebElement logout;

    @FindBy(id = "add-contact")
    private WebElement addContact;

    @FindBy(className = "contactTable")
    private WebElement summaryTable;

    public ContactListPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public WebElement getLogoutButton() {
        return logout;
    }

    public void clickLogoutButton() {
        logout.click();
    }

    public WebElement getAddContactButton() {
        return addContact;
    }

    public void clickAddContactButton() {
        addContact.click();
    }

    public WebElement getSummaryTable() {
        return summaryTable;
    }

    public boolean areAllContactListElementsDisplayed() {

        WebElement[] contactListElements = new WebElement[3];
        contactListElements[0] = getLogoutButton();
        contactListElements[1] = getAddContactButton();
        contactListElements[2] = getSummaryTable();

        for (WebElement contactListElement : contactListElements) {
            if (contactListElement == null || !contactListElement.isDisplayed()) {
                return false;
            }
        }
        return true;
    }

    public boolean isContactDisplayed(String contactName) {
        try {
            getSummaryTable().findElement(By.xpath(".//td[contains(text(),'" + contactName + "')]"));
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}
