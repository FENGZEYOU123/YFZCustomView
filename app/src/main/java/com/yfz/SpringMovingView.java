package com.yfz;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

/**
 *  作者：游丰泽
 *  功能介绍：仿ios具有弹簧效果的自由移动组件，靠近四边带有吸附效果。
 *  移动超出四边具有弹簧阻尼按压效果，且释放后会有弹簧回弹效果，最终落到边界
 **/

public class SpringMovingView extends LinearLayout {

    private OnClickListener mOnClickListener;
    //手指按下时相对于屏幕的X，Y位置
    private float mDownInScreenX, mDownInScreenY;
    //手指按下时相对于组件的X，Y位置
    private double mDownInViewX, mDownInViewY;
    //手指移动产生X，Y的距离
    private double mMoveDistanceX, mMoveDistanceY;
    //新位置left top right bottom
    private double mNewPosition_left, mNewPosition_top, mNewPosition_right, mNewPosition_bottom;
    //屏幕长宽
    private double mScreenHeight, mScreenWidth;
    /**
     * inner 吸附距离四边的最小距离
     */
    private double inner =15;
    /**
     * 弹簧压缩时,移动速率比例
     */
    private double mSpringPressSpeed =0.1;
    /**
     * 弹簧释放时,弹起的高度比例
     */
    private double mSpringReleaseHeightRate =0.5;
    /**
     * 弹簧释放时,弹起的X,Y
     */
    private double mSpringReleaseX =0.0, mSpringReleaseY =0.0;
    /**
     * 弹簧释放时,动画的起终X,起终Y
     */
    private int mAnimStartX =0, mAnimEndX =0, mAnimStartY =0, mAnimEndY =0;
    /**
     * 动画结束后，组件的位置 left top right bottom
     */
    private   int mAnim_left =0, mAnim_top =0, mAnim_right =0, mAnim_bottom =0;

    public SpringMovingView(Context mContext) {
        super(mContext);
        initView(mContext);
    }
    public SpringMovingView(Context mContext, @Nullable AttributeSet attrs) {
        super(mContext,attrs);
        initView(mContext);
    }
    public SpringMovingView(Context mContext, AttributeSet attrs, int defStyle)
    {
        super(mContext,attrs,defStyle);
        initView(mContext);
    }

