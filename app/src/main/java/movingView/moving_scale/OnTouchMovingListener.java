package movingView.moving_scale;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import utils.YFZDisplayUtils;
import utils.YFZUtils;

/**
 * 作者：游丰泽
 * 简介：Implements OnTouchListener 为组件添加更多触摸效果
 * 双击放大，自由拖动，四角缩放，预览延迟移动
 */
public class OnTouchMovingListener implements View.OnTouchListener{
    private final static String TAG=OnTouchMovingListener.class.getName();
    private final static int MODE_DRAW_MOVING=0x100,MODE_DOUBLE_CLICK=0x101;
    private final static int MODE_CORNER_TOP_LEFT=0x200,MODE_CORNER_TOP_RIGHT=0x201,MODE_CORNER_BOTTOM_LEFT=0x202,MODE_CORNER_BOTTOM_RIGHT=0x203;
    private final static int MODE_LINE_LEFT=0x300,MODE_LINE_TOP=0x301,MODE_LINE_RIGHT=0x302,MODE_LINE_BOTTOM=0x303;

    private Context mContext;
    private View mView,mViewParent;
    private MotionEvent mEvent;
    private boolean isFirstTime=false;
    private float mDownX,mDownY;
    private int mMinimumMaxRate=10;

    private boolean mModeMoving=false,mModeDoubleClick=false,mModeFullScreen=false,mModeNormalScreen=false;
    private boolean mModeCornerTopLeft=false,mModeCornerTopRight=false,mModeCornerBottomLeft=false,mModeCornerBottomRight=false;
    private boolean mModeLineLeft =false,mModeLineTop=false,mModeLineRight=false,mModeLineBottom=false;

    private float mDistanceX,mDistanceY;
    private int mViewOriginalWidth,mViewOriginalHeight;
    private float mViewNP_left,mViewNP_top,mViewNP_right,mViewNP_bottom;
    private float mViewCP_left,mViewCP_top,mViewCP_right,mViewCP_bottom;
    private float mViewPP_left,mViewPP_top,mViewPP_right,mViewPP_bottom;
    private float mViewPP_width,mViewPP_height;
    private Rect mCornerRect=new Rect();
    private int mCornerRadius=10;
    public OnTouchMovingListener(Context context,View parentView_limitedBoundary){
        this.mContext=context;
        this.mViewParent=parentView_limitedBoundary;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downFirstTimeInitial(v);
                downEveyTimeRecordInfo(event);
                downCheckMode(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mDistanceX=(int)(event.getX()-mDownX);
                mDistanceY=(int)(event.getY()-mDownY);
                if(mModeMoving) {
                    OnModeMoving();
                }else if(mModeCornerTopLeft||mModeCornerTopRight||mModeCornerBottomLeft||mModeCornerBottomRight){
                    OnModeCornerScaling();
                }else if(mModeLineLeft||mModeLineTop||mModeLineRight||mModeLineBottom){
                    OnModeLineScaling();
                }else if(mModeNormalScreen || mModeFullScreen){
                    OnModeFullScreenOrNormalScreen();
                }
                updateNextPosition();

                break;
            case MotionEvent.ACTION_UP:
                reset();
                break;
            default:
                reset();
                break;
        }
        return true;
    }

    private void refreshDownX(){
        mDownX=mEvent.getX();
    }
    private void refreshDownY(){
        mDownX=mEvent.getY();
    }
    private void refreshDistanceX(){
        mDistanceX=mEvent.getX()-mDownX;
    }
    private void refreshDistanceY(){
        mDistanceY=mEvent.getY()-mDownY;
    }
    /**
     * Down-首次点击，记录view原始大小信息
     */
    private synchronized void downFirstTimeInitial(View v){
        if(!isFirstTime || null== mView){
            mView=v;
            mViewOriginalWidth=v.getWidth();
            mViewOriginalHeight=v.getHeight();
            isFirstTime=false;
        }
    }
    /**
     * Down-每次都记录一下当前的手指位置，和view位置
     */
    private void downEveyTimeRecordInfo(MotionEvent event){
        mEvent=event;
        mDownX=event.getX();
        mDownY=event.getY();
        if(null != mView) {
            if(!mModeFullScreen) {
                mViewPP_left = mView.getLeft();
                mViewPP_top = mView.getTop();
                mViewPP_right = mView.getRight();
                mViewPP_bottom = mView.getBottom();
                Log.d(TAG, "downEveyTimeRecordInfo: 点击-记录当前坐标 "+mViewPP_left+" "+mViewPP_top+" "+mViewPP_right+" "+mViewPP_bottom);
            }else {
                Log.d(TAG, "downEveyTimeRecordInfo: 点击-（之前全屏模式）不记录当前坐标 "+mViewPP_left+" "+mViewPP_top+" "+mViewPP_right+" "+mViewPP_bottom);
            }

            mViewPP_width=mView.getWidth();
            mViewPP_height=mView.getHeight();

            mCornerRadius=mView.getWidth()<=mView.getHeight()?mView.getWidth()/4:mView.getHeight()/4;
        }
    }

