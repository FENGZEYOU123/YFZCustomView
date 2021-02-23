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

import java.util.Timer;
import java.util.TimerTask;

import utils.YFZDisplayUtils;

public class CodeText extends androidx.appcompat.widget.AppCompatEditText {
    private final String TAG= CodeText.class.getName();
    private final int PAINT_FILLED =100, PAINT_STROKE =101;
//    private final int CODE_TEXT_STYLE_NORMAL=200,CODE_TEXT_STYLE_HIGHLIGHT=201;
    private final String DEFAULT_HIDE_CONTENT="*";
    private Context mContext;
    private int measureMode=0;
    private int viewHeight=0;
    private int viewWidth=0;
    //组件
    private boolean mEnableHideCode =false;//是否隐藏输入code
    private String mHideCodeString;//隐藏输入code-显示的内容
    private int mViewBackgroundColor =Color.TRANSPARENT;//背景颜色
    private Drawable mViewBackgroundDrawable;//背景Drawable
//    private int mCodeStyle=CODE_TEXT_STYLE_NORMAL;//组件模式 （正常，高光）
    //盒子
    private Paint mPaintBox;//笔刷
    private RectF mBoxRectF;//矩形（绘制位置）
    private Bitmap mBoxBackgroundBitmap;
    private int mBoxMaxLength=4;//数量
    private int mBoxSize=50;//大小
    private int mBoxStrokeWidth=1;//边框宽度（仅空心）
    private int mBoxMargin=10;//盒子之间的间距
    private int mBoxBackgroundColor =Color.RED;//背景颜色（空心边框，实心背景）
    private int mBoxBackgroundDrawable;//背景Drawable
    private int mBoxStrokeStyle = PAINT_STROKE;//盒子样式（空心，实心）
    private float mBoxRadius=5f;//圆弧半径
    //文字
    private Paint mPaintText;//笔刷
    private Rect mTextRect;//矩形（绘制位置）
    private String[] mCodeArray;//输入Code内容
    //高亮
    private Paint mHighLightPaint;//笔刷
    private boolean mEnableHighLight=false;//是否开启高亮
    private int mHighLightIndex =0;//下坐标
    private int mHighLightBackgroundColor=Color.BLUE;//颜色
    private int mHighLightStrokeStyle = PAINT_STROKE;//高亮样式（空心，实心）
    private int mHighLightStrokeWidth=1;//边框宽度（仅空心）
    private float mHighLightRadius=5f;//圆弧半径
    //光标-笔刷
    private Paint mCursorPaint;//笔刷
    private Timer mCursorTimer;//定时器
    private TimerTask mCursorTimerTask;//定时器任务
    private int mCursorStrokeWidth=1;//宽度
    private int mCursorColor=Color.BLACK;//颜色
    private int mCursorHeightPadding=1;//上下边距
    private int mCursorFrequency=500;//闪烁频率
    private boolean mCursorEnable =true;//是否开启光标
    private boolean mCursorDisplayingByTimer =false;//显示光标-定时器-闪烁效果
    private boolean mCursorDisplayingByIndex =false;//显示光标-第一次下坐标



    public CodeText(@NonNull Context context) {
        super(context);
        initial(context);
    }
    public CodeText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CodeText);
        //view
