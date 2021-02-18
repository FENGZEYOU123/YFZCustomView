package functionView.codeInputView1;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * CodeInputView用到的TextView
 */
public class CodeInputView1TextView extends androidx.appcompat.widget.AppCompatTextView {

    private Context context;
    private LinearLayout.LayoutParams textViewLP;
    public CodeInputView1TextView(@NonNull Context context) {
        super(context);
        initial(context);
    }
    public CodeInputView1TextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public CodeInputView1TextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial(context);
    }
    private void initial(Context context){
        this.context=context;
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setText("1");
        this.textViewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    public void setWeight(int weight){
        this.textViewLP.weight=weight;
    }
}
