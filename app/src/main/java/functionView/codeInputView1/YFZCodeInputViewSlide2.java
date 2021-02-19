package functionView.codeInputView1;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.yfz.yfzcustomview.R;

public class YFZCodeInputViewSlide2 extends YFZCodeInputBaseView {
    public YFZCodeInputViewSlide2(Context context) {
        super(context);
        initial(context);
    }
    public YFZCodeInputViewSlide2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public YFZCodeInputViewSlide2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial(context);
    }

    private void initial(Context context){
        this.setCodeBoxBackgroundNoInput(R.drawable.yfz_codeinput_slide2_noinput_background);
        this.setCodeBoxBackgroundHasInput(R.drawable.yfz_codeinput_slide2_hasinput_background);

        this.setCodeBoxTextColor(Color.BLACK);
        this.setCodeBoxCursorIsVisible(false);
    }

}
