package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import buttonView.YFZGestureButton;


public class MainActivity extends AppCompatActivity {
    private YFZGestureButton YFZGestureButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        YFZGestureButton=findViewById(R.id.YFZGestureButton);
        YFZGestureButton.setMTextColor(Color.RED);
        YFZGestureButton.setMTextSIze(20);
        YFZGestureButton.setMTextName("点击按钮");

    }
}