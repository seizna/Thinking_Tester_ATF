package scenariocontext;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private static final Map<ContextKey, String> SCENARIO_CONTEXT = new HashMap<>();
    private static final Map<FormKey, String> CONTACT = new HashMap<>();

    public static void setContext(ContextKey key, String value) {
        SCENARIO_CONTEXT.put(key, value);
    }

    public static String getContext(ContextKey key) {
        return SCENARIO_CONTEXT.get(key);
    }

    public static void saveContact(Map<FormKey, String> parsedContact) {
        CONTACT.clear();
        CONTACT.putAll(parsedContact);
    }

    public static Map<FormKey, String> getContact() {
        return CONTACT;
    }
}