    /**
     * Down-检查点击的类型-拖拽移动，四角缩放
     */
    private void downCheckMode(MotionEvent event){
        if(null != mView) {

            switch (getMode(event)) {
                case MODE_DRAW_MOVING:
                    mModeMoving = true;
                    Log.d(TAG, "downCheckMode: 当前模式为拖拽平移");
                    break;
                case MODE_CORNER_TOP_LEFT:
                    mModeCornerTopLeft = true;
                    Log.d(TAG, "downCheckMode: 当前模式为四角缩放-左上角");
                    break;
                case MODE_CORNER_TOP_RIGHT:
                    mModeCornerTopRight = true;
                    Log.d(TAG, "downCheckMode: 当前模式为四角缩放-右上角");
                    break;
                case MODE_CORNER_BOTTOM_LEFT:
                    mModeCornerBottomLeft = true;
                    Log.d(TAG, "downCheckMode: 当前模式为四角缩放-左下角");
                    break;
                case MODE_CORNER_BOTTOM_RIGHT:
                    mModeCornerBottomRight = true;
                    Log.d(TAG, "downCheckMode: 当前模式为四角缩放-右下角");
                    break;
                case MODE_LINE_LEFT:
                    mModeLineLeft = true;
                    Log.d(TAG, "downCheckMode: 当前模式为线缩放-左边");
                    break;
                case MODE_LINE_TOP:
                    mModeLineTop = true;
                    Log.d(TAG, "downCheckMode: 当前模式为线缩放-上边");
                    break;
                case MODE_LINE_RIGHT:
                    mModeLineRight = true;
                    Log.d(TAG, "downCheckMode: 当前模式为线缩放-右边");
                    break;
                case MODE_LINE_BOTTOM:
                    mModeLineBottom = true;
                    Log.d(TAG, "downCheckMode: 当前模式为线缩放-下边");
                    break;
                case MODE_DOUBLE_CLICK:
                    mModeDoubleClick = true;
                    if(getCurrentHeightView()==getMaxLimitedHeight() && getCurrentWidthView()==getMaxLimitedWidth()){
                        mModeNormalScreen=true;
                        Log.d(TAG, "downCheckMode: 当前模式为双击-缩小");
                    }else {
                        mModeFullScreen=true;
                        Log.d(TAG, "downCheckMode: 当前模式为双击-放大");
                    }

                    break;
                default:
                    break;
            }
        }
    }
    private int getMode(MotionEvent event){
        //是否为双击
        if(YFZUtils.isDoubleClick()){
            return MODE_DOUBLE_CLICK;
        }
        //是否点击在四个角落
        int isInCorners= checkInCorners(event);
        if(isInCorners!=-1){
            return checkInCorners(event);
        }
        //是否点击在线上
        int isInLines=checkInLines(event);
        if(isInLines!=-1){
            return checkInLines(event);
        }
        //以上都不是，返回拖拽
        return MODE_DRAW_MOVING;
    }

