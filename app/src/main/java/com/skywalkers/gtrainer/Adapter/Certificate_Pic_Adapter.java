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
import com.skywalkers.gtrainer.model.CertificatePhotoPojo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Certificate_Pic_Adapter extends RecyclerView.Adapter<Certificate_Pic_Adapter.CertiPicViewHolder> {

    UrlLink urls = new UrlLink();
    private final String url = urls.url;
    List<CertificatePhotoPojo> data ;
    Context context;

    public Certificate_Pic_Adapter(List<CertificatePhotoPojo> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public Certificate_Pic_Adapter.CertiPicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.demo_certi_pic,parent,false);
        return new CertiPicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Certificate_Pic_Adapter.CertiPicViewHolder holder, int position) {
        CertificatePhotoPojo info = data.get(position);
        String[] arrOfStr = info.getPic().split("uploads");


        String photourl = url + info.getPic();


        Picasso.get().load(photourl).into(holder.mCertiPic);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CertiPicViewHolder extends RecyclerView.ViewHolder{

        ImageView mCertiPic;

        public CertiPicViewHolder(@NonNull View itemView) {
            super(itemView);

            mCertiPic = itemView.findViewById(R.id.profile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                }
            });
        }
    }
}
