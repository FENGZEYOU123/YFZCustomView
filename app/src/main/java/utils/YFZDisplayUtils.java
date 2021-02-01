package utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class YFZDisplayUtils {
    private static DisplayMetrics dm = new DisplayMetrics();

    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) (dipValue * (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        return (int) (pxValue / ( context.getResources().getDisplayMetrics().scaledDensity)+0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        return (int) (spValue * (context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    /**
     * 获取屏幕尺寸
     * PPI = √（长度像素数² + 宽度像素数²） / 屏幕对角线英寸数
     */

    public static void test(Context context){
    }
}
