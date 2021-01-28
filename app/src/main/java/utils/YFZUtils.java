package utils;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class YFZUtils {
    public static void toast(Context context,String str){
     Toast toast=Toast.makeText(context,str,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,150);
        toast.show();
    }

}



