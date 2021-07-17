package com.skywalkers.gtrainer.ui.image;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.skywalkers.gtrainer.R;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

public class Full_Image_View_Activity extends AppCompatActivity {

    private SubsamplingScaleImageView mFullScreenImageView;
    private ProgressBar mFullimageProgressBar;
    private final Picasso picasso = Picasso.get();
    private String imageUrl;

    public static final String IMAGE_URL = "image_url";


    public static Intent createIntent(
            Context ctx, String url
    ) {
        return new Intent(ctx, Full_Image_View_Activity.class)
                .putExtra(IMAGE_URL, url);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__image__view_);
        initView();

        imageUrl = getIntent().getStringExtra(IMAGE_URL);
        if (imageUrl == null) {
            finish();
        }
        else {
            loadImageByUrl(imageUrl,new OkHttpClient());
        }
        mFullimageProgressBar.setVisibility(View.VISIBLE);
    }
    public void loadImageByUrl(final String url ,final OkHttpClient okHttpClient) {

        mFullScreenImageView.setVisibility(View.VISIBLE);
        Uri photo = Uri.parse(url);
        mFullScreenImageView.setMaxScale(10.0f);

        mFullScreenImageView.setBitmapDecoderFactory(() -> new PicassoDecoder(url, picasso));

          mFullScreenImageView.setRegionDecoderFactory(() -> new PicassoRegionDecoder(okHttpClient));

        mFullScreenImageView.setOnImageEventListener(new SubsamplingScaleImageView.OnImageEventListener() {
            @Override
            public void onReady() {

            }

            @Override
            public void onImageLoaded() {
                mFullimageProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPreviewLoadError(Exception e) {

            }

            @Override
            public void onImageLoadError(Exception e) {
                mFullimageProgressBar.setVisibility(View.GONE);
                Toast.makeText(Full_Image_View_Activity.this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTileLoadError(Exception e) {

            }

            @Override
            public void onPreviewReleased() {

            }
        });
        mFullScreenImageView.setImage(ImageSource.uri(imageUrl));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (imageUrl != null) {
            picasso.cancelTag(imageUrl);
        }
    }


    private void initView() {
        mFullScreenImageView = findViewById(R.id.full_screen_imageView);
        mFullimageProgressBar = findViewById(R.id.fullimage_progress_bar);
    }
}