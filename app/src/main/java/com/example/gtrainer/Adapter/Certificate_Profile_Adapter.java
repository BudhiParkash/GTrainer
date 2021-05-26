package com.example.gtrainer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gtrainer.Api.UrlLink;
import com.example.gtrainer.R;
import com.example.gtrainer.model.CertificatePhotoPojo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Certificate_Profile_Adapter extends RecyclerView.Adapter<Certificate_Profile_Adapter.CertiPicProfoleViewHolder> {

    UrlLink urls = new UrlLink();
    private final String url = urls.url;
    List<CertificatePhotoPojo> data ;
    Context context;

    public Certificate_Profile_Adapter(List<CertificatePhotoPojo> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public CertiPicProfoleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.profile_certificate_view,parent,false);
        return new CertiPicProfoleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CertiPicProfoleViewHolder holder, int position) {
        CertificatePhotoPojo info = data.get(position);
        String[] arrOfStr = info.getPic().split("uploads");


        String photourl = url + info.getPic();


        Picasso.get().load(photourl).into(holder.mCertiPic);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CertiPicProfoleViewHolder extends RecyclerView.ViewHolder{

        ImageView mCertiPic;

        public CertiPicProfoleViewHolder(@NonNull View itemView) {
            super(itemView);

            mCertiPic = itemView.findViewById(R.id.profile_certificate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "oooooooo", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
