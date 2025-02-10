package stepdefinitions;

import api.ApiRequestMethods;
import database.Users;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

public class ApiSteps {

    private final Logger LOGGER = LogManager.getLogger(ApiSteps.class);
    ApiRequestMethods apiRequestMethods = new ApiRequestMethods();
    DbSteps dbSteps = new DbSteps();
    Users users = new Users();
    Response apiResponse;
    int statusCode;
    String token = "";

    @When("User sends authentication request providing valid email and password")
    public void authorizeUser() throws Exception {
        LOGGER.info("Attempting to authorize last registered user.");
        users = dbSteps.selectLastInsertedUser();
        String rawPassword = dbSteps.decryptAesKey(users.getPassword());
        LOGGER.info("Sending authentication request with the following credentials: {} and {}.", users.getEmail(), rawPassword);

        apiResponse = apiRequestMethods.authorizationRequest("users/login", users.getEmail(), rawPassword);
    }

    @When("User sends authentication request providing invalid {} email and {} password")
    public void authorizeUser(String email, String password) {
        LOGGER.info("Sending authentication request with the following invalid credentials: {} and {}.", email, password);

        apiResponse = apiRequestMethods.authorizationRequest("users/login", email, password);
    }

    @When("User does not send authentication request")
    public void doNotAuthUser() {
        LOGGER.info("User authentication is skipped.");
    }

    @And("Response returns status code {}")
    public void validateApiResponseStatusCode(int expectedStatusCode) {
        statusCode = apiResponse.getStatusCode();
        LOGGER.info("API response is returned with status code: {}", statusCode);

        Assert.assertEquals("Unexpected response status code", statusCode, expectedStatusCode);
    }

    public void retrieveAuthToken() {
        token = apiResponse.jsonPath().getString("token");
        LOGGER.info("Authorization token: {}", token);

        Assert.assertFalse("Token value is not returned in response", token.isEmpty());
    }

    @And("Response body contains {} message")
    public void validateApiResponseBody(String expectedErrorMessage) {
        String actualErrorMessage = apiResponse.jsonPath().getString("error");
        LOGGER.info("Validating API error message. Expected: '{}', Actual: '{}'", expectedErrorMessage, actualErrorMessage);
        Assert.assertEquals("Unexpected error message", "Please authenticate.", actualErrorMessage);
    }

    @When("User sends a delete user request")
    public void apiDeleteUser() {
        if (statusCode == 200 || statusCode == 201) {
            retrieveAuthToken();
            LOGGER.info("Sending authenticated delete user request.");
            apiResponse = apiRequestMethods.deleteUserRequest("users/me", token);
        } else {
            LOGGER.info("Sending delete user request without authentication.");
            apiResponse = apiRequestMethods.deleteUserRequest("users/me");
        }
        statusCode = apiResponse.getStatusCode();
    }
}