//                 <attr name="codeText_Style" format="integer">
//            <enum name="Normal" value="200" />
//            <enum name="Highlight" value="201" />
//        </attr>
//        mCodeStyle=typedArray.getInt(R.styleable.CodeText_codeText_Style,mCodeStyle);//View的样式
        mViewBackgroundColor =typedArray.getInt(R.styleable.CodeText_codeText_backgroundColor, mViewBackgroundColor);//View背景颜色
        mViewBackgroundDrawable=typedArray.getDrawable(R.styleable.CodeText_codeText_backgroundDrawable);//View背景Drawable
        //盒子
        mBoxMaxLength=typedArray.getInt(R.styleable.CodeText_codeText_boxLength,mBoxMaxLength);//获取盒子数量（长度）
        mBoxMargin=typedArray.getInt(R.styleable.CodeText_codeText_boxMargin,mBoxMargin);//获取盒子边距
        mBoxSize=typedArray.getInt(R.styleable.CodeText_codeText_boxSize,mBoxSize);//获取盒子大小
        mBoxBackgroundColor =typedArray.getColor(R.styleable.CodeText_codeText_boxBackgroundColor, mBoxBackgroundColor);//获取盒子背景颜色
        mBoxBackgroundDrawable=typedArray.getResourceId(R.styleable.CodeText_codeText_boxBackgroundDrawable,-1);//获取盒子背景Drawable
        mBoxStrokeWidth =typedArray.getInt(R.styleable.CodeText_codeText_boxStrokeWidth, mBoxStrokeWidth);//空心线粗细
        mBoxStrokeStyle =typedArray.getInt(R.styleable.CodeText_codeText_boxStrokeStyle, mBoxStrokeStyle);//笔刷样式
        mBoxRadius=typedArray.getFloat(R.styleable.CodeText_codeText_boxRadius,mBoxRadius);//圆弧半径
        //高亮
        mHighLightBackgroundColor=typedArray.getInt(R.styleable.CodeText_codeText_highLightBackgroundColor,mHighLightBackgroundColor);//颜色-默认跟盒子一样
        mHighLightStrokeStyle =typedArray.getInt(R.styleable.CodeText_codeText_highLightStrokeStyle, mBoxStrokeStyle);//笔刷样式-默认跟盒子一样
        mHighLightStrokeWidth=typedArray.getInt(R.styleable.CodeText_codeText_highLightStrokeWidth, mBoxStrokeWidth);//空心线粗细-默认跟盒子一样
        mHighLightRadius=typedArray.getFloat(R.styleable.CodeText_codeText_highLightRadius,mBoxRadius);//圆弧半径-默认跟盒子一样
        mEnableHighLight=typedArray.getBoolean(R.styleable.CodeText_codeText_enableHighLight,mEnableHighLight);//开启关闭
        //控制
        mEnableHideCode =typedArray.getBoolean(R.styleable.CodeText_codeText_enableHideCode, mEnableHideCode);//是否隐藏输入内容
        mHideCodeString=typedArray.getString(R.styleable.CodeText_codeText_enableHideCode_displayContent);//隐藏内容时-显示的文案
        //光标
        mCursorStrokeWidth=typedArray.getInt(R.styleable.CodeText_codeText_cursorStrokeWidth, mCursorStrokeWidth);//线粗细
        mCursorColor=typedArray.getColor(R.styleable.CodeText_codeText_cursorColor, mCursorColor);//颜色
        mCursorEnable=typedArray.getBoolean(R.styleable.CodeText_codeText_enableCursor,mCursorEnable);//开启关闭
        mCursorHeightPadding=typedArray.getInt(R.styleable.CodeText_codeText_cursorHeightPadding,1);//高度边距
        mCursorFrequency=typedArray.getInt(R.styleable.CodeText_codeText_cursorFrequencyMillisecond,mCursorFrequency);//闪烁频率

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
        this.mCodeArray =new String[mBoxMaxLength];
        this.mBoxSize=YFZDisplayUtils.dip2px(mContext,mBoxSize);
        this.mBoxMargin=YFZDisplayUtils.dip2px(mContext,mBoxMargin);
        this.mBoxRadius=YFZDisplayUtils.dip2pxFloat(mContext,mBoxRadius);
        this.mHighLightRadius=YFZDisplayUtils.dip2pxFloat(mContext,mHighLightRadius);
        this.mBoxRectF=new RectF();
        this.mTextRect=new Rect();
        if(null==this.mHideCodeString){
            this.mHideCodeString=DEFAULT_HIDE_CONTENT;
        }else if(this.mHideCodeString.length()>0) {
            this.mHideCodeString = mHideCodeString.substring(0, 1);
        }
        try {
            this.mBoxBackgroundBitmap = BitmapFactory.decodeResource(getResources(), mBoxBackgroundDrawable).copy(Bitmap.Config.RGB_565, false);
        }catch (Exception e){
            Log.e(TAG, "initial: "+e.toString() );
        }
        mCursorTimerTask = new TimerTask() {
            @Override
            public void run() {
                mCursorDisplayingByTimer = !mCursorDisplayingByTimer;
                postInvalidate();
            }
        };
        mCursorTimer = new Timer();
        initialPaint();
    }

    //初始化-笔刷
    private void initialPaint(){
        //文字
        this.mPaintText=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaintText.setStyle(Paint.Style.FILL);
        this.mPaintText.setTextSize(YFZDisplayUtils.dip2pxFloat(this.getContext(),getTextSize()));
        this.mPaintText.setColor(getCurrentTextColor());
        //盒子
        this.mPaintBox=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaintBox.setStyle(mBoxStrokeStyle == PAINT_STROKE ?Paint.Style.STROKE:Paint.Style.FILL);
        this.mPaintBox.setColor(mBoxBackgroundColor);
        this.mPaintBox.setStrokeWidth(YFZDisplayUtils.dip2pxFloat(this.getContext(),mBoxStrokeWidth));
        //高亮
        this.mHighLightPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mHighLightPaint.setStyle(mHighLightStrokeStyle == PAINT_STROKE ?Paint.Style.STROKE:Paint.Style.FILL);
        this.mHighLightPaint.setColor(mHighLightBackgroundColor);
        this.mHighLightPaint.setStrokeWidth(YFZDisplayUtils.dip2pxFloat(this.getContext(),mHighLightStrokeWidth));
        //光标
        this.mCursorPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mCursorPaint.setColor(mCursorColor);
        this.mCursorPaint.setStyle(Paint.Style.FILL);
        this.mCursorPaint.setStrokeWidth(mCursorStrokeWidth);


    }
    //                    if (mBoxBackgroundBitmap != null) {
