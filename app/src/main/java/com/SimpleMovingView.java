package com;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
/**
 * 简介：简单可移动View
 * 作者：游丰泽
 * 主要功能: （
 */
public class SimpleMovingView extends LinearLayout {
    private Context mContext;
    //是否限制仅在屏幕内移动
    private boolean mIsLimitedInScreen =true;
    //记录手指按下时,手指相对于组件的X位置
    private float mDownOnViewX =0;
    //记录手指按下时,手指相对于组件的U位置
    private float mDownOnViewY =0;
    //记录手指移动时与按下时的X距离
    private float mMoveOnViewXDistance =0;
    //记录手指移动时与按下时的Y距离
    private float mMoveOnViewYDistance =0;
    //点击回调
    private OnClickListener mOnClickListener;
    public SimpleMovingView(Context context) {
        super(context);
        initial(context);
    }
    public SimpleMovingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public SimpleMovingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial(context);
    }
    private void initial(Context context){
        mContext=context;
    }

    //return true,截获触摸焦点，并处理不同的手势事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
              mDownOnViewX =  event.getX();
              mDownOnViewY =  event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveOnViewXDistance =event.getX()- mDownOnViewX;
                mMoveOnViewYDistance =event.getY()- mDownOnViewY;
                //刷新位置UI
                layout((int)(getLeft()+ mMoveOnViewXDistance),(int)(getTop()+ mMoveOnViewYDistance),(int)(getRight()+ mMoveOnViewXDistance),(int)(getBottom()+ mMoveOnViewYDistance));
                break;
            case MotionEvent.ACTION_UP:
                if(null != mOnClickListener){
                    mOnClickListener.isClick(true);
                }
                break;
            default:
                break;
        }
        return true;
    }
    //向外提供监听接口
    public void setOnMovingViewClickListener(OnClickListener onClickListener){
        this.mOnClickListener=onClickListener;
    }
    //接口回调-点击
    public interface OnClickListener{
        void isClick(boolean isClick);
    }
}
