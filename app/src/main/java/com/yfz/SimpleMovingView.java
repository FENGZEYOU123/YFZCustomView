package com.yfz;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

import com.yfz.YfzUtil;

/**
 * 简介：简单可移动View
 * 作者：游丰泽
 * 主要功能: 自由移动组件，并可反馈点击事件
 * mIsLimitedInScreen 设置是否限制仅在屏幕内移动
 * setOnMovingViewClickListener 实现接口，返回点击事件
 */
public class SimpleMovingView extends LinearLayout {
    private Context mContext;
    //是否限制仅在屏幕内移动
    private boolean mIsLimitedInScreen =true;
    //记录手指按下时,手指相对于组件的X,Y位置.
    private float mDownOnViewX =0,mDownOnViewY =0;
    //记录手指按下时,组件相对于屏幕的X,Y绝对位置.
    private float mDownOnScreenX =0,mDownOnScreenY =0;
    //记录手指移动时与按下时的X,Y距离
    private float mMoveOnViewXDistance =0,mMoveOnViewYDistance =0;
    //记录新位置left top right bottom;
    private int mNewLeft=0,mNewTop=0,mNewRight=0,mNewBottom=0;
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
              mDownOnScreenX= this.getX();
              mDownOnScreenY= this.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveOnViewXDistance =event.getX()- mDownOnViewX;
                mMoveOnViewYDistance =event.getY()- mDownOnViewY;
                mNewLeft=(int)(getLeft()+ mMoveOnViewXDistance);
                mNewTop=(int)(getTop()+ mMoveOnViewYDistance);
                mNewRight=(int)(getRight()+ mMoveOnViewXDistance);
                mNewBottom=(int)(getBottom()+ mMoveOnViewYDistance);
                if(mIsLimitedInScreen){ //如果开启了限制仅允许在屏幕内移动
                    checkIfOverBoundary();
                }
                refreshNewPosition();
                break;
            case MotionEvent.ACTION_UP:
                if(null != mOnClickListener){ //如果添加了监听
                    if(mDownOnScreenX==this.getX() && mDownOnScreenY==this.getY()) {  //且组件没有移动
                        mOnClickListener.isClick(true);
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }
    //刷新位置UI
    private void refreshNewPosition(){
        layout(mNewLeft,mNewTop,mNewRight,mNewBottom); //刷新位置UI
    }
    //检测是否超出边界
    private void checkIfOverBoundary(){
        if(mNewLeft<0){ //左边朝边界
            mNewLeft=0;
            mNewRight=mNewLeft+getWidth();
        }
        if(mNewTop<0){ //上边朝边界
            mNewTop=0;
            mNewBottom=mNewTop+getHeight();
        }
        if(mNewRight>YfzUtil.getScreenWidth()){ //右边朝边界
            mNewRight=YfzUtil.getScreenWidth();
            mNewLeft=mNewRight-getWidth();
        }
        if(mNewBottom>YfzUtil.getScreenHeight()){ //下边朝边界
            mNewBottom=YfzUtil.getScreenHeight();
            mNewTop=mNewBottom-getHeight();
        }
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
