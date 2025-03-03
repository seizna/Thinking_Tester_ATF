package database;

import scenariocontext.ContextKey;
import java.time.LocalDateTime;
import static scenariocontext.ScenarioContext.getContext;

public class UserFactory {

    public static Users createUser() {
        Users user = new Users();
        user.setFirstName(getContext(ContextKey.FIRST_NAME).toString());
        user.setLastName(getContext(ContextKey.LAST_NAME).toString());
        user.setEmail(getContext(ContextKey.EMAIL).toString());
        user.setPassword(getContext(ContextKey.ENCRYPTED_PASSWORD).toString());
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
}
