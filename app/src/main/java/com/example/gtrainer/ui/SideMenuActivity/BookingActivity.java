package com.example.gtrainer.ui.SideMenuActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gtrainer.Activity.All_List_Trainer;
import com.example.gtrainer.Adapter.AllTrainerList_Adpter;
import com.example.gtrainer.Adapter.BookingAdapter;
import com.example.gtrainer.Api.ApiClientInterface;
import com.example.gtrainer.R;
import com.example.gtrainer.model.BookingPojo;
import com.example.gtrainer.model.PayPojo;
import com.example.gtrainer.model.User;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity  {



    SharedPreferences preferences;

    String tokken;

    RecyclerView mBookingRecycle;
    BookingAdapter mBookingAdapter;
    List<BookingPojo> bookinglist = new ArrayList<>();

    String tranierid;

    List<User> bookedtainer =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        preferences = getSharedPreferences("ProfileData", MODE_PRIVATE);
        tokken = preferences.getString("tokken", "");
        getBooking();

        mBookingRecycle = findViewById(R.id.bookingRecycle);
        mBookingRecycle.setHasFixedSize(true);
        mBookingRecycle.setLayoutManager(new LinearLayoutManager(BookingActivity.this, LinearLayoutManager.VERTICAL,false));





    }

    private void getBookedTrainer(String tranierid) {
        Call<User> call = ApiClientInterface.getTrainerApiService().getBookedTrainer(tokken , tranierid);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200){
                    Toast.makeText(BookingActivity.this, "get Booked Trainer", Toast.LENGTH_SHORT).show();
                    User user = response.body();

                    bookedtainer.add(user);

                    mBookingAdapter  = new BookingAdapter(bookedtainer , BookingActivity.this);
                    mBookingRecycle.setAdapter(mBookingAdapter);
                    mBookingAdapter.notifyDataSetChanged();



                }
                else {
                    Toast.makeText(BookingActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();
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
                if(response.code() == 200){
                    bookinglist = response.body();
                    assert bookinglist != null;
                    Toast.makeText(BookingActivity.this, "size" + bookinglist.size(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < bookinglist.size(); i++){
                        tranierid = bookinglist.get(i).getTrainerID();
                        Toast.makeText(BookingActivity.this, ""+tranierid, Toast.LENGTH_SHORT).show();
                        getBookedTrainer(tranierid);
                    }




                }
                else {

                }
            }

            @Override
            public void onFailure(Call<List<BookingPojo>> call, Throwable t) {

            }
        });
    }




}