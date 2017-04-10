package com.example.bipl.biplpos.data;

/**
 * Created by fahad on 3/20/2017.
 */

public class DebitBean {
    private String panNo;
    private String expiryStatus;
    private String expiryDate;
    private String debitTitle;
    public String getPanNo() {
        return panNo;
    }
    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }
    public String getExpiryStatus() {
        return expiryStatus;
    }
    public void setExpiryStatus(String expiryStatus) {
        this.expiryStatus = expiryStatus;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDebitTitle() {
        return debitTitle;
    }
    public void setDebitTitle(String debitTitle) {
        this.debitTitle = debitTitle;
    }
    @Override
    public String toString() {
        return "DebitCardBean [panNo=" + panNo + ", expiryStatus=" + expiryStatus + ", expiryDate=" + expiryDate + "]";
    }
}
