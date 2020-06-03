package org.karthikps.testautomation.api;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.karthikps.testautomation.infra.Storage;
import org.karthikps.testautomation.infra.TestProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthApiUtils<T> extends ApiUtils<T>{
    private static final Logger logger = LogManager.getLogger(AuthApiUtils.class);

    public AuthApiUtils(String baseUri) {
        super();
        RestAssured.baseURI = baseUri;
    }

    public Response getAuthToken(String username, String password) {
        RequestSpecification requestSpecification = httpPostFormUrlEncoded();

        List<Header> requestHeaders = new ArrayList<Header>();
        requestHeaders.add(new Header("pb-perf-test", TestProperties.getPropertyValue("api.header.pbperftest")));
        requestHeaders.add(new Header("Cookie", TestProperties.getPropertyValue("api.header.cookie")));
        requestHeaders.add(new Header("origin", TestProperties.getPropertyValue("api.header.origin")));
        requestHeaders.add(new Header("referer", TestProperties.getPropertyValue("referer")));
        requestHeaders.add(new Header("accept", "application/json, text/plain, */*"));
        Headers headers = new Headers(requestHeaders);

        Map<String, String> formParams = new HashMap<String, String>();
        formParams.put("username", username);
        formParams.put("password", password);
        formParams.put("client_id", TestProperties.getPropertyValue("api.request.clientid"));
        formParams.put("sms", "");
        formParams.put("grant_type", "password");

        return requestSpecification.headers(headers).formParams(formParams).request(Method.POST);
    }

    public Response getAuthTokenForCreatedUser() {
        return getAuthToken(Storage.userSignupDataAPI.get(0).getUsername(), Storage.userSignupDataAPI.get(0).getPassword());
    }
}