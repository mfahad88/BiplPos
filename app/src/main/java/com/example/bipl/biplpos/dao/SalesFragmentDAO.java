package com.example.bipl.biplpos.dao;

import android.content.Context;

import com.example.bipl.biplpos.data.SalesBean;

import java.util.List;

/**
 * Created by fahad on 2/16/2017.
 */

public class SalesFragmentDAO {

    public List<SalesBean> getSales(Context context){
        DbHelper dbHelper = new DbHelper(context);
        return dbHelper.getSales();
    }

    public double totalSales(Context context){
        DbHelper dbHelper = new DbHelper(context);
        return dbHelper.totalSales();
    }
}
