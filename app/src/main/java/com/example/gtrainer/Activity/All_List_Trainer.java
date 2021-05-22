package com.example.gtrainer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gtrainer.Adapter.AllTrainerList_Adpter;
import com.example.gtrainer.Adapter.Top_Trainer_Adapter;
import com.example.gtrainer.Api.ApiClientInterface;
import com.example.gtrainer.R;
import com.example.gtrainer.model.TrainerListPojo;
import com.example.gtrainer.model.User;
import com.example.gtrainer.model.UserPojo;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class All_List_Trainer extends AppCompatActivity {

    private List<User> topTrainerList = new ArrayList<>();

    private TrainerListPojo trainerListPojo;

    private Top_Trainer_Adapter top_trainer_adapter;

    DiscreteScrollView toptrainerRecycle;


    SharedPreferences prefs;
    String  tokken;

   private RecyclerView mAllTrainerRecycle;
   private  AllTrainerList_Adpter mAllTrainer_Adapter;


   private List<User> alltrainerList = new ArrayList<User>();

   private ProgressBar mAllList_progress;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__list__trainer);
        mAllList_progress = findViewById(R.id.allList_ProgressBar);
        toptrainerRecycle = findViewById(R.id.toptrainerRecycle2);
        mAllTrainerRecycle = findViewById(R.id.all_Trainer_Recycle);

        mAllList_progress.setVisibility(View.VISIBLE);

        mAllTrainerRecycle.setHasFixedSize(true);
        mAllTrainerRecycle.setLayoutManager(new LinearLayoutManager(All_List_Trainer.this, LinearLayoutManager.VERTICAL,false));

        prefs = getSharedPreferences("ProfileData", MODE_PRIVATE);
        tokken = prefs.getString("tokken","no tokkens");

        getTopTrainers();
        getallTrainers();


    }

    private void getallTrainers() {
        mAllList_progress.setVisibility(View.VISIBLE);
        Call<TrainerListPojo> call  = ApiClientInterface.getTrainerApiService().getAllTrainer(tokken , 1 ,10);
        call.enqueue(new Callback<TrainerListPojo>() {
            @Override
            public void onResponse(Call<TrainerListPojo> call, Response<TrainerListPojo> response) {
                if(response.code() == 200){
                    mAllList_progress.setVisibility(View.GONE);
                    assert response.body() != null;
                    TrainerListPojo trainerListPojo = response.body();

                    alltrainerList.addAll(trainerListPojo.getSearchResult());


                    mAllTrainer_Adapter  = new AllTrainerList_Adpter(alltrainerList , All_List_Trainer.this);
                    mAllTrainerRecycle.setAdapter(mAllTrainer_Adapter);
                    mAllTrainer_Adapter.notifyDataSetChanged();
                }
                else {
                    mAllList_progress.setVisibility(View.GONE);
                    Toast.makeText(All_List_Trainer.this, "try after sometime"+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TrainerListPojo> call, Throwable t) {
                mAllList_progress.setVisibility(View.GONE);
                Toast.makeText(All_List_Trainer.this, "try after sometime"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void getTopTrainers() {
        Call<List<User>> call  = ApiClientInterface.getTrainerApiService().getTopTrainer(tokken);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.code() == 200){
                    mAllList_progress.setVisibility(View.GONE);
                    topTrainerList = response.body();
                    assert topTrainerList != null;
                    Toast.makeText(All_List_Trainer.this, "Success", Toast.LENGTH_SHORT).show();
                    if(topTrainerList.size() == 0){

                    }

                    top_trainer_adapter  = new Top_Trainer_Adapter(topTrainerList , All_List_Trainer.this);
                    toptrainerRecycle.setAdapter(top_trainer_adapter);
                    top_trainer_adapter.notifyDataSetChanged();


                    toptrainerRecycle.setItemTransformer(new ScaleTransformer.Builder()
                            .setMinScale(0.7f)
                            .build());
                }
                else {
                    mAllList_progress.setVisibility(View.GONE);
                    Toast.makeText(All_List_Trainer.this, "try afetr sometimes"+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                mAllList_progress.setVisibility(View.GONE);
                Toast.makeText(All_List_Trainer.this, "try afetr sometimes"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}