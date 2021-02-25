package utils;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class SoftKeyBoardListener implements View.OnKeyListener {
    private OnInputListener mOnInputListener;
    private String TAG=SoftKeyBoardListener.class.getName();
    private String mInput="";
    private View mView;
    public SoftKeyBoardListener( View view, OnInputListener onKeyListener){
        this.mOnInputListener =onKeyListener;
        this.mView=view;
        mView.requestFocus();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d(TAG, "onKey: "+keyCode+" "+event.getAction()+" "+event.toString());
        if(event.getAction()==KeyEvent.ACTION_DOWN) { //按下是触发了
            if(KeyEvent.KEYCODE_9>=keyCode && keyCode>=KeyEvent.KEYCODE_0 && event.getMetaState()==0 ){  //如果是为数字的话
                if(null!= mOnInputListener) mOnInputListener.inputNumber(keyCode-7);
            }else if(KeyEvent.KEYCODE_Z>=keyCode && keyCode>=KeyEvent.KEYCODE_A){  //如果是为字母
                mInput=(event.getMetaState()==0)?String.valueOf(event.getDisplayLabel()).toLowerCase():String.valueOf(event.getDisplayLabel());
                if(null!= mOnInputListener) mOnInputListener.inputLetter(mInput,true);
            }else if(KeyEvent.KEYCODE_ENTER == keyCode  ){  //如果是回车
                if(null!= mOnInputListener) mOnInputListener.isBackClick(true);
            }else if(KeyEvent.KEYCODE_DEL == keyCode){  //如果是删除
                if(null!= mOnInputListener) mOnInputListener.isDeleteClick(true);
            }
            return true;
        }else if(event.getAction()==KeyEvent.ACTION_MULTIPLE){//选择的多个字母
            mInput=String.valueOf(event.getCharacters());
            if(null!= mOnInputListener) mOnInputListener.inputLetter(mInput,false);
        }else {
            Log.d(TAG, "onKey: ");
        }
        mInput="";

        return false;
    }
}


