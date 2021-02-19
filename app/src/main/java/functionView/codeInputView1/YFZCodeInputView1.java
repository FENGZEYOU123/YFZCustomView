package functionView.codeInputView1;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.yfz.yfzcustomview.R;

public class YFZCodeInputView1 extends YFZCodeInputBaseView {
    public YFZCodeInputView1(Context context) {
        super(context);
        initial(context);
    }
    public YFZCodeInputView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public YFZCodeInputView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial(context);
    }

    private void initial(Context context){
        this.setCodeBoxBackground(R.drawable.yfz_codeinput_codebox_background);
        this.setCodeBoxTextColor(Color.BLACK);
        this.setCodeBoxCursorIsVisible(false);
    }

}
