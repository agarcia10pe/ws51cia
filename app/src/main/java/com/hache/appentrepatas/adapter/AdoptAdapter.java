package com.hache.appentrepatas.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hache.appentrepatas.R;
import com.hache.appentrepatas.bean.Adopt;

import java.util.ArrayList;

public class AdoptAdapter extends RecyclerView.Adapter<AdoptAdapter.AdoptViewHolder> {

    private AdoptAdapter.MiListenerClick miListenerClick;
    private ArrayList<Adopt> adopts;
    private  Adopt[] item = null;

    public AdoptAdapter(AdoptAdapter.MiListenerClick miListenerClick, ArrayList<Adopt> item) {
        this.miListenerClick = miListenerClick;
        this.adopts = item;
    }

    public interface MiListenerClick {
        void clickItem(View itemView, int posicion);
    }

    public class AdoptViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtName;
        private ImageView imgFoto;

        public AdoptViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_adopt);
            imgFoto = (ImageView) itemView.findViewById(R.id.im_dog);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                default:
                    miListenerClick.clickItem(itemView,getAdapterPosition());
                    break;
            }
        }
    }

    @NonNull
    @Override
    public AdoptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vistaItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adopt, parent, false);
        return new AdoptViewHolder(vistaItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdoptViewHolder holder, int position) {
        String foto, nombre;
        foto = adopts.get(position).getFoto();
        nombre = adopts.get(position).getNombre();

        holder.txtName.setText(nombre);
        //holder.imgFoto.setImageDrawable(R.drawable.ic_foto);
    }

    @Override
    public int getItemCount() {
        return adopts.size();
    }

    public ArrayList<Adopt> getAdopt() {
        return adopts;
    }

    public void setAdopt(ArrayList<Adopt> adopts) {
        this.adopts = adopts;
    }
}
