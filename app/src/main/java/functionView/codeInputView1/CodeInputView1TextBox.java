package functionView.codeInputView1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * CodeInputView用到的TextView
 */
public class CodeInputView1TextBox extends androidx.appcompat.widget.AppCompatEditText{

    private Context context;
    private LinearLayout.LayoutParams textViewLP;
    private final String TAG=CodeInputView1TextBox.class.getName();

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
    }
    public void setMargin(int left,int top, int right, int bottom){
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        Log.d(TAG, "onTextChanged: "+text +"  "+start+"   "+lengthBefore+"   "+lengthAfter);
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        Log.d(TAG, "addTextChangedListener: "+watcher.toString());
        super.addTextChangedListener(watcher);
    }

    @Override
    public boolean hasFocus() {
        Log.d(TAG, "hasFocus: ");
        return super.hasFocus();
    }

    @Override
    public OnFocusChangeListener getOnFocusChangeListener() {
        Log.d(TAG, "getOnFocusChangeListener: ");
        return super.getOnFocusChangeListener();
    }

    @Override
    public int getFocusable() {
        Log.d(TAG, "getFocusable: ");
        return super.getFocusable();
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        Log.d(TAG, "requestFocus: "+direction+"   ");
        return super.requestFocus(direction, previouslyFocusedRect);
    }
}
