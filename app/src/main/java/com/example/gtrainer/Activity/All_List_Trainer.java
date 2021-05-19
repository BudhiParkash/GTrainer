package com.example.gtrainer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gtrainer.Adapter.PopularAdapter;
import com.example.gtrainer.R;
import com.example.gtrainer.model.PopularPojo;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

public class All_List_Trainer extends AppCompatActivity {

    private List<PopularPojo> popularPojoList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__list__trainer);



        for (int i = 0; i < 10; i++) {
            PopularPojo list = new PopularPojo();

            popularPojoList.add(list);
        }



        DiscreteScrollView scrollView = findViewById(R.id.picker2);
        scrollView.setAdapter(new PopularAdapter(popularPojoList , All_List_Trainer.this));

        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.7f)
                .build());
    }
}