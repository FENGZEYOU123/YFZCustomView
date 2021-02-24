package codeInputView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.Timer;
import java.util.TimerTask;
import utils.YFZDisplayUtils;

/**
 * 简介：自定义验证码输入框
 * 作者：游丰泽
 */
public class CodeText extends androidx.appcompat.widget.AppCompatEditText {
    /**
     * 主要功能:
     * mEnableHideCode 是否隐藏输入内容
     * mEnableHighLight 是否开启高亮
     * mEnableCursor 是否开启光标
     * mEnableHideNotInputBox 是否将没有输入内容的盒子隐藏
     */
    private boolean mEnableHideCode =false;//是否隐藏输入code
    private boolean mEnableHighLight=false;//是否开启高亮
    private boolean mEnableCursor =false;//是否开启光标
    private boolean mEnableHideNotInputBox=false;//是否将没有输入内容的盒子隐藏

    private final String TAG= CodeText.class.getName();
    private final int PAINT_FILLED =100, PAINT_STROKE =101;
//    private final int CODE_TEXT_STYLE_NORMAL=200,CODE_TEXT_STYLE_HIGHLIGHT=201;
    private final String DEFAULT_HIDE_CONTENT="*";
    private Context mContext;
    private int measureMode=0;
    private int viewHeight=0;
    private int viewWidth=0;
    private OnResultListener mOnResultListener;
    //组件
    private String mHideCodeString;//隐藏输入code-显示的内容
    private int mViewBackground=Color.TRANSPARENT;//背景Drawable
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
    //高亮盒子
    private Paint mBoxHighLightPaint;//笔刷
    private int mBoxHighLightIndex =0;//下坐标
    private int mBoxHighLightBackgroundColor =Color.BLUE;//颜色
    private int mBoxHighLightStrokeStyle = PAINT_STROKE;//高亮样式（空心，实心）
    private int mBoxHighLightStrokeWidth =1;//边框宽度（仅空心）
    private float mBoxHighLightRadius =5f;//圆弧半径
    //输入后的盒子
    private int mBoxAfterStrokeStyle = PAINT_STROKE;//高亮样式（空心，实心）
    private int mBoxAfterBackgroundColor =Color.RED;
    private Paint mBoxAfterPaint;//笔刷
    private int mBoxAfterStrokeWidth;//宽度
    private float mBoxAfterRadius =5f;//圆弧半径

    //文字
    private Paint mPaintText;//笔刷
    private Rect mTextRect;//矩形（绘制位置）
    private String[] mCodeArray;//输入Code内容

