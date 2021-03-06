package com.example.gtrainer.OnBoardingScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gtrainer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Screen3 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_screen3, container, false);


        FloatingActionButton mGoLogin = view.findViewById(R.id.fab_GoToLogin);
        mGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}