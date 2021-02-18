package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
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
//        yfzFunctionCodeInputView1.setCodeBoxMaxNumber(4);
        yfzFunctionCodeInputView1.setCodeBoxBackgroundColor(Color.RED);
        yfzFunctionCodeInputView1.setCodeBoxMargin(30,30,30,30);
        yfzFunctionCodeInputView1.setCodeBoxHintText("");

    }
}