package codeInputView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

public class CodeText extends LinearLayout {
    public CodeText(@NonNull Context context) {
        super(context);
        setBackgroundColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
//       canvas.drawRect(0,0,getWidth(),getBottom(),paint);
    }
}
