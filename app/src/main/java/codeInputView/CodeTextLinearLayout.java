package codeInputView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yfz.yfzcustomview.R;

import java.util.Timer;
import java.util.TimerTask;

import utils.OnInputListener;
import utils.SoftKeyBoardListener;
import utils.YFZDisplayUtils;

/**
 * 简介：自定义验证码输入框
 * 作者：游丰泽
 */
public class CodeTextLinearLayout extends LinearLayout {
    /**
     * 主要功能: （以下功能涉及到盒子样式的改变，均可单独控制功能的盒子样式,默认为线，可自定设置backgroundDrawable替代）
     * codeText_box 基础盒子，codeText_boxAfter输入内容后的盒子，codeText_boxHighLight 高亮盒子，codeText_boxLock 锁住状态下盒子
     *
     * mEnableHideCode 是否隐藏输入内容
     * mEnableHighLight 是否开启高亮
     * mEnableCursor 是否开启光标
     * mEnableHideNotInputBox 是否将没有输入内容的盒子隐藏
     * mEnableSoftKeyboardAutoClose 开关自动关闭软键盘
     * mEnableSoftKeyboardAutoShow 开关自动展现软键盘
     * mEnableLockCodeTextIfMaxCode 开关输入内容满足长度后是否锁定
     */
    private boolean mEnableHideCode =false;//是否隐藏输入code
    private boolean mEnableHighLight=false;//是否开启高亮
    private boolean mEnableCursor =false;//是否开启光标
    private boolean mEnableHideNotInputBox=false;//是否将没有输入内容的盒子隐藏
    private boolean mEnableSoftKeyboardAutoShow=false;//是否将没有输入内容的盒子隐藏
    private boolean mEnableSoftKeyboardAutoClose=false;//是否将没有输入内容的盒子隐藏
    private boolean mEnableLockCodeTextIfMaxCode =false;//是否限制输满后锁定view

    private boolean mIsEnableLock=false;
    private boolean mIsLocked=false;
    private boolean mIsCodeFull =false;
    private int mIsFirstTime=0;
    private final String TAG= CodeTextLinearLayout.class.getName();
    private final int PAINT_FILLED =100, PAINT_STROKE =101;
    private final String DEFAULT_HIDE_CONTENT="*";
    private Context mContext;
    private int measureMode=0;
    private int viewHeight=0;
    private int viewWidth=0;
    private OnResultListener mOnResultListener;
    private InputMethodManager inputMethodManager;
    //组件
    private String mHideCodeString;//隐藏输入code-显示的内容
    private int mViewBackground=Color.TRANSPARENT;//背景Drawable
//    private int mCodeStyle=CODE_TEXT_STYLE_NORMAL;//组件模式 （正常，高光）
    //盒子
    private Paint mPaintBox;//笔刷
    private RectF mBoxRectF;//矩形（绘制位置）
    private Rect mBoxRect;//矩形（绘制位置）
    private int mBoxMaxLength=4;//数量
    private int mBoxSize=50;//大小
    private int mBoxStrokeWidth=1;//边框宽度（仅空心）
    private int mBoxMargin=10;//盒子之间的间距
    private int mBoxBackgroundColor =Color.RED;//背景颜色（空心边框，实心背景）
    private Drawable mBoxBackgroundDrawable;//背景Drawable
    private int mBoxStrokeStyle = PAINT_STROKE;//盒子样式（空心，实心）
    private float mBoxRadius=5f;//圆弧半径
    //高亮盒子
    private Paint mBoxHighLightPaint;//笔刷
    private int mBoxHighLightIndex =0;//下坐标
    private Drawable mBoxHighBackgroundDrawable;//背景
    private int mBoxHighLightBackgroundColor =Color.BLUE;//颜色
    private int mBoxHighLightStrokeStyle = PAINT_STROKE;//高亮样式（空心，实心）
    private int mBoxHighLightStrokeWidth =1;//边框宽度（仅空心）
    private float mBoxHighLightRadius =5f;//圆弧半径
    //输入后的盒子
    private int mBoxAfterStrokeStyle = PAINT_STROKE;//高亮样式（空心，实心）
    private int mBoxAfterBackgroundColor =Color.RED;//背景s
    private Drawable mBoxAfterBackgroundDrawable;//背景
    private Paint mBoxAfterPaint;//笔刷
    private int mBoxAfterStrokeWidth;//宽度
    private float mBoxAfterRadius =5f;//圆弧半径
    //文字
    private Paint mPaintText;//笔刷
    private Rect mTextRect;//矩形（绘制位置）
    private String[] mCodeArray;//输入Code内容
    private int mTextColor=Color.BLACK;//颜色
    private int mTextSizePx =10;//文字大小
    private boolean mTextBold=true;//文字粗细

