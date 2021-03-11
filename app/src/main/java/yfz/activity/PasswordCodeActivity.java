package yfz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import yfz.view.passwordCode;

public class PasswordCodeActivity extends AppCompatActivity {
    private TextView codeTextDisplay;
    private passwordCode passwordCodeBasic, passwordCode1, passwordCode2, passwordCode3, passwordCode4, passwordCode5, passwordCode6;
    private Button codeText6_lock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_code);
        initialView();
        setListener();
    }
    private void initialView(){
        codeTextDisplay=findViewById(R.id.codeTextDisplay);
        passwordCodeBasic =findViewById(R.id.codeTextBasic);
        passwordCode1 =findViewById(R.id.codeText1);
        passwordCode2 =findViewById(R.id.codeText2);
        passwordCode3 =findViewById(R.id.codeText3);
        passwordCode4 =findViewById(R.id.codeText4);
        passwordCode5 =findViewById(R.id.codeText5);
        passwordCode6 =findViewById(R.id.codeText6);
        codeText6_lock=findViewById(R.id.codeText6_lock);

    }
    private void setListener(){

        passwordCodeBasic.setOnResultListener(new passwordCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        passwordCode1.setOnResultListener(new passwordCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        passwordCode2.setOnResultListener(new passwordCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        passwordCode3.setOnResultListener(new passwordCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        passwordCode4.setOnResultListener(new passwordCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        passwordCode5.setOnResultListener(new passwordCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        passwordCode6.setOnResultListener(new passwordCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
                codeText6_lock.setText("开锁");
            }
        });
        codeText6_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordCode6.setUnLock();
                codeText6_lock.setText("已解锁");

            }
        });
    }
}