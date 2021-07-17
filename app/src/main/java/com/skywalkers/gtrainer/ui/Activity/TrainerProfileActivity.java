package com.skywalkers.gtrainer.ui.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skywalkers.gtrainer.Adapter.Certificate_Profile_Adapter;
import com.skywalkers.gtrainer.Adapter.Trainer_Pic_Adapter;
import com.skywalkers.gtrainer.R;
import com.skywalkers.gtrainer.model.CertificatePhotoPojo;
import com.skywalkers.gtrainer.model.TrainerPicPojo;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrainerProfileActivity extends AppCompatActivity {


    private CircleImageView mToolbarImageProfile;
    private TextView mToolbarUserName;
    private TextView mProfileName;
    private TextView mGender;
    private TextView mExperince;
    private TextView mProfileAboutTrainer;
    private Button mBtnBook;

    private String trainerName;
    private String aboutTrainer;
    private int price;
    private String rating;
    private String picurl;
    private String experince;
    private String tranierId;
    private String gender;
    private String tokken, userId;

    private SharedPreferences prefs;
    private ProgressBar mProfile_Progressbar;

    private List<TrainerPicPojo> trainerPicPojoList;

    private RecyclerView mProfilePicRecycle;
    private Trainer_Pic_Adapter mTrainerPic_Adapter;



    private List<CertificatePhotoPojo> certificatePhotoPojoList;
    private RecyclerView mCertiPicRecycle;
    private Certificate_Profile_Adapter mCertiPhotoAdapter;
    private ImageButton mBckBtnTrainerProfile;
    private TextView mProfileRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile);
        initView();


        mProfilePicRecycle.setHasFixedSize(true);
        mProfilePicRecycle.setLayoutManager(new CardSliderLayoutManager(Objects.requireNonNull(TrainerProfileActivity.this)));
        new CardSnapHelper().attachToRecyclerView(mProfilePicRecycle);

        mCertiPicRecycle.setHasFixedSize(true);
        mCertiPicRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        prefs = getSharedPreferences("ProfileData", MODE_PRIVATE);
        try {
            tokken = prefs.getString("tokken", "no tokkens");
            userId = prefs.getString("userId", "xx");
        } catch (Exception e) {

        }
        try {
            trainerName = getIntent().getStringExtra("trainerName");
            aboutTrainer = getIntent().getStringExtra("aboutTrainer");
            price = getIntent().getIntExtra("trainerPrice", 0);
            rating = getIntent().getStringExtra("trainerRating");
            picurl = getIntent().getStringExtra("picUrl");
            experince = getIntent().getStringExtra("expirence");
            tranierId = getIntent().getStringExtra("tranierId");
            gender = getIntent().getStringExtra("gender");
            trainerPicPojoList = getIntent().getParcelableArrayListExtra("picProfileArray");
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        mTrainerPic_Adapter = new Trainer_Pic_Adapter(trainerPicPojoList, TrainerProfileActivity.this);
        mProfilePicRecycle.setAdapter(mTrainerPic_Adapter);
        mTrainerPic_Adapter.notifyDataSetChanged();


        certificatePhotoPojoList = getIntent().getParcelableArrayListExtra("picCertiArray");
        mCertiPhotoAdapter = new Certificate_Profile_Adapter(certificatePhotoPojoList, TrainerProfileActivity.this);
        mCertiPicRecycle.setAdapter(mCertiPhotoAdapter);
        mCertiPhotoAdapter.notifyDataSetChanged();


        try {
            mProfileAboutTrainer.setText(aboutTrainer);
            mProfileName.setText(trainerName);
            mProfileRating.setText(rating);
            mExperince.setText(experince);
            mGender.setText(gender);
            mToolbarUserName.setText(trainerName);
            mBtnBook.setText("HIRE NOW " + price + " only/-");
            Picasso.get().load(picurl).into(mToolbarImageProfile);


        } catch (Exception e) {

        }


        mBckBtnTrainerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mBtnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainerProfileActivity.this, BookingDetailsActivity.class);
                intent.putExtra("trainerName", trainerName);
                intent.putExtra("aboutTrainer", aboutTrainer);
                intent.putExtra("trainerPrice", price);
                intent.putExtra("trainerRating", rating);
                intent.putExtra("picUrl", picurl);
                intent.putExtra("expirence", experince);
                intent.putExtra("tranierId", tranierId);
                startActivity(intent);

            }
        });

    }

    private void initView() {
        mProfile_Progressbar = findViewById(R.id.trainerProfile_Progressbar);
        mToolbarImageProfile = findViewById(R.id.toolbar_Image_profile);
        mToolbarUserName = findViewById(R.id.toolbar_User_Name);
        //  mProfleRating = findViewById(R.id.profle_rating);
        // mProfileImage = findViewById(R.id.profile_image);
        mProfileName = findViewById(R.id.profile_name);
        mGender = findViewById(R.id.gender);
        mExperince = findViewById(R.id.experince);
        mBtnBook = findViewById(R.id.btnBook);
        mProfileAboutTrainer = findViewById(R.id.profile_about_trainer);
        mProfilePicRecycle = findViewById(R.id.recycle_trainer_Photo);
        mCertiPicRecycle = findViewById(R.id.recycle_certi);
        mBckBtnTrainerProfile = findViewById(R.id.bck_btn_trainerProfile);
        mProfileRating = findViewById(R.id.profile_rating);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}