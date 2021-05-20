package com.example.gtrainer.ui.SideMenuActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gtrainer.Api.ApiClientInterface;
import com.example.gtrainer.R;
import com.example.gtrainer.model.PayPojo;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity implements PaymentResultListener {

    Button mBtnPAy;

    private int price = 1;
    SharedPreferences preferences;
    List<PayPojo> pp = new ArrayList<>();
    String tokken;

    String key,orderId;
    int finalPays;
    private static final String TAG = "Find";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        mBtnPAy = findViewById(R.id.paymoney);

        preferences = getSharedPreferences("ProfileData", MODE_PRIVATE);

       tokken = preferences.getString("tokken", "");


        mBtnPAy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                payOnline(price);
            }
        });
    }

    private void payOnline(int rate) {

        rate = rate*100;
        JsonObject amout = new JsonObject();
        amout.addProperty("amount", rate);
        Call<List<PayPojo>> call = ApiClientInterface.getTrainerApiService().getOrderId(tokken,amout);

        call.enqueue(new Callback<List<PayPojo>>() {
            @Override
            public void onResponse(Call<List<PayPojo>> call, Response<List<PayPojo>> response) {
                if(response.code() == 201){
                    pp = response.body();
                    assert pp != null;
                    key = pp.get(1).getKeyId();
                    orderId = pp.get(0).getOrder().getId();
                    finalPays = pp.get(0).getOrder().getAmountDue();
                    startPayment();

                }else {
                    Toast.makeText(BookingActivity.this, "Please Check Details Again" + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<PayPojo>> call, Throwable t) {
                Toast.makeText(BookingActivity.this, "Please Check your internet", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void startPayment() {
        /**
         * Instantiate Checkout
         */


        Checkout checkout = new Checkout();
        checkout.setKeyID(key);
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.rzp_logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "Charak");

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Receipt for User");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", orderId);
            options.put("currency", "INR");


            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            String finalPay = String.valueOf(finalPays);
            options.put("amount", finalPay);

            checkout.open(activity, options);
        } catch(Exception e) {
            Toast.makeText(activity, "Try Again Later!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}