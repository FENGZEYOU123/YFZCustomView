package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import buttonView.YFZGestureButton;


public class MainActivity extends AppCompatActivity {
    private YFZGestureButton YFZGestureButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        YFZGestureButton=findViewById(R.id.YFZGestureButton);
        YFZGestureButton.setMTextColor(Color.BLUE);
        YFZGestureButton.setMTextSize(30);
        YFZGestureButton.setMTextName("点击按钮");
        YFZGestureButton.setMBackgroundRadiusRx(10);
        YFZGestureButton.setMBackgroundRadiusRy(10);
        YFZGestureButton.setMBackgroundColorIsClick(Color.RED);
        YFZGestureButton.setMBackgroundColorUnClick(Color.GREEN);
        YFZGestureButton.addListenerCallBack(new buttonView.YFZGestureButton.CallBackIsClick() {
            @Override
            public void isClick(boolean isClick) {
                if(isClick) {
                    Toast.makeText(getApplicationContext(), "被点击", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}