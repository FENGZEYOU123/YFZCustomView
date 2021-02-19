package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import buttonView.YFZGestureButton;
import functionView.codeInputView1.YFZCodeInputBaseView;


public class MainActivity extends AppCompatActivity {
    private YFZGestureButton YFZGestureButton;
    private YFZCodeInputBaseView yfzCodeInputBaseView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        YFZGestureButton=findViewById(R.id.YFZGestureButton);
        YFZGestureButton.setMTextSize(20);
        YFZGestureButton.addListenerCallBack(new buttonView.YFZGestureButton.CallBackIsClick() {
            @Override
            public void isClick(boolean isClick) {
                if(isClick) {
                    Toast.makeText(getApplicationContext(), "isClick", Toast.LENGTH_SHORT).show();
                }
            }
        });
        yfzCodeInputBaseView =findViewById(R.id.yfzFunctionCodeInputView1);
        yfzCodeInputBaseView.setResultListener(new YFZCodeInputBaseView.TextListener() {
            @Override
            public void result(String finalResult) {
               Toast.makeText(getApplicationContext(),"结果是："+finalResult,Toast.LENGTH_SHORT).show();
            }
        });
    }
}