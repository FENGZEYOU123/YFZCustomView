package codeInputView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yfz.yfzcustomview.R;

/**
 * 简介：进阶自绘codeText
 * 作者：游丰泽
 */
public class CodeText11 extends androidx.appcompat.widget.AppCompatEditText {
    private final String TAG= CodeText11.class.getName();
    private final int PAINT_STYLE_STROKE=0;
    private final int PAINT_STYLE_FILLED=1;
    private Context context;
    private TypedArray typedArray;
    private int ViewWidth=-1;
    private int ViewHeight=-1;
    private int onMeasureMode;
    //box数量
    private int mBoxMaxLength=4;
    //box间距
    private int mBoxMargin=20;
    //box长
    private int mBoxSize=60;
    //box方形
    private RectF mBoxRectF;
    //box画笔
    private Paint mBoxPaint;
    //默认box画笔颜色
    private int mBoxPaintColor=Color.GRAY;
    //默认box画笔风格-默认空心
    private int mBoxPaintStyle=0;
    //默认box画笔空心边距
    private float mBoxPaintStrokeWidth=5;
    //文字画笔
    private Paint mTextPaint;
    //当前EditText内容
    private String text = "";

    public CodeText11(@NonNull Context context){
        super(context);
        initial(context);//初始化
    }

    public CodeText11(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.CodeText11);
        mBoxMaxLength = typedArray.getInt(R.styleable.CodeText11_boxMaxNumber, mBoxMaxLength);//box数量，默认4
        mBoxMargin = typedArray.getInt(R.styleable.CodeText11_boxMargin, mBoxMargin);//box边距
        mBoxSize = typedArray.getInt(R.styleable.CodeText11_boxSize, mBoxSize);//box宽度
        mBoxPaintStyle=typedArray.getInt(R.styleable.CodeText11_boxStyle, PAINT_STYLE_STROKE);//box样式-默认空心
        mBoxPaintColor=typedArray.getColor(R.styleable.CodeText11_boxColor, mBoxPaintColor);//box颜色-默认灰色
        mBoxPaintStrokeWidth=typedArray.getFloat(R.styleable.CodeText11_boxStrokeWidth, mBoxPaintStrokeWidth);//box颜色-默认灰色

        initial(context);//初始化EditText
        initialBox(context);//初始化Box笔刷和rect
        typedArray.recycle();//回收
    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        onMeasureMode=MeasureSpec.getMode(widthMeasureSpec);
//        switch (onMeasureMode){
//            case MeasureSpec.AT_MOST:
//                ViewWidth=YFZDisplayUtils.dip2px(context,mBoxMaxLength* mBoxSize +(mBoxMaxLength-1)*mBoxMargin);//没有规定大小的话，View的长度为Box框*数量+Margin的长度
//                break;
//            case MeasureSpec.EXACTLY:
//                ViewWidth=MeasureSpec.getSize(widthMeasureSpec);//有明确长度的话，View的长度为规定的长度
//                mBoxSize= (ViewWidth - (mBoxMargin * (mBoxMaxLength - 1))) / mBoxMaxLength;
//                break;
//            case  MeasureSpec.UNSPECIFIED:
//            default:
//                break;
//        }
//        setMeasuredDimension((ViewWidth!=-1)?ViewWidth:widthMeasureSpec,(mBoxSize!=-1)?YFZDisplayUtils.dip2px(context,mBoxSize):heightMeasureSpec);
//    }

    private void initial(Context context) {
        this.context = context;
        this.mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.d(TAG, "onKey: KEYCODE_DEL");
                }
                return false;
            }
        });
        this.setMaxLines(1);//EditText限制最大行 1
        this.setLines(1);//EditText限制最大行 1
        this.setSingleLine();//屏蔽掉换行
        this.setBackgroundColor(Color.TRANSPARENT);//抹去EditText背景
        this.setCursorVisible(false);//隐藏光标
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mBoxMaxLength >= 1 ? mBoxMaxLength : 4)});//过滤最大输入长度
    }
    private void initialBox(Context context){
        this.mBoxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mBoxRectF = new RectF();
        this.mBoxPaint.setStyle(mBoxPaintStyle==PAINT_STYLE_STROKE?Paint.Style.STROKE:Paint.Style.FILL);
        this.mBoxPaint.setColor(mBoxPaintColor);
        this.mBoxPaint.setStrokeWidth(mBoxPaintStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBox(canvas);//画盒子
    }
//
    private void drawBox(Canvas canvas) {
        canvas.drawRect(0,0,getWidth(),getHeight(),mBoxPaint);
//        mBoxMargin=YFZDisplayUtils.dip2px(this.context,mBoxMargin);
//        mBoxRectF.left = 0 * mBoxSize + mBoxMargin; //第i盒子的左
//        mBoxRectF.right =  mBoxRectF.left + mBoxSize;//第i盒子的右
//        mBoxRectF.top = 1;//第i盒子的上
//        mBoxRectF.bottom = mBoxSize-1;//第i盒子的下
//        canvas.drawRect(mBoxRectF,mBoxPaint);
//
//        mBoxRectF.left = 1 * mBoxSize + mBoxMargin; //第i盒子的左
//        mBoxRectF.right =  mBoxRectF.left + mBoxSize;//第i盒子的右
//        mBoxRectF.top = 1;//第i盒子的上
//        mBoxRectF.bottom = mBoxSize-1;//第i盒子的下
//        canvas.drawRect(mBoxRectF,mBoxPaint);
//        canvas.save();
//        for (int i = 0; i < mBoxMaxLength; i++) {
//        mBoxRectF.left = i * mBoxWidth + mBoxMargin; //第i盒子的左
//        mBoxRectF.right = mBoxRectF.left + mBoxWidth;//第i盒子的右
//        mBoxRectF.top = 0;//第i盒子的上
//        mBoxRectF.bottom = getHeight();//第i盒子的下
//            canvas.drawRect(mBoxRectF,mBoxPaint);
//        }
    }
}
