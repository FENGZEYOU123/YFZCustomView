package codeInputView;

import android.content.Context;
import android.content.res.TypedArray;
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

import com.yfz.yfzcustomview.R;

import utils.YFZDisplayUtils;

public class PasswordCodeText extends androidx.appcompat.widget.AppCompatEditText {
    private final String TAG= PasswordCodeText.class.getName();
    private final int BOX_FILLED=100,BOX_STROKE=101;
    private Context mContext;
    private int measureMode=0;
    private int viewHeight=0;
    private int viewWidth=0;
    //盒子笔刷
    private Paint mPaintBox;
    //盒子矩形
    private RectF mBoxRectF;
    //盒子数量
    private int mBoxMaxLength=4;
    //盒子长宽大小
    private int mBoxSize=50;
    //盒子（空心）线粗细程度
    private float mBoxStrokeWidth=1;
    //盒子边距
    private int mBoxMargin=10;
    //盒子颜色
    private int mBoxColor=Color.RED;
    //文字笔刷
    private Paint mPaintText;

    //文字矩形
    private Rect mTextRect;
    //当前输入的文字内容-string数组储存
    private String[]passwordArray;
    public PasswordCodeText(@NonNull Context context) {
        super(context);
        initial(context);
    }
    public PasswordCodeText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.PasswordCodeText);
        mBoxMaxLength=typedArray.getInt(R.styleable.PasswordCodeText_boxLength,mBoxMaxLength);//获取盒子数量（长度）
        mBoxMargin=typedArray.getInt(R.styleable.PasswordCodeText_boxMargin,mBoxMargin);//获取盒子边距
        mBoxSize=typedArray.getInt(R.styleable.PasswordCodeText_boxSize,mBoxSize);//获取盒子大小
        mBoxColor=typedArray.getColor(R.styleable.PasswordCodeText_boxColor,mBoxColor);//获取盒子颜色
        mBoxStrokeWidth =typedArray.getFloat(R.styleable.PasswordCodeText_boxStrokeWidth, mBoxStrokeWidth);//获取盒子（空心）线粗细程度
        typedArray.recycle();
        initial(context);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureMode=MeasureSpec.getMode(widthMeasureSpec);
        switch (measureMode){
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
        this.mPaintText.setTextSize(YFZDisplayUtils.dip2pxFloat(this.getContext(),getTextSize()));
        this.mPaintText.setColor(getCurrentTextColor());
        this.mPaintBox=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaintBox.setStyle(Paint.Style.STROKE);
        this.mPaintBox.setColor(mBoxColor);
        this.mPaintBox.setStrokeWidth(YFZDisplayUtils.dip2pxFloat(this.getContext(),mBoxStrokeWidth));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
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
