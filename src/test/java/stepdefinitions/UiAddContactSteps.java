package stepdefinitions;

import driversetup.WebDriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.AddEditContactPage;
import pageobjects.ContactListPage;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class UiAddContactSteps {

    private final Logger LOGGER = LogManager.getLogger(UiAddContactSteps.class);
    private final WebDriver DRIVER = WebDriverManager.getDriver();
    private final WebDriverWait WAIT = WebDriverManager.getWait();
    UiSharedSteps sharedSteps = new UiSharedSteps();
    UiLoginSteps loginSteps = new UiLoginSteps();
    ContactListPage contactListPage = new ContactListPage(DRIVER);
    AddEditContactPage addEditContactPage = new AddEditContactPage(DRIVER);


    @Given("User is on Add Contact page")
    public void accessAddContactPage() throws Exception {
        sharedSteps.navigateToLoginPage();
        loginSteps.loginUserWithValidCredentials();
        contactListPage.clickAddContactButton();
        sharedSteps.checkUserRedirectToExpectedPage("Add Contact");
        sharedSteps.checkUiElements("Add Contact");
        LOGGER.info("Add Contact page accessed.");
    }

    @When("User adds contact providing {}")
    public void provideValidContactInfo(String contactDetails) {
        String[] details = contactDetails.split(",", -1);

        String firstName = details[0].trim();
        String lastName = details[1].trim();
        String dateOfBirth = details[2].trim();
        String email = details[3].trim();
        String phone = details[4].trim();
        String streetAddr1 = details[5].trim();
        String streetAddr2 = details[6].trim();
        String city = details[7].trim();
        String stateOrProvince = details[8].trim();
        String postalCode = details[9].trim();
        String country = details[10].trim();

        boolean allOptionalFieldsEmpty = Arrays.stream(details, 2, details.length)
                .allMatch(String::isBlank);

        if (allOptionalFieldsEmpty) {
            LOGGER.info("Adding a contact providing required fields only.");
            addEditContactPage.addContact(firstName, lastName);
        } else {
            LOGGER.info("Adding a contact providing required and optional fields.");
            addEditContactPage.addContact(firstName, lastName, dateOfBirth, email, phone,
                    streetAddr1, streetAddr2, city, stateOrProvince,
                    postalCode, country);
        }
        LOGGER.info("Contact {} is successfully submitted.", firstName + " " + lastName);
    }

    @Then("{} is added to contacts summary table")
    public void checkCreatedContactInSummary(String name) {
        try {
            WAIT.until(ExpectedConditions.visibilityOf(contactListPage.getSummaryTable()));
        } catch (TimeoutException e) {
            LOGGER.error("Summary table is not visible.", e);
        }

        boolean contactFound = false;

        List<WebElement> rows = contactListPage.getSummaryTable().findElements(By.className("contactTableBodyRow"));
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.xpath(".//td"));
            WebElement column = columns.get(1);
            if (column.getText().equals(name)) {
                contactFound = true;
                break;
            }
        }
        assertTrue("Contact " + name + " is not displayed in the summary table", contactFound);
        LOGGER.info("New {} contact is displayed in contacts summary table.", name);
    }
}