    //光标-笔刷
    private Paint mCursorPaint;//笔刷
    private Timer mCursorTimer;//定时器
    private TimerTask mCursorTimerTask;//定时器任务
    private int mCursorStrokeWidth=1;//宽度
    private int mCursorBackgroundColor =Color.BLACK;//颜色
    private Drawable mCursorBackgroundDrawable;//背景
    private int mCursorHeightPadding=1;//上下边距
    private int mCursorFrequency=500;//闪烁频率
    private boolean mCursorDisplayingByTimer =false;//显示光标-定时器-闪烁效果
    private boolean mCursorDisplayingByIndex =false;//显示光标-第一次下坐标
    //锁定盒子
    private int mBoxLockBackgroundColor =-1; //背景
    private int mBoxLockStrokeStyle = PAINT_STROKE;//高亮样式（空心，实心）
    private int mBoxLockTextColor = -1;//文字颜色
    private Drawable  mBoxLockBackgroundDrawable;
    private Paint mBoxLockPaint;//笔刷



    public CodeTextLinearLayout(@NonNull Context context) {
        super(context);
        initial(context);
    }
    public CodeTextLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CodeTextLinearLayout);
        mViewBackground =typedArray.getResourceId(R.styleable.CodeTextLinearLayout_codeTextLayout_viewBackground,Color.TRANSPARENT);//View背景Drawable
        //文字颜色
        mTextColor=typedArray.getColor(R.styleable.CodeTextLinearLayout_codeTextLayout_textColor,mTextColor);
        mTextSizePx =typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_textColor, mTextSizePx);
        mTextBold=typedArray.getBoolean(R.styleable.CodeTextLinearLayout_codeTextLayout_textBold, mTextBold);
        //控制
        mEnableSoftKeyboardAutoShow=typedArray.getBoolean(R.styleable.CodeTextLinearLayout_codeTextLayout_enableSoftKeyboardAutoShow, mEnableSoftKeyboardAutoShow);//自动弹出键盘
        mEnableSoftKeyboardAutoClose =typedArray.getBoolean(R.styleable.CodeTextLinearLayout_codeTextLayout_enableSoftKeyboardAutoClose, mEnableSoftKeyboardAutoClose);//自动隐藏键盘
        mEnableHideCode =typedArray.getBoolean(R.styleable.CodeTextLinearLayout_codeTextLayout_enableHideCode, mEnableHideCode);//是否隐藏输入内容
        mHideCodeString=typedArray.getString(R.styleable.CodeTextLinearLayout_codeTextLayout_enableHideCode_displayContent);//隐藏内容时-显示的文案
        mEnableHideNotInputBox =typedArray.getBoolean(R.styleable.CodeTextLinearLayout_codeTextLayout_enableHideNotInputBox, mEnableHideNotInputBox);//是否将没有输入内容的盒子隐藏
        mEnableHighLight=typedArray.getBoolean(R.styleable.CodeTextLinearLayout_codeTextLayout_enableHighLight,mEnableHighLight);//开启关闭
        mEnableCursor =typedArray.getBoolean(R.styleable.CodeTextLinearLayout_codeTextLayout_enableCursor, mEnableCursor);//开启关闭
        mEnableLockCodeTextIfMaxCode =typedArray.getBoolean(R.styleable.CodeTextLinearLayout_codeTextLayout_enableLockTextView, mEnableLockCodeTextIfMaxCode);//开启关闭
        //盒子
        mBoxMaxLength=typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_boxLength,mBoxMaxLength);//获取盒子数量（长度）
        mBoxMargin=typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_boxMargin,mBoxMargin);//获取盒子边距
        mBoxSize=typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_boxSize,mBoxSize);//获取盒子大小
        if(mBoxSize<50)mBoxSize=50;
        mBoxBackgroundColor =typedArray.getColor(R.styleable.CodeTextLinearLayout_codeTextLayout_boxBackgroundColor, mBoxBackgroundColor);//获取盒子背景颜色
        mBoxBackgroundDrawable=typedArray.getDrawable(R.styleable.CodeTextLinearLayout_codeTextLayout_boxBackgroundDrawable);//获取盒子背景Drawable
        mBoxStrokeWidth =typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_boxStrokeWidth, mBoxStrokeWidth);//空心线粗细
        mBoxStrokeStyle =typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_boxStrokeStyle, mBoxStrokeStyle);//笔刷样式
        mBoxRadius=typedArray.getFloat(R.styleable.CodeTextLinearLayout_codeTextLayout_boxRadius,mBoxRadius);//圆弧半径
        //高亮
        mBoxHighBackgroundDrawable =typedArray.getDrawable(R.styleable.CodeTextLinearLayout_codeTextLayout_boxHighLightBackgroundDrawable);//背景
        mBoxHighLightBackgroundColor =typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_boxHighLightBackgroundColor, mBoxHighLightBackgroundColor);//颜色-默认跟盒子一样
        mBoxHighLightStrokeStyle =typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_boxHighLightStrokeStyle, mBoxStrokeStyle);//笔刷样式-默认跟盒子一样
        mBoxHighLightStrokeWidth =typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_boxHighLightStrokeWidth, mBoxStrokeWidth);//空心线粗细-默认跟盒子一样
        mBoxHighLightRadius =typedArray.getFloat(R.styleable.CodeTextLinearLayout_codeTextLayout_boxHighLightRadius,mBoxRadius);//圆弧半径-默认跟盒子一样

        //输入之后的盒子样式
        mBoxAfterStrokeStyle=typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_boxAfterStrokeStyle,mBoxStrokeStyle);//样式-默认跟普通盒子一样
        mBoxAfterBackgroundColor=typedArray.getColor(R.styleable.CodeTextLinearLayout_codeTextLayout_boxAfterBackgroundColor,mBoxBackgroundColor);//背景颜色-默认跟普通盒子一样
        mBoxAfterBackgroundDrawable=typedArray.getDrawable(R.styleable.CodeTextLinearLayout_codeTextLayout_boxAfterBackgroundDrawable);//背景
        mBoxAfterRadius=typedArray.getFloat(R.styleable.CodeTextLinearLayout_codeTextLayout_boxAfterRadius,mBoxRadius);//圆弧半径-默认跟普通盒子一样
        mBoxAfterStrokeWidth =typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_boxAfterStrokeWidth, mBoxStrokeWidth);//空心线粗细-默认跟普通盒子一样
        //光标
        mCursorStrokeWidth=typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_cursorStrokeWidth, mCursorStrokeWidth);//线粗细
        mCursorBackgroundColor =typedArray.getColor(R.styleable.CodeTextLinearLayout_codeTextLayout_cursorBackgroundColor, mCursorBackgroundColor);//颜色
        mCursorHeightPadding=typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_cursorHeightPadding,1);//高度边距
        mCursorFrequency=typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_cursorFrequencyMillisecond,mCursorFrequency);//闪烁频率
        mCursorBackgroundDrawable=typedArray.getDrawable(R.styleable.CodeTextLinearLayout_codeTextLayout_cursorBackgroundDrawable);//背景
        //锁定
        mBoxLockBackgroundColor=typedArray.getColor(R.styleable.CodeTextLinearLayout_codeTextLayout_boxLockBackgroundColor, mBoxLockBackgroundColor);//颜色
        mBoxLockStrokeStyle=typedArray.getInt(R.styleable.CodeTextLinearLayout_codeTextLayout_boxLockStrokeStyle,mBoxLockStrokeStyle);//空心实心
        mBoxLockTextColor=typedArray.getColor(R.styleable.CodeTextLinearLayout_codeTextLayout_boxLockTextColor, mBoxLockTextColor);//颜色
        mBoxLockBackgroundDrawable=typedArray.getDrawable(R.styleable.CodeTextLinearLayout_codeTextLayout_boxLockBackgroundDrawable);//背景

        typedArray.recycle();
        initial(context);
    }

    //测量-CodeText大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureMode=MeasureSpec.getMode(widthMeasureSpec);

        switch (measureMode) {
            case MeasureSpec.AT_MOST:
                viewWidth = mBoxSize * (mBoxMaxLength) + mBoxMargin * (mBoxMaxLength - 1) + mBoxStrokeWidth;
                viewHeight = mBoxSize;
                break;
            case MeasureSpec.EXACTLY:
//                viewWidth = MeasureSpec.getSize(widthMeasureSpec);
//                if(viewWidth<mBoxSize * (mBoxMaxLength) + mBoxMargin * (mBoxMaxLength - 1) +mBoxStrokeWidth) {
                viewWidth = mBoxSize * (mBoxMaxLength) + mBoxMargin * (mBoxMaxLength - 1) + mBoxStrokeWidth;
//        }
                viewHeight = mBoxSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.d(TAG, "onMeasure:UNSPECIFIED ");
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
        inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        this.setBackgroundResource(mViewBackground);
        this.mCodeArray =new String[mBoxMaxLength];
        this.mBoxSize=YFZDisplayUtils.dip2px(mContext,mBoxSize);
        this.mBoxMargin=YFZDisplayUtils.dip2px(mContext,mBoxMargin);
        this.mBoxRadius=YFZDisplayUtils.dip2pxFloat(mContext,mBoxRadius);
        this.mBoxHighLightRadius =YFZDisplayUtils.dip2pxFloat(mContext, mBoxHighLightRadius);
        this.mBoxAfterRadius =YFZDisplayUtils.dip2pxFloat(mContext, mBoxAfterRadius);
        this.mBoxRectF=new RectF();
        this.mBoxRect=new Rect();
        this.mTextRect=new Rect();
        this.mIsEnableLock=mEnableLockCodeTextIfMaxCode;
        if(null==this.mHideCodeString){
            this.mHideCodeString=DEFAULT_HIDE_CONTENT;
        }else if(this.mHideCodeString.length()>0) {
            this.mHideCodeString = mHideCodeString.substring(0, 1);
        }
        mCursorTimerTask = new TimerTask() {
            @Override
            public void run() {
                mCursorDisplayingByTimer = !mCursorDisplayingByTimer;
                postInvalidate();
            }
        };
        mCursorTimer = new Timer();
        initialPaint();//初始化笔刷
        treeObserver();//监听view是否渲染完毕
        touchListener();//点击触发打开软键盘（未锁定状态下有效）
        layoutChangeListener();
        onKeyListener();//监听键盘按钮

    }
    private void onKeyListener(){
        this.setOnKeyListener(new SoftKeyBoardListener(this,new OnInputListener() {
            @Override
            public void inputNumber(int number) {
                Log.d(TAG, "inputNumber: "+number);
                inputAddOperator(String.valueOf(number));
            }

            @Override
            public void inputLetter(String str, boolean isSingleLetter) {
                Log.d(TAG, "inputLetter: "+str+" "+isSingleLetter);
                    inputAddOperator(str);
            }

            @Override
            public void isDeleteClick(boolean isClick) {
                Log.d(TAG, "IsDeleteClick: "+isClick);
                if(isClick) { inputDeleteOperator(); }
            }

            @Override
            public void isBackClick(boolean isClick) {
                Log.d(TAG, "isBackClick: "+isClick);
                closeSoftKeyboard();
            }
        }));
    }

    private void inputAddOperator(String str){
        mBoxHighLightIndex =str.length();
        if(null != mCodeArray && str.length()>=1) {
                for (int i = 0; i < mCodeArray.length; i++) {
                    if(str.length()==1) {
                        if (mCodeArray[i] == null) {
                            mCodeArray[i] = str;
                            if (i == mBoxHighLightIndex) {
                                mIsCodeFull = true;
                            }
                            break;
                        }
                    }else {
                        if(i<str.length()) {
                            mCodeArray[i] = str.substring(i, i + 1);
                        }
                    }
                }

            this.mCursorDisplayingByIndex=true;
            postInvalidate();
        }
    }
    private void inputDeleteOperator(){
        if(null != mCodeArray) {
            for(int i=mCodeArray.length-1;i>=0;i--){
                if(mCodeArray[i]!=null){
                    mCodeArray[i]=null;
                    break;
                }
            }
            mIsCodeFull=false;
            this.mCursorDisplayingByIndex=true;
            postInvalidate();
        }
    }
    //检测输入内容，并画在画布上
