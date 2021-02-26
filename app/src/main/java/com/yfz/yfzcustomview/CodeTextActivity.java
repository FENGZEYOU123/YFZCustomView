package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import editTextView.EditTextCode;

public class CodeTextActivity extends AppCompatActivity {
    private TextView codeTextDisplay;
    private EditTextCode editTextCodeBasic, editTextCode1, editTextCode2, editTextCode3, editTextCode4, editTextCode5, editTextCode6;
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
        editTextCodeBasic =findViewById(R.id.codeTextBasic);
        editTextCode1 =findViewById(R.id.codeText1);
        editTextCode2 =findViewById(R.id.codeText2);
        editTextCode3 =findViewById(R.id.codeText3);
        editTextCode4 =findViewById(R.id.codeText4);
        editTextCode5 =findViewById(R.id.codeText5);
        editTextCode6 =findViewById(R.id.codeText6);
        codeText6_lock=findViewById(R.id.codeText6_lock);

    }
    private void setListener(){

        editTextCodeBasic.setOnResultListener(new EditTextCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        editTextCode1.setOnResultListener(new EditTextCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        editTextCode2.setOnResultListener(new EditTextCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        editTextCode3.setOnResultListener(new EditTextCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        editTextCode4.setOnResultListener(new EditTextCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        editTextCode5.setOnResultListener(new EditTextCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        editTextCode6.setOnResultListener(new EditTextCode.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
                codeText6_lock.setText("开锁");
            }
        });
        codeText6_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextCode6.setUnLock();
                codeText6_lock.setText("已解锁");

            }
        });
    }
}