package org.karthikps.testautomation.api;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.karthikps.testautomation.infra.TestProperties;
import org.karthikps.testautomation.infra.pojo.UserSignupData;
import org.karthikps.testautomation.tests.ApiTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserAccountApiUtils<T> extends ApiUtils<T> {
    private static final Logger logger = LogManager.getLogger(UserAccountApiUtils.class);
    private static final Faker faker = new Faker();
    public static final String mobileNumber = "0400000000";
    public static final String addressLine1 = "535 Church St";
    public static final String suburb = "Richmond";
    public static final String addressState = "Victoria";
    public static final String addressPostCode = "3121";
    public static final String addressCountry = "Australia";

    public UserAccountApiUtils(String baseUri) {
        super();
        RestAssured.baseURI = baseUri;
    }

    public Response UserSignupRequest() {
        String password = faker.internet().password();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        UserSignupData signupData = new UserSignupData();
        signupData.setTitle("Mr");
        signupData.setFirstName(faker.name().firstName());
        signupData.setLastName(faker.name().lastName());
        signupData.setDOB(dateFormat.format(faker.date().birthday(18, 80)));
        signupData.setContactNumber(mobileNumber);
        signupData.setEmail(faker.internet().safeEmailAddress());
        signupData.setUsername(faker.name().username().replace(".", ""));
        signupData.setPassword(password);
        signupData.setConfirmPassword(password);
        signupData.setAddressLine1(addressLine1);
        signupData.setAddressLine2("");
        signupData.setAddressSuburb(suburb);
        signupData.setAddressState(addressState);
        signupData.setAddressPostCode(addressPostCode);
        signupData.setAddressCountry(addressCountry);
        signupData.setTermsAccepted(true);
        signupData.setPromotionCode("WELCOME");

        JSONObject jsonObject = new JSONObject(signupData);
        RequestSpecification requestSpecification = httpPost(jsonObject);
        logger.info("User signup request body: " + jsonObject.toString());

        List<Header> requestHeaders = new ArrayList<Header>();
        requestHeaders.add(new Header("pb-perf-test", TestProperties.getPropertyValue("api.header.pbperftest")));
        requestHeaders.add(new Header("Cookie", TestProperties.getPropertyValue("api.header.cookie")));
        Headers headers = new Headers(requestHeaders);

        return requestSpecification.with().headers(headers).request(Method.POST, "/api/account");
    }
}
