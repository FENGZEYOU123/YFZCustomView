package codeInputView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import utils.YFZDisplayUtils;

public class CodeText extends androidx.appcompat.widget.AppCompatEditText {
    private final String TAG=CodeText.class.getName();
    private Context mContext;
    private int viewHeight;
    private int viewWidth;
    private Paint mPaintBox;
    private Paint mPaintText;
    private RectF mBoxRectF;
    private Rect mTextRect;
    private int mBoxMaxLength=4;
    private int mBoxSize=50;
    private int mBoxMargin=10;
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
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        viewHeight =0;
        viewWidth=0;

        switch (widthMode){
            case MeasureSpec.AT_MOST:
                viewWidth = mBoxSize * mBoxMaxLength + mBoxMargin * (mBoxMaxLength - 1);
                viewHeight= mBoxSize;
                break;
            case MeasureSpec.EXACTLY:
                viewWidth=MeasureSpec.getSize(widthMeasureSpec);
                viewHeight=MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }
        setMeasuredDimension(viewWidth, viewHeight);
    }

    private void initial(Context context){
        this.mContext=context;
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setSingleLine();
        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mBoxMaxLength)});
        this.passwordArray=new String[mBoxMaxLength];
        this.mBoxSize=YFZDisplayUtils.dip2px(mContext,mBoxSize);
        this.mBoxMargin=YFZDisplayUtils.dip2px(mContext,mBoxMargin);
        this.mBoxRectF=new RectF();
        this.mTextRect=new Rect();
        initialPaint();
    }

    private void initialPaint(){
        this.mPaintText=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaintText.setStyle(Paint.Style.FILL);
        this.mPaintText.setTextSize(YFZDisplayUtils.dip2pxFloat(this.getContext(),20f));
        this.mPaintText.setColor(Color.BLACK);
        this.mPaintBox=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaintBox.setStyle(Paint.Style.STROKE);
        this.mPaintBox.setStrokeWidth(2f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        this.mPaintBox.setColor(Color.RED);
            for (int i = 0; i < mBoxMaxLength; i++) {
                if(null!=passwordArray[i]) {
                    mBoxRectF.left=i*(mBoxSize+mBoxMargin)+1;
                    mBoxRectF.right=mBoxRectF.left+mBoxSize-2;
                    mBoxRectF.top=1;
                    mBoxRectF.bottom=viewHeight-1;
                    canvas.drawRect(mBoxRectF,mPaintBox);
                    mPaintText.getTextBounds(passwordArray[i],0,passwordArray[i].length(),mTextRect);
                    canvas.drawText(passwordArray[i], (mBoxRectF.left+mBoxRectF.right)/2-(mTextRect.left+mTextRect.right)/2, (mBoxRectF.top+mBoxRectF.bottom) / 2-(mTextRect.top+mTextRect.bottom)/2, mPaintText);
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
