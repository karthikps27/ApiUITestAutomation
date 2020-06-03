package org.karthikps.testautomation.api;

import com.google.gson.Gson;
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
import org.karthikps.testautomation.infra.pojo.BPointTransaction;
import org.karthikps.testautomation.infra.pojo.BeginTransaction;
import org.karthikps.testautomation.infra.pojo.CardDetails;
import org.karthikps.testautomation.infra.pojo.TxnReq;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

public class DepositApiUtils<T> extends ApiUtils<T>{
    private static final Logger logger = LogManager.getLogger(UserAccountApiUtils.class);
    private String baseUri;

    public DepositApiUtils(String baseUri) {
        super();
        RestAssured.baseURI = baseUri;
        this.baseUri = baseUri;
    }

    /**
     * Begins the card transaction
     * @param beginTransactionData
     * @return
     */
    public Response beginTransaction(BeginTransaction beginTransactionData, String bearerToken) {
        List<Header> headers = getHeaders();
        headers.add(new Header("authorization", "Bearer " + bearerToken));
        headers.add(new Header("content-type", "application/json;charset=UTF-8"));
        JSONObject jsonObject = new JSONObject(beginTransactionData);
        RequestSpecification requestSpecification = httpPost(jsonObject);
        return requestSpecification.headers(new Headers(headers)).request(Method.POST, "/asl/api/payments/deposit/begin");
    }

    /**
     * Complete the transaction with result key
     * @param resultKey
     * @param bearerToken
     * @return
     */
    public Response completeTransaction(String resultKey, String bearerToken) {
        RestAssured.baseURI = baseUri;
        List<Header> headers = getHeaders();
        headers.add(new Header("authorization", "Bearer " + bearerToken));
        headers.add(new Header("content-type", "application/json;charset=UTF-8"));
        return httpPost("{\"ResultKey\":" + "\"" + resultKey + "\"" + "}").headers(new Headers(headers)).request(Method.POST, "/asl/api/payments/deposit/complete");
    }

    /**
     * Deposit amount using card transaction
     * @param amount
     * @return
     */
    public Response depositAmountSuccessfully(String bearerToken, String cardHolderName, Double amount) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 60);
        LocalDate localDate = cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int monthValue = localDate.getMonthValue();

        return DepositFundsToUserAccount(bearerToken,
                TestProperties.getPropertyValue("api.request.cardnumber"),
                Integer.toString((int) (amount * 100)),
                monthValue < 10 ? '0' + Integer.toString(monthValue) : Integer.toString(monthValue),
                Integer.toString(localDate.getYear()),
                cardHolderName,
                "123"
                );
    }

    /**
     *  @param bearerToken
     * @param amount
     * @return
     */
    public Response DepositFundsToUserAccount(String bearerToken, String cardNumber, String amount, String cardExpiryMonth, String cardExpiryYear, String cardHolderName,
                                              String cvn) {
        String beginTransactionJSON = "{\"maskedCard\":\"411111...111\",\"amount\":1000,\"cardExpiryMonth\":\"07\",\"cardExpiryYear\":2020,\"gatewayType\":\"bpoint\",\"promoCode\":null}";
        String bpointTxnReqJSON = "{\"Amount\":null,\"AmountOriginal\":null,\"AmountSurcharge\":null,\"BillerCode\":null,\"Crn1\":null,\"Crn2\":null,\"Crn3\":null,\"Currency\":null,\"MerchantReference\":null,\"EmailAddress\":null,\"StoreCard\":true,\"CardDetails\":null}";
        String bpointCardDetailsJSON = "{\"CardHolderName\":\"patricia\",\"CardNumber\":\"4111111111111111\",\"Cvn\":\"123\",\"ExpiryDateMonth\":\"07\",\"ExpiryDateYear\":\"20\"}";

        Gson gson = new Gson();
        BeginTransaction beginTransactionData = gson.fromJson(beginTransactionJSON, BeginTransaction.class);
        beginTransactionData.setMaskedCard(getMaskedCardNumber(cardNumber));
        beginTransactionData.setAmount(Integer.parseInt(amount));
        beginTransactionData.setCardExpiryMonth(cardExpiryMonth);
        beginTransactionData.setCardExpiryYear(cardExpiryYear);
        Response response = beginTransaction(beginTransactionData, bearerToken);
        logger.info("Begin transaction response: " + response.getBody().asString());
        String transAuthKey = response.jsonPath().get("gatewayExtension.payToken"); //"e5f7f0b7-9fef-4dd9-9c6c-fb90707b608f"
                //"e5f7f0b7-9fef-4dd9-9c6c-fb90707b608f";

        CardDetails cardDetails = gson.fromJson(bpointCardDetailsJSON, CardDetails.class);
        cardDetails.setCardHolderName(cardHolderName);
        cardDetails.setCardNumber(cardNumber);
        cardDetails.setExpiryDateMonth(cardExpiryMonth);
        cardDetails.setExpiryDateYear(cardExpiryYear.substring(2,4));
        cardDetails.setCvn(cvn);
        TxnReq txnReq = gson.fromJson(bpointTxnReqJSON, TxnReq.class);
        txnReq.setCardDetails(cardDetails);
        txnReq.setStoreCard(true);

        BPointTransaction bPointTransactionData = new BPointTransaction();
        bPointTransactionData.setTxnReq(txnReq);

        BPointApiUtils bPointApiUtils = new BPointApiUtils(TestProperties.getPropertyValue("api.bpointURL"));
        Response transactionResponse = bPointApiUtils.InvokeTransaction(transAuthKey, bPointTransactionData);
        logger.info("BPoint transaction response: " + transactionResponse.getBody().asString());
        String resultKey = transactionResponse.jsonPath().get("ResultKey");

        return completeTransaction(resultKey, bearerToken);
    }

    /**
     * Get masked card number
     * @param cardNumber
     * @return
     */
    public String getMaskedCardNumber(String cardNumber) {
        return cardNumber.substring(0,5) + "..." + cardNumber.substring(cardNumber.length() - 4, cardNumber.length() - 1);
    }
}
