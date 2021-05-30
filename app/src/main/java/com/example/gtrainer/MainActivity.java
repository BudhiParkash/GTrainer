package com.example.gtrainer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import com.example.gtrainer.Activity.SearchActivity;
import com.example.gtrainer.Adapter.AddvertismentAdapter;
import com.example.gtrainer.Adapter.AllTrainerList_Adpter;
import com.example.gtrainer.Adapter.Top_Trainer_Adapter;
import com.example.gtrainer.Api.ApiClientInterface;
import com.example.gtrainer.Api.UrlLink;
import com.example.gtrainer.model.Ads_Pojo;
import com.example.gtrainer.model.TrainerListPojo;
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
import com.squareup.picasso.Picasso;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class MainActivity extends AppCompatActivity {
    static UrlLink urls = new UrlLink();
    private final static String url = urls.url;
    private RecyclerView mRecycleAdds;
    private ScrollingPagerIndicator mIndicator;


    private AddvertismentAdapter mAds_Adapter;

    private List<Ads_Pojo> ads_pojoList = new ArrayList<>();

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
    String tokken;
    private Top_Trainer_Adapter top_trainer_adapter;


    private RecyclerView mNewTrainerRecycle;
    private List<User> newtrainerList = new ArrayList<User>();
    private AllTrainerList_Adpter mAllTrainer_Adapter;

    private String userName, phnNumber;
    private TextView mHomeUserName;
    private CircleImageView mHomeImageProfile;
    private CircleImageView mAccountImage;
    private TextView mAccountUserName;
    private TextView mAccountNumber;
    private TextView mNewTrainerViewAll;
    private TextView mBtnsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        prefs = getSharedPreferences("ProfileData", MODE_PRIVATE);

        try {
            tokken = prefs.getString("tokken", "no tokkens");
            userName = prefs.getString("name", "Your Name");
            phnNumber = prefs.getString("num", "98100xxxxx");
        } catch (Exception e) {

        }

        mAddProgress.setVisibility(View.VISIBLE);
        mHomeUserName.setText(userName);
        mAccountUserName.setText(userName);
        mAccountNumber.setText(phnNumber);

        mRecycleAdds.setHasFixedSize(true);
        mRecycleAdds.setLayoutManager(linearLayoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecycleAdds);

        mNewTrainerRecycle.setHasFixedSize(true);
        mNewTrainerRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        boolean isDarkThemeOn = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;


        if (!isDarkThemeOn) {
            mDrawerLinerLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }
        Call_Ads_Data();
        getTopTrainers();
        getNewTrainers();
        getUser();


        mBtnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

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

        mNewTrainerViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, All_List_Trainer.class);
                startActivity(intent);
            }
        });
    }

    private void getUser() {
        Call<User> call = ApiClientInterface.getTrainerApiService().getUser(tokken);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {

                    String picUrl="";

                    User userData = response.body();

                    for (int i = 0; i < userData.getTrainerPic().size() ; i++){
                     picUrl = userData.getTrainerPic().get(i).getPic();
                    }


                    try {
                        Picasso.get().load(url + picUrl).into(mAccountImage);
                        Picasso.get().load(url+picUrl).into(mHomeImageProfile);
                    }
                    catch (Exception e){

                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void getTopTrainers() {
        Call<List<User>> call = ApiClientInterface.getTrainerApiService().getTopTrainer(tokken);
        mAddProgress.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.code() == 200) {
                    mAddProgress.setVisibility(View.GONE);
                    userList = response.body();
                    assert userList != null;
                    if (userList.size() == 0) {

                    }

                    top_trainer_adapter = new Top_Trainer_Adapter(userList, MainActivity.this);
                    popularRecycle.setAdapter(top_trainer_adapter);
                    top_trainer_adapter.notifyDataSetChanged();


                    popularRecycle.setItemTransformer(new ScaleTransformer.Builder()
                            .setMinScale(0.7f)
                            .build());
                } else {
                    mAddProgress.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                mAddProgress.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getNewTrainers() {
        mAddProgress.setVisibility(View.VISIBLE);
        Call<TrainerListPojo> call = ApiClientInterface.getTrainerApiService().getAllTrainer(tokken, 1, 10);
        call.enqueue(new Callback<TrainerListPojo>() {
            @Override
            public void onResponse(Call<TrainerListPojo> call, Response<TrainerListPojo> response) {
                if (response.code() == 200) {
                    mAddProgress.setVisibility(View.GONE);
                    assert response.body() != null;

                    TrainerListPojo trainerListPojo = response.body();
                    newtrainerList.addAll(trainerListPojo.getSearchResult());


                    mAllTrainer_Adapter = new AllTrainerList_Adpter(newtrainerList, MainActivity.this);
                    mNewTrainerRecycle.setAdapter(mAllTrainer_Adapter);
                    mAllTrainer_Adapter.notifyDataSetChanged();
                } else {
                    mAddProgress.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "try after sometime" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TrainerListPojo> call, Throwable t) {
                mAddProgress.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "try after sometime" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    mAddProgress.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Failed to load" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            mAddProgress.setVisibility(View.GONE);
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
        popularRecycle = findViewById(R.id.toptrainerRecycle);

        mNewTrainerRecycle = findViewById(R.id.newTrainerRecycle);


        mBookingLayout = findViewById(R.id.booking_layout);
        mHelpLayout = findViewById(R.id.help_layout);
        mPrivacyLayout = findViewById(R.id.privacy_layout);
        mTermConditionL = findViewById(R.id.term_conditionL);
        mHomeUserName = findViewById(R.id.home_userName);
        mHomeImageProfile = findViewById(R.id.home_Image_profile);
        mAccountImage = findViewById(R.id.accountImage);
        mAccountUserName = findViewById(R.id.accountUserName);
        mAccountNumber = findViewById(R.id.accountNumber);
        mNewTrainerViewAll = findViewById(R.id.newTrainer_ViewAll);
        mBtnsearch = findViewById(R.id.btnsearch);
    }
}