package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import functionView.codeInputView1.YFZCodeInputViewBasic;
import functionView.codeInputView1.YFZCodeInputViewBasic1;

public class CodeInputActivity extends AppCompatActivity {
    private YFZCodeInputViewBasic yfzCodeInputViewBasic;
    private YFZCodeInputViewBasic1 CodeInputView1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_input);
        yfzCodeInputViewBasic=findViewById(R.id.CodeInputBaseView);
        yfzCodeInputViewBasic.setResultListener(new YFZCodeInputViewBasic.TextListener() {
            @Override
            public void result(String result) {
                Toast.makeText(getApplicationContext(), "验证码为:"+ result, Toast.LENGTH_SHORT).show();
            }
        });
        CodeInputView1=findViewById(R.id.CodeInputView1);
        CodeInputView1.setResultListener(new YFZCodeInputViewBasic.TextListener() {
            @Override
            public void result(String result) {
                Toast.makeText(getApplicationContext(), "验证码为:"+ result, Toast.LENGTH_SHORT).show();
            }
        });
    }
}