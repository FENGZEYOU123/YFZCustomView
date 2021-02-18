package functionView.codeInputView1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Code输入框
 */
public class YFZFunctionCodeInputView1 extends LinearLayout {
    private Context context;
    private ArrayList<CodeInputView1TextView> textViewArrayList =new ArrayList();
    private int codeBoxMaxNumber =4;
    public YFZFunctionCodeInputView1(Context context) {
        super(context);
        initial(context);
    }
    public YFZFunctionCodeInputView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public YFZFunctionCodeInputView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial(context);
    }
    private void initial(Context context) {
        this.context = context;
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.add(new CodeInputView1TextView(context));
            this.textViewArrayList.get(i).setWeight(1);
            this.addView( this.textViewArrayList.get(i));
        }
        this.setOrientation(HORIZONTAL);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    public void setCodeBoxMaxNumber(int number){
        this.removeAllViews();
        this.textViewArrayList.clear();
        this.textViewArrayList =new ArrayList();
        this.codeBoxMaxNumber =number;
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.add(new CodeInputView1TextView(context));
            this.textViewArrayList.get(i).setWeight(1);
            this.addView( this.textViewArrayList.get(i));
        }
    }


}
