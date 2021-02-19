package functionView.codeInputView1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yfz.yfzcustomview.R;

import utils.YFZUtils;

/**
 * CodeInputView用到的TextView
 * 作者：YFZ
 */
public class CodeInputView1TextBox extends androidx.appcompat.widget.AppCompatEditText {

    private Context context;
    private LinearLayout.LayoutParams textViewLP;
    private final String TAG=CodeInputView1TextBox.class.getName();
    private CodeBoxInputCallBack codeBoxInputCallBack;
    private CodeBoxDeleteCallBack codeBoxDeleteCallBack;
    private int codeBoxBackgroundHasInput= -1;
    private Drawable codeBoxBackgroundHasInputDrawable=null;
    private int codeBoxBackgroundNoInput=-1;
    private Drawable codeBoxBackgroundNoInputDrawable=null;
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
    private void initial(final Context context){
        this.context=context;
        this.setBackgroundColor(Color.GRAY);
        this.setTextColor(Color.WHITE);
        this.textViewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.textViewLP.weight=1;
        this.textViewLP.gravity= Gravity.CENTER;
        this.textViewLP.setMargins(10,10,10,10);
        this.setWidth(100);
        this.setHeight(100);
        this.setMaxLines(1);
        this.setLayoutParams(textViewLP);
        this.setGravity(Gravity.CENTER);
        this.getPaint().setFakeBoldText(true);
        this.setInputType(InputType.TYPE_CLASS_NUMBER);
        this.setFocusable(false);
        this.setFocusableInTouchMode(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        this.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_UP) {
                    addDeleteMonitor(codeBoxDeleteCallBack); //检测是否删除
                }else if(keyCode == KeyEvent.KEYCODE_ENTER&& event.getAction() == KeyEvent.ACTION_UP){
                }
                return false;
            }
        });
    }
    public void setMargin(int left,int top, int right, int bottom){
        this.textViewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.textViewLP.weight=1;
        this.textViewLP.gravity= Gravity.CENTER;
        this.textViewLP.setMargins(left,top,right,bottom);
        this.setLayoutParams(textViewLP);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void addCallBackInput(CodeBoxInputCallBack codeBoxInputCallBack){
        this.codeBoxInputCallBack = codeBoxInputCallBack;
    }
    public void addCallBackDeleted(CodeBoxDeleteCallBack codeBoxDeleteCallBack){
        this.codeBoxDeleteCallBack = codeBoxDeleteCallBack;
    }
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        Log.d(TAG, "onTextChanged: "+text +"  "+start+"   "+lengthBefore+"   "+lengthAfter);
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setMaxLength(text,1); //设置最大输入内容
        if(text.length()>=1){
            this.setBackgroundHasInput(this);
        }else{
            this.setBackgroundNoInput(this);
        }
    }

    private void setMaxLength(CharSequence text, int max){
        if(text.length()==max){
            clearFocus();
            setFocusable(false);
            setFocusableInTouchMode(false);
            if(codeBoxInputCallBack !=null) {
                codeBoxInputCallBack.input(true,this);
            }
        }
    }
    private void addDeleteMonitor(CodeBoxDeleteCallBack codeBoxDeleteCallBack){
        if(codeBoxDeleteCallBack!=null)codeBoxDeleteCallBack.deleted(true,this);
    }

    /**
     * 输入内容后返回回调
     */
    public interface CodeBoxInputCallBack {
        void input(boolean done, View view);
    }

    /**
     * 删除内容后返回回调
     */
    public interface CodeBoxDeleteCallBack {
        void deleted(boolean done, View view);
    }

    public void setBackgroundColorHasInput(int color){
        codeBoxBackgroundHasInput=color;
    }
    public void setBackgroundDrawableHasInput(Drawable drawable){
        codeBoxBackgroundHasInputDrawable=drawable;
    }
    public void setBackgroundColorNoInput(int color){
        codeBoxBackgroundNoInput=color;
    }
    public void setBackgroundDrawableNoInput(Drawable drawable){
        codeBoxBackgroundNoInputDrawable=drawable;
    }

    public void setBackgroundNoInput(CodeInputView1TextBox view){
        if(-1!=codeBoxBackgroundNoInput){
            try {
                view.setBackgroundResource(codeBoxBackgroundNoInput);
            }catch (Exception e){
                try {
                    view.setBackgroundColor(codeBoxBackgroundNoInput);
                }catch (Exception d){
                    codeBoxBackgroundNoInput= -1;
                }
            }
        }else if(null!=codeBoxBackgroundNoInputDrawable){
            view.setBackground(codeBoxBackgroundNoInputDrawable);
        }
    }
    public void setBackgroundHasInput(CodeInputView1TextBox view){

        if(-1!=codeBoxBackgroundHasInput){
            try {
                view.setBackgroundResource(codeBoxBackgroundHasInput);
            }catch (Exception e){
                try {
                    view.setBackgroundColor(codeBoxBackgroundHasInput);
                }catch (Exception d){
                    codeBoxBackgroundNoInput= -1;
                }
            }
        }else if(null!=codeBoxBackgroundHasInputDrawable){
            view.setBackground(codeBoxBackgroundHasInputDrawable);
        }

    }
}
