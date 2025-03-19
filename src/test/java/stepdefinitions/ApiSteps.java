package stepdefinitions;

import api.ApiRequestMethods;
import database.DbActions;
import database.Users;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import utils.ConfigReader;
import utils.EncryptionUtils;

public class ApiSteps {

    private final Logger LOG = LogManager.getLogger(ApiSteps.class);
    ApiRequestMethods apiRequestMethods = new ApiRequestMethods();
    DbActions dbActions = new DbActions();
    Users user = new Users();
    private Response apiResponse;
    private int actualStatusCode;
    private String token = "";

    @When("User sends authentication request providing valid email and password")
    public void authorizeUser() throws Exception {
        LOG.info("Attempting to authorize registered user.");
        user = dbActions.selectLastInsertedUser();
        LOG.info("User with the following email {} is retrieved from DB.", user.getEmail());
        String rawPassword = EncryptionUtils.decryptAesKey(user.getPassword());

        LOG.info("Sending authentication request with the following credentials: {} and {}.", user.getEmail(), rawPassword);
        apiResponse = apiRequestMethods.authorizationRequest(ConfigReader.getProperty("auth.endpoint"), user.getEmail(), rawPassword);
    }

    @When("User sends authentication request providing invalid {} email and {} password")
    public void authorizeUser(String email, String password) {
        LOG.info("Sending authentication request with the following invalid credentials: {} and {}.", email, password);
        apiResponse = apiRequestMethods.authorizationRequest(ConfigReader.getProperty("auth.endpoint"), email, password);
    }

    @When("User does not send authentication request")
    public void doNotAuthUser() {
        LOG.info("User authentication is skipped.");
    }

    @And("Response returns status code {}")
    public void validateApiResponseStatusCode(int expectedStatusCode) {
        actualStatusCode = apiResponse.getStatusCode();
        LOG.info("API response is returned with status code: {}", actualStatusCode);

        Assert.assertEquals("Unexpected response status code", expectedStatusCode, actualStatusCode);
    }


    @And("Response body contains {} message")
    public void validateApiResponseBody(String expectedErrorMessage) {
        String actualErrorMessage = apiResponse.jsonPath().getString("error");

        if (actualErrorMessage == null) {
            LOG.error("'error' field is missing is response body.");
            Assert.fail("API response does not contain 'error' key.");
        }

        Assert.assertEquals("Unexpected error message.", expectedErrorMessage, actualErrorMessage);
        LOG.info("Response body contains expected '{}' message.", expectedErrorMessage);
    }

    @When("User sends a delete user request")
    public void apiDeleteUser() {
        if (actualStatusCode == 200 || actualStatusCode == 201) {
            retrieveAuthToken();
            LOG.info("Sending authenticated delete user request.");
            apiResponse = apiRequestMethods.deleteUserRequest(ConfigReader.getProperty("deleteUser.endpoint"), token);
        } else {
            LOG.info("Sending delete user request without authentication.");
            apiResponse = apiRequestMethods.deleteUserRequest(ConfigReader.getProperty("deleteUser.endpoint"));
        }
    }

    private void retrieveAuthToken() {
        token = apiResponse.jsonPath().getString("token");
        LOG.info("Authorization token retrieved: {}", token);

        Assert.assertFalse("Token value is not returned in response", token.isEmpty());
    }
}

