package org.karthikps.testautomation.infra.pojo;

public class TxnReq {

    private Object amount;
    private Object amountOriginal;
    private Object amountSurcharge;
    private Object billerCode;
    private Object crn1;
    private Object crn2;
    private Object crn3;
    private Object currency;
    private Object merchantReference;
    private Object emailAddress;
    private Boolean storeCard;
    private CardDetails cardDetails;

    public Object getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = amount;
    }

    public Object getAmountOriginal() {
        return amountOriginal;
    }

    public void setAmountOriginal(Object amountOriginal) {
        this.amountOriginal = amountOriginal;
    }

    public Object getAmountSurcharge() {
        return amountSurcharge;
    }

    public void setAmountSurcharge(Object amountSurcharge) {
        this.amountSurcharge = amountSurcharge;
    }

    public Object getBillerCode() {
        return billerCode;
    }

    public void setBillerCode(Object billerCode) {
        this.billerCode = billerCode;
    }

    public Object getCrn1() {
        return crn1;
    }

    public void setCrn1(Object crn1) {
        this.crn1 = crn1;
    }

    public Object getCrn2() {
        return crn2;
    }

    public void setCrn2(Object crn2) {
        this.crn2 = crn2;
    }

    public Object getCrn3() {
        return crn3;
    }

    public void setCrn3(Object crn3) {
        this.crn3 = crn3;
    }

    public Object getCurrency() {
        return currency;
    }

    public void setCurrency(Object currency) {
        this.currency = currency;
    }

    public Object getMerchantReference() {
        return merchantReference;
    }

    public void setMerchantReference(Object merchantReference) {
        this.merchantReference = merchantReference;
    }

    public Object getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(Object emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean getStoreCard() {
        return storeCard;
    }

    public void setStoreCard(Boolean storeCard) {
        this.storeCard = storeCard;
    }

    public CardDetails getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(CardDetails cardDetails) {
        this.cardDetails = cardDetails;
    }

}