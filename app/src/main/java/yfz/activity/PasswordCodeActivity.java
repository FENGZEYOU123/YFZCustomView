package yfz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import yfz.view.PasswordCode;

public class PasswordCodeActivity extends AppCompatActivity {
    private TextView codeTextDisplay,unLock;
    private PasswordCode  passwordCode1, passwordCode2, passwordCode3, passwordCode4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_code);
        initialView();
        setListener();
    }
    private void initialView(){
        codeTextDisplay=findViewById(R.id.codeTextDisplay);
        unLock =findViewById(R.id.unLock);
        passwordCode1 =findViewById(R.id.passwordCode1);
        passwordCode2 =findViewById(R.id.passwordCode2);
        passwordCode3 =findViewById(R.id.passwordCode3);
        passwordCode4 =findViewById(R.id.passwordCode4);

    }
    private void setListener(){

        passwordCode1.setOnResultListener(new PasswordCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        passwordCode2.setOnResultListener(new PasswordCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        passwordCode3.setOnResultListener(new PasswordCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        passwordCode4.setOnResultListener(new PasswordCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
                unLock.setText("输入完成，密码框锁定，点击开锁");
            }
        });
        unLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordCode4.setUnLock();
                unLock.setText("已解锁");
            }
        });
    }
}