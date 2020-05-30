package org.karthikps.testautomation.ui.page;

import com.github.javafaker.Faker;
import com.paulhammant.ngwebdriver.ByAngularModel;
import org.karthikps.testautomation.ui.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage extends BasePage {

    private static final Faker faker = new Faker();

    @ByAngularModel.FindBy(model = "$ctrl.user.firstName")
    private WebElement firstNameField;

    @ByAngularModel.FindBy(model = "$ctrl.user.lastName")
    private WebElement lastNameField;

    @ByAngularModel.FindBy(model = "$ctrl.user.email")
    private WebElement emailField;

    public SignupPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Method to fill first page for signing up
     */
    public void fillFirstPage()
    {
        enterStringToField(firstNameField, faker.name().firstName());
        enterStringToField(lastNameField, faker.name().lastName());
        enterStringToField(emailField, faker.internet().safeEmailAddress());
    }


}
