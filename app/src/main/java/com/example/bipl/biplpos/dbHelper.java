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
        String sql="CREATE TABLE IF NOT EXISTS sales_tbl (NAME text, UNITPRICE integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertProducts(String NAME,String UNITPRICE){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("NAME", NAME);
            contentValues.put("UNITPRICE", Integer.parseInt(UNITPRICE));
            db.insert("sales_tbl", null, contentValues);
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }
    }

    public List<ProductBean> getTable(){
        List<ProductBean> list=new ArrayList<>();
        try{
            ProductBean productBean=new ProductBean();
            String sql="SELECT * FROM sales_tbl";
            SQLiteDatabase db=this.getReadableDatabase();
            Cursor rs=db.rawQuery(sql,null);
            //rs.moveToFirst();
            while(rs.moveToNext()){
                productBean.setNAME(rs.getString(0));
                productBean.setUNITPRICE(rs.getInt(1));
                list.add(productBean);
                Log.e("TAG",list.toString());
            }
            rs.close();
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }
}
