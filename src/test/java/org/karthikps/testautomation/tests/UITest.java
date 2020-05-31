package org.karthikps.testautomation.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.karthikps.testautomation.ui.core.DriverFactory;
import org.karthikps.testautomation.ui.page.HomePage;
import org.karthikps.testautomation.ui.page.LoginPage;
import org.karthikps.testautomation.ui.page.SignupPage;
import org.testng.annotations.Test;

@Feature("Sample UI Tests")
public class UITest extends DriverFactory
{
    private static Logger logger = LogManager.getLogger(UITest.class);

    /*@Test(description = "sample UI test")
    @Description("Basic UI Tests")
    public void basicUITest() {
        //logger.fatal("Beginning Tests");
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login();
        //logger.error("Closing Tests");
    }*/

    @Test(description = "Sign up tests")
    public void signUpTest() {
        HomePage homePage = new HomePage(webDriver);
        SignupPage signupPage = homePage.goToSignupPage();
        signupPage.fillFirstPage();
        signupPage.fillSecondPageForSignup();
    }
}
