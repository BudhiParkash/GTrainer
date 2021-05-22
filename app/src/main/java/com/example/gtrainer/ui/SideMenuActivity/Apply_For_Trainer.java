package com.example.gtrainer.ui.SideMenuActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Apply_For_Trainer extends AppCompatActivity {

    Button mbtnNext;

    SharedPreferences preferences;
    private TextView mAttachedCertificae;
    private TextView mAttachedTrainerPic;

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_Gallery = 2;

    RecyclerView mCerti_Recycle;
    private List<CertificatePhotoPojo> certi_picList;
    Certificate_Pic_Adapter mCertiPic_Adpter;


    RecyclerView mTrainer_Pic_Recycle;
    private List<TrainerPicPojo> trainerPicPojoList;
    Trainer_Pic_Adapter mTrainerPic_Adapter;

    String token;
    private ImageButton mBckBtnApply;
    private TextInputLayout mEtxtrainerName;
    private TextInputLayout mEtxtrainerEmail;
    private TextInputLayout mEtxtrainerCity;
    private RadioGroup mRadioGrp;
    private RadioButton mRadioM;
    private RadioButton mRadioF;


    private String trainerName;
    private String trainerEmail;
    private String trainerCity;

    String gender = "Male";
    private ProgressBar mDetails_1_Progressbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply__for__trainer);
        initView();
        mDetails_1_Progressbar.setVisibility(View.VISIBLE);

        certi_picList = new ArrayList<>();
        mCerti_Recycle.setHasFixedSize(true);
        mCerti_Recycle.setLayoutManager(new LinearLayoutManager(Apply_For_Trainer.this, LinearLayoutManager.HORIZONTAL, false));

        certi_picList = new ArrayList<>();
        mTrainer_Pic_Recycle.setHasFixedSize(true);
        mTrainer_Pic_Recycle.setLayoutManager(new LinearLayoutManager(Apply_For_Trainer.this, LinearLayoutManager.HORIZONTAL, false));




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
                            , REQUEST_Gallery);
                } else {
                    Intent intent = new Intent(Apply_For_Trainer.this, ImageUpload.class);
                    intent.putExtra("certiPic" , true);
                    startActivity(intent);
                }
            }
        });

        mAttachedTrainerPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Apply_For_Trainer.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                        ActivityCompat.checkSelfPermission(Apply_For_Trainer.this ,Manifest.permission.READ_EXTERNAL_STORAGE) +
                        ActivityCompat.checkSelfPermission(Apply_For_Trainer.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Apply_For_Trainer.this,
                            new String[]{Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}
                            , REQUEST_CAMERA);
                } else {
                    Intent intent = new Intent(Apply_For_Trainer.this, ImageUpload.class);
                    intent.putExtra("trainerPic" , true);
                    startActivity(intent);
                }
            }
        });

        mRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                  RadioButton rd = (RadioButton) findViewById(checkedId);
                  gender = rd.getText().toString();
                Toast.makeText(Apply_For_Trainer.this, ""+gender, Toast.LENGTH_SHORT).show();
            }
        });

        mbtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trainerName = mEtxtrainerName.getEditText().getText().toString();
                trainerEmail  = mEtxtrainerEmail.getEditText().getText().toString();
                trainerCity = mEtxtrainerCity.getEditText().getText().toString();
                if(TextUtils.isEmpty(trainerName)){
                    mEtxtrainerName.setError("Please enter name");
                    mEtxtrainerName.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(trainerEmail)){
                    mEtxtrainerEmail.setError("Please enter email");
                    mEtxtrainerEmail.requestFocus();

                    return;
                }
                else if(!trainerEmail.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$")){
                    mEtxtrainerEmail.setError("Please enter valid email");
                    mEtxtrainerEmail.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(trainerCity)){
                    mEtxtrainerCity.setError("Please Enter City");
                    mEtxtrainerCity.requestFocus();
                    return;
                }
                else if(certi_picList.size() ==0){
                    Toast.makeText(Apply_For_Trainer.this, "Please Attached Certificate", Toast.LENGTH_SHORT).show();
                }
                else if(trainerPicPojoList.size() == 0){
                    Toast.makeText(Apply_For_Trainer.this, "Please Attached Pic", Toast.LENGTH_SHORT).show();
                }

                else {
                    Intent intent = new Intent(Apply_For_Trainer.this, Apply_For_Trainer_2.class);
                    intent.putExtra("name" , trainerName);
                    intent.putExtra("email" , trainerEmail);
                    intent.putExtra("city" , trainerCity);
                    intent.putExtra("gender" , gender);
                    startActivity(intent);
                }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        Intent intent = new Intent(Apply_For_Trainer.this, ImageUpload.class);
                        intent.putExtra("trainerPic" , true);
                        startActivity(intent);

                    }
                    catch (Exception e){}
                } else {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent,1231);
                    Toast.makeText(this, "Please allow permission from setting", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case REQUEST_Gallery:{

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        Intent intent = new Intent(Apply_For_Trainer.this, ImageUpload.class);
                        intent.putExtra("certiPic" , true);
                        startActivity(intent);

                    }
                    catch (Exception e){}
                } else {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent,1231);
                    Toast.makeText(this, "Please allow permission from setting", Toast.LENGTH_SHORT).show();
                }
                return;

            }
        }
    }

    private void getTrainerPic() {
        mDetails_1_Progressbar.setVisibility(View.VISIBLE);
        Call<List<TrainerPicPojo>> call = ApiClientInterface.getTrainerApiService().getTrainer_Pic(token);

        call.enqueue(new Callback<List<TrainerPicPojo>>() {
            @Override
            public void onResponse(Call<List<TrainerPicPojo>> call, Response<List<TrainerPicPojo>> response) {

                if (response.code() == 200) {
                    mDetails_1_Progressbar.setVisibility(View.GONE);
                    trainerPicPojoList = response.body();
                    assert trainerPicPojoList != null;
                    if (trainerPicPojoList.size() == 0) {

                    }
                    mTrainerPic_Adapter = new Trainer_Pic_Adapter(trainerPicPojoList, Apply_For_Trainer.this);
                    mTrainer_Pic_Recycle.setAdapter(mTrainerPic_Adapter);
                    mTrainer_Pic_Recycle.setVisibility(View.VISIBLE);
                    mTrainerPic_Adapter.notifyDataSetChanged();

                } else if (response.code() == 404) {
                    mDetails_1_Progressbar.setVisibility(View.GONE);
                    mTrainer_Pic_Recycle.setVisibility(View.GONE);
                    Toast.makeText(Apply_For_Trainer.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    mDetails_1_Progressbar.setVisibility(View.GONE);
                    Toast.makeText(Apply_For_Trainer.this, "try after", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<TrainerPicPojo>> call, Throwable t) {
                try {
                    mDetails_1_Progressbar.setVisibility(View.GONE);
                    Toast.makeText(Apply_For_Trainer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            }
        });
    }

    private void getCertiPic() {
        mDetails_1_Progressbar.setVisibility(View.VISIBLE);
        Call<List<CertificatePhotoPojo>> call = ApiClientInterface.getTrainerApiService().getCerti_Pic(token);

        call.enqueue(new Callback<List<CertificatePhotoPojo>>() {
            @Override
            public void onResponse(Call<List<CertificatePhotoPojo>> call, Response<List<CertificatePhotoPojo>> response) {

                if (response.code() == 200) {
                    mDetails_1_Progressbar.setVisibility(View.GONE);
                    certi_picList = response.body();
                    assert certi_picList != null;
                    if (certi_picList.size() == 0) {

                    }
                    mCertiPic_Adpter = new Certificate_Pic_Adapter(certi_picList, Apply_For_Trainer.this);
                    mCerti_Recycle.setAdapter(mCertiPic_Adpter);
                    mCerti_Recycle.setVisibility(View.VISIBLE);
                    mCertiPic_Adpter.notifyDataSetChanged();

                } else if (response.code() == 404) {
                    mDetails_1_Progressbar.setVisibility(View.GONE);
                    mCerti_Recycle.setVisibility(View.GONE);
                    Toast.makeText(Apply_For_Trainer.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    mDetails_1_Progressbar.setVisibility(View.GONE);
                    Toast.makeText(Apply_For_Trainer.this, "try after", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<CertificatePhotoPojo>> call, Throwable t) {
                try {
                    mDetails_1_Progressbar.setVisibility(View.GONE);
                    Toast.makeText(Apply_For_Trainer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initView() {
        mDetails_1_Progressbar = findViewById(R.id.detils1Progressbar);
        mCerti_Recycle = findViewById(R.id.certificate_Reycle);
        mAttachedCertificae = findViewById(R.id.attached_certificae);
        mAttachedTrainerPic = findViewById(R.id.attached_trainerPic);
        mTrainer_Pic_Recycle = findViewById(R.id.trainerPic_Reycle);
        mBckBtnApply = findViewById(R.id.bck_btn_apply);
        mEtxtrainerName = findViewById(R.id.etxtrainerName);
        mEtxtrainerEmail = findViewById(R.id.etxtrainerEmail);
        mEtxtrainerCity = findViewById(R.id.etxtrainerCity);
        mRadioGrp = findViewById(R.id.radioGrp);
        mRadioM = findViewById(R.id.radioM);
        mRadioF = findViewById(R.id.radioF);
        mbtnNext = findViewById(R.id.applybtn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCertiPic();
        getTrainerPic();
    }
}