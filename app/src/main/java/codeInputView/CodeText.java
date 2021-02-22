package codeInputView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import utils.YFZDisplayUtils;

public class CodeText extends androidx.appcompat.widget.AppCompatEditText {
    private final String TAG=CodeText.class.getName();
    private Context mContext;
    private Paint mPaintBox;
    private Paint mPaintText;
    private int mBoxMaxLength=4;
    private int mBoxSize=30;
    private String[]passwordArray;
    private String mText="";
    private String mDrawText="";
    public CodeText(@NonNull Context context) {
        super(context);
        initial(context);
    }
    public CodeText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    private void initial(Context context){
        this.mContext=context;
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setSingleLine();
        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mBoxMaxLength)});
        this.passwordArray=new String[mBoxMaxLength];
        initialPaint();
    }
    private void initialPaint(){
        this.mPaintBox=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaintText=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaintText.setStyle(Paint.Style.FILL);
        this.mPaintText.setTextSize(YFZDisplayUtils.dip2pxFloat(this.getContext(),20f));
        this.mPaintText.setColor(Color.BLACK);
        this.mPaintBox.setStyle(Paint.Style.STROKE);
        this.mPaintBox.setStrokeWidth(2f);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        this.mPaintBox.setColor(Color.RED);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaintBox);

            for (int i = 0; i < mBoxMaxLength; i++) {
                if(null!=passwordArray[i]) {
                    canvas.drawText(passwordArray[i], ((i + 1) * getWidth() / mBoxMaxLength) / 2, getHeight() / 2, mPaintText);
                }
            }
    }



    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if(null!=passwordArray) {
            if (lengthAfter > lengthBefore) {
                for (int i = 0; i < text.length(); i++) {
                    passwordArray[i] = String.valueOf(text.charAt(i));
                }
            } else {
                for (int i = passwordArray.length; i > text.length(); i--) {
                    passwordArray[i-1] = null;
                }
            }
            postInvalidate();
        }
    }

}
