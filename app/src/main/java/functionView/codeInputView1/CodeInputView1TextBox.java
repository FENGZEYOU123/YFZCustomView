package functionView.codeInputView1;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * CodeInputView用到的TextView
 */
public class CodeInputView1TextBox extends androidx.appcompat.widget.AppCompatTextView {

    private Context context;
    private LinearLayout.LayoutParams textViewLP;
    public CodeInputView1TextBox(@NonNull Context context) {
        super(context);
        initial(context);
    }
    public CodeInputView1TextBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public CodeInputView1TextBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial(context);
    }
    private void initial(Context context){
        this.context=context;
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setText("1");
        this.textViewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.textViewLP.weight=1;
        this.textViewLP.gravity= Gravity.CENTER;
        this.textViewLP.setMargins(10,10,10,10);
        this.setLayoutParams(textViewLP);
        this.setGravity(Gravity.CENTER);
    }
    public void setMargin(int left,int top, int right, int bottom){
//        this.textViewLP.setMargins(left,top,right,bottom);
    }
}
