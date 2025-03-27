package scenariocontext;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private static ScenarioContext instance;
    private final Map<ContextKey, String> scenarioContext;
    private final Map<FormKey, String> contact;

    private ScenarioContext() {
        scenarioContext = new HashMap<>();
        contact = new HashMap<>();
    }

    public static ScenarioContext getInstance() {
        if (instance == null) {
            instance = new ScenarioContext();
        }
        return instance;
    }

    public static void setContext(ContextKey key, String value) {
        getInstance().scenarioContext.put(key, value);
    }

    public static String getContext(ContextKey key) {
        return getInstance().scenarioContext.get(key);
    }

    public static void saveContact(Map<FormKey, String> parsedContact) {
        getInstance().contact.clear();
        getInstance().contact.putAll(parsedContact);
    }

    public static Map<FormKey, String> getContact() {
        return getInstance().contact;
    }
}