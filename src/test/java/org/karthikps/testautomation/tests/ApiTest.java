package org.karthikps.testautomation.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.karthikps.testautomation.api.ApiUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Beers API Tests")
public class ApiTest<T> extends ApiUtils<T> {
    private static final Logger logger = LogManager.getLogger(ApiTest.class);

    @Test(groups = {"Tests"}, description = "Beer API Tests")
    @Description("Basic Beer API Tests")
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
