package com.skywalkers.gtrainer.ui.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.skywalkers.gtrainer.Api.ApiClientInterface;
import com.skywalkers.gtrainer.R;
import com.skywalkers.gtrainer.model.BookingPojo;
import com.skywalkers.gtrainer.model.PayPojo;
import com.google.android.material.chip.Chip;
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

public class BookingDetailsActivity extends AppCompatActivity implements PaymentResultListener {

    private ImageButton mBckBtnBookingDetails;
    private CircleImageView mBookprofilePic;
    private TextView mBookingTName;
    private TextView mBookingTExprince;
    private TextView mBookingTDetials;
    private Chip mChipSevenDaytrial;
    private Chip mChipOneMonth;
    private Chip mChipthreeMonth;
    private Chip mChipOneYear;
    private RadioGroup mRadioGrpOffer;
    private RadioButton mRadioBtn25;
    private RadioButton mRadioBtn50;
    private RadioButton mRadioBtnNoOffer;
    private TextView mTrainerPayment;
    private TextView mServiceTax;
    private TextView mBookingCharges;
    private TextView mDiscountAmount;
    private TextView mTotalAmount;
    private ProgressBar mBookingProgressBar;

    private String trainerName;
    private String aboutTrainer;
    private int price;
    private String rating;
    private String picurl;
    private String experince;
    private String tranierId;

    private String tokken, userId;

    private SharedPreferences prefs;

    int disCountPercentage = 0;
    int disCountPrice = 0;
    int ourServiceCharges;
    int GST;
    int payment;
    private Button mBtnfinalbook;
    private int FinalPayment;

    String key,orderId;
    int finalPays;
    private static final String TAG = "Find";
    List<PayPojo> pp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        initView();

        prefs = getSharedPreferences("ProfileData", MODE_PRIVATE);
        try {
            tokken = prefs.getString("tokken", "no tokkens");
            userId = prefs.getString("userId", "xx");
        } catch (Exception e) {

        }

