package scenariocontext;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private static final Map<ContextKey, Object> scenarioContext= new HashMap<>();

    public static Object getContext(ContextKey key){
        return scenarioContext.get(key);
    }

    public static void setContext(ContextKey key, Object value) {
        scenarioContext.put(key, value);
    }
}