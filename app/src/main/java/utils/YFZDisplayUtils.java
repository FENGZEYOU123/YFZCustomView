package utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import static android.view.View.GONE;

public class YFZDisplayUtils {
    private static DisplayMetrics dm = new DisplayMetrics();

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
     * 沉浸式全屏
     * @param activity
     */
    private void fullScreen(Activity activity){
        View vDecorView = activity.getWindow().getDecorView();
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 21) {
            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            activity.getWindow().setAttributes(params);
        }
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            vDecorView.setSystemUiVisibility(GONE);
        } else {
            vDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }
}
