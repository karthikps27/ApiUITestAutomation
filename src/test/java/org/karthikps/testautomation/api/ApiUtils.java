package org.karthikps.testautomation.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.karthikps.testautomation.infra.TestProperties;

import java.util.ArrayList;
import java.util.List;

public class ApiUtils<T> {

    /**
     * Evaluate HTTPResponse with jsonPath
     * @param response
     * @param jsonPathExpression
     * @return
     */
    public T evaluateResponseWithJsonPath(Response response, String jsonPathExpression)
    {
        return response.jsonPath().get(jsonPathExpression);
    }

    /**
     * Invokes
     * @return RestAssured request object
     * @param path
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

    /**
     * HTTP request object for post requests with jsonstring payload
     * @param payload
     * @return
     */
    protected RequestSpecification httpPost(String payload) {
        return RestAssured
                .given()
                .with()
                .contentType(ContentType.JSON)
                .with()
                .body(payload);
    }

    /**
     * form-urlencoded type HTTP post request
     * @return
     */
    protected RequestSpecification httpPostFormUrlEncoded() {
        return RestAssured
                .given()
                    .contentType(ContentType.URLENC);
    }

    /**
     * Setting required headers to call APIs
     * @return
     */
    protected List<Header> getHeaders() {
        List<Header> requestHeaders = new ArrayList<Header>();
        requestHeaders.add(new Header("pb-perf-test", TestProperties.getPropertyValue("api.header.pbperftest")));
        requestHeaders.add(new Header("Cookie", TestProperties.getPropertyValue("api.header.cookie")));
        requestHeaders.add(new Header("origin", TestProperties.getPropertyValue("api.header.origin")));
        requestHeaders.add(new Header("referer", TestProperties.getPropertyValue("api.header.referer")));
        requestHeaders.add(new Header("accept", "application/json, text/plain, */*"));
        requestHeaders.add(new Header("user-agent", TestProperties.getPropertyValue("api.header.useragent")));
        return requestHeaders;
    }
}
