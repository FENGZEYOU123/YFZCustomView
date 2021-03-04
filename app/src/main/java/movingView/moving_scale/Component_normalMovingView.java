package movingView.moving_scale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import utils.YFZDisplayUtils;
import utils.YFZUtils;

public class Component_normalMovingView extends LinearLayout {

    private Context mContext;
    private final String TAG=Component_normalMovingView.class.getName();
    private Rect mRectTopLeft =new Rect();
    private Rect mRectTopRight =new Rect();
    private Rect mRectBottomLeft =new Rect();
    private Rect mRectBottomRight =new Rect();
    private int cornerRadius=20;
    private boolean isDouClick=false;
    private boolean mModeScaleTopLeft =false,mModeScaleTopRight=false,mModeScaleBottomLeft=false,mModeScaleBottomRight=false;
    private boolean mModeFullScreen =false;
    private float mX, mY;
    private int mViewWidth, mViewHeight;
    private float mDistanceX,mDistanceY;
    private float mNextMovingLeft,mNextMovingTop,mNextMovingRight,mNextMovingBottom;
    private float mCurrentLeft,mCurrentTop,mCurrentRight,mCurrentBottom;

    public Component_normalMovingView(Context context) {
        super(context);
        initial(context);
    }
    public Component_normalMovingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public Component_normalMovingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initial(Context context){
        this.mContext=context;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void refresh(){
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX =  event.getX();
                mY =  event.getY();
                mViewWidth =this.getWidth();
                mViewHeight =this.getHeight();
                mCurrentLeft=getLeft();
                mCurrentTop=getTop();
                mCurrentRight=getRight();
                mCurrentBottom=getBottom();
                if(mViewWidth <= mViewHeight){
                    cornerRadius= mViewWidth /4;
                }else {
                    cornerRadius= mViewHeight /4;
                }
                OnTouchDownInitialRect( event);
                break;
            case MotionEvent.ACTION_MOVE:
                mDistanceX = (event.getX() - mX);
                mDistanceY = (event.getY() - mY);
                Log.d(TAG, "onTouch:处在H5中，自由平移");
                mNextMovingLeft=this.getLeft()+mDistanceX;
                mNextMovingRight=mNextMovingLeft+getWidth();
                mNextMovingTop=this.getTop()+mDistanceY;
                mNextMovingBottom=mNextMovingTop+getHeight();

                            if(mModeScaleTopLeft) {
                                Log.d(TAG, "移动: mModeScaleTopLeft");
                                mNextMovingRight =  mCurrentRight;
                                mNextMovingBottom = mCurrentBottom;
                            }else if(mModeScaleTopRight){
                                Log.d(TAG, "移动: mModeScaleTopRight");
                                mNextMovingLeft = mCurrentLeft;
                                mNextMovingRight=mNextMovingLeft+getWidth()+mDistanceX;
                                mNextMovingTop=this.getTop()+mDistanceY;
                                mNextMovingBottom=  mNextMovingTop+getHeight();
                            }else if(mModeScaleBottomLeft){
                                Log.d(TAG, "移动: mModeScaleBottomLeft");
                                mNextMovingRight =   this.getRight();
                                mNextMovingTop =  this.getTop();
                            }else if(mModeScaleBottomRight){
                                Log.d(TAG, "移动: mModeScaleBottomRight");
                                mNextMovingLeft =   this.getLeft();
                                mNextMovingTop =  this.getTop();
                            }else {
                                Log.d(TAG, "移动: 自由拖动");
                            }

//                        futureMoveView.refresh(mSurfaceMove_large_l, mSurfaceMove_large_t, mSurfaceMove_large_r, mSurfaceMove_large_b);
                updateNextMoving();


                break;
            case MotionEvent.ACTION_UP:
//                isDouClick= YFZUtils.isDoubleClick();
//                if(isDouClick) {
//                    OnTouchDoubleClick();
//                }else if(mModeScaleTopLeft ||mModeScaleTopRight || mModeScaleBottomLeft || mModeScaleBottomRight){
//                    onTouchCorner();
//                }else {
//                    if(!mModeFullScreen) {
//                        OnTouchTranslation();
//                    }
//                }
//
//                mSurfaceConfirm_l=v.getLeft();
//                mSurfaceConfirm_r=mSurfaceConfirm_l+v.getWidth();
//                mSurfaceConfirm_t=v.getTop();
//                mSurfaceConfirm_b=mSurfaceConfirm_t+v.getHeight();
//                futureMoveView.refresh(0, 0, 0, 0);
//                if(event.getRawY()<vTopLine.getBottom()){
//                    currentInBar=true;
//                }else {
//                    currentInBar=false;
//                }
//                isDouClick=false;
//                mModeScaleTopLeft =false;
                mModeScaleTopLeft =false;
                mModeScaleTopRight=false;
                mModeScaleBottomLeft =false;
                mModeScaleBottomRight=false;
                break;
            default:
                break;
        }
        return true;
    }


    /**
     * 点击-初始化角落rect判断是否为缩放点击
     * @param event
     */
    private synchronized void OnTouchDownInitialRect( MotionEvent event){

        mRectTopLeft.left= -cornerRadius;
        mRectTopLeft.right= cornerRadius;
        mRectTopLeft.top= -cornerRadius;
        mRectTopLeft.bottom= cornerRadius;

        mRectTopRight.left= this.getWidth()-cornerRadius;
        mRectTopRight.right= this.getWidth()+cornerRadius;
        mRectTopRight.top= -cornerRadius;
        mRectTopRight.bottom= cornerRadius;

        mRectBottomLeft.left= -cornerRadius;
        mRectBottomLeft.right= cornerRadius;
        mRectBottomLeft.top= this.getHeight()-cornerRadius;
        mRectBottomLeft.bottom=  this.getHeight()+cornerRadius;

        mRectBottomRight.left=this.getWidth()-cornerRadius;
        mRectBottomRight.right=this.getWidth()+cornerRadius;
        mRectBottomRight.top= this.getHeight()-cornerRadius;
        mRectBottomRight.bottom=this.getHeight()+cornerRadius;

        if(mRectTopLeft.contains((int)event.getX(),(int)event.getY())){
            Log.d(TAG, "onTouch: down 左上角Mode");
            mModeScaleTopLeft =true;
        }else if(mRectTopRight.contains((int)event.getX(),(int)event.getY())){
            mModeScaleTopRight=true;
            Log.d(TAG, "onTouch: down 右上角Mode");
        }else if(mRectBottomLeft.contains((int)event.getX(),(int)event.getY())){
            mModeScaleBottomLeft=true;
            Log.d(TAG, "onTouch: down 左下角Mode");
        }else if(mRectBottomRight.contains((int)event.getX(),(int)event.getY())){
            mModeScaleBottomRight=true;
            Log.d(TAG, "onTouch: down 右下角Mode");
        }
    }
    private void updateNextMoving(){
        this.layout((int)(mNextMovingLeft), (int)(mNextMovingTop), (int)(mNextMovingRight), (int)(mNextMovingBottom));
    }
}
