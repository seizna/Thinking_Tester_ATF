package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import scenariocontext.FormKey;
import scenariocontext.TableKey;
import utils.PageHelper;

import java.util.List;
import java.util.Map;

import static driversetup.WebDriverManager.getDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static utils.ContactHelper.convertContactFormToSummary;

public class ContactListPage {

    @FindBy(css = "header h1")
    private WebElement header;

    @FindBy(className = "contactTable")
    private WebElement summaryTable;

    @FindBy(id = "add-contact")
    private WebElement addContactButton;

    @FindBy(id = "logout")
    private WebElement logoutButton;

    public ContactListPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public void clickAddContactButton() {
        this.addContactButton.click();
    }

    public boolean areAllContactListElementsDisplayed() {
        WebElement[] contactListElements = {header, logoutButton, addContactButton, summaryTable};
        for (WebElement contactListElement : contactListElements) {
            if (contactListElement == null || !contactListElement.isDisplayed()) {
                return false;
            }
        }
        return true;
    }

    public void selectExistingContact() {
        if (summaryTable != null) {
            List<WebElement> rows = summaryTable.findElements(By.className("contactTableBodyRow"));

            if (!rows.isEmpty()) {
                rows.getFirst().click();
            }
        }
    }

    public void isSpecificContactDisplayed(Map<FormKey, String> existingContact) {
        if (summaryTable != null) {
            List<Map<TableKey, String>> existingContactsInSummary = PageHelper.parseExistingContacts(summaryTable);
            assertThat("Contact is not displayed in contacts summary table.", existingContactsInSummary, hasItem(convertContactFormToSummary(existingContact)));
        }
    }

    public void isDeletedContactMissingInTable(Map<FormKey, String> existingContact) {
        if (summaryTable != null) {
            List<Map<TableKey, String>> existingContactsInSummary = PageHelper.parseExistingContacts(summaryTable);
            assertThat("Contact is still displayed in contacts summary table.", existingContactsInSummary, not(hasItem(convertContactFormToSummary(existingContact))));
        }
    }
}

