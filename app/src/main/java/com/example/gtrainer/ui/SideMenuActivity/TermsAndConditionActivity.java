package com.example.gtrainer.ui.SideMenuActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gtrainer.R;

public class TermsAndConditionActivity extends AppCompatActivity {

    private ImageButton mBckBtnTermCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);
        initView();
        mBckBtnTermCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        mBckBtnTermCondition = findViewById(R.id.bck_btn_termCondition);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}