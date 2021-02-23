package codeInputView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yfz.yfzcustomview.R;

import utils.YFZDisplayUtils;

public class CodeText extends androidx.appcompat.widget.AppCompatEditText {
    private final String TAG= CodeText.class.getName();
    private final int BOX_FILLED=100,BOX_STROKE=101;
    private final int CODE_TEXT_STYLE_NORMAL=200,CODE_TEXT_STYLE_HIGHLIGHT=201;
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
    private int mBoxStrokeWidth=1;
    //盒子边距
    private int mBoxMargin=10;
    //盒子背景-纯色
    private int mBoxBackgroundColor =Color.RED;
    //盒子颜色
    private int mBoxBackgroundDrawable;
    private Bitmap mBoxBackgroundBitmap;
    //盒子样式（空心实心）
    private int mBoxStrokeMode=BOX_STROKE;
    //盒子圆弧半径
    private float mBoxRadius=5f;
    //文字笔刷
    private Paint mPaintText;
    //文字矩形
    private Rect mTextRect;
    //输入的文字内容-string数组储存
    private String[]passwordArray;
    //view的背景-颜色
    private int mViewBackgroundColor =Color.TRANSPARENT;
    //view的背景-Drawable
    private Drawable mViewBackgroundDrawable;
    //view的模式
    private int mCodeStyle=CODE_TEXT_STYLE_NORMAL;
    //高亮-下坐标
    private int mHighLightIndex =0;
    //高亮-颜色
    private int mHighLightBackgroundColor=Color.BLUE;

    public CodeText(@NonNull Context context) {
        super(context);
        initial(context);
    }
    public CodeText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CodeText);
        mBoxMaxLength=typedArray.getInt(R.styleable.CodeText_codeText_boxLength,mBoxMaxLength);//获取盒子数量（长度）
        mBoxMargin=typedArray.getInt(R.styleable.CodeText_codeText_boxMargin,mBoxMargin);//获取盒子边距
        mBoxSize=typedArray.getInt(R.styleable.CodeText_codeText_boxSize,mBoxSize);//获取盒子大小
        mBoxBackgroundColor =typedArray.getColor(R.styleable.CodeText_codeText_boxBackgroundColor, mBoxBackgroundColor);//获取盒子背景颜色
        mBoxBackgroundDrawable=typedArray.getResourceId(R.styleable.CodeText_codeText_boxBackgroundDrawable,-1);//获取盒子背景Drawable
        mBoxStrokeWidth =typedArray.getInt(R.styleable.CodeText_codeText_boxStrokeWidth, mBoxStrokeWidth);//获取盒子（空心）线粗细程度
        mBoxStrokeMode=typedArray.getInt(R.styleable.CodeText_codeText_boxStrokeStyle, mBoxStrokeMode);//获取盒子（空心）线粗细程度
        mBoxRadius=typedArray.getFloat(R.styleable.CodeText_codeText_boxRadius,mBoxRadius);//获取盒子圆弧半径
        mViewBackgroundColor =typedArray.getInt(R.styleable.CodeText_codeText_backgroundColor, mViewBackgroundColor);//View背景颜色
        mViewBackgroundDrawable=typedArray.getDrawable(R.styleable.CodeText_codeText_backgroundDrawable);//View背景Drawable
        mCodeStyle=typedArray.getInt(R.styleable.CodeText_codeText_Style,mCodeStyle);//View的样式
        mHighLightBackgroundColor=typedArray.getInt(R.styleable.CodeText_codeText_highLightBackgroundColor,mHighLightBackgroundColor);//高亮颜色
        typedArray.recycle();
        initial(context);
    }

    //测量-CodeText大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureMode=MeasureSpec.getMode(widthMeasureSpec);
        switch (measureMode){
            case MeasureSpec.AT_MOST:
                viewWidth = mBoxSize * (mBoxMaxLength) + mBoxMargin * (mBoxMaxLength - 1) +mBoxStrokeWidth;
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

    //初始化-CodeText
    private void initial(Context context){
        this.mContext=context;
        this.setSingleLine();
        this.setCursorVisible(false);
        if (mViewBackgroundDrawable != null) {
            this.setBackgroundDrawable(mViewBackgroundDrawable);
        }else {
            this.setBackgroundColor(mViewBackgroundColor);
        }
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mBoxMaxLength)});
        this.setInputType(getInputType());
        this.passwordArray=new String[mBoxMaxLength];
        this.mBoxSize=YFZDisplayUtils.dip2px(mContext,mBoxSize);
        this.mBoxMargin=YFZDisplayUtils.dip2px(mContext,mBoxMargin);
        this.mBoxRadius=YFZDisplayUtils.dip2pxFloat(mContext,mBoxRadius);
        this.mBoxRectF=new RectF();
        this.mTextRect=new Rect();
        try {
            this.mBoxBackgroundBitmap = BitmapFactory.decodeResource(getResources(), mBoxBackgroundDrawable).copy(Bitmap.Config.RGB_565, false);
        }catch (Exception e){
            Log.e(TAG, "initial: "+e.toString() );
        }
        initialPaint();
    }

    //初始化-笔刷
    private void initialPaint(){
        this.mPaintText=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaintText.setStyle(Paint.Style.FILL);
        this.mPaintText.setTextSize(YFZDisplayUtils.dip2pxFloat(this.getContext(),getTextSize()));
        this.mPaintText.setColor(getCurrentTextColor());
        this.mPaintBox=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaintBox.setStyle(mBoxStrokeMode==BOX_STROKE?Paint.Style.STROKE:Paint.Style.FILL);
        this.mPaintBox.setColor(mBoxBackgroundColor);
        this.mPaintBox.setStrokeWidth(YFZDisplayUtils.dip2pxFloat(this.getContext(),mBoxStrokeWidth));
    }
    //                    if (mBoxBackgroundBitmap != null) {
