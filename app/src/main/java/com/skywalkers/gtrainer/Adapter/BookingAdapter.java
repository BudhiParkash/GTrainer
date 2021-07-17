package com.skywalkers.gtrainer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skywalkers.gtrainer.ui.Activity.BookedTrainer_DetailsActivity;
import com.skywalkers.gtrainer.Api.UrlLink;
import com.skywalkers.gtrainer.R;
import com.skywalkers.gtrainer.model.BookingPojo;
import com.skywalkers.gtrainer.model.User;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BokingViewHolder> {

    static UrlLink urls = new UrlLink();
    private final static String url = urls.url;
    private static List<User> data;
    private static List<BookingPojo> mBookingData;
    static Context context;


    public BookingAdapter(List<User> data, Context context , List<BookingPojo> mBookingData) {
        this.data = data;
        this.context = context;
        this.mBookingData = mBookingData;
    }

    @NonNull
    @Override
    public BookingAdapter.BokingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.all_trainer_list_view, parent, false);
        return new BokingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.BokingViewHolder holder, int position) {
        User userData = data.get(position);
        BookingPojo bookingData = mBookingData.get(position);

        for (int i= 0 ; i< userData.getTrainerPic().size(); i++ ){
            String picurl = url+ userData.getTrainerPic().get(i).getPic();
            Picasso.get().load(picurl).into(holder.mTrainerImage);
        }

        for (int i= 0 ; i< userData.getRatings().size(); i++ ){
            String rating = userData.getRatings().get(i).getRating();
            holder.mTrainerRating.setText(rating);
        }

        holder.mAboutTrainer.setText(userData.getAboutUser());
        holder.mTrainerName.setText(userData.getUser_name());
        holder.mTrainerPrice.setText(bookingData.getPayment()+" only/-");


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class BokingViewHolder extends RecyclerView.ViewHolder {
        private TextView mTrainerRating;
        private ImageView mTrainerImage;
        private TextView mTrainerName;
        private TextView mAboutTrainer;
        private TextView mTrainerPrice;
        public BokingViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrainerRating = itemView.findViewById(R.id.trainer_rating);
            mTrainerImage = itemView.findViewById(R.id.trainer_image);
            mTrainerName = itemView.findViewById(R.id.trainer_name);
            mAboutTrainer = itemView.findViewById(R.id.about_trainer);
            mTrainerPrice = itemView.findViewById(R.id.trainer_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String picurl = "";
                    String trainerName ="";
                    String trainerGender="";
                    int payment =0;
                    String dateofbooking="";
                    String periodofbooking="";
                    String bookinglastDate="";

                    BookingPojo bookingData = mBookingData.get(getAdapterPosition());
                    User userData = data.get(getAdapterPosition());

                    try {
                        for (int i= 0 ; i< userData.getTrainerPic().size(); i++ ) {
                            picurl = url + userData.getTrainerPic().get(i).getPic();
                        }

                         trainerName = userData.getUser_name();
                        trainerGender = userData.getGender();
                        payment = bookingData.getPayment();
                        dateofbooking = getDate(bookingData.getCreatedAt());
                        periodofbooking = bookingData.getPeriodOfBooking();
                        bookinglastDate = bookingData.getLastDateOfBook();
                    }
                    catch (Exception e){

                    }







                    Intent intent = new Intent(context , BookedTrainer_DetailsActivity.class);
                    intent.putExtra("trainerName", trainerName);
                    intent.putExtra("trainerGender" , trainerGender);
                    intent.putExtra("payment", payment);
                    intent.putExtra("dateofbooking", dateofbooking);
                    intent.putExtra("periodofbooking",periodofbooking);
                    intent.putExtra("bookinglastDate",bookinglastDate);
                    intent.putExtra("picUrl" , picurl);
                    context.startActivity(intent);
                }
            });
        }

    }

    private String getDate(String ourDate)
    {
        try
        {
            //dd/MM/yyyy HH:mm"
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Date dates = Date.from(ZonedDateTime.parse(ourDate).toInstant());
                DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                String result1 = df1.format(dates);
                ourDate = result1;
            }

        }
        catch (Exception e)
        {
            ourDate = "00-00-0000";
        }
        return ourDate;
    }
}
