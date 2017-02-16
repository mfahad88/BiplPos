package com.example.bipl.biplpos.data;

/**
 * Created by fahad on 2/10/2017.
 */

public class SalesBean {
    private String Name;
    private float totalAmount;
    private float Qty;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getQty() {
        return Qty;
    }

    public void setQty(float qty) {
        Qty = qty;
    }
}
