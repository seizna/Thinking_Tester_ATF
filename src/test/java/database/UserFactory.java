package database;

import scenariocontext.ContextKey;
import java.time.LocalDateTime;
import static scenariocontext.ScenarioContext.getContext;

public class UserFactory {

    public static Users createUser() {
        Users user = new Users();
        user.setFirstName(getContext(ContextKey.USER_FIRST_NAME));
        user.setLastName(getContext(ContextKey.USER_LAST_NAME));
        user.setEmail(getContext(ContextKey.USER_EMAIL));
        user.setPassword(getContext(ContextKey.USER_ENCRYPTED_PASSWORD));
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
}
