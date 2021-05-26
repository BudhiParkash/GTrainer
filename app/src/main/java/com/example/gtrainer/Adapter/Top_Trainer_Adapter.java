package com.example.gtrainer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import com.example.gtrainer.model.CertificatePhotoPojo;
import com.example.gtrainer.model.TrainerPicPojo;
import com.example.gtrainer.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Top_Trainer_Adapter extends RecyclerView.Adapter<Top_Trainer_Adapter.ViewHolder> {
    static UrlLink urls = new UrlLink();
    private final static String url = urls.url;
    private static List<User> data;
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

        for (int i= 0 ; i< userData.getRatings().size(); i++ ){
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
                    String picurl ="";
                    String rating ="";
                    String trainerName ="";
                    String aboutTrainer="";
                    int trainerPrice =0;
                    String expirence ="";
                    String tranierId ="";

                    User userData = data.get(getAdapterPosition());
                    List<TrainerPicPojo> list = new ArrayList<>();
                    list = userData.getTrainerPic();

                    List<CertificatePhotoPojo> certiPhoto = new ArrayList<>();
                    certiPhoto = userData.getCertificates();

                    try {
                        for (int i= 0 ; i< userData.getTrainerPic().size(); i++ ){
                            picurl = url+ userData.getTrainerPic().get(i).getPic();

                        }

                        for (int i= 0 ; i< userData.getRatings().size(); i++ ){
                            rating = userData.getRatings().get(i).getRating();

                        }
                        trainerName = userData.getUser_name();
                        aboutTrainer = userData.getAboutUser();
                        trainerPrice = userData.getPrice();
                        expirence = userData.getExperiance();
                        tranierId = userData.getId();



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
                    intent.putExtra("tranierId" , tranierId);
                    intent.putParcelableArrayListExtra("picProfileArray" , (ArrayList<? extends Parcelable>) list);
                    intent.putParcelableArrayListExtra("picCertiArray" , (ArrayList<? extends Parcelable>) certiPhoto);
                    context.startActivity(intent);

                }
            });

        }
    }
}