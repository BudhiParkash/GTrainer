package com.skywalkers.gtrainer.ui.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.skywalkers.gtrainer.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrainerDetailActivity extends AppCompatActivity {

    private LinearLayout mTransformationLL;
    private CircleImageView mTrainerProfile;
    private TextView mTrainersName;
    private TextView mTrainerAge;
    private TextView mTrainerExperince;
    private TextView mNumOfClient;
    private TextView mRatingOfTrainer;
    private TextView mTrainerAboutUs;
    private ImageView mTrainerCerti1;
    private ImageView mTrainerCerti2;
    private ImageView mTrainerCerti3;
    private ImageView mTrasnformationPic1;
    private ImageView mTrasnformationPic2;
    private ImageView mTrasnformationPic3;
    private TextView mBackTxtTrainerProfile;
    private Button mBtnBooking;
    private ImageView mTrasnformationPic4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_detail);
        initView();

        int position = getIntent().getIntExtra("id", 6);

        if (position == 0) {
            nikhil();
        } else if (position == 1) {
            nayan();
        } else if (position == 2) {
            vakeel();
        } else if (position == 3) {
            rizwan();
        } else if (position == 4) {
            shivamsign();
        } else {
            nikhil();
        }


        mBackTxtTrainerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void shivamsign() {
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti1);
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti2);
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti3);
        mTrainersName.setText("Shivam Pal Singh");
        mTrainerAge.setText("24");
        mTrainerExperince.setText("6Y");
        mNumOfClient.setText("100");
        mRatingOfTrainer.setText("4.5");
        mTrainerAboutUs.setText("Building a website is, in many ways, an exercise of willpower. It’s tempting to get distracted by the bells and whistles of the design process, and forget all about creating compelling content. Building a website is, in many ways, an exercise of willpower. It’s tempting to get distracted by the bells and whistles of the design process");
        Picasso.get().load(R.drawable.change1).into(mTrasnformationPic1);
        Picasso.get().load(R.drawable.change2).into(mTrasnformationPic2);
        Picasso.get().load(R.drawable.change3).into(mTrasnformationPic3);
        Picasso.get().load(R.drawable.change).into(mTrasnformationPic4);
        Picasso.get().load(R.drawable.shivam).into(mTrainerProfile);
    }

    private void rizwan() {
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti1);
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti2);
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti3);
        mTrainersName.setText("Rizwan Ahmad");
        mTrainerAge.setText("31");
        mTrainerExperince.setText("10Y");
        mNumOfClient.setText("300");
        mRatingOfTrainer.setText("4.8");
        mTrainerAboutUs.setText("Building a website is, in many ways, an exercise of willpower. It’s tempting to get distracted by the bells and whistles of the design process, and forget all about creating compelling content. Building a website is, in many ways, an exercise of willpower. It’s tempting to get distracted by the bells and whistles of the design process");
        Picasso.get().load(R.drawable.change1).into(mTrasnformationPic1);
        Picasso.get().load(R.drawable.change2).into(mTrasnformationPic2);
        Picasso.get().load(R.drawable.change3).into(mTrasnformationPic3);
        Picasso.get().load(R.drawable.change).into(mTrasnformationPic4);
        Picasso.get().load(R.drawable.rizwan).into(mTrainerProfile);
    }

    private void vakeel() {
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti1);
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti2);
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti3);
        mTrainersName.setText("Vakeel Chandila");
        mTrainerAge.setText("23");
        mTrainerExperince.setText("5Y");
        mNumOfClient.setText("65");
        mRatingOfTrainer.setText("4.6");
        mTrainerAboutUs.setText("Building a website is, in many ways, an exercise of willpower. It’s tempting to get distracted by the bells and whistles of the design process, and forget all about creating compelling content. Building a website is, in many ways, an exercise of willpower. It’s tempting to get distracted by the bells and whistles of the design process");
        Picasso.get().load(R.drawable.change1).into(mTrasnformationPic1);
        Picasso.get().load(R.drawable.change2).into(mTrasnformationPic2);
        Picasso.get().load(R.drawable.change3).into(mTrasnformationPic3);
        Picasso.get().load(R.drawable.change).into(mTrasnformationPic4);
        Picasso.get().load(R.drawable.vakeelss).into(mTrainerProfile);
    }

    private void nayan() {
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti1);
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti2);
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti3);
        mTrainersName.setText("Nayan Narvekar");
        mTrainerAge.setText("24");
        mTrainerExperince.setText("3Y");
        mNumOfClient.setText("54");
        mRatingOfTrainer.setText("4.6");
        mTrainerAboutUs.setText("Building a website is, in many ways, an exercise of willpower. It’s tempting to get distracted by the bells and whistles of the design process, and forget all about creating compelling content. Building a website is, in many ways, an exercise of willpower. It’s tempting to get distracted by the bells and whistles of the design process");
        Picasso.get().load(R.drawable.change1).into(mTrasnformationPic1);
        Picasso.get().load(R.drawable.change2).into(mTrasnformationPic2);
        Picasso.get().load(R.drawable.change3).into(mTrasnformationPic3);
        Picasso.get().load(R.drawable.change).into(mTrasnformationPic4);
        Picasso.get().load(R.drawable.nayan).into(mTrainerProfile);

    }

    private void nikhil() {
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti1);
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti2);
        Picasso.get().load(R.drawable.certifcate).into(mTrainerCerti3);
        mTrainersName.setText("Nikhil");
        mTrainerAge.setText("21");
        mTrainerExperince.setText("3Y");
        mNumOfClient.setText("55");
        mRatingOfTrainer.setText("4.7");
        mTrainerAboutUs.setText("Building a website is, in many ways, an exercise of willpower. It’s tempting to get distracted by the bells and whistles of the design process, and forget all about creating compelling content. Building a website is, in many ways, an exercise of willpower. It’s tempting to get distracted by the bells and whistles of the design process");
        Picasso.get().load(R.drawable.change1).into(mTrasnformationPic1);
        Picasso.get().load(R.drawable.change2).into(mTrasnformationPic2);
        Picasso.get().load(R.drawable.change3).into(mTrasnformationPic3);
        Picasso.get().load(R.drawable.change).into(mTrasnformationPic4);
        Picasso.get().load(R.drawable.nikhil).into(mTrainerProfile);

    }

    private void initView() {
        mTransformationLL = (LinearLayout) findViewById(R.id.transformationLL);
        mTrainerProfile = (CircleImageView) findViewById(R.id.trainerProfile);
        mTrainersName = (TextView) findViewById(R.id.trainersName);
        mTrainerAge = (TextView) findViewById(R.id.trainerAge);
        mTrainerExperince = (TextView) findViewById(R.id.trainerExperince);
        mNumOfClient = (TextView) findViewById(R.id.numOfClient);
        mRatingOfTrainer = (TextView) findViewById(R.id.ratingOfTrainer);
        mTrainerAboutUs = (TextView) findViewById(R.id.trainerAboutUs);
        mTrainerCerti1 = (ImageView) findViewById(R.id.trainerCerti1);
        mTrainerCerti2 = (ImageView) findViewById(R.id.trainerCerti2);
        mTrainerCerti3 = (ImageView) findViewById(R.id.trainerCerti3);
        mTrasnformationPic1 = (ImageView) findViewById(R.id.trasnformationPic1);
        mTrasnformationPic2 = (ImageView) findViewById(R.id.trasnformationPic2);
        mTrasnformationPic3 = (ImageView) findViewById(R.id.trasnformationPic3);
        mBackTxtTrainerProfile = (TextView) findViewById(R.id.backTxtTrainerProfile);
        mBtnBooking = (Button) findViewById(R.id.btnBooking);
        mTrasnformationPic4 = (ImageView) findViewById(R.id.trasnformationPic4);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}