//    @Override
//    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
//        super.onTextChanged(text, start, lengthBefore, lengthAfter);
//        mBoxHighLightIndex =text.length();
//        Log.d(TAG, "onTextChanged: 高亮下坐标: "+ mBoxHighLightIndex);
//        if(null!= mCodeArray) {
//            if (lengthAfter > lengthBefore) {
//                for (int i = 0; i < text.length(); i++) {
//                    mCodeArray[i] = String.valueOf(text.charAt(i));
//                }
//            } else {
//                for (int i = mCodeArray.length; i > text.length(); i--) {
//                    mCodeArray[i-1] = null;
//                }
//            }
//            this.mCursorDisplayingByIndex=true;
//            if( text.length()==mBoxMaxLength){ //内容长度与盒子数量一致->返回回调结果
//                mIsCodeFull = true;
//            if(null!=mOnResultListener) {
//                mOnResultListener.finish(text.toString());
//               }
//            if(mEnableSoftKeyboardAutoClose || mIsEnableLock){
//                closeSoftKeyboard();
//              }
//                mIsLocked = mEnableLockCodeTextIfMaxCode ? true : false;
//            }
//            postInvalidate();
//        }
//    }
    private void layoutChangeListener(){
        this.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.d(TAG, "onLayoutChange: "+v.toString() +" "+left+" "+top+" "+right+" "+bottom+"      "+oldLeft+" "+oldTop+" "+oldRight+" "+oldBottom);
            }
        });
    }

    private void touchListener(){
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(!mIsLocked) openSoftKeyboard();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: ");
        return super.onKeyDown(keyCode, event);
    }

    //锁定CodeText
    public void setOnLock(){
        mEnableLockCodeTextIfMaxCode=true;
        mIsLocked=true;
    }
    //解除锁定CodeText
    public void setUnLock(){
//        mEnableLockCodeTextIfMaxCode=false;
        mIsLocked=false;
        openSoftKeyboard();
    }

    //监听View是否渲染完成
    private void treeObserver(){
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(!mIsCodeFull && mIsFirstTime<=3) {
                    openSoftKeyboard();
                    mIsFirstTime++;
                }
            }
        });
    }

    //初始化-笔刷
    private void initialPaint(){
        //文字
        this.mPaintText=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaintText.setStyle(Paint.Style.FILL);
        this.mPaintText.setColor(mTextColor);
        this.mPaintText.setTextSize(YFZDisplayUtils.dip2px(mContext, mTextSizePx)*2);
        this.mPaintText.setFakeBoldText(mTextBold);
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
        this.mCursorPaint.setColor(mCursorBackgroundColor);
        this.mCursorPaint.setStyle(Paint.Style.FILL);
        this.mCursorPaint.setStrokeWidth(mCursorStrokeWidth);
        //输入后
        this.mBoxAfterPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mBoxAfterPaint.setColor(mBoxAfterBackgroundColor);
        this.mBoxAfterPaint.setStyle(mBoxAfterStrokeStyle == PAINT_STROKE ?Paint.Style.STROKE:Paint.Style.FILL);
        this.mBoxAfterPaint.setStrokeWidth(YFZDisplayUtils.dip2pxFloat(this.getContext(), mBoxAfterStrokeWidth));
    }

    //画布-绘制板
    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mBoxMaxLength; i++) {
            mBoxRectF.left =(float)( i * (mBoxSize + mBoxMargin) +(mBoxStrokeStyle == PAINT_STROKE || mBoxHighLightStrokeStyle == PAINT_STROKE ? mBoxStrokeWidth:0 )) ;
            mBoxRectF.top =(float)( mBoxStrokeStyle == PAINT_STROKE|| mBoxHighLightStrokeStyle == PAINT_STROKE ?mBoxStrokeWidth :0);
            mBoxRectF.right = (float)(mBoxRectF.left + mBoxSize - (mBoxStrokeStyle == PAINT_STROKE || mBoxHighLightStrokeStyle == PAINT_STROKE ?mBoxStrokeWidth:0 ));
            mBoxRectF.bottom = (float)(viewHeight - (mBoxStrokeStyle == PAINT_STROKE|| mBoxHighLightStrokeStyle == PAINT_STROKE ? mBoxStrokeWidth :0));

            mBoxRect.left=i * (mBoxSize + mBoxMargin) ;
            mBoxRect.top=0 ;
            mBoxRect.right=(int) mBoxRect.left+ mBoxSize;
            mBoxRect.bottom=(int)viewHeight ;
            if(mEnableHighLight && i == mBoxHighLightIndex){
                if(null!= mBoxHighBackgroundDrawable) {  //如果有规定drawable，则使用drawable
                    mBoxHighBackgroundDrawable.setBounds(mBoxRect);
                    mBoxHighBackgroundDrawable.draw(canvas);
                }else {
                    canvas.drawRoundRect(mBoxRectF, mBoxHighLightRadius, mBoxHighLightRadius, mBoxHighLightPaint);
                }

                    onDrawCursor(canvas, mCursorPaint, mBoxRectF,mBoxRect);

            } else if (null != mCodeArray[i]) {

                if(i<mBoxHighLightIndex){
                    mBoxAfterPaint.setStyle(mIsLocked?mBoxLockStrokeStyle == PAINT_STROKE ?Paint.Style.STROKE:Paint.Style.FILL:mBoxAfterStrokeStyle == PAINT_STROKE ?Paint.Style.STROKE:Paint.Style.FILL);
                    mBoxAfterPaint.setColor((mIsLocked&&mBoxLockBackgroundColor!=-1)?mBoxLockBackgroundColor:mBoxAfterBackgroundColor);
                    if(null!=mBoxAfterBackgroundDrawable) {  //如果有规定drawable，则使用drawable
                        if(mIsLocked){
                            if(null!=mBoxLockBackgroundDrawable){
                                mBoxLockBackgroundDrawable.setBounds(mBoxRect);
                                mBoxLockBackgroundDrawable.draw(canvas);
                            }else {
                                canvas.drawRoundRect(mBoxRectF, mBoxAfterRadius, mBoxAfterRadius, mBoxAfterPaint);
                            }
                        }else {
                            mBoxAfterBackgroundDrawable.setBounds(mBoxRect);
                            mBoxAfterBackgroundDrawable.draw(canvas);
                        }
                    }else {
                        canvas.drawRoundRect(mBoxRectF, mBoxAfterRadius, mBoxAfterRadius, mBoxAfterPaint);
                    }
                }else {
                    mPaintBox.setStyle(mIsLocked?mBoxLockStrokeStyle == PAINT_STROKE ?Paint.Style.STROKE:Paint.Style.FILL:mBoxAfterStrokeStyle == PAINT_STROKE ?Paint.Style.STROKE:Paint.Style.FILL);
                    mPaintBox.setColor((mIsLocked&&mBoxLockBackgroundColor!=-1)?mBoxLockBackgroundColor:mBoxBackgroundColor);
                    canvas.drawRoundRect(mBoxRectF, mBoxRadius, mBoxRadius, mPaintBox);
                }
                mPaintText.setColor((mIsLocked&&mBoxLockTextColor!=-1)?mBoxLockTextColor: mTextColor);
                mPaintText.getTextBounds(mEnableHideCode ?mHideCodeString: mCodeArray[i], 0, mCodeArray[i].length(), mTextRect);
                canvas.drawText(mEnableHideCode ?mHideCodeString: mCodeArray[i], (mBoxRectF.left + mBoxRectF.right) / 2 - (mTextRect.left + mTextRect.right) / 2, (mBoxRectF.top + mBoxRectF.bottom) / 2 - (mTextRect.top + mTextRect.bottom) / 2, mPaintText);
            }else if(!mEnableHideNotInputBox){
                if(null!=mBoxBackgroundDrawable) {  //如果有规定drawable，则使用drawable
                    mBoxBackgroundDrawable.setBounds(mBoxRect);
                    mBoxBackgroundDrawable.draw(canvas);
                }else{
                    mPaintBox.setColor(mBoxBackgroundColor);
                    canvas.drawRoundRect(mBoxRectF, mBoxRadius, mBoxRadius, mPaintBox);
                }
            }
        }
    }
    //绘制-光标
    private void onDrawCursor(Canvas canvas,Paint paint,RectF rectF,Rect rect){
        if(paint!=null && mEnableCursor){
            if(null!=mCursorBackgroundDrawable ){
                rect.left= (int)((rectF.left + rectF.right) / 2 - mCursorStrokeWidth);
                rect.top=(int)(mCursorHeightPadding <= 1 ? (rectF.top + rectF.bottom) / 4 : mCursorHeightPadding);
                rect.right=(int)((rectF.left + rectF.right) / 2 + mCursorStrokeWidth);
                rect.bottom=(int) (rectF.bottom - (mCursorHeightPadding <= 1 ? (rectF.top + rectF.bottom) / 4 : mCursorHeightPadding));
                mCursorBackgroundDrawable.setBounds(rect);
                if((mCursorDisplayingByTimer || mCursorDisplayingByIndex) ){
                    mCursorBackgroundDrawable.draw(canvas);
                }
            }else {
                mCursorPaint.setColor((mCursorDisplayingByTimer || mCursorDisplayingByIndex) ? mCursorBackgroundColor : Color.TRANSPARENT);
                canvas.drawRect(
                        (float) ((rectF.left + rectF.right) / 2 - mCursorStrokeWidth),
                        (float) (mCursorHeightPadding <= 1 ? (rectF.top + rectF.bottom) / 4 : mCursorHeightPadding),
                        (float) ((rectF.left + rectF.right) / 2 + mCursorStrokeWidth),
                        (float) (rectF.bottom - (mCursorHeightPadding <= 1 ? (rectF.top + rectF.bottom) / 4 : mCursorHeightPadding))
                        , paint);
            }
        }

        mCursorDisplayingByIndex=false;
    }



    //开始计时器，开始光标闪烁
    @Override
    protected void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow: ");
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
    //打开软键盘
    public void openSoftKeyboard(){
        Log.d(TAG, "openSoftKeyboard: ");
//            this.setFocusable(true);
            this.setFocusableInTouchMode(true);
            this.requestFocus();
            inputMethodManager.showSoftInput(this, 0);

    }
    //关闭软键盘
    public void closeSoftKeyboard(){
        Log.d(TAG, "closeSoftKeyboard: ");
        if(mEnableSoftKeyboardAutoClose||mIsLocked||mIsEnableLock) {
            this.clearFocus();
            inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
        }
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