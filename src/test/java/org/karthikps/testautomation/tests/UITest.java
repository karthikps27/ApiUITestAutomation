package org.karthikps.testautomation.tests;

import io.qameta.allure.Feature;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.karthikps.testautomation.ui.core.DriverFactory;
import org.karthikps.testautomation.ui.page.HomePage;
import org.karthikps.testautomation.ui.page.LoggedInHomePage;
import org.karthikps.testautomation.ui.page.SignupPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Feature("Sample UI Tests")
public class UITest extends DriverFactory
{
    private static Logger logger = LogManager.getLogger(UITest.class);
    private HomePage homePage;
    /*@Test(description = "sample UI test")
    @Description("Basic UI Tests")
    public void basicUITest() {
        //logger.fatal("Beginning Tests");
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login();
        //logger.error("Closing Tests");
    }*/

    @BeforeClass
    public void setupTest() {
        homePage = new HomePage(webDriver);
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
