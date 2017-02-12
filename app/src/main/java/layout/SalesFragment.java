package layout;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipl.biplpos.DbHelper;
import com.example.bipl.biplpos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends Fragment{
    TextView totalAmount;
    View view;
    TableLayout tableLayout;
    FrameLayout frag;
    ScrollView scrollView;
    LinearLayout linearLayout;
    TableRow tr;
    TextView name_tv,unit_tv,qty_tv;
    public SalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view=inflater.inflate(R.layout.fragment_sales, container, false);
        frag=(FrameLayout)view.findViewById(R.id.sales_frag);
        totalAmount=(TextView)view.findViewById(R.id.textTotal);
        tableLayout=(TableLayout)view.findViewById(R.id.tableOrder);
        scrollView=(ScrollView)view.findViewById(R.id.scrollViewSales);
        linearLayout=(LinearLayout)view.findViewById(R.id.linearLayoutSales);
        new getProduct().execute();
        return view;
    }


    public void fillTable(String name, String price,String qty){

        tr = new TableRow(view.getContext());

        name_tv = new TextView(view.getContext());
        unit_tv = new TextView(view.getContext());
        qty_tv = new TextView(view.getContext());
        name_tv.setText(String.valueOf(name));
        unit_tv.setText(String.valueOf(price));
        qty_tv.setText(String.valueOf(qty));
        /*name_tv.setWidth(130);
        unit_tv.setWidth(90);
        qty_tv.setWidth(165);*/
        name_tv.setTextSize((float) 20.0);
        unit_tv.setTextSize((float) 20.0);
        qty_tv.setTextSize((float) 20.0);
        name_tv.setGravity(Gravity.CENTER_HORIZONTAL);
        unit_tv.setGravity(Gravity.CENTER_HORIZONTAL);
        qty_tv.setGravity(Gravity.CENTER_HORIZONTAL);
        TableRow.LayoutParams name_params=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams unit_params=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams qty_params=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        name_params.setMargins(20,0,0,20);
        unit_params.setMargins(150,0,0,150);
        qty_params.setMargins(150,0,0,150);
        name_tv.setLayoutParams(name_params);
        unit_tv.setLayoutParams(unit_params);
        qty_tv.setLayoutParams(qty_params);
        tr.addView(name_tv);
        tr.addView(unit_tv);
        tr.addView(qty_tv);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
    }



    public class getProduct extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            DbHelper dbHelper=new DbHelper(view.getContext());
           for(int i=0;i<dbHelper.getSales().size();i++) {
               //Toast.makeText(getContext(),String.valueOf(dbHelper.getSales().get(i).getTotalAmount()), Toast.LENGTH_SHORT).show();
                fillTable(dbHelper.getSales().get(i).getName(),String.valueOf(dbHelper.getSales().get(i).getQty()), String.valueOf(dbHelper.getSales().get(i).getTotalAmount()));
              // totalAmount.setText(String.valueOf(dbHelper.getSales().get(i).getTotalAmount()));
               Log.e("TAG",dbHelper.getSales().get(i).getName()+" "+String.valueOf(dbHelper.getSales().get(i).getQty())+" "+String.valueOf(dbHelper.getSales().get(i).getTotalAmount()));
            }
        }
    }

}
