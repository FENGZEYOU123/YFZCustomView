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
        YFZGestureButton.setMTextSize(20);
        YFZGestureButton.setMTextName("Button");
        YFZGestureButton.setMBackgroundRadiusRx(25);
        YFZGestureButton.setMBackgroundRadiusRy(25);
        YFZGestureButton.addListenerCallBack(new buttonView.YFZGestureButton.CallBackIsClick() {
            @Override
            public void isClick(boolean isClick) {
                if(isClick) {
                    Toast.makeText(getApplicationContext(), "isClick", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}