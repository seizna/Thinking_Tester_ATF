package utils;

import scenariocontext.FormKey;
import scenariocontext.TableKey;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ContactHelper} class provides utility methods for parsing and converting
 * contact details.
 *
 * <p>This class includes the following methods:</p>
 *
 * <ul>
 *   <li>
 *     <strong>parseContactDetails</strong> - Parses a comma-separated string into a {@code Map} keyed by {@link FormKey}.
 *     <ul>
 *       <li><strong>Parameter:</strong> {@code contactDetails} - a comma-separated string of contact details from feature file.</li>
 *       <li><strong>Returns:</strong> a {@code parsedContact} map containing the parsed contact details.</li>
 *     </ul>
 *   </li>
 *   <li>
 *     <strong>convertContactFormToSummary</strong> - Converts parsed contact details into a summary map using {@link TableKey}.
 *     <ul>
 *       <li><strong>Parameter:</strong> {@code parsedContact} - a map of contact details keyed by {@link FormKey}.</li>
 *       <li><strong>Returns:</strong> a {@code contactMappingInSummary} map with contact details formatted for summary table, keyed by {@link TableKey}.</li>
 *     </ul>
 *   </li>
 * </ul>
 */


public class ContactHelper {

    public static final int FIRST_NAME_INDEX = 0;
    public static final int LAST_NAME_INDEX = 1;
    public static final int DATE_OF_BIRTH_INDEX = 2;
    public static final int EMAIL_INDEX = 3;
    public static final int PHONE_INDEX = 4;
    public static final int STREET_ADDR1_INDEX = 5;
    public static final int STREET_ADDR2_INDEX = 6;
    public static final int CITY_INDEX = 7;
    public static final int STATE_OR_PROVINCE_INDEX = 8;
    public static final int POSTAL_CODE_INDEX = 9;
    public static final int COUNTRY_INDEX = 10;

    public static Map<FormKey, String> parseContactDetails(String contactDetails) {
        String[] details = contactDetails.split(",", -1);

        String firstName = details[FIRST_NAME_INDEX].trim();
        String lastName = details[LAST_NAME_INDEX].trim();
        String dateOfBirth = details[DATE_OF_BIRTH_INDEX].trim();
        String email = details[EMAIL_INDEX].trim();
        String phone = details[PHONE_INDEX].trim();
        String streetAddr1 = details[STREET_ADDR1_INDEX].trim();
        String streetAddr2 = details[STREET_ADDR2_INDEX].trim();
        String city = details[CITY_INDEX].trim();
        String stateOrProvince = details[STATE_OR_PROVINCE_INDEX].trim();
        String postalCode = details[POSTAL_CODE_INDEX].trim();
        String country = details[COUNTRY_INDEX].trim();

        Map<FormKey, String> parsedContact = new HashMap<>();
        parsedContact.put(FormKey.CONTACT_FIRST_NAME, firstName);
        parsedContact.put(FormKey.CONTACT_LAST_NAME, lastName);
        parsedContact.put(FormKey.CONTACT_DATE_OF_BIRTH, dateOfBirth);
        parsedContact.put(FormKey.CONTACT_EMAIL, email);
        parsedContact.put(FormKey.CONTACT_PHONE, phone);
        parsedContact.put(FormKey.CONTACT_STREET_ADDR1, streetAddr1);
        parsedContact.put(FormKey.CONTACT_STREET_ADDR2, streetAddr2);
        parsedContact.put(FormKey.CONTACT_CITY, city);
        parsedContact.put(FormKey.CONTACT_STATE_OR_PROVINCE, stateOrProvince);
        parsedContact.put(FormKey.CONTACT_POSTAL_CODE, postalCode);
        parsedContact.put(FormKey.CONTACT_COUNTRY, country);

        return parsedContact;
    }


    public static Map<TableKey, String> convertContactFormToSummary(Map<FormKey, String> parsedContact) {
        Map<TableKey, String> contactMappingInSummary = new HashMap<>();

        String contactName = parsedContact.get(FormKey.CONTACT_FIRST_NAME) + " " + parsedContact.get(FormKey.CONTACT_LAST_NAME);
        String contactBirthdate = parsedContact.get(FormKey.CONTACT_DATE_OF_BIRTH);
        String contactEmail = parsedContact.get(FormKey.CONTACT_EMAIL);
        String contactPhone = parsedContact.get(FormKey.CONTACT_PHONE);
        String contactAddress = parsedContact.get(FormKey.CONTACT_STREET_ADDR1) + " " + parsedContact.get(FormKey.CONTACT_STREET_ADDR2);
        String contactCityStateZip = parsedContact.get(FormKey.CONTACT_CITY) + " " + parsedContact.get(FormKey.CONTACT_STATE_OR_PROVINCE) + " " + parsedContact.get(FormKey.CONTACT_POSTAL_CODE);
        String contactCountry = parsedContact.get(FormKey.CONTACT_COUNTRY);

        contactMappingInSummary.put(TableKey.NAME_COLUMN, contactName.trim());
        contactMappingInSummary.put(TableKey.BIRTHDATE_COLUMN, contactBirthdate);
        contactMappingInSummary.put(TableKey.EMAIL_COLUMN, contactEmail);
        contactMappingInSummary.put(TableKey.PHONE_COLUMN, contactPhone);
        contactMappingInSummary.put(TableKey.ADDRESS_COLUMN, contactAddress.trim());
        contactMappingInSummary.put(TableKey.CITY_STATE_ZIP_COLUMN, contactCityStateZip.trim());
        contactMappingInSummary.put(TableKey.COUNTRY_COLUMN, contactCountry);

        return contactMappingInSummary;
    }

    public static Map<String,String> extractUpdatedFieldsFromResponseBody(Map<String, String> fieldsToUpdate, Map<String, String> responseBody){
        Map<String, String> convertedResponseBody = new HashMap<>();

        for (Map.Entry<String, String> entry : fieldsToUpdate.entrySet()) {
            String fieldsToUpdateKey = entry.getKey();
            String apiResponseValue = responseBody.get(fieldsToUpdateKey);
            convertedResponseBody.put(fieldsToUpdateKey, apiResponseValue);
        }

        return convertedResponseBody;
    }
}