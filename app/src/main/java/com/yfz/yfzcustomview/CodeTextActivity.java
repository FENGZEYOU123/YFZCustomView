package com.yfz.yfzcustomview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import codeInputView.CodeText;

public class CodeTextActivity extends AppCompatActivity {
    private TextView codeTextDisplay;
    private CodeText codeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_text);
        codeText=findViewById(R.id.codeText);
        codeTextDisplay=findViewById(R.id.codeTextDisplay);
        codeText.setOnResultListener(new CodeText.OnResultListener() {
            @Override
            public void finish(String result) {
                codeText.setText(result);
            }
        });
    }
}