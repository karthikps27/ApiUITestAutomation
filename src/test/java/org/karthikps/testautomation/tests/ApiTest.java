package org.karthikps.testautomation.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.karthikps.testautomation.api.ApiUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiTest<T> extends ApiUtils<T> {
    private static final Logger logger = LogManager.getLogger(ApiTest.class);

    @Test(groups = {"Tests"}, description = "Test API")
    public void AllBeers() {
        RestAssured.baseURI = "https://api.punkapi.com";

        Response response = httpGet("/v2/beers");
        Assert.assertEquals(response.getStatusCode(), 200, "Correct status code returned");
        logger.info("Response Body is =>  " + response.getBody());
        T tagLine = evaluateResponseWithJsonPath(response, "$[0].tagline");
        T description = evaluateResponseWithJsonPath(response, "$[0].description");
        logger.info("Tag Line: " + tagLine);
        logger.info("Description: " + description);
    }
}
