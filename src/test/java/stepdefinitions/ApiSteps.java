package stepdefinitions;

import api.ApiRequestMethods;
import database.Users;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import static org.junit.Assert.assertFalse;

public class ApiSteps {

    private final Logger LOGGER = LogManager.getLogger(ApiSteps.class);
    ApiRequestMethods apiRequestMethods = new ApiRequestMethods();
    DbSteps dbSteps = new DbSteps();
    Users users = new Users();
    Response apiResponse;
    int statusCode;
    String responseBody;
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

    @And("Response returns status code {}")
    public void validateApiResponseStatusCode(int expectedStatusCode) {

        statusCode = apiResponse.getStatusCode();
        LOGGER.info("API response is returned with status code: {}", statusCode);

        Assert.assertEquals("Unexpected response status code", statusCode, expectedStatusCode);


        if (expectedStatusCode == 200) {
            LOGGER.info("Validating response body.");
            validateApiResponseBody(apiResponse);
        } else {
            LOGGER.warn("Response body is empty.");
        }
    }

    public void validateApiResponseBody(Response apiResponse) {
        responseBody = apiResponse.getBody().asString().trim();

        if (responseBody.isEmpty()) {
            LOGGER.warn("Response body is empty, skipping JSON validation.");
        } else {
            if (apiResponse.getContentType().contains("application/json")) {
                try {
                    token = apiResponse.jsonPath().getString("token");
                    LOGGER.info("Authorization token: {}", token);
                    if (token != null && !token.isEmpty()) {
                        LOGGER.debug("Token is present and validated.");
                        assertFalse("Token value is not empty", token.isEmpty());
                    } else {
                        LOGGER.error("Token value is empty or null.");
                    }
                } catch (AssertionError e) {
                    LOGGER.error("Failed to parse the JSON response: {}", e.getMessage());
                    Assert.fail();
                }
            } else {
                LOGGER.error("Expected JSON response but received: {}", apiResponse.getContentType());
                Assert.fail("Expected JSON response but received: " + apiResponse.getContentType());
            }
        }
    }
}

