package movingView.moving_scale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.RequiresApi;
import com.yfz.yfzcustomview.R;
import utils.YFZDisplayUtils;

public class Component_drawFrame extends FrameLayout {
    private Context mContext;
    private  Rect rect;
    private Paint mPaintBorder;
    private Paint mPaintIndex;
    private int margin=2;

    public Component_drawFrame(Context context) {
        super(context);
        initial(context);
    }
    public Component_drawFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public Component_drawFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Component_drawFrame(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initial(Context context){
        this.mContext=context;
        rect=new Rect();
        mPaintBorder =new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBorder.setStyle(Paint.Style.STROKE);
        mPaintBorder.setColor(Color.BLACK);
        mPaintBorder.setStrokeWidth(YFZDisplayUtils.dip2px(mContext,1f));
        mPaintIndex =new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintIndex.setStyle(Paint.Style.FILL);
        mPaintIndex.setColor(mContext.getResources().getColor(R.color.white90));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rect, mPaintBorder);
        rect.left=rect.left+margin;
        rect.top=rect.top+margin;
        rect.right=rect.right-margin;
        rect.bottom=rect.bottom-margin;
        canvas.drawRect(rect, mPaintIndex);

    }
    public void refresh( int vL,int vT,int vR,int vB){
        rect.left=vL;
        rect.top=vT;
        rect.right=vR;
        rect.bottom=vB;

        postInvalidate();
    }


}
