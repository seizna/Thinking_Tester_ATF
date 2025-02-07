package scenariocontext;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private static final Map<ContextKey, Object> SCENARIO_CONTEXT = new HashMap<>();

    public static Object getContext(ContextKey key){
        return SCENARIO_CONTEXT.get(key);
    }

    public static void setContext(ContextKey key, Object value) {
        SCENARIO_CONTEXT.put(key, value);
    }
}