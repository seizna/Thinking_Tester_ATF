package scenariocontext;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private static ScenarioContext instance;
    private final Map<ParentKey, String> globalContext;

    private ScenarioContext() {
        globalContext = new HashMap<>();
    }

    public static ScenarioContext getInstance() {
        if (instance == null) {
            instance = new ScenarioContext();
        }
        return instance;
    }

    public static void setContext(ContextKey key, String value) {
        getInstance().globalContext.put(key, value);
    }

    public static String getContext(ContextKey key) {
        return getInstance().globalContext.get(key);
    }


    public static void saveContact(Map<FormKey, String> parsedContact) {
        for (FormKey value : FormKey.values()) {
            getInstance().globalContext.remove(value);
        }
        getInstance().globalContext.putAll(parsedContact);
    }

    public static Map<FormKey, String> getContact() {
        Map<ParentKey, String> globalContext = getInstance().globalContext;
        Map<FormKey, String> parsedContact = new HashMap<>();

        for (FormKey value : FormKey.values()) {
            parsedContact.put(value, globalContext.get(value));
        }
        return parsedContact;
    }
}
