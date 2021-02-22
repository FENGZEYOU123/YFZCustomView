package codeInputView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.yfz.yfzcustomview.R;

public class YFZCodeInputViewBasicSlide2 extends YFZCodeInputViewBasic {
    public YFZCodeInputViewBasicSlide2(Context context) {
        super(context);
        initial(context);
    }
    public YFZCodeInputViewBasicSlide2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public YFZCodeInputViewBasicSlide2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial(context);
    }

    private void initial(Context context){
        this.setCodeBoxBackgroundNoInput(R.drawable.yfz_codeinput_slide1_noinput_background);
        this.setSlideVisible(true);
        this.setCodeBoxTextColor(Color.BLACK);
        this.setCodeBoxCursorIsVisible(false);
        this.setSlideColor(Color.BLACK);
        this.setSlideWidth(2);
    }

}