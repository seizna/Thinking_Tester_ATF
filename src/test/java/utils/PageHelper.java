package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import scenariocontext.FormKey;
import scenariocontext.TableKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code PageHelper} class provides utility methods for parsing contact details from summary table.
 *
 * <p>This class includes the following methods:</p>
 *
 * <ul>
 *   <li>
 *     <strong>parseExistingContacts</strong> - Parses all existing contacts from summary table.
 *     <ul>
 *       <li><strong>Parameter:</strong> {@code summaryTable} - a {@code WebElement} representing the contact summary table.</li>
 *       <li><strong>Returns:</strong> a {@code existingContactsInSummary} list of maps where each map contains an existing contact details keyed by {@link TableKey}.</li>
 *     </ul>
 *   </li>
 *   <li>
 *     <strong>parseContactDetailsForm</strong> - Parses form on Contact Details page and returns a map of contact details.
 *     <ul>
 *       <li><strong>Parameter:</strong> {@code contactDetailsForm} - a {@code WebElement} representing the contact details form.</li>
 *       <li><strong>Returns:</strong> a {@code contactDetails} map containing existing contact details keyed by {@link FormKey}.</li>
 *     </ul>
 *   </li>
 * </ul>
 */

public class PageHelper {

    public static List<Map<TableKey, String>> parseExistingContacts(WebElement summaryTable) {
        List<Map<TableKey, String>> existingContactsInSummary = new ArrayList<>();

        List<WebElement> rows = summaryTable.findElements(By.tagName("tr"));
        for (int row = 1; row < rows.size(); row++) {
            WebElement contactRow = rows.get(row);

            List<WebElement> cells = contactRow.findElements(By.tagName("td"));

            Map<TableKey, String> existingContact = new HashMap<>();
            existingContact.put(TableKey.NAME_COLUMN, cells.get(1).getText());
            existingContact.put(TableKey.BIRTHDATE_COLUMN, cells.get(2).getText());
            existingContact.put(TableKey.EMAIL_COLUMN, cells.get(3).getText());
            existingContact.put(TableKey.PHONE_COLUMN, cells.get(4).getText());
            existingContact.put(TableKey.ADDRESS_COLUMN, cells.get(5).getText());
            existingContact.put(TableKey.CITY_STATE_ZIP_COLUMN, cells.get(6).getText());
            existingContact.put(TableKey.COUNTRY_COLUMN, cells.get(7).getText());

            existingContactsInSummary.add(existingContact);
        }
        return existingContactsInSummary;
    }

    public static Map<FormKey, String> parseContactDetailsForm(WebElement contactDetailsForm) {
        List<WebElement> listOfFields = contactDetailsForm.findElements(By.tagName("span"));

        Map<FormKey, String> contactDetails = new HashMap<>();
        contactDetails.put(FormKey.CONTACT_FIRST_NAME, listOfFields.get(0).getText());
        contactDetails.put(FormKey.CONTACT_LAST_NAME, listOfFields.get(1).getText());
        contactDetails.put(FormKey.CONTACT_DATE_OF_BIRTH, listOfFields.get(2).getText());
        contactDetails.put(FormKey.CONTACT_EMAIL, listOfFields.get(3).getText());
        contactDetails.put(FormKey.CONTACT_PHONE, listOfFields.get(4).getText());
        contactDetails.put(FormKey.CONTACT_STREET_ADDR1, listOfFields.get(5).getText());
        contactDetails.put(FormKey.CONTACT_STREET_ADDR2, listOfFields.get(6).getText());
        contactDetails.put(FormKey.CONTACT_CITY, listOfFields.get(7).getText());
        contactDetails.put(FormKey.CONTACT_STATE_OR_PROVINCE, listOfFields.get(8).getText());
        contactDetails.put(FormKey.CONTACT_POSTAL_CODE, listOfFields.get(9).getText());
        contactDetails.put(FormKey.CONTACT_COUNTRY, listOfFields.get(10).getText());

        return contactDetails;
    }
}
