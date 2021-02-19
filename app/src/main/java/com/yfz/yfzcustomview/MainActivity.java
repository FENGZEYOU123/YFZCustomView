package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import buttonView.YFZGestureButton;
import functionView.codeInputView1.YFZFunctionCodeInputView1;


public class MainActivity extends AppCompatActivity {
    private YFZGestureButton YFZGestureButton;
    private YFZFunctionCodeInputView1 yfzFunctionCodeInputView1;
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
        yfzFunctionCodeInputView1=findViewById(R.id.yfzFunctionCodeInputView1);
        yfzFunctionCodeInputView1.setResultListener(new YFZFunctionCodeInputView1.TextListener() {
            @Override
            public void result(String finalResult) {
               Toast.makeText(getApplicationContext(),"结果是："+finalResult,Toast.LENGTH_SHORT).show();
            }
        });
    }
}