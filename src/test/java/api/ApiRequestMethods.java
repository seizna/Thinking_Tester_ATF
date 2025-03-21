package api;

import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.Map;

public class ApiRequestMethods {
    ApiSetup apiSetup = new ApiSetup();


    public Response authorizationRequest(String endpoint, String email, String password) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("password", password);

        return apiSetup.buildApiRequest()
                .body(requestBody.toString())
                .post(endpoint);
    }

    public Response getRequest(String endpoint, String token) {
        return apiSetup.buildApiRequestWithToken(token)
                .get(endpoint);
    }

    public Response patchRequest(String endpoint, Map<String, String> fieldsToUpdate, String token) {
        JSONObject requestBody = new JSONObject(fieldsToUpdate);
        return apiSetup.buildApiRequestWithToken(token)
                .body(requestBody.toString())
                .patch(endpoint);
    }

    public Response deleteRequest(String endpoint, String token) {
        return apiSetup.buildApiRequestWithToken(token)
                .delete(endpoint);
    }

    public Response deleteRequest(String endpoint) {
        return apiSetup.buildApiRequest()
                .delete(endpoint);
    }
}
