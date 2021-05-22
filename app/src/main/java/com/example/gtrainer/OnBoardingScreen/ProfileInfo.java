package com.example.gtrainer.OnBoardingScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gtrainer.Api.ApiClientInterface;
import com.example.gtrainer.MainActivity;
import com.example.gtrainer.R;
import com.example.gtrainer.model.User;
import com.example.gtrainer.model.UserPojo;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileInfo extends AppCompatActivity {

    private TextView mTxtNextProfileInfo;
    private CircleImageView mUserPic;
    private EditText mEtxtName;
    private TextView mTxtphonenumber;
    private EditText mEtxtEmail;
    private EditText mEtxtAge;
    private RadioGroup mRadioGrp;
    private RadioButton mRadioM;
    private RadioButton mRadioF;
    private ProgressBar mProfileProgreeBar;

    String name , email , age;
    SharedPreferences prfs;
    private SharedPreferences.Editor editor;

    String onlyNumber;

    String tokken;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        initView();
        editor = getSharedPreferences("ProfileData", MODE_PRIVATE).edit();
        editor.apply();
        try {
            onlyNumber = getIntent().getStringExtra("num");
        }
        catch (Exception e){

        }
        createUser();

        mEtxtName.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEtxtName, InputMethodManager.HIDE_IMPLICIT_ONLY);

        mTxtNextProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProfileProgreeBar.setVisibility(View.VISIBLE);
                name = mEtxtName.getText().toString();
                email = mEtxtEmail.getText().toString();
                age = mEtxtAge.getText().toString();
                if(TextUtils.isEmpty(name)){
                    mEtxtName.setError("Please enter name");
                    mEtxtName.requestFocus();
                    mProfileProgreeBar.setVisibility(View.INVISIBLE);
                    return;
                }
                else if(TextUtils.isEmpty(email)){
                    mEtxtEmail.setError("Please enter email");
                    mEtxtEmail.requestFocus();
                    mProfileProgreeBar.setVisibility(View.INVISIBLE);
                    return;
                }
                else if(!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$")){
                    mEtxtEmail.setError("Please enter valid email");
                    mEtxtEmail.requestFocus();
                    mProfileProgreeBar.setVisibility(View.INVISIBLE);
                    return;
                }
//                else if(TextUtils.isEmpty(age)){
//                    mEtxtAge.setError("Please enter age");
//                    mEtxtAge.requestFocus();
//                    mProfileProgreeBar.setVisibility(View.INVISIBLE);
//                    return;
//              }
                else {
                    postUserData(name , email);
                    editor.putString("name" , name);
                    editor.putString("email" , email);
                    editor.apply();
                }


            }
        });


    }

    private void postUserData(String name , String email) {

        User userData = new User(name , email);

        Call<User>  call = ApiClientInterface.getTrainerApiService().postUserData(tokken , userData);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if((response.code() == 200)){
                    Intent intent = new Intent(ProfileInfo.this , MainActivity.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(ProfileInfo.this, "Sucess", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(ProfileInfo.this, "try after sometime", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileInfo.this, "try after sometime", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void createUser() {

        long number = Long.parseLong(onlyNumber);

        Toast.makeText(this, ""+number, Toast.LENGTH_SHORT).show();
        User phnNumer = new User(number);


        Call<UserPojo> call = ApiClientInterface.getTrainerApiService().createUser(phnNumer);
        call.enqueue(new Callback<UserPojo>() {
            @Override
            public void onResponse(Call<UserPojo> call, Response<UserPojo> response) {
                if(response.code() == 201 || response.code() == 200){
                    UserPojo userData = response.body();

                    assert userData != null;
                    tokken = userData.getToken();
                    userID = userData.getUser().getId();
                    mEtxtName.setText(userData.getUser().getUser_name());
                    mEtxtEmail.setText(userData.getUser().getEmail());
                    editor.putString("tokken",tokken);
                    editor.putString("userId" , userID);
                    editor.apply();
                    Toast.makeText(ProfileInfo.this, "Success", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 400) {

                    Toast.makeText(ProfileInfo.this, "Try after sometime", Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(ProfileInfo.this, "Try after sometime or contact to company" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserPojo> call, Throwable t) {
                Toast.makeText(ProfileInfo.this, "Something went wrong. Please contact to company" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mTxtNextProfileInfo = findViewById(R.id.txtNextProfileInfo);
        mUserPic = findViewById(R.id.userPic);
        mEtxtName = findViewById(R.id.etxtName);
        mTxtphonenumber = findViewById(R.id.txtphonenumber);
        mEtxtEmail = findViewById(R.id.etxtEmail);
        mEtxtAge = findViewById(R.id.etxtAge);
        mRadioGrp = findViewById(R.id.radioGrp);
        mRadioM = findViewById(R.id.radioM);
        mRadioF = findViewById(R.id.radioF);
        mProfileProgreeBar = findViewById(R.id.profileProgreeBar);
    }
}