package com.example.bipl.biplpos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.example.bipl.biplpos.data.ProductBean;
import com.example.bipl.biplpos.data.SalesBean;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

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
        String sql2="CREATE TABLE IF NOT EXISTS sales_tbl (ID integer, NAME text,QTY REAL, AMOUNT REAL)";

        db.execSQL(sql);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS sales_tbl");
        onCreate(db);
    }

    public void insertInventory(int Id,String NAME,String UNITPRICE){
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

    public void insertSales(int Id,String NAME,String Qty,String amount){

        try{

            SQLiteDatabase db=this.getWritableDatabase();

           ContentValues contentValues = new ContentValues();
            contentValues.put("ID", Id);
            contentValues.put("NAME", NAME);
            contentValues.put("QTY", Float.parseFloat(Qty));
            contentValues.put("AMOUNT", Float.parseFloat(amount));
            db.insert("sales_tbl", null, contentValues);
            db.close();
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }

    }

    public List<SalesBean> getSales(){
        List<SalesBean> list=new ArrayList<>();
        try{
            String sql="SELECT * FROM sales_tbl";
            SQLiteDatabase db=this.getReadableDatabase();
            Cursor rs=db.rawQuery(sql,null);
            rs.moveToFirst();
            while(!rs.isAfterLast()){
                SalesBean salesBean=new SalesBean();
                salesBean.setName(rs.getString(1));
                salesBean.setQty(rs.getFloat(2));
                salesBean.setTotalAmount(rs.getFloat(3));
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
    
    public double totalSales(){
        double total = 0.00;
        try{
            String sql="SELECT sum(AMOUNT) FROM sales_tbl";
            SQLiteDatabase db=this.getReadableDatabase();
            Cursor rs=db.rawQuery(sql,null);
            rs.moveToFirst();
            while(!rs.isAfterLast()){
                total=rs.getDouble(0);
                rs.moveToNext();
            }
        }catch (Exception e){
            Log.e("TOTAL SUM: ",e.getMessage());
        }
        return total;
    }

    public List<String> getDebitCard(String panNo) throws Exception{
        String text = null;
        List<String> list=new ArrayList<>();

        String url="http://10.7.255.70:8061/DebitCardWS/rs/webapi/getExpiry/"+panNo;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        InputStream is = httpEntity.getContent();

        BufferedReader br=new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

        if((text=br.readLine())!=null){
            JSONArray array=new JSONArray(text);
            for(int i=0;i<array.length();i++){
                JSONObject obj=array.getJSONObject(i);

                list.add(obj.getString("debitTitle"));
                list.add(obj.getString("panNo"));
                list.add(obj.getString("expiryStatus"));
                list.add(obj.getString("expiryDate"));
            }
        }
        Log.e("List: ",list.toString());
        return list;

    }
}
