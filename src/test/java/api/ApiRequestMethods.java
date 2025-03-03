package api;

import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class ApiRequestMethods {
    ApiSetup apiSetup = new ApiSetup();

    public Response authorizationRequest(String endpoint, String email, String password) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);

        return apiSetup.buildApiRequest()
                .body(requestBody)
                .post(endpoint);
    }

    public Response deleteUserRequest(String url, String token) {
        return apiSetup.buildApiRequestWithToken(token)
                .delete(url);
    }

    public Response deleteUserRequest(String url) {
        return apiSetup.buildApiRequest()
                .delete(url);
    }
}
