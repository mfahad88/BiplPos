package com.example.bipl.biplpos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import data.ProductBean;
import data.SalesBean;

/**
 * Created by fahad on 2/10/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    final static String name="POSdb.db";

    public DbHelper(Context context) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE IF NOT EXISTS inv_tbl (ID INTEGER, NAME text, UNITPRICE REAL)";
        String sql2="CREATE TABLE IF NOT EXISTS sales_tbl (ID integer, NAME text, UNITPRICE REAL)";
        db.execSQL(sql);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertProducts(int Id,String NAME,String UNITPRICE){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID",Id);
            contentValues.put("NAME", NAME);
            contentValues.put("UNITPRICE", Integer.parseInt(UNITPRICE));
            db.insert("inv_tbl", null, contentValues);
            db.close();
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }
    }

    public List<ProductBean> getInventory(){
        List<ProductBean> list=new ArrayList<>();
        try{
            String sql="SELECT * FROM inv_tbl";
            SQLiteDatabase db=this.getReadableDatabase();
            Cursor rs=db.rawQuery(sql,null);
            rs.moveToFirst();
            while(!rs.isAfterLast()){
                ProductBean productBean=new ProductBean();
                productBean.setNAME(rs.getString(1));
                productBean.setUNITPRICE(rs.getInt(2));
                list.add(productBean);
                rs.moveToNext();
            }
            db.close();
            rs.close();
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }

    public List<ProductBean> insertSales(int Id,String NAME,String amount){
        List<ProductBean> list=new ArrayList<>();
        try{

            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID", Id);
            contentValues.put("NAME", NAME);
            contentValues.put("UNITPRICE", Float.parseFloat(amount));
            db.insert("sales_tbl", null, contentValues);
            db.close();
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }

    public List<SalesBean> getSales(){
        List<SalesBean> list=new ArrayList<>();
        try{
            String sql="SELECT * FROM inv_tbl";
            SQLiteDatabase db=this.getReadableDatabase();
            Cursor rs=db.rawQuery(sql,null);
            rs.moveToFirst();
            while(!rs.isAfterLast()){
                SalesBean salesBean=new SalesBean();
                salesBean.setName(rs.getString(1));
                salesBean.setTotalAmount(rs.getInt(2));
                list.add(salesBean);
                rs.moveToNext();
            }
            db.close();
            rs.close();
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }
}