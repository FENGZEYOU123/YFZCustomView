package codeInputView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yfz.yfzcustomview.R;

import utils.YFZDisplayUtils;
import utils.YFZUtils;

/**
 * 简介：进阶自绘codeText
 * 作者：游丰泽
 */
public class CodeText extends androidx.appcompat.widget.AppCompatEditText {
    private final String TAG=CodeText.class.getName();
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
    private int mBoxWidth=60;
    //box宽
    private int mBoxHeight=60;
    //box方形
    private Rect mBoxRect;
    //box画笔
    private Paint mBoxPaint;
    //默认box画笔颜色
    private int mBoxPaintColor=Color.GRAY;
    //默认box画笔风格-默认空心
    private int mBoxPaintStyle=0;
    //文字画笔
    private Paint mTextPaint;
    //当前EditText内容
    private String text = "";

    public CodeText(@NonNull Context context){
        super(context);
        initial(context);//初始化
    }

    public CodeText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.codeText);
        mBoxMaxLength = typedArray.getInt(R.styleable.codeText_boxMaxNumber, mBoxMaxLength);//box数量，默认4
        mBoxMargin = typedArray.getInt(R.styleable.codeText_boxMargin, mBoxMargin);//box边距
        mBoxWidth = typedArray.getInt(R.styleable.codeText_boxWidth, mBoxWidth);//box宽度
        mBoxHeight = typedArray.getInt(R.styleable.codeText_boxHeight, mBoxHeight);//box高度
        mBoxPaintStyle=typedArray.getInt(R.styleable.codeText_boxStyle, PAINT_STYLE_STROKE);//box样式-默认空心
        mBoxPaintColor=typedArray.getInt(R.styleable.codeText_boxColor, mBoxPaintColor);//box颜色-默认灰色
        initial(context);//初始化s
        typedArray.recycle();//回收
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        onMeasureMode=MeasureSpec.getMode(widthMeasureSpec);
        switch (onMeasureMode){
            case MeasureSpec.AT_MOST:
                ViewWidth=YFZDisplayUtils.dip2px(context,mBoxMaxLength*mBoxWidth+(mBoxMaxLength-1)*mBoxMargin);//没有规定大小的话，View的长度为Box框*数量+Margin的长度
                ViewHeight= YFZDisplayUtils.dip2px(context,mBoxHeight);//没有规定大小的话，View的高度为Box框的高度
                break;
            case MeasureSpec.EXACTLY:
                ViewWidth=MeasureSpec.getSize(widthMeasureSpec);//有明确长度的话，View的长度为规定的长度
                ViewHeight=MeasureSpec.getSize(heightMeasureSpec);//有明确高度的话，View的高度为规定的高度
                break;
            case  MeasureSpec.UNSPECIFIED:
            default:
                break;
        }
        setMeasuredDimension((ViewWidth!=-1)?ViewWidth:widthMeasureSpec,(ViewHeight!=-1)?ViewHeight:heightMeasureSpec);
    }

    private void initial(Context context) {
        this.context = context;
        this.mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mBoxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mBoxRect = new Rect();
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
        this.setSingleLine();
        this.setBackgroundColor(Color.TRANSPARENT);//抹去EditText背景
        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mBoxMaxLength >= 1 ? mBoxMaxLength : 4)});
        this.mBoxPaint.setStyle(mBoxPaintStyle==PAINT_STYLE_STROKE?Paint.Style.STROKE:Paint.Style.FILL);

    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(mBoxWidth * mBoxMaxLength + mBoxMargin * (mBoxMaxLength - 1), mBoxHeight * 2);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mBoxPaint.setColor(Color.RED);
        canvas.drawRect(0,0,getWidth(),getHeight(),mBoxPaint);
    }
//
//    private void drawBackground(Canvas canvas) {
//        for (int i = 0; i < mBoxMaxLength; i++) {
//            mBoxRect.left = i * mBoxWidth + mBoxMargin; //第i盒子的左
//            mBoxRect.right = mBoxRect.left + mBoxWidth;//第i盒子的右
//            mBoxRect.top = 0;//第i盒子的上
//            mBoxRect.right = getHeight();//第i盒子的下
//
//        }
//    }
}
