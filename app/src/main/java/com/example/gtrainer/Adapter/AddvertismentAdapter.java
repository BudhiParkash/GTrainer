package com.example.gtrainer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gtrainer.R;
import com.example.gtrainer.model.Ads_Pojo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddvertismentAdapter extends RecyclerView.Adapter<AddvertismentAdapter.AddsViewHolder> {


    private Context context;
    private List<Ads_Pojo> addsPojo;
    String ad_link;

    public AddvertismentAdapter(Context context, List<Ads_Pojo> addsPojo) {
        this.context = context;
        this.addsPojo = addsPojo;
    }

    @NonNull
    @Override
    public AddvertismentAdapter.AddsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_item_view , parent , false);
        return new AddsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddvertismentAdapter.AddsViewHolder holder, int position) {

        Ads_Pojo data = addsPojo.get(position);

        ad_link = data.getAd_Link();

        holder.addprice.setText(data.getAd_Description());
        holder.addanme.setText(data.getAd_Name());

        Picasso.get().load(data.getAd_Photo()).into(holder.mAds_Image);




    }

    @Override
    public int getItemCount() {
        return addsPojo.size();
    }

    public class AddsViewHolder extends RecyclerView.ViewHolder {

        TextView addanme;
        TextView addprice;
        ImageView mAds_Image;



        public AddsViewHolder(@NonNull View itemView) {
            super(itemView);
            addanme = itemView.findViewById(R.id.addname);
            addprice = itemView.findViewById(R.id.txtaddprice);
            mAds_Image = itemView.findViewById(R.id.ad_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(ad_link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });
        }

    }
}
