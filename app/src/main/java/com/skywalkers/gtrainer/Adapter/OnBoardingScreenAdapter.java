package com.skywalkers.gtrainer.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.skywalkers.gtrainer.ui.OnBoardingScreen.Screen1;
import com.skywalkers.gtrainer.ui.OnBoardingScreen.Screen2;
import com.skywalkers.gtrainer.ui.OnBoardingScreen.Screen3;

public class OnBoardingScreenAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 3;
    public OnBoardingScreenAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                Screen1 tab1 = new Screen1();
                return  tab1;
            case 1 :
                Screen2 tab2= new Screen2();
                return  tab2;
            case 2 :
                Screen3 tab3 = new Screen3();
                return  tab3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}