        try {
            trainerName = getIntent().getStringExtra("trainerName");
            aboutTrainer = getIntent().getStringExtra("aboutTrainer");
            price = getIntent().getIntExtra("trainerPrice", 0);
            rating = getIntent().getStringExtra("trainerRating");
            picurl = getIntent().getStringExtra("picUrl");
            experince = getIntent().getStringExtra("expirence");
            tranierId = getIntent().getStringExtra("tranierId");
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try {
            mBookingTDetials.setText(aboutTrainer);
            mBookingTExprince.setText(experince);
            mBookingTName.setText(trainerName);
            Picasso.get().load(picurl).into(mBookprofilePic);

        } catch (Exception e) {

        }
        mBckBtnBookingDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mChipOneMonth.setChecked(true);
        mRadioBtnNoOffer.setChecked(true);
        if (mChipOneMonth.isChecked()) {
            payment = price;
            disCountPercentage = 0;
            ourServiceCharges = 0;
            GST = 0;
            setPayment(payment, disCountPercentage, ourServiceCharges, GST);
        }


        mChipSevenDaytrial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRadioBtn25.setClickable(false);
                    mRadioBtn50.setClickable(false);
                    payment = price;
                    disCountPercentage = 0;
                    ourServiceCharges = 0;
                    GST = 0;
                    setPayment(payment, disCountPercentage, ourServiceCharges, GST);
                } else {
                    mRadioBtn25.setClickable(true);
                    mRadioBtn50.setClickable(true);
                    payment = 0;
                    disCountPercentage = 0;
                    ourServiceCharges = 0;
                    GST = 0;
                }
            }
        });

        mChipOneMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRadioBtn25.setClickable(false);
                    mRadioBtn50.setClickable(false);
                    payment = price;
                    disCountPercentage = 0;
                    ourServiceCharges = 0;
                    GST = 0;
                    setPayment(payment, disCountPercentage, ourServiceCharges, GST);
                } else {
                    mRadioBtn25.setClickable(true);
                    mRadioBtn50.setClickable(true);
                    payment = 0;
                    disCountPercentage = 0;
                    ourServiceCharges = 0;
                    GST = 0;


                }
            }
        });

        mChipthreeMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRadioBtn25.setChecked(true);
                    mRadioBtn50.setClickable(false);
                    payment = price * 3;
                    disCountPercentage = 25;
                    ourServiceCharges = 0;
                    GST = 0;
                    setPayment(payment, disCountPercentage, ourServiceCharges, GST);
                } else {
                    mRadioBtn25.setChecked(false);
                    mRadioBtn50.setClickable(true);
                    payment = 0;
                    disCountPercentage = 0;
                    ourServiceCharges = 0;
                    GST = 0;



                }
            }
        });

        mChipOneYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRadioBtn50.setChecked(true);
                    mRadioBtn25.setClickable(false);
                    payment = price * 12;
                    disCountPercentage = 50;
                    ourServiceCharges = 0;
                    GST = 0;
                    setPayment(payment, disCountPercentage, ourServiceCharges, GST);
                } else {
                    mRadioBtn25.setClickable(true);
                    mRadioBtn50.setChecked(false);
                    payment = 0;
                    disCountPercentage = 0;
                    ourServiceCharges = 0;
                    GST = 0;
                    


                }

            }
        });

        mRadioBtn25.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mChipthreeMonth.setChecked(true);

                } else {
                    mChipthreeMonth.setChecked(false);
                }
            }
        });

        mRadioBtn50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mChipOneYear.setChecked(true);
                } else {
                    mChipOneYear.setChecked(false);
                }
            }
        });


        mBtnfinalbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mChipthreeMonth.isChecked() && !mChipOneYear.isChecked() && !mChipSevenDaytrial.isChecked() && !mChipOneMonth.isChecked()){
                    Toast.makeText(BookingDetailsActivity.this, "Please select period of booking", Toast.LENGTH_SHORT).show();
                }
                else {
                    mBookingProgressBar.setVisibility(View.VISIBLE);
                    payOnline(FinalPayment);
                }
            }
        });


    }

    private void setPayment(int payment, int disCountPercentage, int ourServiceCharges, int tax) {

        double discAmount = payment * (Double.parseDouble(String.valueOf(disCountPercentage)) / 100);
        double taxsAmount = payment * (Double.parseDouble(String.valueOf(tax)) / 100);
        double finalPrice = payment - discAmount + taxsAmount + ourServiceCharges;


        mTrainerPayment.setText(payment + " Rs");
        mDiscountAmount.setText(discAmount + " Rs");
        mServiceTax.setText(taxsAmount + " Rs");
        mBookingCharges.setText(ourServiceCharges + " Rs");

        mTotalAmount.setText(finalPrice + " Rs");

        FinalPayment = (int) finalPrice;

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
                    mBookingProgressBar.setVisibility(View.GONE);
                    pp = response.body();
                    assert pp != null;
                    key = pp.get(1).getKeyId();
                    orderId = pp.get(0).getOrder().getId();
                    finalPays = pp.get(0).getOrder().getAmountDue();
                    startPayment();

                }else {
                    mBookingProgressBar.setVisibility(View.GONE);
                    Toast.makeText(BookingDetailsActivity.this, "Please Check Details Again" + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<PayPojo>> call, Throwable t) {
                mBookingProgressBar.setVisibility(View.GONE);
                Toast.makeText(BookingDetailsActivity.this, "Please Check your internet", Toast.LENGTH_SHORT).show();
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
            options.put("name", "Gym Trainer");

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
        String periodofbooking= "";
        if(mChipOneMonth.isChecked()){
            periodofbooking = mChipOneMonth.getText().toString();
        }
        else if(mChipOneYear.isChecked()){
            periodofbooking = mChipOneYear.getText().toString();
        }
        else if(mChipthreeMonth.isChecked()){
            periodofbooking = mChipthreeMonth.getText().toString();
        }
        else if(mChipSevenDaytrial.isChecked()){
            periodofbooking = mChipSevenDaytrial.getText().toString();
        }

        mBookingProgressBar.setVisibility(View.VISIBLE);
        BookingPojo bookingData = new BookingPojo(userId ,tranierId , FinalPayment , periodofbooking , "21/7/2021"  );

        Call<BookingPojo> call = ApiClientInterface.getTrainerApiService().postBooking(tokken , bookingData);
        call.enqueue(new Callback<BookingPojo>() {
            @Override
            public void onResponse(Call<BookingPojo> call, Response<BookingPojo> response) {
                if(response.code() == 201){
                    mBookingProgressBar.setVisibility(View.GONE);
                    Toast.makeText(BookingDetailsActivity.this, "Done Booking", Toast.LENGTH_SHORT).show();
                }
                else {
                    mBookingProgressBar.setVisibility(View.GONE);
                    Toast.makeText(BookingDetailsActivity.this, "Some Error in Booking" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookingPojo> call, Throwable t) {
                mBookingProgressBar.setVisibility(View.GONE);
                Toast.makeText(BookingDetailsActivity.this, "try after sometimes" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        mBckBtnBookingDetails = findViewById(R.id.bck_btn_bookingDetails);
        mBookprofilePic = findViewById(R.id.bookprofilePic);
        mBookingTName = findViewById(R.id.booking_t_name);
        mBookingTExprince = findViewById(R.id.booking_t_Exprince);
        mBookingTDetials = findViewById(R.id.booking_t_Detials);
        mChipSevenDaytrial = findViewById(R.id.chipSevenDaytrial);
        mChipOneMonth = findViewById(R.id.chipOneMonth);
        mChipthreeMonth = findViewById(R.id.chipthreeMonth);
        mChipOneYear = findViewById(R.id.chipOneYear);
        mRadioGrpOffer = findViewById(R.id.radioGrpOffer);
        mRadioBtn25 = findViewById(R.id.radioBtn25);
        mRadioBtn50 = findViewById(R.id.radioBtn50);
        mRadioBtnNoOffer = findViewById(R.id.radioBtnNoOffer);
        mTrainerPayment = findViewById(R.id.trainer_payment);
        mServiceTax = findViewById(R.id.service_tax);
        mBookingCharges = findViewById(R.id.booking_charges);
        mDiscountAmount = findViewById(R.id.discount_amount);
        mTotalAmount = findViewById(R.id.total_amount);
        mBookingProgressBar = findViewById(R.id.bookingProgressBar);
        mBtnfinalbook = findViewById(R.id.btnfinalbook);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}