package com.example.gtrainer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.gtrainer.Activity.All_List_Trainer;
import com.example.gtrainer.Adapter.AddvertismentAdapter;
import com.example.gtrainer.Adapter.Top_Trainer_Adapter;
import com.example.gtrainer.Api.ApiClientInterface;
import com.example.gtrainer.model.Ads_Pojo;
import com.example.gtrainer.model.PopularPojo;
import com.example.gtrainer.model.User;
import com.example.gtrainer.ui.SideMenuActivity.Apply_For_Trainer;
import com.example.gtrainer.ui.SideMenuActivity.BookingActivity;
import com.example.gtrainer.ui.SideMenuActivity.HelpActivity;
import com.example.gtrainer.ui.SideMenuActivity.PrivacyAndPolicyActivity;
import com.example.gtrainer.ui.SideMenuActivity.TermsAndConditionActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycleAdds;
    private ScrollingPagerIndicator mIndicator;


    private AddvertismentAdapter mAds_Adapter;

    private List<Ads_Pojo> ads_pojoList = new ArrayList<>();

    private List<PopularPojo> popularPojoList = new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerLinerLayout;

    private TextView mMenuHome;
    DiscreteScrollView popularRecycle;

    private MaterialCardView mViewAllTrainer;
    final int time = 4000;

    private ProgressBar mAddProgress;
    private RelativeLayout mApplyforTrainer;


    private List<User> userList = new ArrayList<>();


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    private RelativeLayout mBookingLayout;
    private RelativeLayout mHelpLayout;
    private RelativeLayout mPrivacyLayout;
    private RelativeLayout mTermConditionL;

    SharedPreferences prefs;
    String  tokken;
    private Top_Trainer_Adapter top_trainer_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        prefs = getSharedPreferences("ProfileData", MODE_PRIVATE);
        tokken = prefs.getString("tokken","no tokkens");
        mAddProgress.setVisibility(View.VISIBLE);

        mRecycleAdds.setHasFixedSize(true);
        mRecycleAdds.setLayoutManager(linearLayoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecycleAdds);

        Call_Ads_Data();



        getTopTrainers();

        mViewAllTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, All_List_Trainer.class);
                startActivity(intent);
            }
        });

        mApplyforTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Apply_For_Trainer.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });

        mBookingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookingActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });

        mHelpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });

        mTermConditionL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TermsAndConditionActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });

        mPrivacyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PrivacyAndPolicyActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
            }
        });



        mMenuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mDrawerLinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });








    }

    private void getTopTrainers() {
        Call<List<User>> call  = ApiClientInterface.getTrainerApiService().getTopTrainer(tokken);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.code() == 200){
                    userList = response.body();
                    assert userList != null;
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    if(userList.size() == 0){

                    }

                    top_trainer_adapter  = new Top_Trainer_Adapter(userList , MainActivity.this);
                    popularRecycle.setAdapter(top_trainer_adapter);
                    top_trainer_adapter.notifyDataSetChanged();


                    popularRecycle.setItemTransformer(new ScaleTransformer.Builder()
                            .setMinScale(0.7f)
                            .build());
                }
                else {
                    Toast.makeText(MainActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void Call_Ads_Data() {
        try {
            db.collection("AddsData")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot DS : list) {
                                    Ads_Pojo data = DS.toObject(Ads_Pojo.class);
                                    ads_pojoList.add(data);
                                }

                                mAddProgress.setVisibility(View.GONE);
                                mAds_Adapter = new AddvertismentAdapter(MainActivity.this, ads_pojoList);
                                mRecycleAdds.setAdapter(mAds_Adapter);
                                mAds_Adapter.notifyDataSetChanged();
                                mIndicator.attachToRecyclerView(mRecycleAdds);

                                final Timer timer = new Timer();
                                timer.schedule(new TimerTask() {

                                    @Override
                                    public void run() {

                                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (mAds_Adapter.getItemCount() - 1)) {
                                            linearLayoutManager.smoothScrollToPosition(mRecycleAdds, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                                        } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (mAds_Adapter.getItemCount() - 1)) {
                                            linearLayoutManager.smoothScrollToPosition(mRecycleAdds, new RecyclerView.State(), 0);
                                        }
                                    }
                                }, 0, time);

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(MainActivity.this, "Failed to load" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        mRecycleAdds = findViewById(R.id.recycle_adds);
        mIndicator = findViewById(R.id.indicator);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLinerLayout = findViewById(R.id.drawer_linerLayout);
        mMenuHome = findViewById(R.id.menu_home);
        mViewAllTrainer = findViewById(R.id.btnViewAllTrainer);
        mAddProgress = findViewById(R.id.ad_progressBar);
        mApplyforTrainer = findViewById(R.id.applyfortrainer_Layout);
        popularRecycle  = findViewById(R.id.toptrainerRecycle);


        mBookingLayout = findViewById(R.id.booking_layout);
        mHelpLayout = findViewById(R.id.help_layout);
        mPrivacyLayout = findViewById(R.id.privacy_layout);
        mTermConditionL = findViewById(R.id.term_conditionL);
    }
}