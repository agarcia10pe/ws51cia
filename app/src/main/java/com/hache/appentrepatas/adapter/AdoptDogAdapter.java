package com.hache.appentrepatas.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.bean.Adopt;
import com.hache.appentrepatas.dto.PerroPartialDTO;

import java.util.ArrayList;

public class AdoptDogAdapter extends RecyclerView.Adapter<AdoptDogAdapter.AdoptDogViewHolder>{

    private AdoptDogAdapter.MiListenerClick miListenerClick;
    private ArrayList<PerroPartialDTO> listaPerroAdopcion;
    private Context context;

    public AdoptDogAdapter(AdoptDogAdapter.MiListenerClick miListenerClick, ArrayList<PerroPartialDTO> items, Context context) {
        this.miListenerClick = miListenerClick;
        this.listaPerroAdopcion = items;
        this.context = context;
    }

    public interface MiListenerClick {
        void clickItem(View itemView, int idPerro);
    }

    public class AdoptDogViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private ImageView imgFoto;

        public AdoptDogViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txt_adopt);
            imgFoto = (ImageView) itemView.findViewById(R.id.im_dog);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listaPerroAdopcion.size() > 0) {
                        int idPerro = listaPerroAdopcion.get(getAdapterPosition()).getIdPerro();
                        miListenerClick.clickItem(itemView, idPerro);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public AdoptDogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vistaItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adopt, parent, false);
        return new AdoptDogAdapter.AdoptDogViewHolder(vistaItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdoptDogViewHolder holder, int position) {
        String foto, nombre;
        foto = listaPerroAdopcion.get(position).getImgPerro();
        nombre = listaPerroAdopcion.get(position).getNombrePerro();

        holder.txtName.setText(nombre);
        Glide.with(context)
              .load(foto)
              .centerCrop()
              .into(holder.imgFoto);
    }

    @Override
    public int getItemCount() {
        return listaPerroAdopcion.size();
    }
    public void setItems(ArrayList<PerroPartialDTO> items) {
        this.listaPerroAdopcion = items;
    }
}
