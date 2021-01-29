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
 * 编写者姓名：游丰泽
 * 功能简介：带有按压交互效果的自定义按钮
 */
public class YFZGestureButton extends ConstraintLayout {
    private String TAG=YFZGestureButton.class.getName();
    private Context mContext;
    /**
     * 当前选中状态
     */
    private Boolean isClick;
    /**
     * 手指点击位置
     */
    private double mFigureDownX,mFigureDownY;
    /**
     * 画笔
     */
    private Paint mPaint;
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
        this.mPaint=new Paint();
        this.mRectF=new RectF();
        this.setBackgroundColor(Color.TRANSPARENT);
        this.mBackgroundColorUnClick=Color.argb(10,0,0,0);
        this.mBackgroundColorIsClick=Color.argb(100,0,0,0);
        initialMPaint();
        addTextView();
        refresh();
    }
    /**
     * 初始化画笔
     */
    private void initialMPaint(){
        if(null != mPaint){
            mPaint.setColor(mBackgroundColorUnClick);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
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
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouch: ACTION_DOWN");
                this.isClick=true;
                this.mFigureDownX=getX();
                this.mFigureDownY=getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouch: ACTION_MOVE");
//                this.isClick= ((getX()<0 || getX()>event.getX())
//                        ||(getY()<0 || getY()>event.getHeight()))
//                        ?false:true;
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouch: ACTION_UP");

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
        mPaint.setColor(isClick?mBackgroundColorIsClick:mBackgroundColorUnClick);

            mRectF.set(
                    0+mBackgroundPaddingAll +mBackgroundPaddingLeft,
                    0+mBackgroundPaddingAll +mBackgroundPaddingTop,
                    getRight()-getLeft()-mBackgroundPaddingAll-mBackgroundPaddingRight,
                    getBottom()-getTop()-mBackgroundPaddingAll-mBackgroundPaddingBottom);

        canvas.drawRoundRect(mRectF,10,10,mPaint);
        super.onDraw(canvas);
    }

    public void setMTextName(String TextName){
        this.mTextName=TextName;
        mTextView.setText(mTextName);
    }
    public void setMTextColor(int TextColor){
        this.mTextColor=TextColor;
        mTextView.setTextColor(mTextColor);
    }
    public void setMTextSIze(float TextSize){
        this.mTextSize=TextSize;
        mTextView.setTextSize(mTextSize);
    }
    public void setMBackgroundColorUnClick(int unClickColor){
        this.mBackgroundColorUnClick =unClickColor;
    }
    public void setMBackgroundColorIsClick(int isClickColor){
        this.mBackgroundColorIsClick =isClickColor;
    }
    public void setMBackgroundPaddingLeft(float paddingLeft){
        this.mBackgroundPaddingLeft =paddingLeft;
    }
    public void setMBackgroundPaddingTop(float paddingTop){
        this.mBackgroundPaddingTop =paddingTop;
    }
    public void setMBackgroundPaddingRight(float paddingRight){
        this.mBackgroundPaddingRight =paddingRight;
    }
    public void setMBackgroundPaddingBottom(float paddingBottom){
        this.mBackgroundPaddingBottom =paddingBottom;
    }
    public void setmBackgroundPaddingAll(float paddingAll){
        this.mBackgroundPaddingAll =paddingAll;
    }
    /**
     * 刷新UI
     */
    public void refresh(){
        invalidate();
    }
}
