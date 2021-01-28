package buttonView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 编写者姓名：游丰泽
 * 功能简介：带有按压交互效果的自定义按钮
 */
public class YFZGestureButton extends ConstraintLayout {
    private Context mContext;
    private TextView mTextView;
    private String mTextName ="";
    private float mTextSize =0;
    private int mTextColor=0;

    public YFZGestureButton(@NonNull Context context) {
        super(context);
        initialView(context);
    }
    public YFZGestureButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialView(context);
    }
    public YFZGestureButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialView(context);
    }

    private void initialView(Context context){
        this.mContext=context;
        this.mTextName ="显示的名字";
        this.mTextSize =10;
        this.mTextColor=Color.BLACK;
        this.setBackgroundColor(Color.BLACK);
        addTextView();
    }

    private void addTextView(){
        if(null==mTextView && null!=mContext){
            mTextView=new TextView(mContext);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity= Gravity.CENTER;
            mTextView.setLayoutParams(layoutParams);
            mTextView.setText(mTextName);
            mTextView.setTextSize(mTextSize);
            mTextView.setTextColor(mTextColor);
            this.addView(mTextView);
        }
    }



}
