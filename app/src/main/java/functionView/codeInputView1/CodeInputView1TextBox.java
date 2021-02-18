package functionView.codeInputView1;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * CodeInputView用到的TextView
 */
public class CodeInputView1TextBox extends androidx.appcompat.widget.AppCompatEditText{

    private Context context;
    private LinearLayout.LayoutParams textViewLP;
    private final String TAG=CodeInputView1TextBox.class.getName();
    private CodeBoxCallBack codeBoxCallBack;

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
        this.textViewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.textViewLP.weight=1;
        this.textViewLP.gravity= Gravity.CENTER;
        this.textViewLP.setMargins(10,10,10,10);
        this.setWidth(100);
        this.setHeight(100);
        this.setMaxLines(1);
        this.setLayoutParams(textViewLP);
        this.setGravity(Gravity.CENTER);
        this.setInputType(InputType.TYPE_CLASS_NUMBER);
        this.setFocusable(false);
        this.setFocusableInTouchMode(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
    }
    public void setMargin(int left,int top, int right, int bottom){
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void addCallBack(CodeBoxCallBack codeBoxCallBack){
        this.codeBoxCallBack=codeBoxCallBack;
    }
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        Log.d(TAG, "onTextChanged: "+text +"  "+start+"   "+lengthBefore+"   "+lengthAfter);
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setMaxLength(text,1);
    }

    private void setMaxLength(CharSequence text, int max){
        if(text.length()==max){
            clearFocus();
            setFocusable(false);
            setFocusableInTouchMode(false);
            if(codeBoxCallBack!=null) {
                codeBoxCallBack.back(true,this);
            }
        }
    }

    /**
     * 输入内容后返回回调
     */
    public interface CodeBoxCallBack {
        void back(boolean done, View view);
    }

}
