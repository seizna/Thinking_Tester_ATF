package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static driversetup.WebDriverManager.getDriver;
import static driversetup.WebDriverManager.getWait;

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

    public WebElement getSummaryTable() {
        try {
            return getDriver().findElement(By.id("myTable"));
        } catch (NoSuchElementException ex) {
            return summaryTable;
        }
    }

    public void clickAddContactButton() {
        addContactButton.click();
    }

    public void clickLogoutButton() {
        logoutButton.click();
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

    public boolean isSummaryTableDisplayed() {
        try {
            return getWait().until(ExpectedConditions.visibilityOf(summaryTable)).isDisplayed();
        } catch (TimeoutException | NoSuchElementException ex) {
            return false;
        }
    }

    public boolean hasAtLeastOneContact() {
        if (isSummaryTableDisplayed()) {
            try {
                return !getSummaryTable().findElements(By.xpath(".//td")).isEmpty();
            } catch (NoSuchElementException ex) {
                return false;
            }
        }
        return false;
    }

    public void selectExistingContact() {
        try {
            List<WebElement> rows = getSummaryTable().findElements(By.className("contactTableBodyRow"));
            if (!rows.isEmpty()) {
                rows.getFirst().click();
            }
        } catch (NoSuchElementException ex) {
            ex.getMessage();
        }
    }

    public boolean isSpecificContactDisplayed(String contactName) {
        if (isSummaryTableDisplayed()) {
            try {
                getSummaryTable().findElement(By.xpath(".//td[contains(text(),'" + contactName + "')]"));
                return true;
            } catch (NoSuchElementException ex) {
                return false;
            }
        }
        return false;
    }
}
