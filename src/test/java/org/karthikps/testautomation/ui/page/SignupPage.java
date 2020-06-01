package org.karthikps.testautomation.ui.page;

import com.github.javafaker.Faker;
import com.paulhammant.ngwebdriver.*;
import io.qameta.allure.Allure;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.karthikps.testautomation.infra.Storage;
import org.karthikps.testautomation.infra.pojo.UserData;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SignupPage<T> extends HomePage<T> {

    private static final Faker faker = new Faker();
    private final Logger logger = LogManager.getLogger(SignupPage.class);

    @ByAngularModel.FindBy(model = "$ctrl.user.firstName")
    private WebElement firstNameField;

    @ByAngularModel.FindBy(model = "$ctrl.user.lastName")
    private WebElement lastNameField;

    @ByAngularModel.FindBy(model = "$ctrl.user.email")
    private WebElement emailField;

    @ByAngularButtonText.FindBy(buttonText = "Continue")
    private WebElement continueButton;

    @FindBy(className = "div.custom-radiobuttons.custom-radiobuttons-inline > ul > li")
    private List<WebElement> salutations;

    @FindBy(id = "day")
    private WebElement dayDropDown;

    @FindBy(id = "month")
    private WebElement monthDropDown;

    @FindBy(id = "year")
    private WebElement yearDropDown;

    @ByAngularModel.FindBy(model = "$ctrl.user.contactNumber")
    private WebElement mobileNumberField;

    @ByAngularModel.FindBy(model = "$ctrl.address.value")
    private WebElement addressField;

    @FindBy(css = ".uib-typeahead-match.ng-scope.active")
    private WebElement addressConfirm;

    @ByAngularModel.FindBy(model = "$ctrl.user.userName")
    private WebElement usernameField;

    @ByAngularModel.FindBy(model = "$ctrl.user.password")
    private WebElement passwordField;

    @ByAngularModel.FindBy(model = "$ctrl.user.confirmPassword")
    private WebElement confirmPasswordField;

    @FindBy(css = "div.form-group.custom-checkbox.extra-space > label")
    private WebElement agreeTermsAndCondition;

    @FindBy(css = ".form-inline:nth-child(5) > button")
    private WebElement joinPointsBetButton;
    //@ByAngularButtonText.FindBy(buttonText = "Join PointsBet")

    @FindBy(css = "[ng-show=\"$ctrl.inProgress === true\"]")
    private WebElement signupProgress;

    public SignupPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
        createFakeUserDataDictionary();
    }

    public void createFakeUserDataDictionary() {
        String mobileNumber = "0400000000";
        String address = "535 Church St, Richmond VIC 3121";
        UserData userData = new UserData(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().safeEmailAddress(),
                faker.date().birthday(18, 80),
                mobileNumber,
                address,
                faker.name().username().replace(".", ""),
                faker.internet().password()
                );
        Storage.userDataMap.add(userData);
    }

    /**
     * Method to fill first page for signing up
     */
    public void fillFirstPage()
    {
        enterStringToField(firstNameField, Storage.userDataMap.get(0).getFirstname());
        enterStringToField(lastNameField, Storage.userDataMap.get(0).getLastname());
        enterStringToField(emailField, Storage.userDataMap.get(0).getEmail());
        click(continueButton);
        logger.info("Filled first page for new user sign up");
    }

    /**
     * Fill the second page for signing up
     */
    public void fillSecondPageAndSignup() throws Exception {
        try {
            Calendar calendar = Calendar.getInstance();
            UserData userData = Storage.userDataMap.get(0);
            Date dateOfBirth = userData.getDateOfBirth();
            LocalDate date = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            selectFromListUsingRepeater(ByAngular.repeater("salutation in $ctrl.salutations track by $index"), SelectBy.INDEX, (T)(Integer) 1);

            click(dayDropDown);
            selectFromListUsingRepeater(ByAngular.repeater("n in [] | range:1:31"), SelectBy.INDEX,
                    (T)(Integer) (date.getDayOfMonth() - 1));

            click(monthDropDown);
            selectFromListUsingRepeater(ByAngular.repeater("n in [] | range:1:12"), SelectBy.INDEX,
                    (T)(Integer) (date.getMonthValue() - 1));

            click(yearDropDown);
            selectFromListUsingRepeater(ByAngular.repeater("year in $ctrl.years track by $index"), SelectBy.VALUE,
                    (T) Integer.toString(date.getYear()));


            enterStringToField(mobileNumberField, userData.getMobileNumber());

            enterStringToField(addressField, userData.getAddress());
            waitForElement(addressConfirm);
            selectFromListUsingRepeater(ByAngular.repeater("match in matches track by $index"), SelectBy.INDEX, (T)(Integer) 0);

            enterStringToField(usernameField, userData.getUsername());
            enterStringToField(passwordField, userData.getPassword());
            enterStringToField(confirmPasswordField, userData.getPassword());

            click(agreeTermsAndCondition);

            click(joinPointsBetButton);
            waitForElementToDisappear(signupProgress);

            logger.info("User sign up successful. Username: " + userData.getUsername() + ", Password:" + userData.getPassword());
            Allure.addAttachment("signUpTest", new ByteArrayInputStream(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES)));
        }
        catch (Exception e)
        {
            logger.error(e.getStackTrace());
            throw new Exception(e);
        }
    }

    public LoggedInHomePage loginWithSignedUpUser() throws Exception {
        UserData userData = Storage.userDataMap.get(0);
        return login(userData.getUsername(), userData.getPassword());
    }
}
