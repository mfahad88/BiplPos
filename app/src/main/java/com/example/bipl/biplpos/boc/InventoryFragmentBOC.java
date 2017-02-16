package com.example.bipl.biplpos.boc;

import android.content.Context;
import com.example.bipl.biplpos.dao.InventoryFragmentDAO;
import com.example.bipl.biplpos.data.ProductBean;

import java.util.List;

/**
 * Created by fahad on 2/16/2017.
 */

public class InventoryFragmentBOC  {

    public void insertInventory(Context context, int Id, String inventoryName, String inventoryPrice){
        InventoryFragmentDAO dao=new InventoryFragmentDAO();
        dao.insertInventory(context,Id,inventoryName,inventoryPrice);
    }

    public void insertSales(Context context,int Id_prod,String prodName,String prodQty,String prodPrice){
        InventoryFragmentDAO dao=new InventoryFragmentDAO();
        dao.insertSales(context,Id_prod,prodName,prodQty,prodPrice);
    }

    public List<ProductBean> getInventory(Context context){
        InventoryFragmentDAO dao=new InventoryFragmentDAO();
        return dao.getInventory(context);
    }
}
