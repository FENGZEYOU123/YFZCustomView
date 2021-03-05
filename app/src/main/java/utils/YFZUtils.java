package utils;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class YFZUtils {
    private static long lastClickTime;

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
     * 是否为双击事件
     * @return
     */
    public synchronized static boolean isDoubleClick(){
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 200) {
            lastClickTime=0;
            return true;
        }
        lastClickTime = time;
        return false;
    }

}



