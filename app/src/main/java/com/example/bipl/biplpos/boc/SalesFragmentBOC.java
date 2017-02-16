package com.example.bipl.biplpos.boc;

import android.content.Context;

import com.example.bipl.biplpos.dao.SalesFragmentDAO;
import com.example.bipl.biplpos.data.SalesBean;

import java.util.List;

/**
 * Created by fahad on 2/16/2017.
 */

public class SalesFragmentBOC {

    public List<SalesBean> getSales(Context context){
        SalesFragmentDAO dao=new SalesFragmentDAO();
        return dao.getSales(context);
    }

    public double totalSales(Context context){
        SalesFragmentDAO dao=new SalesFragmentDAO();
        return dao.totalSales(context);
    }
}
