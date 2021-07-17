package com.skywalkers.gtrainer.ui.OnBoardingScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.skywalkers.gtrainer.Adapter.OnBoardingScreenAdapter;
import com.skywalkers.gtrainer.MainActivity;
import com.skywalkers.gtrainer.R;
import com.github.ybq.android.spinkit.SpinKitView;

public class SplashScreen extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    ViewPager viewPager;
    OnBoardingScreenAdapter slidePageAdapter;
    SpinKitView spinKitView;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        viewPager = findViewById(R.id.pager);
        spinKitView = findViewById(R.id.spin_kit_splash);

        getWindow().setStatusBarColor(getColor(R.color.black));

        FullScreencall();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences prefs = getSharedPreferences("ProfileData", MODE_PRIVATE);
                    String  tokken = prefs.getString("tokken","no tokkens");

                    if(tokken.equals("no tokkens")){
                        slidePageAdapter = new OnBoardingScreenAdapter(getSupportFragmentManager() , 0);
                        spinKitView.setVisibility(View.GONE);

                        viewPager.setAdapter(slidePageAdapter);
                        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                if(position == 0){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        getWindow().setStatusBarColor(getColor(R.color.colorScreen1));
                                    }
                                }
                                else if(position == 1){
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        getWindow().setStatusBarColor(getColor(R.color.colorScreen2));
                                    }
                                }
                                else {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        getWindow().setStatusBarColor(getColor(R.color.colorScreen3));
                                    }

                                }
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                    }

                    else {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }



                }catch (Exception  e){
                    Toast.makeText(SplashScreen.this, "Splash Screen Error", Toast.LENGTH_SHORT).show();
                } }
        };
        handler.postDelayed(runnable, 2500);
    }

    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    }
