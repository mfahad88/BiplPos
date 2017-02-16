package com.example.bipl.biplpos.ui;


import android.app.Dialog;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.bipl.biplpos.boc.SalesFragmentBOC;
import com.example.bipl.biplpos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends UpdatableFragment{
    TextView totalAmount;
    View view;
    TableLayout tableLayout;
    FrameLayout frag;
    ScrollView scrollView;
    LinearLayout linearLayout;
    TableRow tr;
    TextView name_tv,unit_tv,qty_tv;
    Button btn_finger,btn_qr;
    UpdatableFragment reportFragment;
    private Camera mCamera;
    private CameraPreview mPreview;

    public SalesFragment(UpdatableFragment reportFragment) {
        // Required empty public constructor
        super();
        this.reportFragment=reportFragment;
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
        btn_finger=(Button)view.findViewById(R.id.buttonFinger);
        btn_qr=(Button)view.findViewById(R.id.buttonQR);

        //frag.addView(totalAmount);
        new getProduct().execute();
        btn_finger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showpopup();
                try {
                    mCamera= Camera.open();
                    mPreview=new CameraPreview(getContext(),mCamera);
                }catch (Exception e){
                    Log.e("Error",e.getMessage());
                }

            }
        });
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
        name_params.setMargins(70,0,70,0);
        unit_params.setMargins(60,0,60,0);
        qty_params.setMargins(130,0,130,0);
        name_tv.setLayoutParams(name_params);
        unit_tv.setLayoutParams(unit_params);
        qty_tv.setLayoutParams(qty_params);
        tr.addView(name_tv);
        tr.addView(unit_tv);
        tr.addView(qty_tv);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
    }

    public void showpopup(){
        final Dialog dialog=new Dialog(view.getContext());
        dialog.setContentView(R.layout.fingerdialog);
        dialog.setTitle("Scanning...");
        dialog.setCanceledOnTouchOutside(false);
        ImageView imageView=(ImageView)dialog.findViewById(R.id.imageFinger);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void update() {
        try {
            SalesFragmentBOC boc = new SalesFragmentBOC();
            totalAmount.setText(String.valueOf(boc.totalSales(getContext())));
            new getProduct().execute();
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }


    public class getProduct extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            SalesFragmentBOC boc=new SalesFragmentBOC();

           for(int i=0;i<boc.getSales(getContext()).size();i++) {
                fillTable(boc.getSales(getContext()).get(i).getName(),String.valueOf(boc.getSales(getContext()).get(i).getQty()), String.valueOf(boc.getSales(getContext()).get(i).getTotalAmount()));
            }

        }
    }

}
