package org.karthikps.testautomation.api;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.karthikps.testautomation.infra.pojo.BPointTransaction;

import java.util.List;

public class BPointApiUtils<T> extends ApiUtils<T> {
    private static final Logger logger = LogManager.getLogger(UserAccountApiUtils.class);

    public BPointApiUtils(String baseUri) {
        super();
        RestAssured.baseURI = baseUri;
    }

    /**
     * Invokes card transaction with transAuthKey
     * @param transAuthKey
     * @param transactionData
     * @return
     */
    public Response InvokeTransaction(String transAuthKey, BPointTransaction transactionData) {
        List<Header> headers = getHeaders();
        headers.add(new Header("content-type", "application/json;charset=UTF-8"));
        JSONObject jsonObject = new JSONObject(transactionData);
        RequestSpecification requestSpecification = httpPost(jsonObject.toString()
                .replace("txnReq", "TxnReq")
                .replace("card", "Card")
                .replace("store", "Store")
                .replace("cvn", "Cvn")
                .replace("expiry", "Expiry"));
        return requestSpecification.headers(new Headers(headers)).request(Method.POST,"/webapi/v2/txns/withauthkey/" + transAuthKey);
    }
}
