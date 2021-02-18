package containerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
/**
 * 编写人：游丰泽
 * 功能简介：带阴影框架
 * 创建时间：2020/11/2
 */
public class YFZContainerShadow extends ViewGroup {
    private Context context;
    /**
     * 阴影画笔
     */
    private Paint mPaintShadow;
    /**
     * 阴影矩形
     */
    private RectF mRectF;
    public YFZContainerShadow(Context context) {
        super(context);
        initialView(context);
    }
    public YFZContainerShadow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialView(context);
    }
    public YFZContainerShadow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialView(context);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        View child = this.getChildAt(0);
        child.layout(childLeft, childTop, width - getPaddingRight(), height - getPaddingBottom());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 初始化
     * @param context
     */
    private void initialView(Context context){
        this.context=context;
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setPadding(10,10,10,10);
        this.mPaintShadow =new Paint();
        this.mRectF=new RectF();
        initialPaint();
        invalidate();
    }

    /**
     * 初始化阴影画笔
     */
    private void initialPaint(){
       if(null== this.mPaintShadow) this.mPaintShadow =new Paint();
        this.mPaintShadow.setAntiAlias(true);
        this.mPaintShadow.setColor(Color.RED);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mRectF.set(
//                0+mBackgroundPaddingAll +mBackgroundPaddingLeft,
//                0+mBackgroundPaddingAll +mBackgroundPaddingTop,
//                getRight()-getLeft()-mBackgroundPaddingAll-mBackgroundPaddingRight,
//                getBottom()-getTop()-mBackgroundPaddingAll-mBackgroundPaddingBottom);
//        canvas.drawRoundRect(mRectF,mBackgroundRadiusRx,mBackgroundRadiusRy, mPaintShadow);
    }
}
