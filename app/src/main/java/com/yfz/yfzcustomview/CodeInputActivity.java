package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import functionView.codeInputView1.YFZCodeInputViewBasic;
import functionView.codeInputView1.YFZCodeInputViewBasic1;
import functionView.codeInputView1.YFZCodeInputViewBasic2;
import functionView.codeInputView1.YFZCodeInputViewBasicSlide1;
import functionView.codeInputView1.YFZCodeInputViewBasicSlide2;

public class CodeInputActivity extends AppCompatActivity {
    private YFZCodeInputViewBasic yfzCodeInputViewBasic;
    private YFZCodeInputViewBasic1 CodeInputView1;
    private YFZCodeInputViewBasic2 CodeInputView2;
    private YFZCodeInputViewBasicSlide1 CodeInputViewSlide1;
    private YFZCodeInputViewBasicSlide2 CodeInputViewSlide2;



    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_input);
        yfzCodeInputViewBasic=findViewById(R.id.CodeInputBaseView);
        textView=findViewById(R.id.textView);
        CodeInputView1=findViewById(R.id.CodeInputView1);
        CodeInputView2=findViewById(R.id.CodeInputView2);
        yfzCodeInputViewBasic.setResultListener(new YFZCodeInputViewBasic.TextListener() {
            @Override
            public void result(String result) {
                textView.setText("验证码为: "+result);
            }
        });
        CodeInputView1.setResultListener(new YFZCodeInputViewBasic.TextListener() {
            @Override
            public void result(String result) {
                textView.setText("验证码为: "+result);
            }
        });
        CodeInputView2.setResultListener(new YFZCodeInputViewBasic.TextListener() {
            @Override
            public void result(String result) {
                textView.setText("验证码为: "+result);
            }
        });
        CodeInputViewSlide1.setResultListener(new YFZCodeInputViewBasic.TextListener() {
            @Override
            public void result(String result) {
                textView.setText("验证码为: "+result);
            }
        });
        CodeInputViewSlide2.setResultListener(new YFZCodeInputViewBasic.TextListener() {
            @Override
            public void result(String result) {
                textView.setText("验证码为: "+result);
            }
        });
    }
}