package com.yfz.yfzcustomview;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;

import movingView.moving_scale.Component_drawFrame;
import movingView.moving_scale.Component_normalMovingView;

public class MovingViewActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getName();
    private Context mContext;
    private Component_normalMovingView movingView;
    private Component_drawFrame drawFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moving_view);
        initialView();
    }
    private void initialView(){
        mContext=getApplicationContext();
        movingView=findViewById(R.id.movingView);
        drawFrame=findViewById(R.id.drawFrame);
    }
}