    //光标-笔刷
    private Paint mCursorPaint;//笔刷
    private Timer mCursorTimer;//定时器
    private TimerTask mCursorTimerTask;//定时器任务
    private int mCursorStrokeWidth=1;//宽度
    private int mCursorColor=Color.BLACK;//颜色
    private int mCursorHeightPadding=1;//上下边距
    private int mCursorFrequency=500;//闪烁频率
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
        mViewBackground =typedArray.getResourceId(R.styleable.CodeText_codeText_viewBackground,Color.TRANSPARENT);//View背景Drawable
        //控制
        mEnableHideNotInputBox =typedArray.getBoolean(R.styleable.CodeText_codeText_enableHideNotInputBox, mEnableHideNotInputBox);//是否将没有输入内容的盒子隐藏
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
        mBoxHighLightBackgroundColor =typedArray.getInt(R.styleable.CodeText_codeText_boxHighLightBackgroundColor, mBoxHighLightBackgroundColor);//颜色-默认跟盒子一样
        mBoxHighLightStrokeStyle =typedArray.getInt(R.styleable.CodeText_codeText_boxHighLightStrokeStyle, mBoxStrokeStyle);//笔刷样式-默认跟盒子一样
        mBoxHighLightStrokeWidth =typedArray.getInt(R.styleable.CodeText_codeText_boxHighLightStrokeWidth, mBoxStrokeWidth);//空心线粗细-默认跟盒子一样
        mBoxHighLightRadius =typedArray.getFloat(R.styleable.CodeText_codeText_boxHighLightRadius,mBoxRadius);//圆弧半径-默认跟盒子一样
        mEnableHighLight=typedArray.getBoolean(R.styleable.CodeText_codeText_enableHighLight,mEnableHighLight);//开启关闭
        //输入之后的盒子样式
        mBoxAfterStrokeStyle=typedArray.getInt(R.styleable.CodeText_codeText_boxAfterStrokeStyle,mBoxStrokeStyle);//样式-默认跟普通盒子一样
        mBoxAfterBackgroundColor=typedArray.getColor(R.styleable.CodeText_codeText_boxAfterBackgroundColor,mBoxBackgroundColor);//背景颜色-默认跟普通盒子一样
        mBoxAfterRadius=typedArray.getFloat(R.styleable.CodeText_codeText_boxAfterRadius,mBoxRadius);//圆弧半径-默认跟普通盒子一样
        mBoxAfterStrokeWidth =typedArray.getInt(R.styleable.CodeText_codeText_boxAfterStrokeWidth, mBoxStrokeWidth);//空心线粗细-默认跟普通盒子一样
        //控制
        mEnableHideCode =typedArray.getBoolean(R.styleable.CodeText_codeText_enableHideCode, mEnableHideCode);//是否隐藏输入内容
        mHideCodeString=typedArray.getString(R.styleable.CodeText_codeText_enableHideCode_displayContent);//隐藏内容时-显示的文案
        //光标
        mCursorStrokeWidth=typedArray.getInt(R.styleable.CodeText_codeText_cursorStrokeWidth, mCursorStrokeWidth);//线粗细
        mCursorColor=typedArray.getColor(R.styleable.CodeText_codeText_cursorColor, mCursorColor);//颜色
        mEnableCursor =typedArray.getBoolean(R.styleable.CodeText_codeText_enableCursor, mEnableCursor);//开启关闭
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
    @SuppressLint("ResourceType")
    private void initial(Context context){
        this.mContext=context;
        this.setSingleLine();
        this.setCursorVisible(false);
        this.setBackgroundResource(mViewBackground);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mBoxMaxLength)});
        this.setInputType(getInputType());
        this.mCodeArray =new String[mBoxMaxLength];
        this.mBoxSize=YFZDisplayUtils.dip2px(mContext,mBoxSize);
        this.mBoxMargin=YFZDisplayUtils.dip2px(mContext,mBoxMargin);
        this.mBoxRadius=YFZDisplayUtils.dip2pxFloat(mContext,mBoxRadius);
        this.mBoxHighLightRadius =YFZDisplayUtils.dip2pxFloat(mContext, mBoxHighLightRadius);
        this.mBoxAfterRadius =YFZDisplayUtils.dip2pxFloat(mContext, mBoxAfterRadius);


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
        this.mBoxHighLightPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mBoxHighLightPaint.setStyle(mBoxHighLightStrokeStyle == PAINT_STROKE ?Paint.Style.STROKE:Paint.Style.FILL);
        this.mBoxHighLightPaint.setColor(mBoxHighLightBackgroundColor);
        this.mBoxHighLightPaint.setStrokeWidth(YFZDisplayUtils.dip2pxFloat(this.getContext(), mBoxHighLightStrokeWidth));
        //光标
        this.mCursorPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mCursorPaint.setColor(mCursorColor);
        this.mCursorPaint.setStyle(Paint.Style.FILL);
        this.mCursorPaint.setStrokeWidth(mCursorStrokeWidth);
        //输入后
        this.mBoxAfterPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mBoxAfterPaint.setColor(mBoxAfterBackgroundColor);
        this.mBoxAfterPaint.setStyle(mBoxAfterStrokeStyle == PAINT_STROKE ?Paint.Style.STROKE:Paint.Style.FILL);
        this.mBoxAfterPaint.setStrokeWidth(YFZDisplayUtils.dip2pxFloat(this.getContext(), mBoxAfterStrokeWidth));

    }
    //                    if (mBoxBackgroundBitmap != null) {
