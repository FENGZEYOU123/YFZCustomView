package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import editTextView.EditCodeText;

public class CodeTextActivity extends AppCompatActivity {
    private TextView codeTextDisplay;
    private EditCodeText editCodeTextBasic, editCodeText1, editCodeText2, editCodeText3, editCodeText4, editCodeText5, editCodeText6;
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
        editCodeTextBasic =findViewById(R.id.codeTextBasic);
        editCodeText1 =findViewById(R.id.codeText1);
        editCodeText2 =findViewById(R.id.codeText2);
        editCodeText3 =findViewById(R.id.codeText3);
        editCodeText4 =findViewById(R.id.codeText4);
        editCodeText5 =findViewById(R.id.codeText5);
        editCodeText6 =findViewById(R.id.codeText6);
        codeText6_lock=findViewById(R.id.codeText6_lock);

    }
    private void setListener(){

        editCodeTextBasic.setOnResultListener(new EditCodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        editCodeText1.setOnResultListener(new EditCodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        editCodeText2.setOnResultListener(new EditCodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        editCodeText3.setOnResultListener(new EditCodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        editCodeText4.setOnResultListener(new EditCodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        editCodeText5.setOnResultListener(new EditCodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        editCodeText6.setOnResultListener(new EditCodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
                codeText6_lock.setText("开锁");
            }
        });
        codeText6_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCodeText6.setUnLock();
                codeText6_lock.setText("已解锁");

            }
        });
    }
}