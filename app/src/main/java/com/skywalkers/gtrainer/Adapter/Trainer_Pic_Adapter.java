package com.skywalkers.gtrainer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skywalkers.gtrainer.Api.UrlLink;
import com.skywalkers.gtrainer.R;
import com.skywalkers.gtrainer.model.TrainerPicPojo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Trainer_Pic_Adapter extends RecyclerView.Adapter<Trainer_Pic_Adapter.TrainerPicViewHolder> {

    UrlLink urls = new UrlLink();
    private final String url = urls.url;
    List<TrainerPicPojo> data ;
    Context context;

    public Trainer_Pic_Adapter(List<TrainerPicPojo> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public TrainerPicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.demo_certi_pic,parent,false);
        return new TrainerPicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainerPicViewHolder holder, int position) {
        TrainerPicPojo info = data.get(position);
        String[] arrOfStr = info.getPic().split("uploads");


        String photourl = url + info.getPic();


        Picasso.get().load(photourl).into(holder.mCertiPic);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TrainerPicViewHolder extends RecyclerView.ViewHolder{

        ImageView mCertiPic;

        public TrainerPicViewHolder(@NonNull View itemView) {
            super(itemView);

            mCertiPic = itemView.findViewById(R.id.profile);

        }
    }
}