    /**
     * 检查是否在四角
     * @param event
     * @return
     */
    private int checkInCorners(MotionEvent event){
        mCornerRect.left   = - mCornerRadius;
        mCornerRect.right  =   mCornerRadius;
        mCornerRect.top    = - mCornerRadius;
        mCornerRect.bottom =   mCornerRadius;
        if(mCornerRect.contains((int)event.getX(),(int)event.getY())){
            return MODE_CORNER_TOP_LEFT;
        }else {
            mCornerRect.left   = mView.getWidth() - mCornerRadius;
            mCornerRect.right  = mView.getWidth() + mCornerRadius;
            mCornerRect.top    = -mCornerRadius;
            mCornerRect.bottom = mCornerRadius;
            if (mCornerRect.contains((int) event.getX(), (int) event.getY())) {
                return MODE_CORNER_TOP_RIGHT;
            } else {
                mCornerRect.left   = -mCornerRadius;
                mCornerRect.right  = mCornerRadius;
                mCornerRect.top    = mView.getHeight() - mCornerRadius;
                mCornerRect.bottom = mView.getHeight() + mCornerRadius;
                if (mCornerRect.contains((int) event.getX(), (int) event.getY())) {
                    return MODE_CORNER_BOTTOM_LEFT;
                } else {
                    mCornerRect.left   = mView.getWidth() - mCornerRadius;
                    mCornerRect.right  = mView.getWidth() + mCornerRadius;
                    mCornerRect.top    = mView.getHeight() - mCornerRadius;
                    mCornerRect.bottom = mView.getHeight() + mCornerRadius;
                    if (mCornerRect.contains((int) event.getX(), (int) event.getY())) {
                        return MODE_CORNER_BOTTOM_RIGHT;
                    }else {
                        return -1;
                    }
                }
            }
        }

    }
    /**
     * 检查是否在角落
     * @param event
     * @return
     */
    private int checkInLines(MotionEvent event){
        mCornerRect.left   = - mCornerRadius;
        mCornerRect.right  =   mCornerRadius;
        mCornerRect.top    =   mCornerRadius;
        mCornerRect.bottom =   getCurrentHeightView()-mCornerRadius;
        if(mCornerRect.contains((int)event.getX(),(int)event.getY())){
            return MODE_LINE_LEFT;
        }else {
            mCornerRect.left   =  mCornerRadius;
            mCornerRect.right  =  getCurrentWidthView() - mCornerRadius;
            mCornerRect.top    = -mCornerRadius;
            mCornerRect.bottom =  mCornerRadius;
            if (mCornerRect.contains((int) event.getX(), (int) event.getY())) {
                return MODE_LINE_TOP;
            } else {
                mCornerRect.left   = getCurrentWidthView()-mCornerRadius;
                mCornerRect.right  = getCurrentWidthView()+mCornerRadius;
                mCornerRect.top    = mCornerRadius;
                mCornerRect.bottom = getCurrentHeightView()-mCornerRadius;
                if (mCornerRect.contains((int) event.getX(), (int) event.getY())) {
                    return MODE_LINE_RIGHT;
                } else {
                    mCornerRect.left   = mCornerRadius;
                    mCornerRect.right  = getCurrentWidthView() - mCornerRadius;
                    mCornerRect.top    = getCurrentHeightView() - mCornerRadius;
                    mCornerRect.bottom = getCurrentHeightView() + mCornerRadius;
                    if (mCornerRect.contains((int) event.getX(), (int) event.getY())) {
                        return MODE_LINE_BOTTOM;
                    }else {
                        return -1;
                    }
                }
            }
        }

    }
    private void OnModeMoving(){
        mViewNP_left=mView.getLeft()+mDistanceX;
        mViewNP_right=mView.getRight()+mDistanceX;
        mViewNP_top=mView.getTop()+mDistanceY;
        mViewNP_bottom=mView.getBottom()+mDistanceY;
    }
    private void OnModeFullScreenOrNormalScreen(){
        if(mModeNormalScreen){
            mViewNP_left = mViewPP_left;
            mViewNP_right =  mViewPP_right;
            mViewNP_top =  mViewPP_top;
            mViewNP_bottom =  mViewPP_bottom;
            mModeFullScreen=false;
        }else if(mModeFullScreen) {
            mViewNP_left = getMaxLimitedLeft();
            mViewNP_right = getMaxLimitedRight();
            mViewNP_top = getMaxLimitedTop();
            mViewNP_bottom = getMaxLimitedBottom();
            mModeNormalScreen=false;

        }

    }
    private void OnModeLineScaling(){
        if (mModeLineLeft) {
            mViewNP_left = (getLeftView() + mDistanceX);
            mViewNP_right = mViewPP_right;
            mViewNP_top = mViewPP_top;
            mViewNP_bottom =mViewPP_bottom;
        } else if (mModeLineRight) {
            mViewNP_left = (mViewPP_left);
            mViewNP_right = getRightView()+mDistanceX;
            mViewNP_top = mViewPP_top;
            mViewNP_bottom =mViewPP_bottom;
        } else if (mModeLineTop) {
            mViewNP_left = (mViewPP_left);
            mViewNP_right = (mViewPP_right);
            mViewNP_top = (getTopView()+mDistanceY);
            mViewNP_bottom = (mViewPP_bottom);
        } else if (mModeLineBottom) {
            mViewNP_left = (mViewPP_left);
            mViewNP_right = (mViewPP_right);
            mViewNP_top = (mViewPP_top);
            mViewNP_bottom = (getTopView()+mDistanceY);
        }
    }
    private void OnModeCornerScaling(){
            if (mModeCornerTopLeft) {
                mViewNP_left = (getLeftView() + mDistanceX);
                mViewNP_right = (mViewPP_right);
                mViewNP_top = (getTopView() + mDistanceY);
                mViewNP_bottom = (mViewPP_bottom);
            } else if (mModeCornerTopRight) {
                mViewNP_left = (getLeftView());
                mViewNP_right = (mViewPP_right + mDistanceX);
                mViewNP_top = (getTopView() + mDistanceY);
                mViewNP_bottom = (mViewPP_bottom);
            } else if (mModeCornerBottomLeft) {
                mViewNP_left = (getLeftView() + mDistanceX);
                mViewNP_right = (getRightView());
                mViewNP_top = (getTopView());
                mViewNP_bottom = (mViewPP_bottom + mDistanceY);
            } else if (mModeCornerBottomRight) {
                mViewNP_left = (getLeftView());
                mViewNP_right = (mViewPP_right + mDistanceX);
                mViewNP_top = (mViewPP_top);
                mViewNP_bottom = (mViewPP_bottom + mDistanceY);
            }
    }
    private int getCurrentWidthView(){
        if(null !=mView) {
            return mView.getWidth();
        }else {
            return 0;
        }
    }
    private int getCurrentHeightView(){
        if(null !=mView) {
            return mView.getHeight();
        }else {
            return 0;
        }
    }
    private int getLeftView(){
        if(null !=mView) {
            return mView.getLeft();
        }else {
            return 0;
        }
    }
    private int getTopView(){
        if(null !=mView) {
            return mView.getTop();
        }else {
            return 0;
        }
    }
    private int getRightView(){
        if(null !=mView) {
            return mView.getRight();
        }else {
            return 0;
        }
    }
    private int getBottomView(){
        if(null !=mView) {
            return mView.getBottom();
        }else {
            return 0;
        }
    }

