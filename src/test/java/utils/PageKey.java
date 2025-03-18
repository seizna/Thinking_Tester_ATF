package utils;
/**
 * The {@code PageKey} enum represents existing pages of the application under test.
 * It centralizes key properties for each page, including:
 * <ul>
 *   <li><strong>scenarioKey</strong>: An identifier used in BDD scenarios to reference the page.</li>
 *   <li><strong>pageTitle</strong>: The expected title of the page, which is used for validation.</li>
 *   <li><strong>pageUrlKey</strong>: The key used to retrieve the expected page URL from the config.properties file.</li>
 * </ul>
 * Due to application inconsistency, not all pages have a {@code pageTitle} defined in the DOM.
 * This enum allows dynamic verification of user redirect to the expected page based on either the
 * {@code pageTitle} or the {@code pageUrlKey}.
 */

public enum PageKey {
    LOGIN("Login", "Contact List App", "login.url"),
    ADD_USER("Add User", "Add User", "addUser.url"),
    CONTACT_LIST("Contact List", "My Contacts", "contactList.url"),
    ADD_CONTACT("Add Contact", "Add Contact", "addContact.url"),
    CONTACT_DETAILS("Contact Details", "", "contactDetails.url"),
    EDIT_CONTACT("Edit Contact", "", "editContact.url");

    private final String scenarioKey;
    private final String pageTitle;
    private final String pageUrlKey;


    PageKey(String scenarioKey, String pageTitle, String pageUrlKey) {
        this.scenarioKey = scenarioKey;
        this.pageTitle = pageTitle;
        this.pageUrlKey = pageUrlKey;
    }


    public String getScenarioKey() {
        return scenarioKey;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getPageUrlKey() {
        return pageUrlKey;
    }

    public static PageKey fromScenarioKey(String scenarioKey) {
        for (PageKey pageKey : values()) {
            if (pageKey.getScenarioKey().equals(scenarioKey)) {
                return pageKey;
            }
        }
        return null;
    }
}
