package com.example.bipl.biplpos.dao;

import android.content.Context;

import com.example.bipl.biplpos.data.ProductBean;

import java.util.List;

/**
 * Created by fahad on 2/16/2017.
 */

public class InventoryFragmentDAO {



    public void insertInventory(Context context, int Id, String inventoryName, String inventoryPrice){
        DbHelper dbHelper = new DbHelper(context);
        dbHelper.insertProducts(Id,inventoryName, inventoryPrice);
    }

    public void insertSales(Context context,int Id_prod,String prodName,String prodQty,String prodPrice){
        DbHelper dbHelper = new DbHelper(context);
        dbHelper.insertSales(Id_prod,prodName,prodQty, prodPrice);
    }

    public List<ProductBean> getInventory(Context context){
        DbHelper dbHelper = new DbHelper(context);
        return dbHelper.getInventory();
    }
}
