package org.karthikps.testautomation.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.karthikps.testautomation.api.UserAccountApiUtils;
import org.karthikps.testautomation.infra.TestProperties;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Signup and Deposit API Tests")
public class ApiTest {
    private static final Logger logger = LogManager.getLogger(ApiTest.class);

    @Test(groups = {"Tests"}, description = "Signup and Login Tests")
    @Description("Signup and Login Tests")
    public void SignupAndLogin() {
        try {
            UserAccountApiUtils accountApiUtils = new UserAccountApiUtils(TestProperties.getPropertyValue("api.baseURL"));
            Response response = accountApiUtils.UserSignupRequest();
            logger.info("User signup status: " + response.getStatusCode());
            logger.info("Signup response: " + response.getBody().asString());
            Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
            Assert.assertTrue(response.jsonPath().get("Success"));
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
