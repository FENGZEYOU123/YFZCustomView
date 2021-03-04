package movingView.moving_scale;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import utils.YFZUtils;

/**
 * 作者：有丰泽
 * 简介：Implements OnTouchListener 为组件添加更多触摸效果
 * 双击放大，自由拖动，四角缩放，预览延迟移动
 */
public class OnTouchMovingListener implements View.OnTouchListener{
    private final static String TAG=OnTouchMovingListener.class.getName();
    private final static int MODE_DRAW_MOVING=100,MODE_DOUBLE_CLICK=101;
    private final static int MODE_CORNER_TOP_LEFT=200,MODE_CORNER_TOP_RIGHT=201,MODE_CORNER_BOTTOM_LEFT=202,MODE_CORNER_BOTTOM_RIGHT=203;
    private View mView;
    private boolean isFirstTime=false;
    private ArrayList<Rect> mRectArrayList=new ArrayList<>();
    private float mDownX,mDownY;

    private boolean mModeMoving=false,mModeDoubleClick=false;
    private boolean mModeCornerTopLeft=false,mModeCornerTopRight=false,mModeCornerBottomLeft=false,mModeCornerBottomRight=false;


    private float mDistanceX,mDistanceY;
    private int mViewOriginalWidth,mViewOriginalHeight;
    private float mViewNP_left,mViewNP_top,mViewNP_right,mViewNP_bottom;
    private float mViewCP_left,mViewCP_top,mViewCP_right,mViewCP_bottom;
    private int mCheckMode =MODE_DRAW_MOVING;

    private Rect mCornerRect=new Rect();
    private int mCornerRadius=10;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downFirstTimeInitial(v);
                downEveyTimeRecordInfo(event);
                downCheckMode(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mDistanceX=event.getX()-mDownX;
                mDistanceY=event.getY()-mDownY;
                if(mModeMoving) {
                    OnModeMoving();
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

    /**
     * Down-首次点击，记录view原始大小信息
     */
    private synchronized void downFirstTimeInitial(View v){
        if(!isFirstTime || null== mView){
            mView=v;
            mViewOriginalWidth=v.getWidth();
            mViewOriginalHeight=v.getHeight();
            isFirstTime=false;
            mRectArrayList.add(new Rect());
            mRectArrayList.add(new Rect());
            mRectArrayList.add(new Rect());
            mRectArrayList.add(new Rect());
        }
    }
    /**
     * Down-每次都记录一下当前的手指位置，和view位置
     */
    private void downEveyTimeRecordInfo(MotionEvent event){
        mDownX=event.getX();
        mDownY=event.getY();
        if(null != mView) {
            mViewCP_left = mView.getLeft();
            mViewCP_top = mView.getTop();
            mViewCP_right = mView.getRight();
            mViewCP_bottom = mView.getBottom();
            mCornerRadius=mView.getWidth()<=mView.getHeight()?mView.getWidth()/4:mView.getHeight()/2;
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
                case MODE_DOUBLE_CLICK:
                    mModeDoubleClick = true;
                    Log.d(TAG, "downCheckMode: 当前模式为双击-放大/缩小");
                    break;
                default:
                    break;
            }
        }
    }
    private int getMode(MotionEvent event){
        if(YFZUtils.isDoubleClick()){
            return MODE_DOUBLE_CLICK;
        }
        mCornerRect.left   = - mCornerRadius;
        mCornerRect.right  =   mCornerRadius;
        mCornerRect.top    = - mCornerRadius;
        mCornerRect.bottom =   mCornerRadius;
        if(mCornerRect.contains((int)event.getX(),(int)event.getY())){
            return MODE_CORNER_TOP_LEFT;
        }else {
            mCornerRect.left = mView.getWidth() - mCornerRadius;
            mCornerRect.right = mView.getWidth() + mCornerRadius;
            mCornerRect.top = -mCornerRadius;
            mCornerRect.bottom = mCornerRadius;
            if (mCornerRect.contains((int) event.getX(), (int) event.getY())) {
                return MODE_CORNER_TOP_RIGHT;
            } else {
                mCornerRect.left = -mCornerRadius;
                mCornerRect.right = mCornerRadius;
                mCornerRect.top = mView.getHeight() - mCornerRadius;
                mCornerRect.bottom = mView.getHeight() + mCornerRadius;
                if (mCornerRect.contains((int) event.getX(), (int) event.getY())) {
                    return MODE_CORNER_BOTTOM_LEFT;
                } else {
                    mCornerRect.left = mView.getWidth() - mCornerRadius;
                    mCornerRect.right = mView.getWidth() + mCornerRadius;
                    mCornerRect.top = mView.getHeight() - mCornerRadius;
                    mCornerRect.bottom = mView.getHeight() + mCornerRadius;
                    if (mCornerRect.contains((int) event.getX(), (int) event.getY())) {
                        return MODE_CORNER_BOTTOM_RIGHT;
                    }
                }
            }
        }

        return MODE_DRAW_MOVING;
    }
    private void OnModeMoving(){
        mViewNP_left=mView.getLeft()+mDistanceX;
        mViewNP_right=mView.getRight()+mDistanceX;
        mViewNP_top=mView.getTop()+mDistanceY;
        mViewNP_bottom=mView.getBottom()+mDistanceY;
    }

    /**
     * UP-重置mode
     */
    private synchronized void reset(){
        mModeMoving=false;
        mModeCornerTopLeft= false;
        mModeCornerTopRight = false;
        mModeCornerBottomLeft = false;
        mModeCornerBottomRight = false;
        mModeDoubleClick=false;
        mCheckMode=MODE_DRAW_MOVING;
    }
    /**
     * 更新下个移动位置画面
     */
    private void updateNextPosition(){
        if(null != mView) {
            mView.layout((int)mViewNP_left,(int)mViewNP_top,(int)mViewNP_right,(int)mViewNP_bottom);
//            mView.postInvalidate();
        }
    }

}
