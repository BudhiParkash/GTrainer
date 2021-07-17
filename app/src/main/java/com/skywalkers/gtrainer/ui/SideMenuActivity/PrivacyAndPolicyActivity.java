package com.skywalkers.gtrainer.ui.SideMenuActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.skywalkers.gtrainer.R;

public class PrivacyAndPolicyActivity extends AppCompatActivity {

    private ImageButton mBckBtnPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_and_policy);

        initView();

        mBckBtnPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        mBckBtnPrivacy = findViewById(R.id.bck_btn_privacy);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}