package functionView.codeInputView1;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import utils.YFZPreventError;
import utils.YFZUtils;

/**
 * Code输入框
 */
public class YFZCodeInputBaseView extends LinearLayout {
    private Context context;
    private ArrayList<CodeInputView1TextBox> textViewArrayList =new ArrayList();
    private int codeBoxMaxNumber =4;
    private final String TAG= YFZCodeInputBaseView.class.getName();
    private int currentFocus=0;
    private int nextFocus=0;
    private TextListener textListener;
    private String result="";
    private int codeBoxBackgroundHasInput=-1;
    private Drawable codeBoxBackgroundHasInputDrawable=null;
    private int codeBoxBackgroundNoInput=-1;
    private Drawable codeBoxBackgroundNoInputDrawable=null;
    public YFZCodeInputBaseView(Context context) {
        super(context);
        initial(context);
    }
    public YFZCodeInputBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial(context);
    }
    public YFZCodeInputBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initial(context);
    }
    private void initial(Context context) {
        this.context = context;
        this.setOrientation(HORIZONTAL);
        this.setBackgroundColor(Color.TRANSPARENT);
        setCodeBoxMaxNumber(codeBoxMaxNumber);
    }

    /**
     * 点击组件内任意地方弹出软键盘
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP && YFZPreventError.checkArrayList( this.textViewArrayList)) {
            for(int i=0;i<textViewArrayList.size();i++){
                if(textViewArrayList.get(i).length()<=0){
                    YFZUtils.showSoftKeyboard(this.textViewArrayList.get(i), context);
                    break;
                }else {
                    YFZUtils.showSoftKeyboard(this.textViewArrayList.get(textViewArrayList.size()-1), context);
                }
            }
        }
        return true;
    }
//    /**
//     * 设置输入框BOX数量，需要提前设置，不然会重置所有属性
//     * @param number
//     */
//    public void setCodeBoxMaxNumber(int number){
//        Log.d(TAG, "setCodeBoxMaxNumber: "+number);
//        if(number==codeBoxMaxNumber ){
//            if(textViewArrayList.size()!=number) {
//                for (int i = 0; i < codeBoxMaxNumber; i++) {
//                    this.textViewArrayList.add(new CodeInputView1TextBox(context));
//                    this.addView(this.textViewArrayList.get(i));
//                }
//            }
//        }else {
//            if(number>=1) {
//                if(number-codeBoxMaxNumber<0){
//                    for (int i = 0; i < Math.abs(number-codeBoxMaxNumber); i++) {
//                        this.removeView(this.textViewArrayList.get(i));
//                        this.textViewArrayList.remove(i);
//                    }
//                }else {
//                    for (int i = 0; i < Math.abs(number-codeBoxMaxNumber); i++) {
//                        this.textViewArrayList.add(new CodeInputView1TextBox(context));
//                        this.addView(this.textViewArrayList.get(textViewArrayList.size()-1));
//                    }
//                }
//            }
//        }
//        if(YFZPreventError.checkArrayList( this.textViewArrayList)) {
//            YFZUtils.showSoftKeyboard(this.textViewArrayList.get(0), context);
//        }
//    }

    /**
     * 设置输入框BOX数量，需要提前设置，不然会重置所有属性
     * @param number
     */
    public void setCodeBoxMaxNumber(int number){
        Log.d(TAG, "setCodeBoxMaxNumber: "+number);
        if(number<1) number=codeBoxMaxNumber;
            Log.d(TAG, "second");
            this.removeAllViews();
            this.textViewArrayList.clear();
            this.textViewArrayList = new ArrayList();
            this.codeBoxMaxNumber = number;
            for (int i = 0; i < codeBoxMaxNumber; i++) {
                this.textViewArrayList.add(new CodeInputView1TextBox(context));
                this.textViewArrayList.get(i).addCallBackInput(new CodeInputView1TextBox.CodeBoxInputCallBack() {
                    @Override
                    public void input(boolean done, View view) {
                        if(textViewArrayList.contains(view)){
                            currentFocus=textViewArrayList.indexOf(view); //当前正在输入的框
                            setBackgroundHasInput(textViewArrayList.get(currentFocus));
                            nextFocus = currentFocus < textViewArrayList.size() - 1 ? currentFocus + 1 : currentFocus;
                            if(currentFocus==textViewArrayList.size()-1){
                                if(null!= textListener ) {
                                    result = "";
                                    for (CodeInputView1TextBox index : textViewArrayList) {
                                        result = result + index.getEditableText();
                                    }
                                    textListener.result(result);
                                }
                                YFZUtils.closeSoftKeyBoard(textViewArrayList.get(nextFocus), context);
                            }else {

                                YFZUtils.showSoftKeyboard(textViewArrayList.get(nextFocus), context);
                            }

                        }
                    }
                });
                this.textViewArrayList.get(i).addCallBackDeleted(new CodeInputView1TextBox.CodeBoxDeleteCallBack() {
                    @Override
                    public void deleted(boolean done, View view) {
                        if(textViewArrayList.contains(view)){
                            currentFocus=textViewArrayList.indexOf(view); //当前正在输入的框
                            setBackgroundNoInput(textViewArrayList.get(currentFocus));
                            nextFocus=currentFocus>0?currentFocus-1:currentFocus;
                            YFZUtils.showSoftKeyboard(textViewArrayList.get(nextFocus), context);
                            textViewArrayList.get(nextFocus).setText("");
                        }
                    }
                });
                this.addView(this.textViewArrayList.get(i));
            }

        if(YFZPreventError.checkArrayList( this.textViewArrayList)) {
            YFZUtils.showSoftKeyboard(this.textViewArrayList.get(0), context);
        }
    }

    /**
     * 设置输入框光标颜色
     * @param textCursorDrawable
     */
    public void setCodeBoxCursorColor(Drawable textCursorDrawable){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                this.textViewArrayList.get(i).setTextCursorDrawable(textCursorDrawable);
            }
        }
    }
    /**
     * 设置输入框光标颜色
     * @param textCursorDrawable
     */

    public void setCodeBoxCursorColor(int textCursorDrawable){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                this.textViewArrayList.get(i).setTextCursorDrawable(textCursorDrawable);
            }
        }
    }
    /**
     * 设置输入框光标是否显示
     * @param hideCursor
     */
    public void setCodeBoxCursorIsVisible(boolean hideCursor){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setCursorVisible(hideCursor);
        }
    }
    /**
     * 设置输入框文字颜色
     * @param color
     */
    public void setCodeBoxTextColor(ColorStateList color){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setTextColor(color);
        }
    }

    /**
     * 设置输入框文字颜色
     * @param color
     */
    public void setCodeBoxTextColor(int color){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setTextColor(color);
        }
    }

    /**
     * 设置输入框文字大小
     * @param size
     */
    public void setCodeBoxTextSize(float size){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setTextSize(size);
        }
    }
    /**
     * 设置输入框文字粗细
     * @param isBold
     */
    public void setCodeBoxTextBold(boolean isBold){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).getPaint().setFakeBoldText(isBold);
        }
    }
    /**
     * 设置输入框类型-默认是数字
     * @param inputType
     */
    public void setCodeBoxInputType(int inputType){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setInputType(inputType);
        }
    }
    /**
     * 设置输入框宽度
     * @param width
     */
    public void setCodeBoxWidth(int width){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setWidth(width);
        }
    }
    /**
     * 设置输入框高度
     * @param height
     */
    public void setCodeBoxHeight(int height){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setHeight(height);
        }
    }

    /**
     * 设置输入框提示内容
     * @param object
     */
    public void setCodeBoxHintText(Object object){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setHint(object.toString());
        }
    }
    /**
     * 设置输入框margin边距
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setCodeBoxMargin(int left, int top, int right, int bottom){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setMargin(left,top,right,bottom);
        }
    }
    /**
     * 设置输入框padding边距
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setCodeBoxPadding(int left, int top, int right, int bottom){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setPadding(left,top,right,bottom);
        }
    }

//    /**
//     * 设置输入框BOX背景
//     * @param backgroundColor
//     */
//    public void setCodeBoxBackground(int backgroundColor){
//        for(int i = 0; i< codeBoxMaxNumber; i++){
//            this.textViewArrayList.get(i).setBackgroundColor(backgroundColor);
//        }
//    }
    /**
     * 设置输入框BOX背景-已输入
     * @param backgroundDrawable
     */
    public void setCodeBoxBackgroundHasInput(Drawable backgroundDrawable){
        this.codeBoxBackgroundHasInputDrawable =backgroundDrawable;
    }
    /**
     * 设置输入框BOX背景-已输入
     * @param resId
     */
    public void setCodeBoxBackgroundHasInput(int resId){
            this.codeBoxBackgroundHasInput=resId;
    }
    /**
     * 设置输入框BOX背景-未输入
     * @param backgroundDrawable
     */
    public void setCodeBoxBackgroundNoInput(Drawable backgroundDrawable){
        codeBoxBackgroundNoInputDrawable=backgroundDrawable;
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setBackground(backgroundDrawable);
        }
    }
    /**
     * 设置输入框BOX背景-未输入
     * @param resId
     */
    public void setCodeBoxBackgroundNoInput(int resId){
        codeBoxBackgroundNoInput=resId;
        try {
            getResources().getResourceTypeName(resId);
            for(int i = 0; i< codeBoxMaxNumber; i++){
                this.textViewArrayList.get(i).setBackgroundResource(resId);
            }
        }catch (Exception e){
            try {
                for (int i = 0; i < codeBoxMaxNumber; i++) {
                    this.textViewArrayList.get(i).setBackgroundColor(resId);
                }
            }catch (Exception d){
                Log.e(TAG, "setCodeBoxBackground: setBackgroundColor error "+e.toString() );
                codeBoxBackgroundNoInput= -1;
            }
            Log.e(TAG, "setCodeBoxBackground: id not found "+e.toString() );
        }

    }
    /**
     * 设置输入框BOX背景
     * @param colorStateList
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setCodeBoxBackgroundColorStateList(ColorStateList colorStateList){
        for(int i = 0; i< codeBoxMaxNumber; i++){
            this.textViewArrayList.get(i).setBackgroundTintList(colorStateList);
        }
    }
    /**
     * 接口
     */
    public interface TextListener {
        void result(String result);
    }
    /**
     * 提供接口监听输入内容
     */
    public void setResultListener(TextListener textlistener){
        this.textListener = textlistener;
    }

    private void setBackgroundNoInput(CodeInputView1TextBox view){
        if(-1!=codeBoxBackgroundNoInput){
            try {
                view.setBackgroundResource(codeBoxBackgroundNoInput);
            }catch (Exception e){
                try {
                    view.setBackgroundColor(codeBoxBackgroundNoInput);
                }catch (Exception d){
                    codeBoxBackgroundNoInput= -1;
                }
            }
        }else if(null!=codeBoxBackgroundNoInputDrawable){
            view.setBackground(codeBoxBackgroundNoInputDrawable);
        }
    }
    private void setBackgroundHasInput(CodeInputView1TextBox view){
        if(-1!=codeBoxBackgroundHasInput){
            try {
                view.setBackgroundResource(codeBoxBackgroundHasInput);
            }catch (Exception e){
                try {
                    view.setBackgroundColor(codeBoxBackgroundHasInput);
                }catch (Exception d){
                    codeBoxBackgroundNoInput= -1;
                }
            }
        }else if(null!=codeBoxBackgroundHasInputDrawable){
            view.setBackground(codeBoxBackgroundHasInputDrawable);
        }

    }
}
