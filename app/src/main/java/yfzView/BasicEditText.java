package yfzView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class BasicEditText extends LinearLayout {
    private Paint mPaint;
    private Context mContext;
    private Drawable mDrawable;
    private Rect mRect;

    public BasicEditText(Context context) {
        super(context);
        initial(context);
    }
    public BasicEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public BasicEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial(context);
    }

    private void initial(Context context){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mContext=context;
        mDrawable=getBackground();
        mRect=new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStrokeBackground(canvas);
    }


    /**
     * 绘制方框
     */
    private void drawStrokeBackground(Canvas canvas) {
        // 下面绘制方框背景颜色
        // 确定反馈位置
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = getWidth()/3;
        mRect.bottom = getHeight()/3;
        mDrawable.setBounds(mRect); // 设置位置
        mDrawable.setState(new int[]{android.R.attr.state_enabled}); // 设置图像状态
        mDrawable.draw(canvas); //  画到画布上
    }

}
