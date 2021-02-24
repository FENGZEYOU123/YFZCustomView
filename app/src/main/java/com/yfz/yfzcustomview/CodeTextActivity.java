package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import codeInputView.CodeText;

public class CodeTextActivity extends AppCompatActivity {
    private TextView codeTextDisplay;
    private CodeText codeTextBasic,codeText1,codeText2,codeText3,codeText4,codeText5,codeText6;
    private Button codeText6_lock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_text);
        initialView();
        setListener();
    }
    private void initialView(){
        codeTextDisplay=findViewById(R.id.codeTextDisplay);
        codeTextBasic=findViewById(R.id.codeTextBasic);
        codeText1=findViewById(R.id.codeText1);
        codeText2=findViewById(R.id.codeText2);
        codeText3=findViewById(R.id.codeText3);
        codeText4=findViewById(R.id.codeText4);
        codeText5=findViewById(R.id.codeText5);
        codeText6=findViewById(R.id.codeText6);
        codeText6_lock=findViewById(R.id.codeText6_lock);

    }
    private void setListener(){

        codeTextBasic.setOnResultListener(new CodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        codeText1.setOnResultListener(new CodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        codeText2.setOnResultListener(new CodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        codeText3.setOnResultListener(new CodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        codeText4.setOnResultListener(new CodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        codeText5.setOnResultListener(new CodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        codeText6.setOnResultListener(new CodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
                codeText6_lock.setText("开锁");
            }
        });
        codeText6_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeText6.setUnLock();
                codeText6_lock.setText("已解锁");

            }
        });
    }
}