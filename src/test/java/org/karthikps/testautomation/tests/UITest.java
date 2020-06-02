package org.karthikps.testautomation.tests;

import io.qameta.allure.Feature;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.karthikps.testautomation.infra.TestProperties;
import org.karthikps.testautomation.ui.core.DriverFactory;
import org.karthikps.testautomation.ui.page.HomePage;
import org.karthikps.testautomation.ui.page.LoggedInHomePage;
import org.karthikps.testautomation.ui.page.SignupPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Feature("Signup and deposit UI Tests")
public class UITest extends DriverFactory
{
    private static Logger logger = LogManager.getLogger(UITest.class);
    private HomePage homePage;

    @BeforeClass
    public void setupTest() {
        homePage = new HomePage(webDriver);
        homePage.openBrowser(TestProperties.getPropertyValue("ui.baseURL"));
    }

    @Test(description = "Sign up tests")
    public void signUpTest() {
        try {
            SignupPage signupPage = homePage.goToSignupPage();
            signupPage.fillFirstPage();
            signupPage.fillSecondPageAndSignup();
            LoggedInHomePage loggedInHomePage = signupPage.loginWithSignedUpUser();
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
