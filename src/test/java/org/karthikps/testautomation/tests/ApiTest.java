package org.karthikps.testautomation.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.karthikps.testautomation.api.ApiUtils;
import org.karthikps.testautomation.api.UserAccountApiUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Beers API Tests")
public class ApiTest {
    private static final Logger logger = LogManager.getLogger(ApiTest.class);

    @Test(groups = {"Tests"}, description = "Signup and Login Tests")
    @Description("Signup and Login Tests")
    public void SignupAndLogin() {
        Response response = accountApiUtils.UserSignupRequest();
        logger.info("User signup status: " + response.getStatusCode());
        logger.info("Signup response: " + response.getBody().asString());
    }
}
