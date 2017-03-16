package com.example.bipl.biplpos.ui;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipl.biplpos.boc.SalesFragmentBOC;
import com.example.bipl.biplpos.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nurugo.api.*;
import com.square.MagRead;
import com.square.MagReadListener;

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
    Button btn_finger,btn_qr,btn_debit;
    UpdatableFragment reportFragment;
    Dialog dialog;
    Dialog dialogQr,dialogDebit;
    Boolean isScanned=false;

    private UpdateBytesHandler updateBytesHandler;
    private UpdateBitsHandler updateBitsHandler;
    private String panNo;

    public SalesFragment(UpdatableFragment reportFragment) {
        // Required empty public constructor
        super();
        this.reportFragment=reportFragment;
    }

    public SalesFragment() {
    }

    public static SalesFragment newInstance() {
         return new SalesFragment();
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
        btn_debit=(Button)view.findViewById(R.id.buttonDebit);
        update();
        btn_finger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showDialog();

                }catch (Exception e){
                    Log.e("Finger Dialog",e.getMessage());
                }

            }
        });
        btn_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    showDialogQR();

                    } catch(Exception e){
                        e.getMessage();
                }
            }
        });

        btn_debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    showDialogDebit();
                }catch (Exception e){
                    e.printStackTrace();
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

    public void showDialogQR(){

        dialogQr = new Dialog(view.getContext());
        dialogQr.setContentView(R.layout.fragment_qr);
        dialogQr.show();
        IntentIntegrator qrScan=IntentIntegrator.forSupportFragment(SalesFragment.this);
        qrScan.setPrompt("Scanning..");
        qrScan.setCameraId(0);
        qrScan.setBarcodeImageEnabled(true);
        qrScan.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String prName="";
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
               // Toast.makeText(view.getContext(), "Result Not Found", Toast.LENGTH_LONG).show();
                prName=result.getContents();
                dialogQr.dismiss();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    //JSONObject obj = new JSONObject(result.getContents());

                    prName=result.getContents();
                    dialogQr.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        Toast.makeText(view.getContext(), prName, Toast.LENGTH_LONG).show();
    }

    /*public void showDialogQR(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialogQR");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        QRFragment newFragment = new QRFragment();
        newFragment.show(getFragmentManager(),"dialogQR");
    }*/

    public void showDialog(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        FingerFragment newFragment = new FingerFragment();
        newFragment.show(getFragmentManager(),"dialog");
    }
    ///// for DEBIT CARD////////
    public void showDialogDebit(){


            dialogDebit=new Dialog(view.getContext());
            dialogDebit.setTitle("Scanning Debit Card..");
            dialogDebit.show();


            try {
                MagRead read = new MagRead();
                read.addListener(new MagReadListener() {

                    @Override
                    public void updateBytes(String bytes) {
                        Message msg = new Message();
                        msg.obj = bytes;
                        updateBytesHandler.sendMessage(msg);
                    }

                    @Override
                    public void updateBits(String bits) {
                        Message msg = new Message();
                        msg.obj = bits;
                        updateBitsHandler.sendMessage(msg);

                    }
                });


            updateBytesHandler = new UpdateBytesHandler();
            updateBitsHandler = new UpdateBitsHandler();
            read.start();


        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }
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

    private class UpdateBytesHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            String bytes = (String)msg.obj;
            panNo=bytes.length()>14?bytes:"";

            if(panNo!=null && panNo.length()>14){
                dialogDebit.dismiss();
               // Toast.makeText(view.getContext(), "PAN: "+panNo.substring(panNo.indexOf(";")+1,panNo.indexOf("="))+"\n LENGTH: "+panNo.length(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), SelectionActivity.class);
                i.putExtra("ReturnPAN", panNo.substring(panNo.indexOf(";")+1,panNo.indexOf("=")));
                i.putExtra("ReturnAmount",totalAmount.getText().toString());
                startActivity(i);
            }else{
                Toast.makeText(view.getContext(), "Please Card properly", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private class UpdateBitsHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            String bits = (String)msg.obj;
            //strippedBinaryView.setText(bits);
        }

    }

}
