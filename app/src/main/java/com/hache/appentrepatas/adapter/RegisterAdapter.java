package com.hache.appentrepatas.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hache.appentrepatas.R;
import com.hache.appentrepatas.util.CenterZoomLayoutManager;

import java.io.Console;
import java.util.ArrayList;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.RegisterViewHolder>{
    private Context context;
    private RegisterAdapter.MiListenerClick miListenerClick;
    private ArrayList<Uri> items;

    public RegisterAdapter(Context context, RegisterAdapter.MiListenerClick miListenerClick, ArrayList<Uri> items) {
        this.context = context;
        this.miListenerClick = miListenerClick;
        this.items = items;
    }

    public interface MiListenerClick {
        void clickItem(View itemView, int posicion);
        boolean openGalery();
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
                    miListenerClick.openGalery();
                    break;
                default:
                    break;
            }
        }

    }

    @NonNull
    @Override
    public RegisterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_register, parent, false);
        //context = parent.getContext();
        return new RegisterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterViewHolder holder, int position) {
        Uri foto = items.get(position);
        holder.imgFoto.setImageURI(foto);
        //holder.imgFoto.setImageDrawable(context.getDrawable(R.drawable.ic_add));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<Uri> getItems() {
        return items;
    }

    public void setItems(ArrayList<Uri> items) {
        this.items = items;
    }
}
