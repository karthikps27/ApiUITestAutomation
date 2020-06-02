package org.karthikps.testautomation.ui.page;

import io.qameta.allure.Allure;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.karthikps.testautomation.ui.core.BasePage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.ByteArrayInputStream;

public class HomePage<T> extends BasePage<T> {
    private final Logger logger = LogManager.getLogger(HomePage.class);

    @FindBy(css = "div.user-area.logged-out > a")
    protected WebElement signUpButton;

    @FindBy(css = "div.user-area.logged-out > button")
    protected WebElement loginButton;

    @FindBy(id = "login-username")
    protected WebElement loginUsernameField;

    @FindBy(id = "Password")
    protected WebElement loginPasswordField;

    @FindBy(id = "login-button")
    protected WebElement loginSubmitButton;

    @FindBy(css = "div.user-area.user-area-fo.logged-in > button > span.hidden-xs.ng-binding")
    protected WebElement userDropDownAtNavbar;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * This method is to click on signup button
     * @return
     */
    public SignupPage goToSignupPage() {
        click(signUpButton);
        return new SignupPage(webDriver);
    }

    /**
     * This method is to login
     * @param username
     * @param password
     * @return
     */
    public LoggedInHomePage login(String username, String password) throws Exception {
        click(loginButton);
        enterStringToField(loginUsernameField, username);
        enterStringToField(loginPasswordField, password);
        try
        {
            click(loginSubmitButton);
            waitForElement(userDropDownAtNavbar);
            logger.info("Login successful with username:" + username + " and password:" + password);
            return new LoggedInHomePage(webDriver);
        }
        catch (Exception e) {
            logger.error("Failure during user login:" + e.getMessage());
            Allure.addAttachment("LoginTestFailure", new ByteArrayInputStream(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES)));
            throw new Exception(e);
        }
    }
}
