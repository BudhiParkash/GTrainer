package com.example.gtrainer.OnBoardingScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gtrainer.R;

public class Login extends AppCompatActivity {

    private TextView mTxtNext;
    private EditText mETxtphoneNumber;
    private TextView mtxtCountryCode;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        mETxtphoneNumber.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mETxtphoneNumber, InputMethodManager.HIDE_IMPLICIT_ONLY);


        mTxtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userNum = mETxtphoneNumber.getText().toString();

                if(userNum.length() != 10 ){
                    mETxtphoneNumber.setError("Please enter valid number");
                    mETxtphoneNumber.requestFocus();
                    return;
                }

                else {
                    Intent intent = new Intent(Login.this,OtpActivity.class);
                    String number =  "+91" + userNum;
                    intent.putExtra("number", number);
                    intent.putExtra("onlyNumber", userNum);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    private void initView() {
        mTxtNext = (TextView) findViewById(R.id.txtNext);
        mETxtphoneNumber = (EditText) findViewById(R.id.eTxtphoneNumber);
        mtxtCountryCode = findViewById(R.id.txtcountrycode);
        mProgressBar = findViewById(R.id.phoneProgressbar);

    }
}