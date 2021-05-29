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

        User Data = data.get(position);

        holder.mAboutTrainer.setText(Data.getAboutUser());
        holder.mTrainerName.setText(Data.getUser_name());
        holder.mTrainerPrice.setText(Data.getPrice()+" only/-");
        try {
            for (int i= 0 ; i< Data.getTrainerPic().size(); i++ ){
                String picurl = url+ Data.getTrainerPic().get(i).getPic();
                Picasso.get().load(picurl).into(holder.mTrainerImage);
            }

            for (int i= 0 ; i< Data.getRatings().size(); i++ ){
                String rating = Data.getRatings().get(i).getRating();
                holder.mTrainerRating.setText(rating);
            }



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
                    String trainerName ="";
                    String aboutTrainer="";
                    int trainerPrice =0;
                    String expirence ="";
                    String tranierId ="";
                    String gender="";

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
                        gender = userData.getGender();



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
                    intent.putExtra("gender" , gender);
                    intent.putParcelableArrayListExtra("picProfileArray" , (ArrayList<? extends Parcelable>) list);
                    intent.putParcelableArrayListExtra("picCertiArray" , (ArrayList<? extends Parcelable>) certiPhoto);
                    context.startActivity(intent);


                }
            });

        }
    }
}
