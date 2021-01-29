package buttonView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 编写人：游丰泽
 * 功能简介：带有按压交互效果的自定义按钮
 * 创建时间：2020/11/2
 */
public class YFZGestureButton extends ConstraintLayout {
    private String TAG=YFZGestureButton.class.getName();
    private Context mContext;
    private CallBackIsClick mCallBackIsCLick;
    /**
     * 当前选中状态
     */
    private Boolean isClick;
    /**
     * 画笔-外边框
     */
    private Paint mPaintBoxBorder;
    /**
     * 画笔-外边框-粗细
     */
    private float mBoxBorderWidth;
    /**
     * 画笔-外边框-颜色
     */
    private int mBoxBorderColor;
    /**
     * 画笔-背景
     */
    private Paint mPaintBackground;
    /**
     * 背景大小
     */
    private RectF mRectF;
    /**
     * 添加文字组件
     */
    private TextView mTextView;
    /**
     * 文字内容
     */
    private String mTextName ="";
    /**
     * 文字大小
     */
    private float mTextSize =0;
    /**
     * 文字颜色
     */
    private int mTextColor=0;
    /**
     * 文字距离背景边距 左
     */
    private int mTextMarginLeft=10;
    /**
     * 文字距离背景边距 上
     */
    private int mTextMarginTop=5;
    /**
     * 文字距离背景边距 右
     */
    private int mTextMarginRight=10;
    /**
     * 文字距离背景边距 下
     */
    private int mTextMarginBottom=5;
    /**
     * 文字距离背景边距 全部
     */
    private int mTextMarginAll=10;
    /**
     * 无选中状态下背景颜色
     */
    private int mBackgroundColorUnClick;
    /**
     * 选中状态下背景颜色
     */
    private int mBackgroundColorIsClick;
    /**
     * 背景边距 左
     */
    private float mBackgroundPaddingLeft=0;
    /**
     * 背景边距 上
     */
    private float mBackgroundPaddingTop=0;
    /**
     * 背景边距 右
     */
    private float mBackgroundPaddingRight=0;
    /**
     * 背景边距 下
     */
    private float mBackgroundPaddingBottom=0;
    /**
     * 背景边距 全部
     */
    private float mBackgroundPaddingAll=0;
    /**
     * 背景边距 全部
     */
    private float mBackgroundRadiusRx=0;
    /**
     * 背景边距 全部
     */
    private float mBackgroundRadiusRy=0;


