package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import buttonView.YFZGestureButton;

public class MainActivity extends AppCompatActivity {
    private YFZGestureButton codeInputButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codeInputButton=findViewById(R.id.codeInputButton);
        codeInputButton.setMTextName("CodeInput组件");
        codeInputButton.addListenerCallBack(new YFZGestureButton.CallBackIsClick() {
            @Override
            public void isClick(boolean isClick) {
                startActivity(new Intent(getApplicationContext(), CodeInputActivity.class));
            }
        });
    }
}