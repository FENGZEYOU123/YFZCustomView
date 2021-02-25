package utils;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class SoftKeyBoardListener implements View.OnKeyListener {
    private OnInputListener mOnInputListener;
    private String TAG=SoftKeyBoardListener.class.getName();
    public SoftKeyBoardListener( OnInputListener onKeyListener){
        this.mOnInputListener =onKeyListener;
    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN) { //按下是触发了
            Log.d(TAG, "onKey: "+keyCode+" "+event.getAction()+" "+event.toString());
            if(KeyEvent.KEYCODE_9>=keyCode && keyCode>=KeyEvent.KEYCODE_0 && event.getMetaState()==0 ){  //如果是为数字的话
                if(null!= mOnInputListener) mOnInputListener.inputNumber(keyCode-7);
            }else if(KeyEvent.KEYCODE_Z>=keyCode && keyCode>=KeyEvent.KEYCODE_A){  //如果是为字母
                if(null!= mOnInputListener) mOnInputListener.inputLetter((event.getMetaState()==0)?String.valueOf(event.getDisplayLabel()).toLowerCase():String.valueOf(event.getDisplayLabel()),event.getMetaState()!=0);
            }
        }
        return false;
    }
}


