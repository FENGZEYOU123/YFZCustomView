package com.yfz;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class YfzUtil {
    private static Activity mActivity;
    private static DisplayMetrics mOutMetrics = new DisplayMetrics();
    private static int mScreenWidth=0;
    private static int mScreenHeight=0;

    /**
     * 设置 activity
     * @param activity
     */
    public static void setActivity(Activity activity){
        mActivity=activity;
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(mOutMetrics);
        mScreenWidth = mOutMetrics.widthPixels;
        mScreenHeight= mOutMetrics.heightPixels;

    }
    /**
     * 吐司
     * @param context
     * @param str
     */
    public static void toast(Context context,String str){
     Toast toast=Toast.makeText(context,str,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,150);
        toast.show();
    }

    /**
     * 控制软键盘弹出
     * @param view
     * @param mContext
     */
    public static void showSoftKeyboard(View view, Context mContext) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }
    /**
     * 控制软键盘关闭
     * @param view
     * @param mContext
     */
    public static void closeSoftKeyBoard(View view, Context mContext){
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 像素
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) (dipValue * (context.getResources().getDisplayMetrics().density) + 0.5f);
    }
    public static float dip2pxFloat(Context context, float dipValue) {
        return (float) (dipValue * (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        return (int) (pxValue / ( context.getResources().getDisplayMetrics().scaledDensity)+0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        return (int) (spValue * (context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    /**
     * 获取屏幕总宽
     */
    public static int getScreenWidth(){
        return mScreenWidth;
    }
    /**
     * 获取屏幕高宽
     */
    public static int getScreenHeight(){
        return mScreenHeight;
    }
}