    public YFZGestureButton(@NonNull Context context) {
        super(context);
        initialView(context);
    }
    public YFZGestureButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialView(context);
    }
    public YFZGestureButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialView(context);
    }

    /**
     * 初始化组件
     * @param context
     */
    private void initialView(Context context){
        this.mContext=context;
        this.isClick=false;
        this.mTextName ="显示的名字";
        this.mTextSize =10;
        this.mTextColor=Color.BLACK;
        this.mBoxBorderColor =Color.TRANSPARENT;
        this.mBoxBorderWidth=2;
        this.mPaintBackground =new Paint();
        this.mPaintBoxBorder=new Paint();
        this.mRectF=new RectF();
        this.setBackgroundColor(Color.TRANSPARENT);
        this.mBackgroundColorIsClick=Color.argb(50,0,0,0);
        this.mBackgroundColorUnClick=Color.argb(100,0,0,0);
        this.setPadding(
                mTextMarginLeft+mTextMarginAll,
                mTextMarginTop+mTextMarginAll,
                mTextMarginRight+mTextMarginAll,
                mTextMarginBottom+mTextMarginAll
        );
        initialMPaint();
        addTextView();
        refresh();
    }
    /**
     * 初始化画笔
     */
    private void initialMPaint(){
        if(null != mPaintBackground){
            mPaintBackground.setColor(mBackgroundColorUnClick);
            mPaintBackground.setAntiAlias(true);
            mPaintBackground.setStyle(Paint.Style.FILL);
        }
        if(null !=mPaintBoxBorder){
            mPaintBoxBorder.setColor(mBoxBorderColor);
            mPaintBoxBorder.setAntiAlias(true);
            mPaintBoxBorder.setStyle(Paint.Style.STROKE);
            mPaintBoxBorder.setStrokeWidth(mBoxBorderWidth);
        }
    }

    /**
     * 添加按钮文字
     */
    private void addTextView(){
        if(null==mTextView && null!=mContext){
            mTextView=new TextView(mContext);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            layoutParams.gravity=Gravity.CENTER;
            mTextView.setLayoutParams(layoutParams);
            mTextView.setText(mTextName);
            mTextView.setTextSize(mTextSize);
            mTextView.setTextColor(mTextColor);
            mTextView.setGravity(Gravity.CENTER);
            this.addView(mTextView);
            mTextView.getPaint().setFakeBoldText(true);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.isClick=true;
                break;
            case MotionEvent.ACTION_MOVE:
                this.isClick= ((event.getX()<0 || event.getX()>getWidth())
                             ||(event.getY()<0 || event.getY()>getHeight())) ?false:true;
                break;
            case MotionEvent.ACTION_UP:
                if(null != mCallBackIsCLick) {
                    mCallBackIsCLick.isClick(isClick);
                }
                this.isClick=false;
                break;
            default:
                this.isClick=false;
                break;
        }
        refresh();
        return true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas){
        Log.d(TAG, "onDraw: ");
        mPaintBackground.setColor(isClick?mBackgroundColorIsClick:mBackgroundColorUnClick);
            mRectF.set(
                    0+mBackgroundPaddingAll +mBackgroundPaddingLeft,
                    0+mBackgroundPaddingAll +mBackgroundPaddingTop,
                    getRight()-getLeft()-mBackgroundPaddingAll-mBackgroundPaddingRight,
                    getBottom()-getTop()-mBackgroundPaddingAll-mBackgroundPaddingBottom);
        canvas.drawRoundRect(mRectF,mBackgroundRadiusRx,mBackgroundRadiusRy, mPaintBackground);
        canvas.drawRoundRect(mRectF,mBackgroundRadiusRx,mBackgroundRadiusRy, mPaintBoxBorder);


        super.onDraw(canvas);
    }

    public void setMTextName(String TextName){
        this.mTextName=TextName;
        if(null!=mTextView)mTextView.setText(mTextName);
    }
    public void setMTextColor(int TextColor){
        this.mTextColor=TextColor;
        if(null!=mTextView)mTextView.setTextColor(mTextColor);
    }
    public void setMTextSize(float TextSize){
        this.mTextSize=Math.abs(TextSize);
        if(null!=mTextView)mTextView.setTextSize(mTextSize);
    }
    public void setMTextBold(boolean isBold){
        if(null!=mTextView)this.mTextView.getPaint().setFakeBoldText(isBold);
    }

    public void setMTextMarginLeft(int marginLeft){
        if(null!=mTextView)this.mTextMarginLeft =Math.abs(marginLeft);
        updateTextMargin();
    }
    public void setMTextMarginTop(int marginTop){
        if(null!=mTextView)this.mTextMarginTop=Math.abs(marginTop);
        updateTextMargin();
    }
    public void setMTextMarginRight(int marginRight){
        if(null!=mTextView)this.mTextMarginRight =Math.abs(marginRight);
        updateTextMargin();
    }
    public void setMTextMarginBottom(int marginBottom){
        if(null!=mTextView)this.mTextMarginBottom =Math.abs(marginBottom);
        updateTextMargin();
    }
    public void setMTextMarginAll(int marginAll){
        if(null!=mTextView)this.mTextMarginAll =Math.abs(marginAll);
        updateTextMargin();
    }
    public void setMTextUnderLine(){
        if(null!=mTextView)this.mTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
    }
    public void setMTextMiddleLine(){
        if(null!=mTextView)this.mTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
    }
    public void removeMTextLines(){
        if(null!=mTextView)this.mTextView.getPaint().setFlags(0|Paint.ANTI_ALIAS_FLAG);
    }
    public void setMBackgroundColorUnClick(int unClickColor){
        this.mBackgroundColorUnClick =unClickColor;
    }
    public void setMBackgroundColorIsClick(int isClickColor){
        this.mBackgroundColorIsClick =isClickColor;
    }
    public void setMBackgroundPaddingLeft(float paddingLeft){
        this.mBackgroundPaddingLeft =Math.abs(paddingLeft);
    }
    public void setMBackgroundPaddingTop(float paddingTop){
        this.mBackgroundPaddingTop =Math.abs(paddingTop);
    }
    public void setMBackgroundPaddingRight(float paddingRight){
        this.mBackgroundPaddingRight =Math.abs(paddingRight);
    }
    public void setMBackgroundPaddingBottom(float paddingBottom){
        this.mBackgroundPaddingBottom =Math.abs(paddingBottom);
    }
    public void setMBackgroundPaddingAll(float paddingAll){
        this.mBackgroundPaddingAll =Math.abs(paddingAll);
    }
    public void setMBackgroundRadiusRx(float radiusRx){
        this.mBackgroundRadiusRx =Math.abs(radiusRx);
    }
    public void setMBackgroundRadiusRy(float radiusRy){
        this.mBackgroundRadiusRy =Math.abs(radiusRy);
    }
    public void setMBoxBorderColor(int colorBoxBorder){
        this.mBoxBorderColor =colorBoxBorder;
        if(null!=mPaintBoxBorder)mPaintBoxBorder.setColor(mBoxBorderColor);
    }
    public void setMPaintBoxBorderWidth(float mPaintBoxBorderWidth){
        this.mBoxBorderWidth =mPaintBoxBorderWidth;
        if(null!=mPaintBoxBorder)mPaintBoxBorder.setStrokeWidth(mBoxBorderWidth);
    }
//    public void aa(){
//        LinearGradient backGradient = new LinearGradient(0, 0, getWidth(), getHeight(), new int[]{Color.RED, Color.YELLOW, Color.GRAY}, null, Shader.TileMode.CLAMP);
//        this.mPaint.setShader(backGradient);
//    }
    /**
     * 刷新UI
     */
    public void refresh(){
        invalidate();
    }

    /**
     * CallBack回调是否触发点击
     */
    public void addListenerCallBack(CallBackIsClick callBackIsClick){
        this.mCallBackIsCLick =callBackIsClick;
    }
    public interface CallBackIsClick {
        void isClick(boolean isClick);
    }
    private void updateTextMargin(){
        this.setPadding(
                mTextMarginLeft+mTextMarginAll,
                mTextMarginTop+mTextMarginAll,
                mTextMarginRight+mTextMarginAll,
                mTextMarginBottom+mTextMarginAll
        );
    }
}
