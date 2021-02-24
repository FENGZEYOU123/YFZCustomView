package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import codeInputView.CodeText;

public class CodeTextActivity extends AppCompatActivity {
    private TextView codeTextDisplay;
    private CodeText codeText,codeText1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_text);
        codeText=findViewById(R.id.codeText);
        codeTextDisplay=findViewById(R.id.codeTextDisplay);
        codeText.setOnResultListener(new CodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeTextDisplay.setText("输入结果： "+result);
            }
        });
        codeText1=findViewById(R.id.codeText1);
        codeText1.setOnResultListener(new CodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeText.setUnLock();
            }
        });

    }

}