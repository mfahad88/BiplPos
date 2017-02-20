package com.example.bipl.biplpos.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipl.biplpos.R;
import com.nurugo.api.NurugoBSP;

import static android.content.ContentValues.TAG;

/**
 * Created by fahad on 2/17/2017.
 */

public class FingerFragment extends DialogFragment {
    static Camera mCamera;
    CameraPreview mCameraPreview;
    FrameLayout mFrameLayout;
    NurugoBSP nurugoBSP;
    Button btn_enroll;
    ImageView img_current;
    Bitmap bm;
    static boolean isCapturing = false;
    View view;



    static Camera getCameraInstance(){

        if(mCamera==null){
            mCamera = Camera.open();
        }
        return mCamera;

    }

    void init(){

        img_current = (ImageView)view.findViewById(R.id.img_current);

        img_current.setImageBitmap(null);


        if(mCamera!=null)
            mCamera.setPreviewCallback(null);

    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(600,900);
        init();
        initCamera();

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    public void onPause() {


        super.onPause();
        releaseCamera();
        System.exit(0);
    }

    /**
     * Camera release
     * void
     */
    public void releaseCamera(){

        mCameraPreview.getHolder().removeCallback(mCameraPreview);
        mCameraPreview = null;
        mFrameLayout.removeAllViews();
        mCamera.setPreviewCallback(null);
        mCamera.release();
        mCamera = null;

    }

    /**
     * camera initialize
     */
    public void initCamera(){

        mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(getContext(), mCamera);
        mFrameLayout.addView(mCameraPreview);
        nurugoBSP.initCameraParam(mCamera);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            view=inflater.inflate(R.layout.fingerdialog,container,false);
            mFrameLayout = (FrameLayout)view.findViewById(R.id.camera_preview);

            nurugoBSP = new NurugoBSP();

            NurugoBSP.InfoData infoData = nurugoBSP.new InfoData();
            final int ret = nurugoBSP.init(infoData);
            try {
            /*    getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(view.getContext(),"Place your finger",Toast.LENGTH_SHORT).show();
                    }
                });*/

            }catch (Exception e){
                Log.e(TAG,e.getMessage());
            }


            new captureTask().execute();


        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }

        return view;
    }



    PreviewCallback mPreviewCallback = new PreviewCallback() {

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            try {

                enroll(data);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.e("Error",e.getMessage());
            }

        }
    };

    void enroll(byte[] data) throws Exception {
        try {

            int ret = -1;

            byte[] outRaw = nurugoBSP.extractYuvToRaw(data, 1);

            bm = nurugoBSP.extractRawToBitmap(outRaw);

            new Handler().post(new Runnable() {

                @Override
                public void run() {

                    img_current.setImageBitmap(bm);
                }
            });
            ret = nurugoBSP.getErrorCode();
            if(ret!=0){
                Toast.makeText(getContext(), getErrorMessage(ret), Toast.LENGTH_SHORT).show();
                Camera.Parameters p = mCamera.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(p);
                mCamera.release();
                getDialog().hide();
             /*   Intent i=new Intent(getActivity(),SelectionActivity.class);
                i.putExtra("ReturnFinger",true);
                startActivity(i);*/

                isCapturing=false;
                releaseCamera();
                onResume();
            }else{
                Toast.makeText(getContext(), String.valueOf(getErrorMessage(ret)), Toast.LENGTH_SHORT).show();
                isCapturing=false;
                releaseCamera();
                onResume();
            }

        }catch (Exception e){
            Log.e("Error",e.getMessage());
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
    private class captureTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mCamera.setPreviewCallback(mPreviewCallback);
        }
    }
}
