package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactListPage {

    public ContactListPage(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "logout")
    private WebElement logout;

    @FindBy(id = "add-contact")
    private WebElement addContact;

    @FindBy(xpath = "//body/div[1]/div[1]/table[1]")
    private WebElement summaryTable;


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
}
