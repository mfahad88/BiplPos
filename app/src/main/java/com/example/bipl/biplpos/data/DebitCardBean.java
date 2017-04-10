package com.example.bipl.biplpos.data;

/**
 * Created by fahad on 3/28/2017.
 */

public class DebitCardBean {
    public String panNo;

    public String statusCode;

    public String statusDescription;

    public String ibUser;

    public String responseCode;

    public String responseDescription;

    public String expiryDate;

    public String STAN;

    public String amount;

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getIbUser() {
        return ibUser;
    }

    public void setIbUser(String ibUser) {
        this.ibUser = ibUser;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSTAN() {
        return STAN;
    }

    public void setSTAN(String STAN) {
        this.STAN = STAN;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
