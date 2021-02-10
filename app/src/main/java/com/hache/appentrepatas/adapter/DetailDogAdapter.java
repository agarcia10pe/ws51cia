package com.hache.appentrepatas.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hache.appentrepatas.R;

import java.util.ArrayList;

public class DetailDogAdapter extends RecyclerView.Adapter<DetailDogAdapter.RegisterViewHolder>{
    private Context context;
    private ArrayList<String> items;

    public DetailDogAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public DetailDogAdapter.RegisterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register, parent, false);
        return new DetailDogAdapter.RegisterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailDogAdapter.RegisterViewHolder holder, int position) {
        String url = items.get(position);
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_foto)
                .centerCrop()
                .into(holder.imgFoto);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public class RegisterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imgFoto;

        public RegisterViewHolder(View itemView) {
            super(itemView);
            imgFoto = (ImageView) itemView.findViewById(R.id.im_dog);
            imgFoto.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.im_dog:
                    System.out.println("Click 1");
                    break;
                default:
                    break;
            }
        }
    }

}
