package stepdefinitions;

import database.DbActions;
import database.UserFactory;
import database.Users;
import io.cucumber.java.en.And;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scenariocontext.ContextKey;

import static scenariocontext.ScenarioContext.getContext;

public class DbSteps {

    private static final Logger LOG = LogManager.getLogger(DbSteps.class);
    DbActions dbActions = new DbActions();

    @And("User with email under test is present in DB")
    public void insertRegisteredUser() {
        String email = getContext(ContextKey.USER_EMAIL);
        Users existingUser = dbActions.selectUserByEmail(email);

            if (existingUser != null) {
                LOG.error("User with email {} already exists in DB.", email);
            } else {
                Users newUser = UserFactory.createUser();
                dbActions.insertUser(newUser);
                LOG.info("User with email {} is successfully inserted in DB.", email);
            }
    }

    @And("User with email under test is removed from DB")
    public void deleteRegisteredUser() {
        Users lastInsertedUser = dbActions.selectLastInsertedUser();

        if (lastInsertedUser != null) {
            dbActions.removeUser(lastInsertedUser);
            LOG.info("User with email {} is successfully removed from DB.", lastInsertedUser.getEmail());
        }
    }
}