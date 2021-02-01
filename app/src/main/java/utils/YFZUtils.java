package utils;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Toast;

public class YFZUtils {
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



}



