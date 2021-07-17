package com.skywalkers.gtrainer.ui.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skywalkers.gtrainer.Adapter.AllTrainerList_Adpter;
import com.skywalkers.gtrainer.Adapter.Top_Trainer_Adapter;
import com.skywalkers.gtrainer.Api.ApiClientInterface;
import com.skywalkers.gtrainer.R;
import com.skywalkers.gtrainer.model.TrainerListPojo;
import com.skywalkers.gtrainer.model.User;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class All_List_Trainer extends AppCompatActivity {

    private List<User> topTrainerList = new ArrayList<>();
    private Top_Trainer_Adapter top_trainer_adapter;
    DiscreteScrollView toptrainerRecycle;


    SharedPreferences prefs;
    String tokken;

    private RecyclerView mAllTrainerRecycle;
    private AllTrainerList_Adpter mAllTrainer_Adapter;
    private List<User> alltrainerList = new ArrayList<User>();

    private ProgressBar mAllList_progress;

    Boolean isScrolling = false;
    int currentItem, totalItem, scrollOutItem;
    LinearLayoutManager manager;
    int PageNo = 0;
    boolean noScroll = false;
    private ImageButton mBckBtnAlltrainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__list__trainer);
        initView();


        manager = new LinearLayoutManager(this);

        mAllList_progress.setVisibility(View.VISIBLE);

        mAllTrainerRecycle.setHasFixedSize(true);
        mAllTrainerRecycle.setLayoutManager(manager);


        prefs = getSharedPreferences("ProfileData", MODE_PRIVATE);
        tokken = prefs.getString("tokken", "no tokkens");

        getallTrainers();
        getTopTrainers();

        mBckBtnAlltrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAllTrainerRecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = manager.getChildCount();
                totalItem = manager.getItemCount();
                scrollOutItem = manager.findFirstCompletelyVisibleItemPosition();

                if (isScrolling && (currentItem + scrollOutItem == totalItem)) {
                    isScrolling = false;
                    getallTrainers();
                }
            }
        });



    }

    private void getallTrainers() {

        PageNo = PageNo + 1;
        int limit = 10;
        int Skip = (PageNo - 1) * limit;

        if (noScroll) {
            return;
        }
        mAllList_progress.setVisibility(View.VISIBLE);
        Call<TrainerListPojo> call = ApiClientInterface.getTrainerApiService().getAllTrainer(tokken, limit, Skip);
        call.enqueue(new Callback<TrainerListPojo>() {
            @Override
            public void onResponse(Call<TrainerListPojo> call, Response<TrainerListPojo> response) {
                if (response.code() == 200) {
                    mAllList_progress.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body() == null) {
                        noScroll = true;
                        return;
                    }
                    TrainerListPojo trainerListPojo = response.body();
                    alltrainerList.addAll(trainerListPojo.getSearchResult());

                    Toast.makeText(All_List_Trainer.this, "Success", Toast.LENGTH_SHORT).show();
                    mAllTrainer_Adapter = new AllTrainerList_Adpter(alltrainerList, All_List_Trainer.this);
                    mAllTrainerRecycle.setAdapter(mAllTrainer_Adapter);
                    mAllTrainer_Adapter.notifyDataSetChanged();
                } else {
                    mAllList_progress.setVisibility(View.GONE);
                    Toast.makeText(All_List_Trainer.this, "try after sometime" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TrainerListPojo> call, Throwable t) {
                mAllList_progress.setVisibility(View.GONE);
                Toast.makeText(All_List_Trainer.this, "try after sometime" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void getTopTrainers() {
        Call<List<User>> call = ApiClientInterface.getTrainerApiService().getTopTrainer(tokken);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.code() == 200) {
                    mAllList_progress.setVisibility(View.GONE);
                    topTrainerList = response.body();
                    assert topTrainerList != null;
                    Toast.makeText(All_List_Trainer.this, "Success", Toast.LENGTH_SHORT).show();
                    if (topTrainerList.size() == 0) {

                    }

                    top_trainer_adapter = new Top_Trainer_Adapter(topTrainerList, All_List_Trainer.this);
                    toptrainerRecycle.setAdapter(top_trainer_adapter);
                    top_trainer_adapter.notifyDataSetChanged();


                    toptrainerRecycle.setItemTransformer(new ScaleTransformer.Builder()
                            .setMinScale(0.7f)
                            .build());
                } else {
                    mAllList_progress.setVisibility(View.GONE);
                    Toast.makeText(All_List_Trainer.this, "try afetr sometimes" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                mAllList_progress.setVisibility(View.GONE);
                Toast.makeText(All_List_Trainer.this, "try afetr sometimes" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initView() {
        mBckBtnAlltrainer = findViewById(R.id.bck_btn_alltrainer);
        mAllList_progress = findViewById(R.id.allList_ProgressBar);
        toptrainerRecycle = findViewById(R.id.toptrainerRecycle2);
        mAllTrainerRecycle = findViewById(R.id.all_Trainer_Recycle);
    }
}