package datafaker;

import net.datafaker.Faker;
import utils.ApiContactKey;

import java.util.HashMap;
import java.util.Map;

public class DataFaker {

    private static final Faker FAKER = new Faker();

    public static Map<String, String> getMockedContact() {
        Map<String, String> mockedContactDetails = new HashMap<>();
        for (ApiContactKey key : ApiContactKey.values()) {
            mockedContactDetails.put(key.getFieldName(), getFieldValue(key));
        }
        return mockedContactDetails;
    }

    private static String getFieldValue(ApiContactKey key) {
        switch (key) {
            case FIRST_NAME:
                return FAKER.name().firstName();
            case LAST_NAME:
                return FAKER.name().lastName();
            case BIRTHDATE:
                return FAKER.date().birthday(18, 90, "yyyy-MM-dd");
            case EMAIL:
                return FAKER.internet().emailAddress();
            case PHONE:
                return FAKER.numerify("##########");
            case STREET1:
                return FAKER.address().streetAddress();
            case STREET2:
                return FAKER.address().secondaryAddress();
            case CITY:
                return FAKER.address().city();
            case STATE_PROVINCE:
                return FAKER.address().stateAbbr();
            case POSTAL_CODE:
                return FAKER.address().zipCode();
            case COUNTRY:
                return FAKER.address().country();
            default:
                throw new IllegalArgumentException("Unknown field: " + key);
        }
    }
}
