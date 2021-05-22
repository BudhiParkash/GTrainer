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

public class AllTrainerList_Adpter extends RecyclerView.Adapter<AllTrainerList_Adpter.AllListViewHolder> {

    static UrlLink urls = new UrlLink();
    private final static String url = urls.url;
    private static List<User> data;
    static Context context;


    public AllTrainerList_Adpter(List<User> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public AllListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.all_trainer_list_view, parent, false);
        return new AllListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AllListViewHolder holder, int position) {

        User userData = data.get(position);

        try {
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
        catch (Exception e){

        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class AllListViewHolder extends RecyclerView.ViewHolder {
        private TextView mTrainerRating;
        private ImageView mTrainerImage;
        private TextView mTrainerName;
        private TextView mAboutTrainer;
        private TextView mTrainerPrice;

        public AllListViewHolder(View itemView) {
            super(itemView);
            mTrainerRating = itemView.findViewById(R.id.trainer_rating);
            mTrainerImage = itemView.findViewById(R.id.trainer_image);
            mTrainerName = itemView.findViewById(R.id.trainer_name);
            mAboutTrainer = itemView.findViewById(R.id.about_trainer);
            mTrainerPrice = itemView.findViewById(R.id.trainer_price);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String picurl ="";
                    String rating ="";
                    String trainerName="";
                    String aboutTrainer="";
                    int trainerPrice=0;
                    String expirence="";
                    String trainerId ="";

                    User userData = data.get(getAdapterPosition());

                    try {
                        for (int i= 0 ; i< userData.getTrainerPic().size(); i++ ){
                            picurl = url+ userData.getTrainerPic().get(i).getPic();

                        }

                        for (int i= 0 ; i< userData.getTrainerPic().size(); i++ ){
                            rating = userData.getRatings().get(i).getRating();
                        }
                        trainerName = userData.getUser_name();
                        aboutTrainer = userData.getAboutUser();
                        trainerPrice = userData.getPrice();
                        expirence = userData.getExperiance();
                        trainerId = userData.getId();
                    }
                    catch (Exception e){

                    }





                    Intent intent = new Intent(context, TrainerProfileActivity.class);
                    intent.putExtra("trainerName", trainerName);
                    intent.putExtra("aboutTrainer" , aboutTrainer);
                    intent.putExtra("trainerPrice" , trainerPrice);
                    intent.putExtra("trainerRating", rating);
                    intent.putExtra("picUrl",picurl);
                    intent.putExtra("expirence" , expirence);
                    intent.putExtra("tranierId" , trainerId);
                    context.startActivity(intent);


                }
            });

        }
    }
}
