package com.example.gtrainer.OnBoardingScreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import com.example.gtrainer.Api.ApiClientInterface;
import com.example.gtrainer.R;
import com.example.gtrainer.model.User;
import com.example.gtrainer.model.UserPojo;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.safetynet.SafetyNetClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity  {

    private static final String TAG = "otpActivty" ;
    private ProgressBar mOtpProgressBar;
    private TextView mTxtBackFromOtp;
    private TextView mTxtVerificationName;
    private TextView mTxtVerify;
    private TextView mTxtHeadingOtp;
    private OtpTextView mEtxtOtp;
    private TextView mTxtResendOtp;
    private TextView mOtpcountdown;


    private FirebaseAuth myAuth;
    String userNumer;
    String onlyNumber;
    String verificationId;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        initView();
        mEtxtOtp.requestFocus();
    /*    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEtxtOtp, InputMethodManager.HIDE_IMPLICIT_ONLY);*/

        myAuth = FirebaseAuth.getInstance();
        



        userNumer = getIntent().getStringExtra("number");
        onlyNumber = getIntent().getStringExtra("onlyNumber");

        mTxtHeadingOtp.setText("Enter four digit code we sent on your phone number - " + userNumer);

        sendVerificationCode(userNumer);

        mTxtVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOtpProgressBar.setVisibility(View.VISIBLE);
                String otp = mEtxtOtp.getOTP();
                verifyCode(otp);
            }
        });

        mEtxtOtp.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                mOtpProgressBar.setVisibility(View.VISIBLE);
                verifyCode(otp);

            }
        });

        OtpCountdown();



    }




    private void sendVerificationCode(String userNumer) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(myAuth)
                        .setPhoneNumber(userNumer)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Log.d(TAG, "onCodeSent:" + verificationId);
            //Toast.makeText(OtpView.this, verificationId, Toast.LENGTH_SHORT).show();
            super.onCodeSent(s, forceResendingToken);
            Log.d(TAG, "onCodesSent:" + verificationId);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code == null){signInWithCredential(phoneAuthCredential);
            }
            else {verifyCode(code);}
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OtpActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();


        }};

    //Verify code
    public void verifyCode(String code) {
        try {
            mOtpProgressBar.setVisibility(View.VISIBLE);
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithCredential(credential);
        }catch (Exception e){
            mOtpProgressBar.setVisibility(View.GONE);
            Toast toast = Toast.makeText(getApplicationContext(), "Verification Code is wrong, try again", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    //Sign in with phone no. using Firebase
    public void signInWithCredential(PhoneAuthCredential credential){
        myAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mOtpProgressBar.setVisibility(View.GONE);
                            Intent i = new Intent(OtpActivity.this, ProfileInfo.class);
                            i.putExtra("num",onlyNumber);
                            startActivity(i);
                            finish();

                        }
                        else{
                            Toast.makeText(OtpActivity.this,"Fail" , Toast.LENGTH_SHORT).show();
                            mOtpProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
    public void OtpCountdown(){

        new CountDownTimer( 60 * 1000 , 1000) {

            public void onTick(long millisUntilFinished) {
                mOtpcountdown.setText("Sending Otp - " + millisUntilFinished / 1000 + " Sec");
                //here you can have your logic to set text to edittext
            }

            @SuppressLint("ResourceAsColor")
            public void onFinish() {
                mOtpcountdown.setText("");
                mTxtResendOtp.setVisibility(View.VISIBLE);
                mTxtResendOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendVerificationCode(userNumer);
                        Toast.makeText(OtpActivity.this, "Sending otp", Toast.LENGTH_SHORT).show();

                    }
                });

            }

        }.start();


    }

    private void initView() {
        mOtpProgressBar = findViewById(R.id.otpProgressBar);
        mTxtBackFromOtp = findViewById(R.id.txtBackFromOtp);
        mTxtVerificationName = findViewById(R.id.txtVerificationName);
        mTxtVerify = findViewById(R.id.txtVerify);
        mTxtHeadingOtp = findViewById(R.id.txtHeadingOtp);
        mEtxtOtp = findViewById(R.id.etxtOtp);
        mTxtResendOtp = findViewById(R.id.txtResendOtp);
        mOtpcountdown = findViewById(R.id.otpcountdown);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
