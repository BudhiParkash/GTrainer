package com.example.gtrainer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gtrainer.Activity.TrainerProfileActivity;
import com.example.gtrainer.Api.UrlLink;
import com.example.gtrainer.R;
import com.example.gtrainer.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Top_Trainer_Adapter extends RecyclerView.Adapter<Top_Trainer_Adapter.ViewHolder> {
    UrlLink urls = new UrlLink();
    private final String url = urls.url;
    private List<User> data;
    static Context context;


    public Top_Trainer_Adapter(List<User> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.popular_item_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        User userData = data.get(position);


        for (int i= 0 ; i< userData.getTrainerPic().size(); i++ ){
            String picurl = url+ userData.getTrainerPic().get(i).getPic();
            Picasso.get().load(picurl).into(holder.mTopTranierPic);
        }

        for (int i= 0 ; i< userData.getTrainerPic().size(); i++ ){
            String rating = userData.getRatings().get(i).getRating();
            holder.mTopTrainerRating.setText(rating);
        }

        holder.mTopTrainerName.setText(userData.getUser_name());
        holder.mTopTrainerPrice.setText(userData.getPrice()+" only/-");
     //   holder.mTopTrainerRating.setText(userData.getRatings().get(position).getRating());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTopTrainerName;
        private TextView mTopTrainerRating;
        private TextView mTopTrainerPrice;
        private ImageView mTopTranierPic;

        public ViewHolder(View itemView) {
            super(itemView);

            mTopTrainerName = itemView.findViewById(R.id.top_trainer_name);
            mTopTrainerRating = itemView.findViewById(R.id.top_trainer_rating);
            mTopTrainerPrice = itemView.findViewById(R.id.top_trainer_price);
            mTopTranierPic = itemView.findViewById(R.id.top_tranier_pic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TrainerProfileActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }
}