package org.karthikps.testautomation.tests;

import org.karthikps.testautomation.ui.core.DriverFactory;
import org.karthikps.testautomation.ui.page.LoginPage;
import org.testng.annotations.Test;

public class UITest extends DriverFactory {

    @Test(description = "sample UI test")
    public void basicUITest() {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login();
    }
}
