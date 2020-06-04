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

    @Test(groups = {"Tests"}, description = "Signup and Login Tests")
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
    }

    @Test(groups = {"Tests"}, description = "Login Tests", dependsOnMethods = {"SignupAndLogin"})
    @Description("Login Tests")
    public void Login() {
        try {
            AuthApiUtils authApiUtils = new AuthApiUtils(TestProperties.getPropertyValue("api.authURL"));
            Response response = authApiUtils.getAuthTokenForCreatedUser();
            logger.info("Login response: " + response.getBody().asString());
            logger.info("Auth token:" + response.jsonPath().get("access_token"));
            Storage.randomStorageMap.put("AuthToken", response.jsonPath().get("access_token"));
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test(groups = {"Tests"}, description = "Deposit Tests", dependsOnMethods = {"Login"})
    @Description("Deposit Fund Tests")
    public void Deposit() {
        float amountToDeposit = (float) 10.0;
        try {
            UserAccountApiUtils userAccountApiUtils = new UserAccountApiUtils(TestProperties.getPropertyValue("api.baseURL"));
            Response balanceInAccount = userAccountApiUtils.checkBalanceInAccount(Storage.randomStorageMap.get("AuthToken").toString());
            float balanceBeforeDeposit = Float.parseFloat(balanceInAccount.jsonPath().get("balances.balance").toString());
            logger.info("Account balance before deposit: " + balanceBeforeDeposit);

            DepositApiUtils depositApiUtils = new DepositApiUtils(TestProperties.getPropertyValue("api.baseURL"));
            Response depositResponse = depositApiUtils.depositAmountSuccessfully(Storage.randomStorageMap.get("AuthToken").toString(),
                    Storage.userSignupDataAPI.get(0).getFirstName(),
                    amountToDeposit);

            logger.info("Transaction response: " + depositResponse.getBody().asString());
            Assert.assertTrue(depositResponse.jsonPath().get("Success"));

            Response balanceInAccountAfter = userAccountApiUtils.checkBalanceInAccount(Storage.randomStorageMap.get("AuthToken").toString());
            float balanceAfterDeposit = Float.parseFloat(balanceInAccountAfter.jsonPath().get("balances.balance").toString());
            logger.info("Account balance after deposit: " + balanceAfterDeposit);
            Assert.assertEquals(balanceAfterDeposit, balanceBeforeDeposit + amountToDeposit);
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
