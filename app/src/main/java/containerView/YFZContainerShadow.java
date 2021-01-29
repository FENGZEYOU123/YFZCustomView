package containerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
/**
 * 编写人：游丰泽
 * 功能简介：带阴影框架
 * 创建时间：2020/11/2
 */
public class YFZContainerShadow extends ViewGroup {
    private Context context;
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

    }
    private void initialView(Context context){
        this.context=context;
        this.setBackgroundColor(Color.RED);
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
