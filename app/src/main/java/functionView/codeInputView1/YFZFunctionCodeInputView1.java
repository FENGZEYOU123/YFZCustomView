package functionView.codeInputView1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputType;
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

    /**
     * 设置输入框类型-默认是数字
     * @param inputType
     */
    public void setCodeBoxInputType(int inputType){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setInputType(inputType);
        }
    }
    /**
     * 设置输入框宽度
     * @param width
     */
    public void setCodeBoxWidth(int width){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setWidth(width);
        }
    }
    /**
     * 设置输入框高度
     * @param height
     */
    public void setCodeBoxHeight(int height){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setHeight(height);
        }
    }

    /**
     * 设置输入框提示内容
     * @param object
     */
    public void setCodeBoxHintText(Object object){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setHint(object.toString());
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
