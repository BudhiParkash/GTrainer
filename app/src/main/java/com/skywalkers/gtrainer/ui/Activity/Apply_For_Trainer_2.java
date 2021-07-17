package com.skywalkers.gtrainer.ui.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.skywalkers.gtrainer.Api.ApiClientInterface;
import com.skywalkers.gtrainer.R;
import com.skywalkers.gtrainer.model.User;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Apply_For_Trainer_2 extends AppCompatActivity {

    private ImageButton mBckBtnApply2;
    private TextInputLayout mEtxtrainerExperince;
    private TextInputLayout mEtxtrainerabout;
    private TextInputLayout mEtxaddress;
    private TextInputLayout mEtxlanguage;
    private Button mbtnFinish;


    String expierance;
    String about;
    String address;
    String langauge;

    private String trainerName;
    private String trainerEmail;
    private String trainerCity;

    String gender ;
    SharedPreferences preferences;
    String token;
    private ProgressBar mDetailsProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply__for__trainer_2);
        initView();
        preferences = getSharedPreferences("ProfileData", MODE_PRIVATE);
        token = preferences.getString("tokken", "");

        trainerCity = getIntent().getStringExtra("city");
        trainerName = getIntent().getStringExtra("name");
        trainerEmail = getIntent().getStringExtra("email");
        gender = getIntent().getStringExtra("gender");

        mbtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expierance = mEtxtrainerExperince.getEditText().getText().toString();
                about = mEtxtrainerabout.getEditText().getText().toString();
                address = mEtxaddress.getEditText().getText().toString();
                langauge = mEtxlanguage.getEditText().getText().toString();

                if(TextUtils.isEmpty(expierance)){
                    mEtxtrainerExperince.setError("Please enter Experince");
                    mEtxtrainerExperince.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(about)){
                    mEtxtrainerabout.setError("Please enter about yourself");
                    mEtxtrainerabout.requestFocus();

                    return;
                }
                else if(TextUtils.isEmpty(address)){
                    mEtxaddress.setError("Please enter proper Address");
                    mEtxaddress.requestFocus();

                    return;
                }
                else if(TextUtils.isEmpty(langauge)){
                    mEtxlanguage.setError("Please enter your LAnguage");
                    mEtxlanguage.requestFocus();
                    return;
                }
                else {
                    trainerapply();
                }
            }
        });



    }

    private void trainerapply() {
        mDetailsProgressBar.setVisibility(View.VISIBLE);
        User trainerData = new User(true, trainerName, trainerEmail,
                trainerCity, about, address, gender,
                langauge, expierance);

        Call<User> call = ApiClientInterface.getTrainerApiService().applyfortrainer(token, trainerData);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    mDetailsProgressBar.setVisibility(View.GONE);
                    finish();
                    Toast.makeText(Apply_For_Trainer_2.this, "Successfully apply for trainer", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mDetailsProgressBar.setVisibility(View.GONE);
                    Toast.makeText(Apply_For_Trainer_2.this, "try after some time" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mDetailsProgressBar.setVisibility(View.GONE);
                Toast.makeText(Apply_For_Trainer_2.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mDetailsProgressBar = findViewById(R.id.trainerdetail_Progressbar);
        mBckBtnApply2 = findViewById(R.id.bck_btn_apply2);
        mEtxtrainerExperince = findViewById(R.id.etxtrainerExperince);
        mEtxtrainerabout = findViewById(R.id.etxtrainerabout);
        mEtxaddress = findViewById(R.id.etxaddress);
        mEtxlanguage = findViewById(R.id.etxlanguage);
        mbtnFinish = findViewById(R.id.btnfinish);
    }
}