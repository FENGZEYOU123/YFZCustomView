package com.yfz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.yfz.yfzcustomview.R;

public class MainActivity extends AppCompatActivity {
    private SpringMovingView springMovingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        YfzUtil.setActivity(this);
        springMovingView=findViewById(R.id.springMovingView);
        springMovingView.setOnSpringMovingClickListener(new SpringMovingView.OnClickListener() {
            @Override
            public void isClick(boolean isClick) {
                Toast.makeText(getApplicationContext(),"没有移动，视为点击",Toast.LENGTH_SHORT).show();
            }
        });
    }
}