//                        canvas.drawBitmap(mBoxBackgroundBitmap, mBoxRectF.left, mBoxRectF.right, mPaintBox);
//                    }
    //画布-绘制板
    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mBoxMaxLength; i++) {
            mBoxRectF.left =(float)( i * (mBoxSize + mBoxMargin) +(mBoxStrokeStyle == PAINT_STROKE || mBoxHighLightStrokeStyle == PAINT_STROKE ? mBoxStrokeWidth:0 )) ;
            mBoxRectF.right = (float)(mBoxRectF.left + mBoxSize - (mBoxStrokeStyle == PAINT_STROKE || mBoxHighLightStrokeStyle == PAINT_STROKE ?mBoxStrokeWidth:0 ));
            mBoxRectF.top =(float)( mBoxStrokeStyle == PAINT_STROKE|| mBoxHighLightStrokeStyle == PAINT_STROKE ?mBoxStrokeWidth :0);
            mBoxRectF.bottom = (float)(viewHeight - (mBoxStrokeStyle == PAINT_STROKE|| mBoxHighLightStrokeStyle == PAINT_STROKE ? mBoxStrokeWidth :0));
            if(mEnableHighLight && i == mBoxHighLightIndex){
                canvas.drawRoundRect(mBoxRectF, mBoxHighLightRadius, mBoxHighLightRadius, mBoxHighLightPaint);
                onDrawCursor(canvas,mCursorPaint,mBoxRectF);
            } else if (null != mCodeArray[i]) {
                if(i<mBoxHighLightIndex){
                    canvas.drawRoundRect(mBoxRectF, mBoxAfterRadius, mBoxAfterRadius, mBoxAfterPaint);
                }else {
                    canvas.drawRoundRect(mBoxRectF, mBoxRadius, mBoxRadius, mPaintBox);
                }
                mPaintText.getTextBounds(mEnableHideCode ?mHideCodeString: mCodeArray[i], 0, mCodeArray[i].length(), mTextRect);
                canvas.drawText(mEnableHideCode ?mHideCodeString: mCodeArray[i], (mBoxRectF.left + mBoxRectF.right) / 2 - (mTextRect.left + mTextRect.right) / 2, (mBoxRectF.top + mBoxRectF.bottom) / 2 - (mTextRect.top + mTextRect.bottom) / 2, mPaintText);
            }else if(!mEnableHideNotInputBox){
                mPaintBox.setColor(mBoxBackgroundColor);
                canvas.drawRoundRect(mBoxRectF, mBoxRadius, mBoxRadius, mPaintBox);
            }

//            if(null != mCodeArray[i]mCodeArray[i].length()>0 ){
//                mBoxAfterPaint.setColor(mBoxAfterBackgroundColor);
//                canvas.drawRoundRect(mBoxRectF, mBoxRadius, mBoxRadius, mBoxAfterPaint);
//            }
        }

    }
    //绘制-光标
    private void onDrawCursor(Canvas canvas,Paint paint,RectF rectF){
        if(paint!=null && mEnableCursor){
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
        mBoxHighLightIndex =text.length();
        Log.d(TAG, "onTextChanged: 高亮下坐标: "+ mBoxHighLightIndex);
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
            if( null!=mOnResultListener && text.length()==mBoxMaxLength){ //内容长度与盒子数量一致->返回回调结果
                mOnResultListener.finish(text.toString());
            }
        }
    }

    //开始计时器，开始光标闪烁
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(mEnableCursor) {
            mCursorTimer.scheduleAtFixedRate(mCursorTimerTask, 0, mCursorFrequency);
        }
    }
    //停止计时器，停止光标闪烁
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mCursorTimer.cancel();
    }

    //接口回调输入结果
    public interface OnResultListener {
        void finish(String result);
    }

    //监听接口回调
    public void setOnResultListener(OnResultListener onResultListener){
        this.mOnResultListener=onResultListener;
    }


}
