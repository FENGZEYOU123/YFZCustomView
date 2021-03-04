package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import buttonView.YFZGestureButton;

public class MainActivity extends AppCompatActivity {
    private YFZGestureButton codeInputButton,newCodeInputButton,buttonMovingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initialView();
        initialData();
    }
    private void initialView(){
        codeInputButton=findViewById(R.id.codeInputButton);
        newCodeInputButton=findViewById(R.id.newCodeInputButton);
        buttonMovingView=findViewById(R.id.buttonMovingView);

    }
    private void initialData(){
        codeInputButton.setMTextName("旧密码框-自组制");
        newCodeInputButton.setMTextName("新密码框-自绘制");
        buttonMovingView.setMTextName("移动组件");
        codeInputButton.addListenerCallBack(new YFZGestureButton.CallBackIsClick() {
            @Override
            public void isClick(boolean isClick) {
                startActivity(new Intent(getApplicationContext(), CodeInputActivity.class));
            }
        });
        newCodeInputButton.addListenerCallBack(new YFZGestureButton.CallBackIsClick() {
            @Override
            public void isClick(boolean isClick) {
                startActivity(new Intent(getApplicationContext(), CodeTextActivity.class));
            }
        });
        buttonMovingView.addListenerCallBack(new YFZGestureButton.CallBackIsClick() {
            @Override
            public void isClick(boolean isClick) {
                startActivity(new Intent(getApplicationContext(), MovingViewActivity.class));
            }
        });
    }


}