//                        canvas.drawBitmap(mBoxBackgroundBitmap, mBoxRectF.left, mBoxRectF.right, mPaintBox);
//                    }
    //画布-绘制板
    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mBoxMaxLength; i++) {
            mBoxRectF.left =(float)( i * (mBoxSize + mBoxMargin) +(mBoxStrokeStyle == PAINT_STROKE || mHighLightStrokeStyle == PAINT_STROKE ? mBoxStrokeWidth:0 )) ;
            mBoxRectF.right = (float)(mBoxRectF.left + mBoxSize - (mBoxStrokeStyle == PAINT_STROKE || mHighLightStrokeStyle == PAINT_STROKE ?mBoxStrokeWidth:0 ));
            mBoxRectF.top =(float)( mBoxStrokeStyle == PAINT_STROKE|| mHighLightStrokeStyle == PAINT_STROKE ?mBoxStrokeWidth :0);
            mBoxRectF.bottom = (float)(viewHeight - (mBoxStrokeStyle == PAINT_STROKE|| mHighLightStrokeStyle == PAINT_STROKE ? mBoxStrokeWidth :0));
            if(mEnableHighLight && i == mHighLightIndex){
                mPaintBox.setColor(mHighLightBackgroundColor);
                canvas.drawRoundRect(mBoxRectF, mHighLightRadius, mHighLightRadius, mHighLightPaint);
                onDrawCursor(canvas,mCursorPaint,mBoxRectF);
            }else{
                mPaintBox.setColor(mBoxBackgroundColor);
                canvas.drawRoundRect(mBoxRectF, mBoxRadius, mBoxRadius, mPaintBox);
            }
            if (null != mCodeArray[i]) {
                canvas.drawRoundRect(mBoxRectF, mBoxRadius, mBoxRadius, mPaintBox);
                mPaintText.getTextBounds(mEnableHideCode ?mHideCodeString: mCodeArray[i], 0, mCodeArray[i].length(), mTextRect);
                canvas.drawText(mEnableHideCode ?mHideCodeString: mCodeArray[i], (mBoxRectF.left + mBoxRectF.right) / 2 - (mTextRect.left + mTextRect.right) / 2, (mBoxRectF.top + mBoxRectF.bottom) / 2 - (mTextRect.top + mTextRect.bottom) / 2, mPaintText);
            }
        }

    }
    //绘制-光标
    private void onDrawCursor(Canvas canvas,Paint paint,RectF rectF){
        if(paint!=null && mCursorEnable){
            mCursorPaint.setColor((mCursorDisplayingByTimer || mCursorDisplayingByIndex) ?mCursorColor:Color.TRANSPARENT);
            canvas.drawRect(
                    (float)((rectF.left+rectF.right)/2-mCursorStrokeWidth),
                    (float)(mCursorHeightPadding<=1?(rectF.top+rectF.bottom)/4:mCursorHeightPadding),
                    (float)( (rectF.left+rectF.right)/2+mCursorStrokeWidth),
                    (float)( rectF.bottom-(mCursorHeightPadding<=1?(rectF.top+rectF.bottom)/4:mCursorHeightPadding))
                    ,paint);
        }
        mCursorDisplayingByIndex=false;
    }

    //检测输入内容，并画在画布上
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        mHighLightIndex =text.length();
        Log.d(TAG, "onTextChanged: 高亮下坐标: "+mHighLightIndex);
        if(null!= mCodeArray) {
            if (lengthAfter > lengthBefore) {
                for (int i = 0; i < text.length(); i++) {
                    mCodeArray[i] = String.valueOf(text.charAt(i));
                }
            } else {
                for (int i = mCodeArray.length; i > text.length(); i--) {
                    mCodeArray[i-1] = null;
                }
            }
            postInvalidate();
            this.mCursorDisplayingByIndex=true;
        }
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //cursorFlashTime为光标闪动的间隔时间
        if(mCursorEnable) {
            mCursorTimer.scheduleAtFixedRate(mCursorTimerTask, 0, mCursorFrequency);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mCursorTimer.cancel();
    }

}
