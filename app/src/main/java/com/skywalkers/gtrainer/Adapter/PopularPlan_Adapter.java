package com.skywalkers.gtrainer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skywalkers.gtrainer.R;
import com.skywalkers.gtrainer.model.Ads_Pojo;
import com.skywalkers.gtrainer.model.PopuplarPlanModel;
import com.skywalkers.gtrainer.model.User;
import com.skywalkers.gtrainer.ui.Activity.TrainerDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularPlan_Adapter extends RecyclerView.Adapter<PopularPlan_Adapter.PopularPlanViewHolder> {

    private Context context;
    private List<PopuplarPlanModel> planModelList;


    public PopularPlan_Adapter(Context context, List<PopuplarPlanModel> planModelList) {
        this.context = context;
        this.planModelList = planModelList;
    }
    @NonNull
    @Override
    public PopularPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_popular_plans , parent , false);
        return new PopularPlanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularPlanViewHolder holder, int position) {
        PopuplarPlanModel model = planModelList.get(position);
        Picasso.get().load(model.getImageLinks()).into(holder.planImage);
    }

    @Override
    public int getItemCount() {
        return planModelList.size();
    }

    public class PopularPlanViewHolder extends RecyclerView.ViewHolder {

        ImageView planImage;

        public PopularPlanViewHolder(@NonNull View itemView) {
            super(itemView);

            planImage = itemView.findViewById(R.id.plansImage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TrainerDetailActivity.class);
                    intent.putExtra("id",getAdapterPosition());
                    context.startActivity(intent);
                }
            });

        }
    }


}
