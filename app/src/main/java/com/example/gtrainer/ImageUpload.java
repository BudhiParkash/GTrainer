package com.example.gtrainer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.gtrainer.Api.ApiClientInterface;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.ybq.android.spinkit.SpinKitView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ImageUpload extends AppCompatActivity {

    private static final String CAMERA_IMAGE_PATH = "camera_image_path";
    Integer REQUEST_CAMERA = 0, SELECT_FILE = 64;


    ImageView imageView;

    boolean cameraPic = false;
    Toolbar toolbar;
    String tokken;

    Uri imageUri;



   // ArrayList<String> files = new ArrayList<>();
    //  private ProgressDialog pDialog;
    String onclick;
    private ImageButton mBckBtnCertificate;
    private ImageView mCertificatePic;
    private Button mBpicselector;
    private Button mBupload;
    private SpinKitView mSpinKit222;

    final  int PICK_IMAGE = 1;

    Boolean uploading = false;
    InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        onclick = getIntent().getStringExtra("OnClick");
        initView();


        mBpicselector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chossingImage();
            }
        });


        mBupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    mBpicselector.setClickable(false);
                    mBpicselector.setEnabled(false);
                    mBupload.setClickable(false);
                    mBupload.setEnabled(false);
                    mSpinKit222.setVisibility(View.VISIBLE);
                    uploading = true;
                    uploadImage(getBytes(is));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Get Tokken
        SharedPreferences prefs = Objects.requireNonNull(this).getSharedPreferences("ProfileData", MODE_PRIVATE);
        tokken = prefs.getString("tokken", "no tokkens");
        //  pDialog = new ProgressDialog(ImageUpload.this);
        // showProgress("Uploading image");
//        assert onclick != null;
//        if (savedInstanceState != null) {
//            currentCameraPhotoPath = savedInstanceState.getString(CAMERA_IMAGE_PATH);
//            if (currentCameraPhotoPath != null) {
//                files.clear();
//                files.add(currentCameraPhotoPath);
//                uploadFiles();
//            }
//        } else {
//            if (onclick.equals("camera")) {
//                GoOnCamera();
//            } else {
//                GoOnGallery();
//            }
//        }
    }

    private void chossingImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,PICK_IMAGE);
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == -1 && data!= null && data.getData()!= null){
            imageUri = data.getData();



            try {
                is = getContentResolver().openInputStream(data.getData());

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Not getting directory", Toast.LENGTH_SHORT).show();
            }


            Picasso.get().load(imageUri).into(mCertificatePic);
            mCertificatePic.setImageURI(imageUri);
        }
    }

    private void uploadImage(byte[] imageBytes){

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        MultipartBody.Part slipImage = MultipartBody.Part.createFormData("DocImage", "img.jpg",requestFile);


        Call call =  ApiClientInterface.getTrainerApiService().uploadCertificate(tokken,slipImage);

        call.enqueue(new retrofit2.Callback() {
            @Override
            public void onResponse(retrofit2.Call call, retrofit2.Response response) {
                if(response.code() == 201) {
                    Toast.makeText(ImageUpload.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                    finish();

                }
                else{
                    Toast.makeText(ImageUpload.this, "Not Upload", Toast.LENGTH_SHORT).show();
                    mBpicselector.setClickable(true);
                    mBupload.setClickable(true);
                    mBupload.setEnabled(true);
                    mSpinKit222.setVisibility(View.INVISIBLE);
                    uploading = false;
                    mBpicselector.setEnabled(true);

                }



            }

            @Override
            public void onFailure(retrofit2.Call call, Throwable t) {
                Toast.makeText(ImageUpload.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                mBpicselector.setClickable(true);
                mBupload.setClickable(true);
                mBupload.setEnabled(true);
                mSpinKit222.setVisibility(View.INVISIBLE);
                uploading = false;


            }
        });






    }


//    public void uploadFiles() {
//        File[] filesToUpload = new File[files.size()];
//        for (int i = 0; i < files.size(); i++) {
//            filesToUpload[i] = new File(files.get(i));
//        }
//
//        FileUpload fileUploader = new FileUpload();
//        fileUploader.uploadFiles(this, tokken, filesToUpload, new FileUpload.FileUploaderCallback() {
//            @Override
//            public void onError() {
//                //   hideProgress();
//                files.clear();
//                Toast.makeText(ImageUpload.this, "Some error occurred. Please retry", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//
//            @Override
//            public void onFinish(String[] responses) {
//                // hideProgress();
//                /*for (String respons : responses) {
//                    Log.e("RESPONSE - " + respons, respons);
//                }*/
//                files.clear();
//                finish();
//            }
//
//            @Override
//            public void onProgressUpdate(int currentpercent, int totalpercent, int filenumber) {
//                //   updateProgress(totalpercent, "Uploading image", "");
//            }
//        });
//
//
//    }

//    public void updateProgress(int val, String title, String msg) {
//        pDialog.setTitle(title);
//        pDialog.setMessage(msg);
//        pDialog.setProgress(val);
//    }

//    public void showProgress(String str) {
//        try {
//            pDialog.setCancelable(false);
//            pDialog.setTitle("Please wait");
//            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            pDialog.setMax(100); // Progress Dialog Max Value
//            pDialog.setMessage(str);
//            if (pDialog.isShowing())
//                pDialog.dismiss();
//            pDialog.show();
//        } catch (Exception e) { Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show(); }
//    }

//    public void hideProgress() {
//        try {
//            if (pDialog.isShowing())
//                pDialog.dismiss();
//        } catch (Exception e) { Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show(); }
//
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void GoOnGallery() {
        String[] mimeTypes = new String[]{"image/png",
                "image/jpg",
                "image/jpeg"};
        ImagePicker.Companion.with(this)
                .galleryOnly()
                .maxResultSize(2440, 2440)
                .compress(2200)
                .galleryMimeTypes(mimeTypes)
                .start();
    }

    private void GoOnCamera() {
        ImagePicker.Companion.with(this)
                .maxResultSize(2440, 2440)
                .compress(2200)
                .cameraOnly()
                .start();
    }

    String currentCameraPhotoPath;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(CAMERA_IMAGE_PATH, currentCameraPhotoPath);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    private void initView() {
        mBckBtnCertificate = findViewById(R.id.bck_btn_certificate);
        mCertificatePic = findViewById(R.id.certificate_Pic);
        mBpicselector = findViewById(R.id.bpicselector);
        mBupload = findViewById(R.id.bupload);
        mSpinKit222 = findViewById(R.id.spin_kit222);
    }
}