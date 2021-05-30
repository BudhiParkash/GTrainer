package com.example.gtrainer.ui.SideMenuActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gtrainer.R;

public class HelpActivity extends AppCompatActivity {

    private ImageButton mBckBtnHelp;
    private Button mBtnContactUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        initView();

        mBckBtnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBtnContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "9992468544";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mBckBtnHelp = findViewById(R.id.bck_btn_help);
        mBtnContactUs = findViewById(R.id.btnContactUs);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}