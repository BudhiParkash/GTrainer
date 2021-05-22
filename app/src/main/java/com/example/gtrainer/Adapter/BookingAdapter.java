package com.example.gtrainer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gtrainer.Api.UrlLink;
import com.example.gtrainer.R;
import com.example.gtrainer.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BokingViewHolder> {

    static UrlLink urls = new UrlLink();
    private final static String url = urls.url;
    private static List<User> data;
    static Context context;


    public BookingAdapter(List<User> data, Context context) {
        this.data = data;
        this.context = context;
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
        holder.mTrainerPrice.setText(userData.getPrice()+" only/-");


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
        }
    }
}
