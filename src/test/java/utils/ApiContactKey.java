package utils;

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
