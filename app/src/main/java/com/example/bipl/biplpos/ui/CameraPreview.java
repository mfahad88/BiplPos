package com.example.bipl.biplpos.ui;

/**
 * Created by fahad on 2/13/2017.
 */

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{

    private static final String TAG = "CameraPreview";

    private SurfaceHolder mHolder;
    private Camera mCamera;
    Context mContext;



    public CameraPreview(Context context, Camera camera) {

        super(context);
        mContext = context;
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        // 3.0 ÀÌÀü ¹öÀü¸¸
//		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    /* (non-Javadoc)
     * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    public void stopPreview(){

        if(mHolder.getSurface()==null){
            return;
        }

        try{
            mCamera.stopPreview();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void startPreview(){

        if(mHolder.getSurface()==null){
            return;
        }

        try{
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    /* (non-Javadoc)
     * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview();
    }

    /* (non-Javadoc)
     * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        try{
            if(mCamera!=null)
                mCamera.stopPreview();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /* (non-Javadoc)
     * @see android.view.SurfaceView#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec));

    }

}