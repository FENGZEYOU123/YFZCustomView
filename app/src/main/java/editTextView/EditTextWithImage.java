package editTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

import com.yfz.yfzcustomview.R;

/**
 * 简介：带图片的输入框 （组合）
 * 作者：游丰泽
 */
public class EditTextWithImage extends androidx.appcompat.widget.AppCompatEditText {
    private final int IMAGE_ON_LEFT=100,IMAGE_ON_RIGHT=101;
    private final String TAG=EditTextWithImage.class.getName();
    private Context mContext;
    private int mImagePosition=IMAGE_ON_RIGHT;
    private int mImageDrawableRes;
    private Drawable mImageDrawable;
    private Rect mRect;
    private int mImageHeight=50;
    private int mImageWidth=50;
    public EditTextWithImage(Context context) {
        super(context);
        initial(context);
    }
    public EditTextWithImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.EditTextWithImage);
        mImagePosition=typedArray.getInt(R.styleable.EditTextWithImage_editTextWithImage_imagePosition,mImagePosition);
        mImageDrawableRes=typedArray.getResourceId(R.styleable.EditTextWithImage_editTextWithImage_imageDrawable,-1);
        initial(context);

    }

    //初始化
    private void initial(Context context){
        this.mContext=context;
        this.mRect=new Rect();
        try{
            mImageDrawable= getResources().getDrawable(mImageDrawableRes);
        }catch (Exception e){
            Log.e(TAG, "EditTextWithImage: "+e.toString() );
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mImagePosition==IMAGE_ON_LEFT){
            mRect.left=0;
            mRect.right= mRect.left+mImageWidth;
            mRect.bottom=getHeight();
            mRect.top=mRect.bottom+mImageHeight;
        }else if(mImagePosition==IMAGE_ON_RIGHT) {
            mRect.right=getWidth();
            mRect.left= mRect.right-mImageWidth;
            mRect.bottom=getHeight();
            mRect.top=mImageHeight;
        }
        if(mImageDrawable!=null) {
            mImageDrawable.setBounds(mRect);
            mImageDrawable.draw(canvas);
        }

    }

    //初始化-按钮
}
