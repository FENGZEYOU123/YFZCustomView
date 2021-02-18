package functionView.codeInputView2;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;

import functionView.codeInputView1.CodeInputView1TextBox;

/**
 * Code输入框
 */
public class dasdsa extends LinearLayout {
    private Context context;
    private ArrayList<CodeInputView1TextBox> textViewArrayList =new ArrayList();
    private int codeBoxMaxNumber =4;
    public dasdsa(Context context) {
        super(context);
        initial(context);
    }
    public dasdsa(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public dasdsa(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial(context);
    }
    private void initial(Context context) {
        this.context = context;
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.add(new CodeInputView1TextBox(context));
            this.addView( this.textViewArrayList.get(i));
        }
        this.setBackgroundColor(Color.TRANSPARENT);

    }

    public void setCodeBoxMaxNumber(int number){
        this.textViewArrayList.clear();
        this.textViewArrayList =new ArrayList();
        this.codeBoxMaxNumber =number;
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.add(new CodeInputView1TextBox(context));
            this.addView( this.textViewArrayList.get(i));
        }
    }


}
