package org.karthikps.testautomation.infra.pojo;

public class CardDetails {

    private String cardHolderName;
    private String cardNumber;
    private String cvn;
    private String expiryDateMonth;
    private String expiryDateYear;

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvn() {
        return cvn;
    }

    public void setCvn(String cvn) {
        this.cvn = cvn;
    }

    public String getExpiryDateMonth() {
        return expiryDateMonth;
    }

    public void setExpiryDateMonth(String expiryDateMonth) {
        this.expiryDateMonth = expiryDateMonth;
    }

    public String getExpiryDateYear() {
        return expiryDateYear;
    }

    public void setExpiryDateYear(String expiryDateYear) {
        this.expiryDateYear = expiryDateYear;
    }

}