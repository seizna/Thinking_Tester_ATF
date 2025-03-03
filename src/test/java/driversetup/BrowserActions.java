package driversetup;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static driversetup.WebDriverManager.*;

public class BrowserActions {

    public void openBrowser() {
        getDriver();
    }

    public String getPageTitle() {
        return getDriver().getTitle();
    }

    public void navigateTo(String url) {
        getDriver().navigate().to(url);
    }

    public void waitForPageToLoad(String expectedPageTitle) {
        getWait().until(ExpectedConditions.titleIs(expectedPageTitle));
    }

    public void waitForElement(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public void closeBrowser() {
        closeDriver();
    }
}
