package codeInputView;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

/**
 * CodeInputView用到的Slide
 * 作者：YFZ
 */
public class YFZCodeInputSlide extends View {
    private Context context;
    private LinearLayout.LayoutParams viewLP;
    public YFZCodeInputSlide(Context context) {
        super(context);
        initial(context);
    }
    public YFZCodeInputSlide(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public YFZCodeInputSlide(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial(context);
    }

    private void initial(Context context){
        this.context=context;
        this.setBackgroundColor(Color.BLACK);
        this.viewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        this.viewLP.setMargins(0,20,0,20);
        this.viewLP.width=1;
        this.setLayoutParams(this.viewLP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.setForegroundGravity(Gravity.CENTER);
        }
        this.setVisibility(View.GONE);
    }
    public void setMargin(int left,int top, int right, int bottom){
        this.viewLP.setMargins(left,top,right,bottom);
        this.setLayoutParams(viewLP);
    }
    public void setIsVisible(boolean isVisible){
        this.setVisibility(isVisible?View.VISIBLE:View.GONE);
    }
    public void setColor(int color){
        this.setBackgroundColor(color);
    }

    public void setWidth(int width){
        this.viewLP.width=width;
        this.setLayoutParams(this.viewLP);
    }

}
