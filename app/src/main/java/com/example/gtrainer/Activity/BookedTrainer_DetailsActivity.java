package com.example.gtrainer.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gtrainer.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookedTrainer_DetailsActivity extends AppCompatActivity {



    SharedPreferences preferences;

    String tokken;

    String trainerName;
    String trainerGender;
    int payment;
    String dateofbooking;
    String periodofbooking;
    String bookinglastDate;
    private TextView mBookedtrainerName;
    private TextView mBookedtrainerGender;
    private TextView mPayment;
    private TextView mDateofbooking;
    private TextView mPeriodofbooking;
    private TextView mLastdateofbooking;
    private Button mBtnCancel;
    private ProgressBar mBookedtrainerdetailsProgressbar;

    private ImageView mTrainerBookedImage;

    String picUrl;
    private ImageButton mBckBtnBookeddetails;
    private CircleImageView mToolbarImageBookedtrainer;
    private TextView mToolbarBookedtrainerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_trainer__details);
        initView();

        preferences = getSharedPreferences("ProfileData", MODE_PRIVATE);
        tokken = preferences.getString("tokken", "");



        try {
            trainerName = getIntent().getStringExtra("trainerName");
            trainerGender = getIntent().getStringExtra("trainerGender");
            payment = getIntent().getIntExtra("payment", 0);
            dateofbooking = getIntent().getStringExtra("dateofbooking");
            periodofbooking = getIntent().getStringExtra("periodofbooking");
            bookinglastDate = getIntent().getStringExtra("bookinglastDate");
            picUrl = getIntent().getStringExtra("picUrl");

        } catch (Exception e) {

        }


        try {
            mToolbarBookedtrainerName.setText(trainerName);
            mBookedtrainerGender.setText(trainerGender);
            mBookedtrainerName.setText(trainerName);
            mPayment.setText(payment + "");
            mDateofbooking.setText(dateofbooking);
            mPeriodofbooking.setText(periodofbooking);
            mLastdateofbooking.setText(bookinglastDate);

            Picasso.get().load(picUrl).into(mTrainerBookedImage);
            Picasso.get().load(picUrl).into(mToolbarImageBookedtrainer);
        }
        catch (Exception e){}



        mBckBtnBookeddetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    private void initView() {
        mBookedtrainerName = findViewById(R.id.bookedtrainer_name);
        mBookedtrainerGender = findViewById(R.id.bookedtrainer_gender);
        mPayment = findViewById(R.id.payment);
        mDateofbooking = findViewById(R.id.dateofbooking);
        mPeriodofbooking = findViewById(R.id.periodofbooking);
        mLastdateofbooking = findViewById(R.id.lastdateofbooking);
        mBtnCancel = findViewById(R.id.btnCancel);
        mBookedtrainerdetailsProgressbar = findViewById(R.id.bookedtrainerdetails_Progressbar);
        mTrainerBookedImage = findViewById(R.id.bookedtrainer_image);
        mBckBtnBookeddetails = findViewById(R.id.bck_btn_bookeddetails);
        mToolbarImageBookedtrainer = findViewById(R.id.toolbar_Image_bookedtrainer);
        mToolbarBookedtrainerName = findViewById(R.id.toolbar_bookedtrainer_Name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}