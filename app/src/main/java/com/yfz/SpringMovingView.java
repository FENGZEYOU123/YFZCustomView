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
    private int mNewPosition_left, mNewPosition_top, mNewPosition_right, mNewPosition_bottom;
    //屏幕长宽
    private int mScreenHeight, mScreenWidth;
    //弹簧释放时,弹起的X,Y
    private double mSpringReleaseX, mSpringReleaseY;
    //弹簧释放时,动画的起X,终X,起Y,终Y
    private int mAnimStartX=0, mAnimEndX, mAnimStartY=0, mAnimEndY;
    /**
     * inner 吸附距离四边的最小距离
     */
    private double mAttach_Distance =20;
    /**
     * 弹簧压缩时,移动速率比例
     */
    private double mSpringPressSpeed =0.1;
    /**
     * 弹簧释放时,弹起的高度比例
     */
    private double mSpringReleaseHeightRate =0.5;

    /**
     * 动画播放时
     */
    private int mAnimationDuringTime=300;
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

            case MotionEvent.ACTION_MOVE:
                //记录移动距离
                mMoveDistanceX = event.getX() - mDownInViewX;  //计算手指相对于View移动距离X
                mMoveDistanceY = event.getY() - mDownInViewY;  //计算手指相对于View移动距离Y
                //组件新的位置left top right bottom
                mNewPosition_left = (int) (getLeft() + mMoveDistanceX);
                mNewPosition_right = (int) (getRight() + mMoveDistanceX);
                mNewPosition_top = (int) (getTop() + mMoveDistanceY);
                mNewPosition_bottom = (int) (getBottom() + mMoveDistanceY);
                do_attach_boundary_effect();  //吸边效果 当组件靠近屏幕四边时会有吸附上去的效果
                do_spring_press_effect();  //弹簧压缩效果 当组件超过屏幕四边后,继续移动会有阻尼效果
                //防止超出容器边界，而无法进行动画操作
                if(mNewPosition_left >(-1*getWidth()+1)&& mNewPosition_top >(-1*getHeight()+1)&& mNewPosition_bottom <(mScreenHeight +getHeight()-1)&& mNewPosition_right <(mScreenWidth +getWidth()-1)){
                    layout( mNewPosition_left, mNewPosition_top, mNewPosition_right,  mNewPosition_bottom); //左，上，右，下
                }
                break;

            case MotionEvent.ACTION_UP:
                if(getLeft()<0||getRight()> mScreenWidth){ //当左 或 右超出边界时，视为触发了弹簧压缩，则计算弹簧释放后弹出的 X 距离
                    mSpringReleaseX = calculateSpringReleaseX(getLeft(),getRight())* mSpringReleaseHeightRate;
                }else {
                    mSpringReleaseX=0;
                }
                if(getTop()<0||getBottom()> mScreenHeight){ //当上 或 下超出边界时，视为触发了弹簧压缩，则计算弹簧释放后弹出的 Y 距离
                    mSpringReleaseY = calculateSpringReleaseY(getTop(),getBottom())* mSpringReleaseHeightRate;
                }else {
                    mSpringReleaseY=0;
                }
                do_spring_release_effect();   //弹簧回弹效果，当触发了弹簧压缩后，释放手势组件回弹，压缩越多，回弹越多
                if(null != mOnClickListener) { //当实现了监听click接口
                    if (mDownInScreenX == event.getRawX() && mDownInScreenY == event.getRawY()) { //且view没有移动时，视为点击
                        mOnClickListener.isClick(true);
                    }
                }
                break;
        }
        return true; //返回true,为了截取手势事件，当前view进行处理
    }


    /**
     * ios弹簧方法-压缩-通过放慢移动速度的方法(超出越多，放慢越多)，从而达到压缩的效果
     **/
    private  void do_spring_press_effect(){ //ios弹簧方法-压缩
        if (getLeft() < 0) {  //左放慢
            mNewPosition_left = (int) (getLeft() + calculateSpringPressDistance(mMoveDistanceX, getLeft(),false));
            mNewPosition_right = mNewPosition_left + getWidth();
        } else if (getRight() > mScreenWidth) {   //右放慢
            mNewPosition_right = (int) (getRight() + calculateSpringPressDistance(mMoveDistanceX/2, getRight(),false));
            mNewPosition_left = mNewPosition_right - getWidth();
        }
        if (getTop() < 0) {   //上放慢
            mNewPosition_top = (int) (getTop() + calculateSpringPressDistance(mMoveDistanceY, getTop(),true));
            mNewPosition_bottom = mNewPosition_top + getHeight();
        } else if (getBottom() > mScreenHeight) { //下放慢
            mNewPosition_bottom = (int) (getBottom() + calculateSpringPressDistance(mMoveDistanceY/2, getBottom(),true));
            mNewPosition_top = mNewPosition_bottom - getHeight();
        }
    }

    //移动速度随着组件view超出屏幕边界越多，变的越慢
    private double calculateSpringPressDistance(double distance, double get_view, boolean isRightOrBottom){
        return  mSpringPressSpeed * 0.1*distance * Math.sqrt(Math.abs(get_view));
    }

    //记录一下组件view超出边界的W
    private double calculateSpringReleaseX(double Left, double Right){
        double W=0.0;
        if(Left<0){   //左超边界
            W= Left;
        }else if( Left>0 && Right> mScreenWidth)  {  //右超边界
            W= Right- mScreenWidth;
        }
        return W ;
    }
    //记录一下组件view超出边界的H
    private double calculateSpringReleaseY(double Top, double Bottom){
        double H=0.0;
        if(Top<0){   //上超边界
            H= Top;
        }else if( Top>0 && Bottom> mScreenHeight)  {  //下超边界
            H= Bottom- mScreenHeight;
        }
        return H ;
    }



    /**
     * ios弹簧方法-释放
     **/
    private  void do_spring_release_effect() { //ios弹簧方法-释放
        //当开启弹簧效果，且任意一边超出屏幕边界
        if(getLeft()<mSpringReleaseX||getRight()> mScreenWidth ||getTop()<mSpringReleaseY||getBottom()> mScreenHeight) {
            mAnim_left =getLeft(); mAnim_top =getTop();
            mAnim_right =(int)(mAnim_left +getWidth());   mAnim_bottom = (int) (mAnim_top + getHeight());
            mAnimEndX =0;
            mAnimEndY =0;
            if (getLeft() < mSpringReleaseX) {
                mAnimEndX = (int) (-1 * mSpringReleaseX * 3);
                mAnim_left = 0;
                mAnim_right =(int)(mAnim_left +getWidth());
            } else if (getRight() > mScreenWidth) {
                mAnimEndX = (int) ((-1 * mSpringReleaseX * 3));
                mAnim_left = (int) (mScreenWidth - getWidth());
                mAnim_right = (int) (mScreenWidth);
            }
            if (getTop() < mSpringReleaseY) {
                mAnimEndY = (int) (-1 * mSpringReleaseY * 3);
                mAnim_top = 0;
                mAnim_bottom = (int) (mAnim_top + getHeight());
            } else if (getBottom() > mScreenHeight) {
                mAnimEndY = (int) (-1 * mSpringReleaseY * 3);
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
        transAnim.setDuration(mAnimationDuringTime);
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
     * 吸附屏幕方法,当view靠近四边小于设定的距离,且还在屏幕内时触发
     **/
    private void do_attach_boundary_effect(){
        if (mNewPosition_left <= mAttach_Distance && mNewPosition_left > 0) {  //左吸边
            mNewPosition_left = 0;
            mNewPosition_right = mNewPosition_left + getWidth();
            return;
        } else if (mScreenWidth > mNewPosition_right && mNewPosition_right > mScreenWidth - mAttach_Distance) {  //右吸边
            mNewPosition_right = mScreenWidth;
            mNewPosition_left = mNewPosition_right - getWidth();
            return;
        }
        if (mNewPosition_top <= mAttach_Distance && mNewPosition_top > 0) {   //上边吸
            mNewPosition_top = 0;
            mNewPosition_bottom = mNewPosition_top + getHeight();
            return;
        } else if (mScreenHeight > mNewPosition_bottom && mNewPosition_bottom > mScreenHeight - mAttach_Distance) {  //下边吸
            mNewPosition_bottom = mScreenHeight;
            mNewPosition_top = mNewPosition_bottom - getHeight();
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
