package com.yfz.yfzcustomview;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import movingView.scale.Component_drawFrame;
import utils.YFZDisplayUtils;
import utils.YFZUtils;

import static android.view.View.GONE;

public class MovingViewActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getName();
    private Context mContext;
    private SurfaceView mSurface;
    private float mX, mY;
    private ConstraintLayout vTopLayout, vTopLine;
    private LinearLayout vSurfaceLayout;
    private int mSurfaceMove_large_l, mSurfaceMove_large_t, mSurfaceMove_large_r, mSurfaceMove_large_b;
    private int mSurfaceOriginalX,mSurfaceOriginalY;
    private Component_drawFrame mDrawFrame;
    private boolean mIsSurfaceInBar=false;
    private boolean mIsFirsTime=true;
    private float mSurfaceOriginal_l,mSurfaceOriginal_t,mSurfaceOriginal_r,mSurfaceOriginal_b;
    private int mSurfaceOriginal_width,mSurfaceOriginal_Height;
    private float mSurfaceConfirm_l,mSurfaceConfirm_t,mSurfaceConfirm_r,mSurfaceConfirm_b;
    private float mDistanceX,mDistanceY;
    private int mSurfaceMarginTop;
    private WebView webview;
    private boolean currentInBar=true;
    private boolean isDouClick=false;
    private boolean mModeScaleTopLeft =false,mModeScaleTopRight=false,mModeScaleBottomLeft=false,mModeScaleBottomRight=false;
    private boolean mModeFullScreen =false;
    private Rect mRectTopLeft =new Rect();
    private Rect mRectTopRight =new Rect();
    private Rect mRectBottomLeft =new Rect();
    private Rect mRectBottomRight =new Rect();

    private int cornerRadius=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mContext=getApplicationContext();
        setContentView(R.layout.activity_main);
        initialView();
        OnListener();
    }




    private void initialView() {
        vTopLayout = this.findViewById(R.id.topLayout);
        mDrawFrame = this.findViewById(R.id.futureMoveView);
        vTopLine =this.findViewById(R.id.topLine);
        webview=this.findViewById(R.id.webview);
        vSurfaceLayout=findViewById(R.id.surfaceLayout);
        webViewSetting();
        cornerRadius=YFZDisplayUtils.dip2px(mContext,cornerRadius);
    }
    private void webViewSetting(){
        WebSettings webSettings = webview.getSettings();
        webSettings.setDomStorageEnabled(true);//是否开启本地DOM存储 防止镶嵌h5页面加载显示白板
        webSettings.setJavaScriptEnabled(true);//允许js
        webSettings.setAllowFileAccessFromFileURLs(true); //允许文件url
        webSettings.setSupportZoom(true);           // 支持缩放
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setUseWideViewPort(true);//设置webview推荐使用的窗口，用于解决页面不适配屏幕
        webSettings.setLoadWithOverviewMode(true);  //所防止屏幕大小
        webSettings.setBlockNetworkLoads(false);  //不阻止网络加载
        webview.setVerticalScrollBarEnabled(false);  //防止滑动
        webview.setHorizontalScrollBarEnabled(false);
        webSettings.setLoadsImagesAutomatically(true);  //自动加载图片资源
        webview.setWebChromeClient(new WebChromeClient());//chrome
        webview.getSettings().setSupportZoom(true);
        webview.loadUrl("https://xhyuat.qyhub.cn/whiteboard/#/?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXBlIjoibWVtYmVyIiwidXNlcklkIjoiODE2MDAwMDAwMzEwMSIsInVzZXJJZFdpdGhvdXRBcmVhQ29kZSI6IjAwMDAwMzEwMSIsImRldmljZUlkIjoiMzMxNzc1Y2VmNzQ2NWNhNCIsInRzIjoxNjE0NzUzMzcxOTQ0LCJtb2JpbGUiOiI4MTYwMDAwMDAwODM1IiwiY2hhbm5lbElkIjoiODE2MC02MDAtMDAzLTAzMSIsImlhdCI6MTYxNDc1MzM3MSwiZXhwIjo5MzkwNzUzMzcxfQ.WkhbXbzBrForEk2ALrtk1_N_QbJtPKYAmPKP3zzOVCg&deviceId=331775cef7465ca4&mid=210303147411&id=CsMRoLhomI&model=android&lang=en-CN");
    }

    private void OnListener() {
        if (null != mSurface) {
            mSurface.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mX =  event.getX();
                            mY =  event.getY();
                            if(mSurfaceOriginal_width<=mSurfaceOriginal_Height){
                                cornerRadius=mSurfaceOriginal_width/3;
                            }else {
                                cornerRadius=mSurfaceOriginal_Height/3;
                            }
                            if(mIsFirsTime){
                                mSurfaceOriginal_l=v.getLeft();
                                mSurfaceOriginal_t=v.getTop();
                                mSurfaceOriginal_r=v.getRight();
                                mSurfaceOriginal_b=v.getBottom();
                                mSurfaceConfirm_l=v.getLeft();
                                mSurfaceConfirm_r=v.getRight();
                                mSurfaceConfirm_t=v.getTop();
                                mSurfaceConfirm_b=v.getBottom();
                                mIsFirsTime=false;
                            }
                            OnTouchDownInitialRect( v, event);

                            break;
                        case MotionEvent.ACTION_MOVE:
                            if(mModeFullScreen) {
                            }else {
                                mDistanceX = (event.getX() - mX);
                                mDistanceY = (event.getY() - mY);
                                if (event.getRawY() < vTopLine.getBottom()) {
                                    mIsSurfaceInBar = true;
                                } else {
                                    mIsSurfaceInBar = false;
                                }
                                if (!mIsSurfaceInBar) {
                                    if (mSurfaceConfirm_l == mSurfaceOriginal_l && !mModeScaleTopLeft) {
                                        Log.d(TAG, "onTouch:从bar移动到H5中");
                                        mSurfaceMove_large_l = (int) (v.getLeft() + mDistanceX - YFZDisplayUtils.dip2px(mContext, 100));
                                        mSurfaceMove_large_t = (int) (v.getTop() + mDistanceY - YFZDisplayUtils.dip2px(mContext, 55));
                                        mSurfaceMove_large_r = (int) (v.getRight() + mDistanceX + YFZDisplayUtils.dip2px(mContext, 100));
                                        mSurfaceMove_large_b = (int) (v.getBottom() + mDistanceY + YFZDisplayUtils.dip2px(mContext, 55));
                                    } else {
                                        if(mModeScaleTopLeft) {
                                            Log.d(TAG, "onTouch:处在H5中，左上角缩放");
                                            mSurfaceMove_large_l = (int) (mSurfaceConfirm_l + mDistanceX);
                                            mSurfaceMove_large_r = (int) (mSurfaceConfirm_r);
                                            mSurfaceMove_large_t = (int) (mSurfaceConfirm_t + mDistanceY);
                                            mSurfaceMove_large_b = (int) (mSurfaceConfirm_b);
                                        }else if(mModeScaleTopRight){
                                            Log.d(TAG, "onTouch:处在H5中，右上角缩放");
                                            mSurfaceMove_large_l = (int) (mSurfaceConfirm_l );
                                            mSurfaceMove_large_r = (int) (mSurfaceConfirm_r+ mDistanceX);
                                            mSurfaceMove_large_t = (int) (mSurfaceConfirm_t + mDistanceY);
                                            mSurfaceMove_large_b = (int) (mSurfaceConfirm_b);
                                        }else if(mModeScaleBottomLeft){
                                            Log.d(TAG, "onTouch:处在H5中，坐下角缩放");
                                            mSurfaceMove_large_l = (int) (mSurfaceConfirm_l + mDistanceX);
                                            mSurfaceMove_large_r = (int) (mSurfaceConfirm_r);
                                            mSurfaceMove_large_t = (int) (mSurfaceConfirm_t);
                                            mSurfaceMove_large_b = (int) (mSurfaceConfirm_b+mDistanceY);
                                        }else if(mModeScaleBottomRight){
                                            Log.d(TAG, "onTouch:处在H5中，右下角缩放");
                                            mSurfaceMove_large_l = (int) (mSurfaceConfirm_l );
                                            mSurfaceMove_large_r = (int) (mSurfaceConfirm_r+ mDistanceX);
                                            mSurfaceMove_large_t = (int) (mSurfaceConfirm_t);
                                            mSurfaceMove_large_b = (int) (mSurfaceConfirm_b+mDistanceY);
                                        }else{
                                            Log.d(TAG, "onTouch:处在H5中，自由平移");
                                            mSurfaceMove_large_l = (int) (mSurfaceConfirm_l + mDistanceX);
                                            mSurfaceMove_large_t = (int) (mSurfaceConfirm_t + mDistanceY);
                                            mSurfaceMove_large_r = (int) (mSurfaceConfirm_r + mDistanceX);
                                            mSurfaceMove_large_b = (int) (mSurfaceConfirm_b + mDistanceY);
                                        }
                                    }
                                    mDrawFrame.refresh(mSurfaceMove_large_l, mSurfaceMove_large_t, mSurfaceMove_large_r, mSurfaceMove_large_b);
                                } else {
                                    Log.d(TAG, "onTouch: Move: 在bar里 ");

                                    mDrawFrame.refresh(
                                            (int) (mSurfaceOriginal_l),
                                            (int) (mSurfaceOriginal_t),
                                            (int) (mSurfaceOriginal_r),
                                            (int) (mSurfaceOriginal_b));
                                }
                            }

                            break;
                        case MotionEvent.ACTION_UP:
                            isDouClick= YFZUtils.isDoubleClick();
                            if(isDouClick) {
                                OnTouchDoubleClick();
                            }else if(mModeScaleTopLeft ||mModeScaleTopRight || mModeScaleBottomLeft || mModeScaleBottomRight){
                                onTouchCorner();
                            }else {
                                if(!mModeFullScreen) {
                                    OnTouchTranslation();
                                }
                            }

                            mSurfaceConfirm_l=v.getLeft();
                            mSurfaceConfirm_r=mSurfaceConfirm_l+v.getWidth();
                            mSurfaceConfirm_t=v.getTop();
                            mSurfaceConfirm_b=mSurfaceConfirm_t+v.getHeight();
                            mDrawFrame.refresh(0, 0, 0, 0);
                            if(event.getRawY()<vTopLine.getBottom()){
                                currentInBar=true;
                            }else {
                                currentInBar=false;
                            }
                            isDouClick=false;
                            mModeScaleTopLeft =false;
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
        }
        if (null != vTopLine) {
            Log.d(TAG, "OnListener: ");
            final boolean[] isFirst = {true};
            vTopLayout.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
                @Override
                public void onDraw() {
                    if (isFirst[0] && vTopLayout.getHeight() > 0) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(YFZDisplayUtils.dip2px(mContext,100), vTopLayout.getHeight());
                        mSurfaceOriginal_width=YFZDisplayUtils.dip2px(mContext,100);
                        mSurfaceOriginal_Height=vTopLayout.getHeight();
                        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(layoutParams);
                        marginLayoutParams.topMargin=vTopLayout.getTop();
                        mSurfaceMarginTop=vTopLayout.getTop();
                        marginLayoutParams.leftMargin=(vTopLayout.getWidth()-YFZDisplayUtils.dip2px(mContext,100))/2;
                        vSurfaceLayout.addView(mSurface, marginLayoutParams);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            mSurface.setForegroundGravity(Gravity.BOTTOM);
                        }
                        isFirst[0] = false;
                    }
                    mSurfaceOriginalX=mSurface.getWidth();
                    mSurfaceOriginalY=mSurface.getHeight();


                }
            });
        }
    }



    /**
     * 点击-拖动
     */
    private synchronized void OnTouchTranslation(){
        if(mIsSurfaceInBar) {
            Log.d(TAG, "onTouch UP: 加载原始大小");
            mSurface.layout((int)mSurfaceOriginal_l, (int)mSurfaceOriginal_t, (int)mSurfaceOriginal_r, (int)mSurfaceOriginal_b);
        }else {
            if(!currentInBar) {
                if (mSurfaceMove_large_t < webview.getTop()) {
                    Log.d(TAG, "onTouch UP:  加载缩放大小 -顶部");
                    mSurfaceMove_large_t = webview.getTop();
                    mSurfaceMove_large_b = webview.getTop() + mSurface.getHeight();
                }
                if (mSurfaceMove_large_l < 0) {
                    Log.d(TAG, "onTouch UP:  加载缩放大小 -左");
                    mSurfaceMove_large_l = 0;
                    mSurfaceMove_large_r = mSurface.getWidth();
                }
                if (mSurfaceMove_large_r > webview.getRight()) {
                    Log.d(TAG, "onTouch UP:  加载缩放大小 -左");
                    mSurfaceMove_large_r = webview.getRight();
                    mSurfaceMove_large_l = mSurfaceMove_large_r - mSurface.getWidth();
                }

                if (mSurfaceMove_large_b > webview.getBottom()) {
                    Log.d(TAG, "onTouch UP:  加载缩放大小 -下");
                    mSurfaceMove_large_b = webview.getBottom();
                    mSurfaceMove_large_t = mSurfaceMove_large_b - mSurface.getHeight();
                }
            }
            mSurface.layout(mSurfaceMove_large_l, mSurfaceMove_large_t, mSurfaceMove_large_r, mSurfaceMove_large_b);
        }
    }

    /**
     * 点击-双击
     */
    private synchronized void OnTouchDoubleClick(){
        if(mSurface.getWidth() != webview.getWidth() && mSurface.getHeight() != webview.getHeight()) {
            mModeFullScreen =true;
            mSurfaceMove_large_l = webview.getLeft();
            mSurfaceMove_large_t = webview.getTop();
            mSurfaceMove_large_r = webview.getRight();
            mSurfaceMove_large_b = webview.getBottom();
            mSurface.layout(mSurfaceMove_large_l, mSurfaceMove_large_t, mSurfaceMove_large_r, mSurfaceMove_large_b);
        }else {
            mSurface.layout((int)mSurfaceOriginal_l, (int)mSurfaceOriginal_t, (int)mSurfaceOriginal_r, (int)mSurfaceOriginal_b);
            mModeFullScreen =false;
        }

    }

    /**
     * 点击-角落
     */
    private synchronized void onTouchCorner(){
        if ((mSurfaceMove_large_r - mSurfaceMove_large_l) <= mSurfaceOriginal_width) {
            if(mModeScaleTopLeft || mModeScaleBottomLeft) {
                mSurfaceMove_large_l = (mSurfaceMove_large_r - mSurfaceOriginal_width);
            }else if(mModeScaleTopRight || mModeScaleBottomRight){
                mSurfaceMove_large_r =(mSurfaceMove_large_l+mSurfaceOriginal_width);
            }
        }
        if ((mSurfaceMove_large_b - mSurfaceMove_large_t) <= mSurfaceOriginal_Height) {
            if(mModeScaleTopLeft || mModeScaleTopRight) {
                mSurfaceMove_large_t =(mSurfaceMove_large_b - mSurfaceOriginal_Height);
            }else if(mModeScaleBottomLeft || mModeScaleBottomRight){
                mSurfaceMove_large_b =(mSurfaceMove_large_t +mSurfaceOriginal_Height);
            }
        }
        mModeScaleTopLeft =false;
        mModeScaleTopRight=false;
        mModeScaleBottomLeft =false;
        mModeScaleBottomRight=false;
        mSurface.layout(mSurfaceMove_large_l, mSurfaceMove_large_t, mSurfaceMove_large_r, mSurfaceMove_large_b);
    }

    /**
     * 点击-初始化角落rect判断是否为缩放点击
     * @param v
     * @param event
     */
    private synchronized void OnTouchDownInitialRect(View v, MotionEvent event){

        mRectTopLeft.left=v.getLeft()-cornerRadius;
        mRectTopLeft.right= mRectTopLeft.left+cornerRadius*2;
        mRectTopLeft.top=v.getTop()-cornerRadius;
        mRectTopLeft.bottom=mRectTopLeft.top+cornerRadius*2;

        mRectTopRight.right= v.getRight()+cornerRadius;
        mRectTopRight.left=mRectTopRight.right-cornerRadius*2;
        mRectTopRight.top=v.getTop()-cornerRadius;
        mRectTopRight.bottom=mRectTopLeft.top+cornerRadius*2;

        mRectBottomLeft.left=v.getLeft()-cornerRadius;
        mRectBottomLeft.right= mRectBottomLeft.left+cornerRadius*2;
        mRectBottomLeft.bottom=v.getBottom()+cornerRadius;
        mRectBottomLeft.top= mRectBottomLeft.bottom-cornerRadius*2;

        mRectBottomRight.right= v.getRight()+cornerRadius;
        mRectBottomRight.left=mRectTopRight.right-cornerRadius*2;
        mRectBottomRight.bottom=v.getBottom()+cornerRadius;
        mRectBottomRight.top= mRectBottomLeft.bottom-cornerRadius*2;


        if(mRectTopLeft.contains((int)event.getRawX(),(int)event.getRawY())){
            Log.d(TAG, "onTouch: down 左上角Mode");
            mModeScaleTopLeft =true;
        }else if(mRectTopRight.contains((int)event.getRawX(),(int)event.getRawY())){
            mModeScaleTopRight=true;
            Log.d(TAG, "onTouch: down 右上角Mode");
        }else if(mRectBottomLeft.contains((int)event.getRawX(),(int)event.getRawY())){
            mModeScaleBottomLeft=true;
            Log.d(TAG, "onTouch: down 左下角Mode");
        }else if(mRectBottomRight.contains((int)event.getRawX(),(int)event.getRawY())){
            mModeScaleBottomRight=true;
            Log.d(TAG, "onTouch: down 右下角Mode");
        }
    }
}