//                        canvas.drawBitmap(mBoxBackgroundBitmap, mBoxRectF.left, mBoxRectF.right, mPaintBox);
//                    }
    //画布
    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mBoxMaxLength; i++) {
            mBoxRectF.left =(float)( i * (mBoxSize + mBoxMargin) +(mBoxStrokeMode==BOX_STROKE? mBoxStrokeWidth:0 )) ;
            mBoxRectF.right = (float)(mBoxRectF.left + mBoxSize - (mBoxStrokeMode==BOX_STROKE?mBoxStrokeWidth:0 ));
            mBoxRectF.top =(float)( mBoxStrokeMode==BOX_STROKE?mBoxStrokeWidth :0);
            mBoxRectF.bottom = (float)(viewHeight - (mBoxStrokeMode==BOX_STROKE? mBoxStrokeWidth :0));
            if(mCodeStyle==CODE_TEXT_STYLE_HIGHLIGHT && i == mHighLightIndex){
                mPaintBox.setColor(mHighLightBackgroundColor);
            }else{
                mPaintBox.setColor(mBoxBackgroundColor);
            }
            canvas.drawRoundRect(mBoxRectF, mBoxRadius, mBoxRadius, mPaintBox);
            if (null != passwordArray[i]) {
                canvas.drawRoundRect(mBoxRectF, mBoxRadius, mBoxRadius, mPaintBox);
                mPaintText.getTextBounds(passwordArray[i], 0, passwordArray[i].length(), mTextRect);
                canvas.drawText(passwordArray[i], (mBoxRectF.left + mBoxRectF.right) / 2 - (mTextRect.left + mTextRect.right) / 2, (mBoxRectF.top + mBoxRectF.bottom) / 2 - (mTextRect.top + mTextRect.bottom) / 2, mPaintText);
            }
        }
    }

    //检测输入内容，并画在画布上
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        mHighLightIndex =text.length();
        Log.d(TAG, "onTextChanged: 高亮下坐标: "+mHighLightIndex);
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
