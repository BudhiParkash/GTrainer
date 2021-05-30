package com.example.gtrainer.ui.SideMenuActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gtrainer.Adapter.BookingAdapter;
import com.example.gtrainer.Api.ApiClientInterface;
import com.example.gtrainer.R;
import com.example.gtrainer.model.BookingPojo;
import com.example.gtrainer.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {


    SharedPreferences preferences;

    String tokken;

    RecyclerView mBookingRecycle;
    BookingAdapter mBookingAdapter;
    List<BookingPojo> bookinglist = new ArrayList<>();

    String tranierid;

    List<User> bookedtainer = new ArrayList<>();
    private ImageButton mBckBtnBooking;
    private TextView mTxtNoBooking;
    private ProgressBar mBookingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        initView();

        preferences = getSharedPreferences("ProfileData", MODE_PRIVATE);
        tokken = preferences.getString("tokken", "");
        mBookingProgress.setVisibility(View.VISIBLE);
        getBooking();

        mBookingRecycle = findViewById(R.id.bookingRecycle);
        mBookingRecycle.setHasFixedSize(true);
        mBookingRecycle.setLayoutManager(new LinearLayoutManager(BookingActivity.this, LinearLayoutManager.VERTICAL, false));


        mBckBtnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void getBookedTrainer(String tranierid) {
        Call<User> call = ApiClientInterface.getTrainerApiService().getBookedTrainer(tokken, tranierid);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();


                    bookedtainer.add(user);


                    mBookingAdapter = new BookingAdapter(bookedtainer, BookingActivity.this, bookinglist);
                    mBookingRecycle.setAdapter(mBookingAdapter);
                    mBookingAdapter.notifyDataSetChanged();


                } else {
                    Toast.makeText(BookingActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(BookingActivity.this, "try after sometimes", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getBooking() {
        Call<List<BookingPojo>> call = ApiClientInterface.getTrainerApiService().getBookings(tokken);
        call.enqueue(new Callback<List<BookingPojo>>() {
            @Override
            public void onResponse(Call<List<BookingPojo>> call, Response<List<BookingPojo>> response) {
                if (response.code() == 200) {
                    mBookingProgress.setVisibility(View.GONE);
                    bookinglist = response.body();
                    if(bookinglist.size() == 0){
                        mTxtNoBooking.setVisibility(View.VISIBLE);
                    }
                    else {
                        mTxtNoBooking.setVisibility(View.GONE);
                    }
                    assert bookinglist != null;
                    for (int i = 0; i < bookinglist.size(); i++) {
                        tranierid = bookinglist.get(i).getTrainerID();
                        getBookedTrainer(tranierid);
                    }

                } else {
                    mBookingProgress.setVisibility(View.GONE);
                    Toast.makeText(BookingActivity.this, "try after somttimes" + response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<BookingPojo>> call, Throwable t) {
                mBookingProgress.setVisibility(View.GONE);
                Toast.makeText(BookingActivity.this, "try after sometimes" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initView() {
        mBckBtnBooking = findViewById(R.id.bck_btn_booking);
        mTxtNoBooking = findViewById(R.id.txtNoBooking);
        mBookingProgress = findViewById(R.id.bookingProgress);
    }
}