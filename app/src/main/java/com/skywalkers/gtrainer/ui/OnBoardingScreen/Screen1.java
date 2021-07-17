package com.skywalkers.gtrainer.ui.OnBoardingScreen;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skywalkers.gtrainer.R;


public class Screen1 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_screen1, container, false);
       // final FloatingActionButton flotNextBtn = view.findViewById(R.id.fab_GoToNext);

       /* if (Build.VERSION.SDK_INT < 26) {
            flotNextBtn.setVisibility(View.VISIBLE);
        } else {
            flotNextBtn.setVisibility(View.GONE);
        }


        flotNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.firstFragmnet, new Screen2());
                fr.commit();
                flotNextBtn.setVisibility(View.GONE);
            }
        });
*/
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.colorScreen1));
        }
    }
    }
