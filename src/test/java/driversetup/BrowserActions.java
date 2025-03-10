package driversetup;

import org.openqa.selenium.support.ui.ExpectedConditions;
import static driversetup.WebDriverManager.*;

public class BrowserActions {

    public void openBrowser() {
        getDriver();
    }

    public void navigateTo(String url) {
        getDriver().navigate().to(url);
    }

    public String getPageTitle() {
        return getDriver().getTitle();
    }

    public void waitForExpectedPageTitle(String expectedPageTitle) {
        getWait().until(ExpectedConditions.titleIs(expectedPageTitle));
    }

    public String getPageUrl() {
        return getDriver().getCurrentUrl();
    }

    public void waitForExpectedPageUrl(String expectedPageUrl) {
        getWait().until(ExpectedConditions.urlToBe(expectedPageUrl));
    }

    public void closeBrowser() {
        closeDriver();
    }
}
