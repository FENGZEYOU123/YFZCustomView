package codeInputView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.InputFilter;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yfz.yfzcustomview.R;

/**
 * 简介：进阶自绘codeText
 * 作者：游丰泽
 */
public class CodeText extends androidx.appcompat.widget.AppCompatEditText {
    private Context context;
    private TypedArray typedArray;
    //code数量
    private int mMaxLength;
    //文字画笔
    private Paint mPaintText;
    //背景画笔
    private Paint mPaintBackground;
    //当前EditText内容
    private String text="";
    //文字间距
    private Float textSpace;

    public CodeText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        mPaintText=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBackground=new Paint(Paint.ANTI_ALIAS_FLAG);
        initialXmlValues(attrs);
        initial();
    }
    //初始化xml属性
    private void initialXmlValues(@Nullable AttributeSet attrs){
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.codeText);//TypedArray是一个数组容器
        mMaxLength =typedArray.getInt(R.styleable.codeText_maxNumber,4);
        mPaintBackground.setColor(typedArray.getColor(R.styleable.codeText_backgroundColor, Color.TRANSPARENT));
        mPaintText.setColor(typedArray.getColor(R.styleable.codeText_textColor, Color.BLUE));
        textSpace =typedArray.getFloat(R.styleable.codeText_textSpace,2f);
        setMaxLines(mMaxLength);
        setBackgroundColor(Color.TRANSPARENT);
        typedArray.recycle();
    }
    private void initial(){
        this.setFilters( new InputFilter[]{ new InputFilter.LengthFilter( mMaxLength >=1? mMaxLength :4)});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setLetterSpacing(textSpace);
        }
        this.setCursorVisible(false);
        for(int i=0;i<mMaxLength;i++){
            text=text+"a";
        }
//        this.setText(text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaintBackground);
    }
}