    private int getMinimumLimitedWidth(){
        return getMaxLimitedWidth()/mMinimumMaxRate;
    }
    private int getMinimumLimitedHeight(){
        return getMaxLimitedHeight()/mMinimumMaxRate;
    }
    private int getMaxLimitedHeight(){
        if(null != mViewParent){
            return mViewParent.getHeight();
        }else {
            return 0;
        }
    }
    private int getMaxLimitedWidth(){
        if(null != mViewParent){
            return mViewParent.getWidth();
        }else {
            return 0;
        }
    }
    private int getMaxLimitedLeft(){
        if(null != mViewParent){
            return mViewParent.getLeft();
        }else {
            return 0;
        }
    }
    private int getMaxLimitedTop(){
        if(null != mViewParent){
            return mViewParent.getTop();
        }else {
            return 0;
        }
    }
    private int getMaxLimitedRight(){
        if(null != mViewParent){
            return mViewParent.getRight();
        }else {
            return YFZDisplayUtils.getScreenWidth(mContext);
        }
    }
    private int getMaxLimitedBottom(){
        if(null != mViewParent){
            return mViewParent.getHeight();
        }else {
            return YFZDisplayUtils.getScreenHeight(mContext);
        }
    }
    /**
     * UP-重置mode
     */
    private synchronized void reset(){
        updateNextPosition();
        mModeMoving=false;
        mModeCornerTopLeft= false;
        mModeCornerTopRight = false;
        mModeCornerBottomLeft = false;
        mModeCornerBottomRight = false;
        mModeLineLeft=false;
        mModeLineTop=false;
        mModeLineRight=false;
        mModeLineBottom=false;
        mModeDoubleClick=false;
        mModeNormalScreen=false;
//        mModeFullScreen=false;

    }
    /**
     * 更新下个移动位置画面
     */
    private void updateNextPosition(){
        if(null != mView) {
            if(mViewNP_right-mViewNP_left<getMinimumLimitedWidth()){
                if(mModeCornerBottomLeft||mModeCornerTopLeft){
                    mViewNP_left = mViewCP_right - getMinimumLimitedWidth() - 1;
                }else {
                    mViewNP_right = mViewNP_left + getMinimumLimitedWidth() + 1;
                }
                Log.d(TAG, "updateNextPosition: 小于最低限制长度");
            }
            if(mViewNP_bottom-mViewNP_top<getMinimumLimitedHeight()){
                Log.d(TAG, "updateNextPosition: 小于最低限制高度");
                if(mModeCornerTopLeft ||mModeCornerTopRight) {
                    mViewNP_top = mViewCP_bottom - getMinimumLimitedHeight() - 1;
                }else {
                    mViewNP_bottom = mViewNP_top + getMinimumLimitedHeight() + 1;
                }
            }
                mView.layout((int) mViewNP_left, (int) mViewNP_top, (int) mViewNP_right, (int) mViewNP_bottom);
                mViewCP_left = mView.getLeft();
                mViewCP_top = mView.getTop();
                mViewCP_right = mView.getRight();
                mViewCP_bottom = mView.getBottom();


            mView.postInvalidate();
        }
    }


}
