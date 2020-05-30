package org.karthikps.testautomation.ui.page;

import org.karthikps.testautomation.ui.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

    @FindBy(css = "div.user-area.logged-out > a")
    protected WebElement signUpButton;

    @FindBy(css = "div.user-area.logged-out > button")
    protected WebElement loginButton;

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
}
