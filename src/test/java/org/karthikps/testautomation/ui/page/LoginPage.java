package org.karthikps.testautomation.ui.page;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.karthikps.testautomation.ui.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    private final Logger logger = LogManager.getLogger(LoginPage.class);

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "pass")
    private WebElement password;

    @FindBy(id = "send2")
    private WebElement loginButton;

    @FindBy(id = "advice-validate-password-pass")
    private WebElement errorText;


    public LoginPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void login() {
        try {
            openBrowser("http://live.guru99.com/index.php/customer/account/login/");

            enterStringToField(email, "testnow@gmail.com");
            enterStringToField(password, "123456");
            click(loginButton);
            //captureImage(tcName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Step("Verify password step for test:{0}, for method: {method}")
    public void verifyPassword(String tcName) {
        try {
            navigate("http://live.guru99.com/index.php/customer/account/login/");
            waitForElement(email);
            captureScreen("Home Page");
            enter(email, "testnow@gmail.com");
            enter(password, "12345");
            click(loginButton);
            captureImage(tcName);
            String actualValue = "Please enter 6 or more characters without leading or trailing spaces.";
            compareText(actualValue, getText(errorText));
            captureScreen("Error Page");
            clear(password);
            enter(password, "123456");
            click(loginButton);
            captureScreen("Login Page");
            captureImage(tcName);
        } catch (Exception e) {
            logger.error(e);
        }
    }*/

}