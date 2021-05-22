package com.example.gtrainer.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gtrainer.Api.ApiClientInterface;
import com.example.gtrainer.R;
import com.example.gtrainer.model.BookingPojo;
import com.example.gtrainer.model.PayPojo;
import com.example.gtrainer.ui.SideMenuActivity.BookingActivity;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainerProfileActivity extends AppCompatActivity implements PaymentResultListener {


    String key,orderId;
    int finalPays;
    private static final String TAG = "Find";
    List<PayPojo> pp = new ArrayList<>();

    private CircleImageView mToolbarImageProfile;
    private TextView mToolbarUserName;
    private TextView mProfleRating;
    private ImageView mProfileImage;
    private TextView mProfileName;
    private TextView mGender;
    private TextView mExperince;
    private TextView mProfileAboutTrainer;
    private Button mBtnBook;

    private String trainerName;
    private String aboutTrainer;
    private int price;
    private String rating;
    private String picurl;
    private String experince;
    private  String tranierId;
    private String  tokken , userId;

    private SharedPreferences prefs;
    private ProgressBar mProfile_Progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile);
        initView();

         prefs = getSharedPreferences("ProfileData", MODE_PRIVATE);
         try{
             tokken = prefs.getString("tokken","no tokkens");
             userId = prefs.getString("userId" , "xx");
         }
         catch (Exception e){

         }

        try {
             trainerName = getIntent().getStringExtra("trainerName");
             aboutTrainer = getIntent().getStringExtra("aboutTrainer");
             price = getIntent().getIntExtra("trainerPrice", 0);
             rating = getIntent().getStringExtra("trainerRating");
             picurl = getIntent().getStringExtra("picUrl");
             experince = getIntent().getStringExtra("expirence");
             tranierId = getIntent().getStringExtra("tranierId");
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }






        try {
            mProfileAboutTrainer.setText(aboutTrainer);
            mProfileName.setText(trainerName);
            mProfleRating.setText(rating);
            mExperince.setText(experince);
            mToolbarUserName.setText(trainerName);
            Picasso.get().load(picurl).into(mProfileImage);
            Picasso.get().load(picurl).into(mToolbarImageProfile);

            mBtnBook.setText("HIRE NOW " + price + " only/-");
        }
        catch (Exception e){

        }



        mBtnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfile_Progressbar.setVisibility(View.VISIBLE);
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
                    mProfile_Progressbar.setVisibility(View.GONE);
                    pp = response.body();
                    assert pp != null;
                    key = pp.get(1).getKeyId();
                    orderId = pp.get(0).getOrder().getId();
                    finalPays = pp.get(0).getOrder().getAmountDue();
                    startPayment();

                }else {
                    mProfile_Progressbar.setVisibility(View.GONE);
                    Toast.makeText(TrainerProfileActivity.this, "Please Check Details Again" + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<PayPojo>> call, Throwable t) {
                mProfile_Progressbar.setVisibility(View.GONE);
                Toast.makeText(TrainerProfileActivity.this, "Please Check your internet", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
        doBooking();

    }

    @Override
    public void onPaymentError(int i, String s) {

    }

    private void doBooking() {
        mProfile_Progressbar.setVisibility(View.VISIBLE);
        BookingPojo bookingData = new BookingPojo(tranierId , userId , price , "two months" , "21/7/2021"  );

        Call<BookingPojo> call = ApiClientInterface.getTrainerApiService().postBooking(tokken , bookingData);
        call.enqueue(new Callback<BookingPojo>() {
            @Override
            public void onResponse(Call<BookingPojo> call, Response<BookingPojo> response) {
                if(response.code() == 201){
                    mProfile_Progressbar.setVisibility(View.GONE);
                    Toast.makeText(TrainerProfileActivity.this, "Done Booking", Toast.LENGTH_SHORT).show();
                }
                else {
                    mProfile_Progressbar.setVisibility(View.GONE);
                    Toast.makeText(TrainerProfileActivity.this, "Some Error in Booking" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingPojo> call, Throwable t) {
                mProfile_Progressbar.setVisibility(View.GONE);
                Toast.makeText(TrainerProfileActivity.this, "try after sometimes" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        mProfile_Progressbar = findViewById(R.id.trainerProfile_Progressbar);
        mToolbarImageProfile = findViewById(R.id.toolbar_Image_profile);
        mToolbarUserName = findViewById(R.id.toolbar_User_Name);
        mProfleRating = findViewById(R.id.profle_rating);
        mProfileImage = findViewById(R.id.profile_image);
        mProfileName = findViewById(R.id.profile_name);
        mGender = findViewById(R.id.gender);
        mExperince = findViewById(R.id.experince);
        mBtnBook = findViewById(R.id.btnBook);
        mProfileAboutTrainer = findViewById(R.id.profile_about_trainer);
    }
}