    /**
     * 初始化View
     * @param context
     */
    private void initView(final Context context) {
        DisplayMetrics dm= new DisplayMetrics();
        WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics( dm );
        mScreenHeight =dm.heightPixels;
        mScreenWidth =dm.widthPixels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownInViewX =event.getX();
                mDownInViewY =event.getY();
                mDownInScreenX = event.getRawX();
                mDownInScreenY = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:  //当手指开始移动的时候
                //记录移动距离
                mMoveDistanceX = event.getX() - mDownInViewX;  //记录组件内移动的距离X
                mMoveDistanceY = event.getY() - mDownInViewY;  //记录组件内移动的距离Y
                //将要展示的组件的四个顶点位置
                mNewPosition_left = getLeft() + mMoveDistanceX;
                mNewPosition_top = getRight() + mMoveDistanceX;
                mNewPosition_right = getTop() + mMoveDistanceY;
                mNewPosition_bottom = getBottom() + mMoveDistanceY;
                do_attach_boundary_effect();  //吸边效果 当组件靠近屏幕四边时会有吸附上去的效果
                do_spring_press_effect();  //弹簧压缩效果 当组件超过屏幕四边后,继续移动会有阻尼效果
                //防止超出容器边界，而无法进行动画操作
                if(mNewPosition_left >(-1*getWidth()+1)&& mNewPosition_right >(-1*getHeight()+1)&& mNewPosition_bottom <(mScreenHeight +getHeight()-1)&& mNewPosition_top <(mScreenWidth +getWidth()-1)){
                    this.layout( (int) mNewPosition_left,  (int) mNewPosition_right,  (int) mNewPosition_top, (int) mNewPosition_bottom); //左，上，右，下
                }
                break;

            case MotionEvent.ACTION_UP:  //当手指上抬起（停止触屏屏幕）
                //记录W超出边界值
                if(getLeft()<0||getRight()> mScreenWidth){
                    mSpringReleaseX = popup_W(getLeft(),getRight())* mSpringReleaseHeightRate;
                }
                //记录H超出边界值
                if(getTop()<0||getBottom()> mScreenHeight){
                    mSpringReleaseY = popup_H(getTop(),getBottom())* mSpringReleaseHeightRate;
                }
                do_spring_release_effect(mSpringReleaseX, mSpringReleaseY);   //弹簧回弹效果，当触发了弹簧压缩后，释放手势组件回弹，压缩越多，回弹越多
                if(null != mOnClickListener) {
                    if (mDownInScreenX == event.getRawX() && mDownInScreenY == event.getRawY()) {
                        mOnClickListener.isClick(true);
                    }
                }
                break;
        }
        return true;
    }


    /**
     * ios弹簧方法-压缩
     **/
    private  void do_spring_press_effect(){ //ios弹簧方法-压缩
        if (getLeft() < 0) {  //左边小于距离的时候，开始放慢向左移动速度
            mNewPosition_left = (double) (getLeft() + press_speed(mSpringPressSpeed, mMoveDistanceX, getLeft()));
            mNewPosition_top = mNewPosition_left + getWidth();
        } else if (getRight() > mScreenWidth) {  //右边大于Screen距离的时候，开始放慢向右移动速度
            mNewPosition_top = (double) (getRight() + press_speed(mSpringPressSpeed, mMoveDistanceX, getRight()));
            mNewPosition_left = mNewPosition_top - getWidth();
        }
        if (getTop() < 0) {  //上边小于距离的时候，开始放慢向上移动速度
            mNewPosition_right = (double) (getTop() + press_speed(mSpringPressSpeed, mMoveDistanceY, getTop()));
            mNewPosition_bottom = mNewPosition_right + getHeight();
        } else if (getBottom() > mScreenHeight) {
            mNewPosition_bottom = (double) (getBottom() + press_speed(mSpringPressSpeed, mMoveDistanceY, getBottom())*0.3);
            mNewPosition_right = mNewPosition_bottom - getHeight();
        }
    }

    //放慢速率随着组件view，超出屏幕边界越多越慢
    private double  press_speed(double more_slow,double Move_X_Distance,double get_view){
        double speed=0.0;
        if(get_view<0) {
            speed = 0.1*more_slow * (Move_X_Distance) * (double)(Math.sqrt(Math.abs(get_view)));
        }else{
            speed = 0.1*more_slow * (Move_X_Distance) * (double)(Math.sqrt(Math.abs(get_view- mScreenWidth)) );
        }
        return speed ;
    }

    //记录一下组件view超出边界的W
    private double popup_W(double Left,double Right){
        double W=0.0;
        if(Left<0){   //如果说超出左边W
            W= Left;
        }else if( Left>0 && Right> mScreenWidth)  {  //如果说超出右边W
            W= Right- mScreenWidth;
        }
        return W ;
    }
    //记录一下组件view超出边界的H
    private double popup_H(double Top,double Bottom){
        double H=0.0;
        if(Top<0){   //如果说超出上边H
            H= Top;
        }else if( Top>0 && Bottom> mScreenHeight)  {  //如果说超出下边H
            H= Bottom- mScreenHeight;
        }
        return H ;
    }



    /**
     * ios弹簧方法-释放
     **/
    private  void do_spring_release_effect(final double popup_W, final double popup_H) { //ios弹簧方法-释放
        //当开启弹簧效果，且任意一边超出屏幕边界
        if(getLeft()<popup_W||getRight()> mScreenWidth ||getTop()<popup_H||getBottom()> mScreenHeight) {
            mAnim_left =getLeft(); mAnim_top =getTop();
            mAnim_right =(int)(mAnim_left +getWidth());   mAnim_bottom = (int) (mAnim_top + getHeight());
            mAnimEndX =0;
            mAnimEndY =0;
            if (getLeft() < popup_W) {
                mAnimEndX = (int) (-1 * popup_W * 3);
                mAnim_left = 0;
                mAnim_right =(int)(mAnim_left +getWidth());
            } else if (getRight() > mScreenWidth) {
                mAnimEndX = (int) ((-1 * popup_W * 3));
                mAnim_left = (int) (mScreenWidth - getWidth());
                mAnim_right = (int) (mScreenWidth);
            }
            if (getTop() < popup_H) {
                mAnimEndY = (int) (-1 * popup_H * 3);
                mAnim_top = 0;
                mAnim_bottom = (int) (mAnim_top + getHeight());
            } else if (getBottom() > mScreenHeight) {
                mAnimEndY = (int) (-1 * popup_H * 3);
                mAnim_bottom = (int) mScreenHeight;
                mAnim_top = (int) (mAnim_bottom - getHeight());
            }
            animation(); //开始进行位移动画
        }
    }

    /**
     * 回弹位移动画
     */
    private void animation(){
        TranslateAnimation transAnim = new TranslateAnimation(mAnimStartX, mAnimEndX, mAnimStartY, mAnimEndY);
        transAnim.setDuration(300);
        transAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                clearAnimation();
                layout(mAnim_left, mAnim_top, mAnim_right, mAnim_bottom);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        startAnimation(transAnim);
    }


    /**
     * 吸附屏幕方法
     **/
    private void do_attach_boundary_effect(){
        if (mNewPosition_left <= inner && mNewPosition_left > 0) {  //左边吸边效果
            mNewPosition_left = 0;
            mNewPosition_top = mNewPosition_left + getWidth();
            return;
        } else if (mScreenWidth > mNewPosition_top && mNewPosition_top > mScreenWidth - inner) {  //右边吸边效果
            mNewPosition_top = mScreenWidth;
            mNewPosition_left = mNewPosition_top - getWidth();
            return;
        }
        if (mNewPosition_right <= inner && mNewPosition_right > 0) {   //上边吸边效果
            mNewPosition_right = 0;
            mNewPosition_bottom = mNewPosition_right + getHeight();
            return;
        } else if (mScreenHeight > mNewPosition_bottom && mNewPosition_bottom > mScreenHeight - inner) {  //下边吸边效果
            mNewPosition_bottom = mScreenHeight;
            mNewPosition_right = mNewPosition_bottom - getHeight();
            return;
        }
    }

    /**
     * 像外提供监听click方法
     * @param onClickListener
     */
    public void setOnSpringMovingClickListener(OnClickListener onClickListener){
        this.mOnClickListener = onClickListener;
    }

    /**
     * 接口
     */
    public interface OnClickListener {
        void isClick(boolean isClick);
    }
}