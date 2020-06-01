package org.karthikps.testautomation.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

public class ApiUtils<T> {

    /**
     * Evaluate HTTPResponse with jsonPath
     * @param response
     * @param jsonPathExpression
     * @return
     */
    protected T evaluateResponseWithJsonPath(Response response, String jsonPathExpression)
    {
        return response.jsonPath().get(jsonPathExpression);
    }

    /**
     * Invokes
     * @return RestAssured request object
     * @param s
     */
    protected RequestSpecification httpGet(String path) {
        return RestAssured
            .given()
            .with()
            .contentType(ContentType.JSON);
    }

    /**
     * HTTP request object for post requests
     * @param payload
     * @return
     */
    protected RequestSpecification httpPost(JSONObject payload) {
        return RestAssured
            .given()
            .with()
                .contentType(ContentType.JSON)
            .with()
                .body(payload.toString());
    }
}
