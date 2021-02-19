package functionView.codeInputView1;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.yfz.yfzcustomview.R;

public class YFZCodeInputView2 extends YFZCodeInputBaseView {
    public YFZCodeInputView2(Context context) {
        super(context);
        initial(context);
    }
    public YFZCodeInputView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public YFZCodeInputView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial(context);
    }

    private void initial(Context context){
        this.setCodeBoxBackgroundNoInput(R.drawable.yfz_codeinput_notinput_background);
        this.setCodeBoxBackgroundHasInput(R.drawable.yfz_codeinput_hasinput_background);
        this.setBackgroundColorCurrentFocus(R.drawable.yfz_codeinput_currentfocus_background);
        this.setCodeBoxTextColor(Color.WHITE);
        this.setCodeBoxCursorIsVisible(false);
    }

}