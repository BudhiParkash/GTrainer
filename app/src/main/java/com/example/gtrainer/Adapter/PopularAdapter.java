package com.example.gtrainer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gtrainer.Activity.TrainerProfileActivity;
import com.example.gtrainer.R;
import com.example.gtrainer.model.PopularPojo;

import java.util.List;

public class PopularAdapter  extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private List<PopularPojo> data;
    static Context context;

    public PopularAdapter(List<PopularPojo> data , Context context) {
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
        ;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , TrainerProfileActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }
}