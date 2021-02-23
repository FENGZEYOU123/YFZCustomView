package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import buttonView.YFZGestureButton;

public class MainActivity extends AppCompatActivity {
    private YFZGestureButton codeInputButton,newCodeInputButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codeInputButton=findViewById(R.id.codeInputButton);
        newCodeInputButton=findViewById(R.id.newCodeInputButton);
        codeInputButton.setMTextName("旧密码框-自组制");
        newCodeInputButton.setMTextName("新密码框-自绘制");
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
    }
}