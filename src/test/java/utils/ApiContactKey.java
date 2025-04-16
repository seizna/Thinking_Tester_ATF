package utils;

/**
 * The {@code ApiContactKey} enum holds keys for contact details used in API interactions.
 *
 * <p>Each enum constant represents a specific contact field and provides a corresponding field name as a string.</p>
 *
 * <p>Use {@link #getFieldName()} to retrieve the string representation of the contact field.</p>
 */

public enum ApiContactKey {

    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    BIRTHDATE("birthdate"),
    EMAIL("email"),
    PHONE("phone"),
    STREET1("street1"),
    STREET2("street2"),
    CITY("city"),
    STATE_PROVINCE("stateProvince"),
    POSTAL_CODE("postalCode"),
    COUNTRY("country");

    private final String fieldName;

    ApiContactKey(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
