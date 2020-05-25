package org.karthikps.testautomation.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
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
     * HTTP request object for get requests
     * @return RestAssured request object
     * @param s
     */
    protected Response httpGet(String path) {
        return RestAssured
            .given()
            .with()
                .contentType(ContentType.JSON)
             .request(Method.GET, path);
    }

    /**
     * HTTP request object for post requests
     * @param payload
     * @return
     */
    protected Response httpPost(JSONObject payload, String path) {
        return RestAssured
                .given()
                .with()
                    .contentType(ContentType.JSON)
                .with()
                    .body(payload.toString())
                .request(Method.POST, path);
    }
}
