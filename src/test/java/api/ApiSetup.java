package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;

public class ApiSetup {
    ConfigReader configReader = new ConfigReader();

    public RequestSpecification buildApiRequest() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = configReader.getUrl("login.url");
        return RestAssured.given()
                .baseUri(RestAssured.baseURI)
                .contentType(ContentType.JSON);
    }

    public RequestSpecification buildApiRequestWithToken(String token) {
        return buildApiRequest()
                .header("Authorization", "Bearer " + token);
    }
}
