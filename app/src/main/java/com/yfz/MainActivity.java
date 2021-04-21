package com.yfz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.yfz.yfzcustomview.R;

public class MainActivity extends AppCompatActivity {
    private SimpleMovingView mSimpleMovingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        YfzUtil.setActivity(this);
        mSimpleMovingView=findViewById(R.id.simpleMovingView);
        mSimpleMovingView.setOnMovingViewClickListener(new SimpleMovingView.OnClickListener() {
            @Override
            public void isClick(boolean isClick) {
                Toast.makeText(getApplicationContext(),"点击",Toast.LENGTH_SHORT).show();
            }
        });

    }
}