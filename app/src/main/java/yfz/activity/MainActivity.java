package yfz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import yfz.view.GestureButton;

public class MainActivity extends AppCompatActivity {
    private GestureButton newCodeInputButton,buttonMovingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialView();
        initialData();
    }
    private void initialView(){
        newCodeInputButton=findViewById(R.id.newCodeInputButton);
        buttonMovingView=findViewById(R.id.buttonMovingView);

    }
    private void initialData(){
        newCodeInputButton.setMTextName("新密码框-自绘制");
        buttonMovingView.setMTextName("移动组件");
        newCodeInputButton.addListenerCallBack(new GestureButton.CallBackIsClick() {
            @Override
            public void isClick(boolean isClick) {
                startActivity(new Intent(getApplicationContext(), PasswordCodeActivity.class));
            }
        });
        buttonMovingView.addListenerCallBack(new GestureButton.CallBackIsClick() {
            @Override
            public void isClick(boolean isClick) {
                startActivity(new Intent(getApplicationContext(), MovingViewActivity.class));
            }
        });
    }


}