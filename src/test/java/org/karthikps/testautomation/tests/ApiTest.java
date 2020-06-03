package org.karthikps.testautomation.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.karthikps.testautomation.api.AuthApiUtils;
import org.karthikps.testautomation.api.DepositApiUtils;
import org.karthikps.testautomation.api.UserAccountApiUtils;
import org.karthikps.testautomation.infra.Storage;
import org.karthikps.testautomation.infra.TestProperties;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Signup and Deposit API Tests")
public class ApiTest {
    private static final Logger logger = LogManager.getLogger(ApiTest.class);

    /* @Test(groups = {"Tests"}, description = "Signup and Login Tests")
    @Description("Signup Tests")
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
    }*/

    /* @Test(groups = {"Tests"}, description = "Login Tests")
    @Description("Login Tests")
    public void Login() {
        try {
            AuthApiUtils authApiUtils = new AuthApiUtils(TestProperties.getPropertyValue("api.authURL"));
            //Response response = authApiUtils.getAuthTokenForCreatedUser();
            Response response = authApiUtils.getAuthToken("manualstehr", "unvbzw5gk2yi");
            logger.info("Login response: " + response.getBody().asString());
            logger.info("Auth token:" + response.jsonPath().get("access_token"));
            Storage.randomStorageMap.put("AuthToken", response.jsonPath().get("access_token"));
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    } */

    @Test(groups = {"Tests"}, description = "Deposit Tests")
    @Description("Deposit Fund Tests")
    public void Deposit() {
        try {
            AuthApiUtils authApiUtils = new AuthApiUtils(TestProperties.getPropertyValue("api.authURL"));
            //Response response = authApiUtils.getAuthTokenForCreatedUser();
            Response response = authApiUtils.getAuthToken("manualstehr", "unvbzw5gk2yi");
            logger.info("Login response: " + response.getBody().asString());
            logger.info("Auth token:" + response.jsonPath().get("access_token"));
            Storage.randomStorageMap.put("AuthToken", response.jsonPath().get("access_token"));

            DepositApiUtils depositApiUtils = new DepositApiUtils(TestProperties.getPropertyValue("api.baseURL"));
            Response depositResponse = depositApiUtils.depositAmountSuccessfully(Storage.randomStorageMap.get("AuthToken").toString(),
                    //"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1bmlxdWVfbmFtZSI6Im1hbnVhbHN0ZWhyIiwicHJpbWFyeXNpZCI6IjcwMjM0NCIsIlRva2VuSWQiOiI4OTdmN2Q1Zi1iNjc2LTQwMjctYTYwOS1mZTM3Nzk1OThmNDkiLCJpc3MiOiJodHRwczovL2F1dGgtdXQucG9pbnRzYmV0LmNvbSIsImF1ZCI6IjU3OGZlYThmOTk1MDQ4OGU5YTJhNTg1ZTgzNGJmMDNhIiwiZXhwIjoxNTkxNzkzNTAyLCJuYmYiOjE1OTExODg3MDJ9.KfrnNUV3hsjewxzarDpiVvDk3yiqZOCf50maFLH3Cms",
                    //Storage.userSignupDataAPI.get(0).getFirstName(),
                    "manualstehr",
                    10.00);
            logger.info("Transaction response: " + depositResponse.getBody().asString());
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
