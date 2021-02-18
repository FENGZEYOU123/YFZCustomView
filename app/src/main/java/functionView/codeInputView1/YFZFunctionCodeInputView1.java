package functionView.codeInputView1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Code输入框
 */
public class YFZFunctionCodeInputView1 extends LinearLayout {
    private Context context;
    private ArrayList<CodeInputView1TextBox> textViewArrayList =new ArrayList();
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
            this.textViewArrayList.add(new CodeInputView1TextBox(context));
            this.addView( this.textViewArrayList.get(i));
        }
        this.setOrientation(HORIZONTAL);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * 设置输入框BOX数量，需要提前设置，不然会重置所有属性
     * @param number
     */
    public void setCodeBoxMaxNumber(int number){
        this.removeAllViews();
        this.textViewArrayList.clear();
        this.textViewArrayList =new ArrayList();
        this.codeBoxMaxNumber =number;
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.add(new CodeInputView1TextBox(context));
            this.addView( this.textViewArrayList.get(i));
        }
    }

    public void setCodeBoxText(Object object){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setText(object.toString());
        }
    }
    /**
     * 设置输入框margin边距
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setCodeBoxMargin(int left, int top, int right, int bottom){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setMargin(left,top,right,bottom);
        }
    }

    /**
     * 设置输入框BOX背景
     * @param backgroundColor
     */
    public void setCodeBoxBackgroundColor(int backgroundColor){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置输入框BOX背景
     * @param backgroundDrawable
     */
    public void setCodeBoxBackgroundDrawable(Drawable backgroundDrawable){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setBackground(backgroundDrawable);
        }
    }
    /**
     * 设置输入框BOX背景
     * @param resId
     */
    public void setCodeBoxBackgroundResource(int resId){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setBackgroundResource(resId);
        }
    }

}
