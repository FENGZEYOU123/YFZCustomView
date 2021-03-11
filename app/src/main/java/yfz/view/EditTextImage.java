package yfz.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import yfz.activity.R;

/**
 * 简介：带图片的输入框 （组合）
 * 作者：游丰泽
 */
public class EditTextImage extends LinearLayout {
    private final int IMAGE_ON_LEFT=100,IMAGE_ON_RIGHT=101;
    private final String TAG= EditTextImage.class.getName();
    private Context mContext;
    private EditText mEditText;
    private ImageView mImageView;
    private LinearLayout.LayoutParams mEditTextLP,mImageViewLP;
    private int mImagePosition=IMAGE_ON_RIGHT;
    private int mImageDrawableRes;
    private Drawable mImageDrawable;
    private int mImageHeight=50;
    private int mImageWidth=50;
    public EditTextImage(Context context) {
        super(context);
        initial(context);
    }
    public EditTextImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.EditTextImage);
        mImagePosition=typedArray.getInt(R.styleable.EditTextImage_editTextWithImage_imagePosition,mImagePosition);
        mImageDrawableRes=typedArray.getResourceId(R.styleable.EditTextImage_editTextWithImage_imageDrawable,-1);
        initial(context);

    }

    //初始化
    private void initial(Context context){
        this.mContext=context;
        this.setOrientation(HORIZONTAL);
        try{
            mImageDrawable= getResources().getDrawable(mImageDrawableRes);
        }catch (Exception e){
            Log.e(TAG, "EditTextWithImage: "+e.toString() );
        }
        addImageView();
        addEditText();

    }
    //初始化-添加EditText
    private void addEditText(){
        this.mEditText=new EditText(this.getContext());
        mEditTextLP =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mEditTextLP.weight=1;
        mEditTextLP.gravity=Gravity.CENTER;
        this.mEditText.setLayoutParams(mEditTextLP);
        this.addView(this.mEditText);
        this.mEditText.setText("asdsdasds");
    }
    private void addImageView(){
        this.mImageView=new ImageView(this.getContext());
        mImageViewLP=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImageViewLP.gravity=Gravity.CENTER;
        mImageViewLP.weight=1;
        this.mImageView.setLayoutParams(mImageViewLP);
        this.addView(this.mImageView);
        this.mImageView.setImageResource(R.drawable.yfz_codeinput_hasinput_background);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    //初始化-按钮
}
