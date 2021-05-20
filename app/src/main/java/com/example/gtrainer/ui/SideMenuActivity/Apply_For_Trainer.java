package com.example.gtrainer.ui.SideMenuActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gtrainer.Activity.Apply_For_Trainer_2;
import com.example.gtrainer.Adapter.Certificate_Pic_Adapter;
import com.example.gtrainer.Adapter.Trainer_Pic_Adapter;
import com.example.gtrainer.Api.ApiClientInterface;
import com.example.gtrainer.ImageUpload;
import com.example.gtrainer.R;
import com.example.gtrainer.model.CertificatePhotoPojo;
import com.example.gtrainer.model.TrainerPicPojo;
import com.example.gtrainer.model.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.ImagePickerActivity;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Apply_For_Trainer extends AppCompatActivity {

    Button mApply;

    SharedPreferences preferences;
    private TextView mAttachedCertificae;
    private TextView mAttachedTrainerPic;

    private static final int REQUEST_CAMERA = 1;

    RecyclerView mCerti_Recycle;
    private List<CertificatePhotoPojo> certi_picList;
    Certificate_Pic_Adapter mCertiPic_Adpter;


    RecyclerView mTrainer_Pic_Recycle;
    private  List<TrainerPicPojo> trainerPicPojoList;
    Trainer_Pic_Adapter mTrainerPic_Adapter;

    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply__for__trainer);
        initView();

        certi_picList = new ArrayList<>();
        mCerti_Recycle.setHasFixedSize(true);
        mCerti_Recycle.setLayoutManager(new LinearLayoutManager(Apply_For_Trainer.this, LinearLayoutManager.HORIZONTAL,false));

        certi_picList = new ArrayList<>();
        mTrainer_Pic_Recycle.setHasFixedSize(true);
        mTrainer_Pic_Recycle.setLayoutManager(new LinearLayoutManager(Apply_For_Trainer.this, LinearLayoutManager.HORIZONTAL,false));


        mApply = findViewById(R.id.applybtn);

        preferences = getSharedPreferences("ProfileData", MODE_PRIVATE);

        token = preferences.getString("tokken", "");

       // getSlipPic();

      //  getTrainerPic();



        mAttachedCertificae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Apply_For_Trainer.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Apply_For_Trainer.this,
                            new String[]{Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}
                            , REQUEST_CAMERA);
                }
                else {
                    Intent intent = new Intent(Apply_For_Trainer.this , ImageUpload.class);
                    startActivity(intent);
                }
            }
        });


        mApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Apply_For_Trainer.this, Apply_For_Trainer_2.class);
                startActivity(intent);

                User trainerData = new User(true, "shivam", "s@gmail.com",
                        "hisar", "akfkljf", "s@gmail,com", "s@gmail,com",
                        "s@gmail,com", "s@gmail,com");

                Call<User> call = ApiClientInterface.getTrainerApiService().applyfortrainer(token, trainerData);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == 200) {
                            Toast.makeText(Apply_For_Trainer.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Apply_For_Trainer.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void getTrainerPic() {
        Call<List<TrainerPicPojo>> call = ApiClientInterface.getTrainerApiService().getTrainer_Pic(token);

        call.enqueue(new Callback<List<TrainerPicPojo>>() {
            @Override
            public void onResponse(Call<List<TrainerPicPojo>> call, Response<List<TrainerPicPojo>> response) {

                if(response.code() == 200 ){
                    trainerPicPojoList = response.body();
                    assert trainerPicPojoList != null;
                    if(trainerPicPojoList.size() == 0){

                    }
                    mTrainerPic_Adapter = new Trainer_Pic_Adapter(trainerPicPojoList,Apply_For_Trainer.this);
                    mTrainer_Pic_Recycle.setAdapter(mTrainerPic_Adapter);
                    mTrainer_Pic_Recycle.setVisibility(View.VISIBLE);
                    mTrainerPic_Adapter.notifyDataSetChanged();
                    Toast.makeText(Apply_For_Trainer.this, "Success Trainer Pic", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 404){

                    mTrainer_Pic_Recycle.setVisibility(View.GONE);
                    Toast.makeText(Apply_For_Trainer.this, ""+response.code(), Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(Apply_For_Trainer.this, "try after", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<List<TrainerPicPojo>> call, Throwable t) {
                try {
                    Toast.makeText(Apply_For_Trainer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){}
            }
        });
    }

    private void getSlipPic() {
        Call<List<CertificatePhotoPojo>> call = ApiClientInterface.getTrainerApiService().getCerti_Pic(token);

        call.enqueue(new Callback<List<CertificatePhotoPojo>>() {
            @Override
            public void onResponse(Call<List<CertificatePhotoPojo>> call, Response<List<CertificatePhotoPojo>> response) {

                if(response.code() == 200 ){
                    certi_picList = response.body();
                    assert certi_picList != null;
                    if(certi_picList.size() == 0){

                    }
                    mCertiPic_Adpter = new Certificate_Pic_Adapter(certi_picList,Apply_For_Trainer.this);
                    mCerti_Recycle.setAdapter(mCertiPic_Adpter);
                    mCerti_Recycle.setVisibility(View.VISIBLE);
                    mCertiPic_Adpter.notifyDataSetChanged();
                    Toast.makeText(Apply_For_Trainer.this, "Success", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 404){

                    mCerti_Recycle.setVisibility(View.GONE);
                    Toast.makeText(Apply_For_Trainer.this, ""+response.code(), Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(Apply_For_Trainer.this, "try after", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<List<CertificatePhotoPojo>> call, Throwable t) {
                try {
                    Toast.makeText(Apply_For_Trainer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){}
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initView() {
        mCerti_Recycle = findViewById(R.id.certificate_Reycle);
        mAttachedCertificae = findViewById(R.id.attached_certificae);
        mAttachedTrainerPic = findViewById(R.id.attached_trainerPic);
        mTrainer_Pic_Recycle = findViewById(R.id.trainerPic_Reycle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSlipPic();
        getTrainerPic();
    }
}