package stepdefinitions;

import api.ApiRequestMethods;
import database.DbActions;
import database.Users;
import datafaker.DataFaker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigReader;
import utils.ContactHelper;
import utils.EncryptionUtils;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class ApiSteps {

    private static final Logger LOG = LogManager.getLogger(ApiSteps.class);
    ApiRequestMethods apiRequestMethods = new ApiRequestMethods();
    DbActions dbActions = new DbActions();
    Users user = new Users();
    private Response apiResponse;
    private int actualStatusCode;
    private String token = "";
    Map<String, Object> contactUnderTest;
    Map<String, String> fieldsToUpdate;

    @When("User sends authentication request providing valid email and password")
    public void authorizeUser() throws Exception {
        LOG.info("Attempting to authorize registered user.");

        user = dbActions.selectLastInsertedUser();
        String rawPassword = EncryptionUtils.decryptAesKey(user.getPassword());
        LOG.info("User with the following email {} is retrieved from DB.", user.getEmail());

        LOG.info("Sending authentication request with the following credentials: {} and {}.", user.getEmail(), rawPassword);
        apiResponse = apiRequestMethods.authorizationRequest(ConfigReader.getProperty("auth.endpoint"), user.getEmail(), rawPassword);

        actualStatusCode = apiResponse.getStatusCode();
        if (actualStatusCode == 200) {
            token = apiResponse.jsonPath().getString("token");
            LOG.info("Authentication response received with status code: {}. Authorization token retrieved: {}", actualStatusCode, token);
        } else {
            LOG.info("Authentication response received with status code: {}.", actualStatusCode);
        }
    }

    @And("Response body contains user's email")
    public void validateUserAuthResponse() {
        String expectedUserEmail = user.getEmail();
        String actualUserEmail = apiResponse.jsonPath().getString("user.email");
        assertEquals("User email from authentication response does not match request value. ", expectedUserEmail, actualUserEmail);
    }

    @When("User sends authentication request providing invalid {} email and {} password")
    public void authorizeUser(String email, String password) {
        LOG.info("Sending authentication request with the following invalid credentials: {} and {}.", email, password);
        apiResponse = apiRequestMethods.authorizationRequest(ConfigReader.getProperty("auth.endpoint"), email, password);

        actualStatusCode = apiResponse.getStatusCode();
        LOG.info("Authentication response received with status code: {}", actualStatusCode);
    }

    @When("User does not send authentication request")
    public void skipUserAuthorization() {
        LOG.info("User authentication is skipped.");
    }

    @And("Contact's under test ID is retrieved from Contact List")
    public void retrieveContactIdFromList() {
        LOG.info("Retrieving the list of contacts.");
        apiResponse = apiRequestMethods.getRequest(ConfigReader.getProperty("getContactList.endpoint"), token);

        List<Map<String, Object>> contactList = apiResponse.jsonPath().getList("$");
        if (contactList.isEmpty()) {
            LOG.error("Response returned an empty contact list.");
        } else {
            LOG.info("Contact list obtained. Retrieving contact under test.");
        }

        contactUnderTest = contactList.getFirst();
        LOG.info("Contact with the following ID is retrieved: '{}'", contactUnderTest.get("_id"));
    }

    @When("User sends a patch contact request providing any valid info")
    public void patchContact() {
        fieldsToUpdate = DataFaker.getMockedContact();
        String patchEndpoint = ConfigReader.getProperty("patchContact.endpoint") + contactUnderTest.get("_id");
        LOG.info("Sending patch contact request to '{}' endpoint with the following body: {}", patchEndpoint, fieldsToUpdate);
        apiResponse = apiRequestMethods.patchRequest(patchEndpoint, fieldsToUpdate, token);
        actualStatusCode = apiResponse.getStatusCode();
        LOG.info("Patch contact response received with status code: {}.", actualStatusCode);
    }

    @And("Response body contains updated contact info")
    public void validatePatchContactResponse() {
        Map<String, String> responseBody = apiResponse.jsonPath().getMap("$");
        Map<String, String> convertedResponseBody = ContactHelper.extractUpdatedFieldsFromResponseBody(fieldsToUpdate, responseBody);
        assertThat("Mismatch in updated contact fields.", convertedResponseBody, equalTo(fieldsToUpdate));
        LOG.info("Response body contains updated fields: {}", convertedResponseBody);
    }

    @When("User sends a delete user request")
    public void deleteUser() {
        if (actualStatusCode == 200 || actualStatusCode == 201) {
            LOG.info("Sending authenticated delete user request.");
            apiResponse = apiRequestMethods.deleteRequest(ConfigReader.getProperty("deleteUser.endpoint"), token);
        } else {
            LOG.info("Sending delete user request without authentication.");
            apiResponse = apiRequestMethods.deleteRequest(ConfigReader.getProperty("deleteUser.endpoint"));
        }
        actualStatusCode = apiResponse.getStatusCode();
        LOG.info("Delete user response received with status code: {}", actualStatusCode);
    }

    @And("Response body contains {} message")
    public void validateDeleteUserResponse(String expectedErrorMessage) {
        String actualErrorMessage = apiResponse.jsonPath().getString("error");
        assertEquals("Unexpected error message.", expectedErrorMessage, actualErrorMessage);
        LOG.info("Response body contains expected '{}' message.", expectedErrorMessage);
    }

    @Then("Response returns status code {}")
    public void validateResponseStatusCode(int expectedStatusCode) {
        assertEquals("Unexpected response status code", expectedStatusCode, actualStatusCode);
    }
}

