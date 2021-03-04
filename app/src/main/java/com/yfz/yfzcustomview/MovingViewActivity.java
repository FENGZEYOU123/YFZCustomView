package com.yfz.yfzcustomview;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import movingView.moving_scale.Component_drawFrame;
import movingView.moving_scale.Component_normalMovingView;
import movingView.moving_scale.OnTouchMovingListener;

public class MovingViewActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getName();
    private Context mContext;
    private LinearLayout movingView;
    private ConstraintLayout baseLayout;
    private Component_drawFrame drawFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moving_view);
        initialView();
        initialData();
    }
    private void initialView(){
        mContext=getApplicationContext();
        movingView=findViewById(R.id.movingView);
        drawFrame=findViewById(R.id.drawFrame);
        baseLayout=findViewById(R.id.baseLayout);
    }
    private void initialData(){
        movingView.setOnTouchListener(new OnTouchMovingListener(this.getApplicationContext(),baseLayout));
    }
}

