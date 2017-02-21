package com.example.bipl.biplpos.ui;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import com.nurugo.api.*;

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
    static Camera mCamera;
    CameraPreview mCameraPreview;
    FrameLayout mFrameLayout;
    NurugoBSP nurugoBSP;
    Button btn_enroll;
    ImageView img_current;
    Bitmap bm;
    static boolean isCapturing = false;
    Dialog dialog;
    FingerFragment newFragment;
    public SalesFragment(UpdatableFragment reportFragment) {
        // Required empty public constructor
        super();
        this.reportFragment=reportFragment;
    }

    public SalesFragment() {
    }

    static Camera getCameraInstance(){

        if(mCamera==null){
            mCamera = Camera.open();
        }
        return mCamera;

    }


    void init(){
        img_current.setImageBitmap(null);
        btn_enroll.setEnabled(true);

        if(mCamera!=null)
            mCamera.setPreviewCallback(null);

    }

    public void releaseCamera(){

        mCameraPreview.getHolder().removeCallback(mCameraPreview);
        mCameraPreview = null;
        mFrameLayout.removeAllViews();
        mCamera.setPreviewCallback(null);
        mCamera.release();
        mCamera = null;

    }

    public void initCamera(){

        mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(view.getContext(), mCamera);
        mFrameLayout.addView(mCameraPreview);

        nurugoBSP.initCameraParam(mCamera);

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
        update();
        btn_finger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    newFragment = new FingerFragment();
                    newFragment.show(getFragmentManager(),null);

                }catch (Exception e){
                    Log.e("Finger Dialog",e.getMessage());
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


    Camera.PreviewCallback mPreviewCallback = new Camera.PreviewCallback() {

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            try {
                enroll(data);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    };

    void enroll(byte[] data) throws Exception {

        int ret = -1;

        byte[] outRaw = nurugoBSP.extractYuvToRaw(data, 1);

        bm = nurugoBSP.extractRawToBitmap(outRaw);

        new Handler().post(new Runnable() {

            @Override
            public void run() {

                img_current.setImageBitmap(bm);
            }
        });
        ret =  nurugoBSP.getErrorCode();
        if(ret!=0){
            Toast.makeText(dialog.getContext(), getErrorMessage(ret), Toast.LENGTH_SHORT).show();
            isCapturing=false;
            releaseCamera();
            onResume();
        }else{
            Toast.makeText(dialog.getContext(), String.valueOf(getErrorMessage(ret)), Toast.LENGTH_SHORT).show();
            isCapturing=false;
            releaseCamera();
            onResume();
        }

    }

    String getErrorMessage(int errorCode){

        String errorMessage = "";

        switch(errorCode){
            case NurugoBSP.NURUGO_ERROR.NONE:
                errorMessage = "SUCCESS";
                break;
            case NurugoBSP.NURUGO_ERROR.INIT_EXCEPTION:
                errorMessage = "INIT_EXCEPTION";
                break;
            case NurugoBSP.NURUGO_ERROR.CONSTRUCTOR_INVALID:
                errorMessage = "CONSTRUCTOR_INVALID";
                break;
            case NurugoBSP.NURUGO_ERROR.CAMERA_SET_EXCEPTION:
                errorMessage = "CAMERA_SET_EXCEPTION";
                break;
            case NurugoBSP.NURUGO_ERROR.CAMERA_SET_INVALID:
                errorMessage = "CAMERA_SET_INVALID";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_JPEG_TO_RAW_EXCEPTION:
                errorMessage = "EXTRACT_JPEG_TO_RAW_EXCEPTION";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_JPEG_TO_RAW_INVALID:
                errorMessage = "EXTRACT_JPEG_TO_RAW_INVALID";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_IMAGE_CENTER_FIND_FAIL:
                errorMessage = "EXTRACT_IMAGE_CENTER_FIND_FAIL";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_VALID_IMAGE_FAIL:
                errorMessage = "EXTRACT_VALID_IMAGE_FAIL";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_RAW_TO_TEMPLATE_EXCEPTION:
                errorMessage = "EXTRACT_RAW_TO_TEMPLATE_EXCEPTION";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_RAW_TO_TEMPLATE_INVALID:
                errorMessage = "EXTRACT_RAW_TO_TEMPLATE_INVALID";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_TEMPLATE_FAIL:
                errorMessage = "EXTRACT_TEMPLATE_FAIL";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_TEMPLATE_QUALITY_FAIL:
                errorMessage = "EXTRACT_TEMPLATE_QUALITY_FAIL";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_RAWS_TO_ROTATION_TEMPLATES_EXCEPTION:
                errorMessage = "EXTRACT_RAWS_TO_ROTATION_TEMPLATES_EXCEPTION";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_RAWS_TO_ROTATION_TEMPLATES_INVALID:
                errorMessage = "EXTRACT_RAWS_TO_ROTATION_TEMPLATES_INVALID";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_TEMPLATES_TO_MERGING_TEMPLATE_EXCEPTION:
                errorMessage = "EXTRACT_TEMPLATES_TO_MERGING_TEMPLATE_EXCEPTION";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_TEMPLATE_MERGING_INVALID:
                errorMessage = "EXTRACT_TEMPLATE_MERGING_INVALID";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_TEMPLATE_MERGING_FAIL:
                errorMessage = "EXTRACT_TEMPLATE_MERGING_FAIL";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_RAW_TO_IMAGE_EXCEPTION:
                errorMessage = "EXTRACT_RAW_TO_IMAGE_EXCEPTION";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_RAW_TO_IMAGE_INVALID:
                errorMessage = "EXTRACT_RAW_TO_IMAGE_INVALID";
                break;
            case NurugoBSP.NURUGO_ERROR.MATCH_RAW_EXCEPTION:
                errorMessage = "MATCH_RAW_EXCEPTION";
                break;
            case NurugoBSP.NURUGO_ERROR.MATCH_RAW_FAIL:
                errorMessage = "MATCH_RAW_FAIL";
                break;
            case NurugoBSP.NURUGO_ERROR.MATCH_RAW_INVALID:
                errorMessage = "MATCH_RAW_INVALID";
                break;
            case NurugoBSP.NURUGO_ERROR.MATCH_TEMPLATE_EXCEPTION:
                errorMessage = "MATCH_TEMPLATE_EXCEPTION";
                break;
            case NurugoBSP.NURUGO_ERROR.MATCH_TEMPLATE_INVALID:
                errorMessage = "MATCH_TEMPLATE_INVALID";
                break;
            case NurugoBSP.NURUGO_ERROR.MATCH_TEMPLATE_FAIL:
                errorMessage = "MATCH_TEMPLATE_FAIL";
                break;
            case NurugoBSP.NURUGO_ERROR.MATCH_TEMPLATES_EXCEPTION:
                errorMessage = "MATCH_TEMPLATES_EXCEPTION";
                break;
            case NurugoBSP.NURUGO_ERROR.MATCH_TEMPLATES_INVALID:
                errorMessage = "MATCH_TEMPLATES_INVALID";
                break;
            case NurugoBSP.NURUGO_ERROR.MATCH_TEMPLATES_FAIL:
                errorMessage = "MATCH_TEMPLATES_FAIL";
                break;
            case NurugoBSP.NURUGO_ERROR.OVERLAP_RAW_EXCEPTION:
                errorMessage = "OVERLAP_RAW_EXCEPTION";
                break;
            case NurugoBSP.NURUGO_ERROR.OVERLAP_RAW_INVALID:
                errorMessage = "OVERLAP_RAW_INVALID";
                break;
            case NurugoBSP.NURUGO_ERROR.OVERLAP_RAW_FAIL:
                errorMessage = "OVERLAP_RAW_FAIL";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_DIRECTION_SEARCH_FAIL:
                errorMessage = "EXTRACT_DIRECTION_SEARCH_FAIL";
                break;
            case NurugoBSP.NURUGO_ERROR.EXTRACT_DIRECTION_SEARCH_INVALID:
                errorMessage = "EXTRACT_DIRECTION_SEARCH_INVALID";
                break;
        }
        return errorMessage;

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
