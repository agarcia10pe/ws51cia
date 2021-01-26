package com.hache.appentrepatas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hache.appentrepatas.R;
import com.hache.appentrepatas.bean.Adopt;

import java.util.ArrayList;

public class ManagementAdapter extends RecyclerView.Adapter<ManagementAdapter.ManagementViewHolder> {

    private ManagementAdapter.MiListenerClick miListenerClick;
    private ArrayList<Adopt> adopts;
    private Adopt[] item = null;

    public ManagementAdapter(ManagementAdapter.MiListenerClick miListenerClick, ArrayList<Adopt> item) {
        this.miListenerClick = miListenerClick;
        this.adopts = item;
    }

    public interface MiListenerClick {
        void clickItem(View itemView, int posicion);
    }

    public class ManagementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtName;
        private ImageView imgFoto;

        public ManagementViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                default:
                    break;
            }
        }
    }

    @NonNull
    @Override
    public ManagementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vistaItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_management, parent, false);
        return new ManagementViewHolder(vistaItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagementViewHolder holder, int position) {
        String foto, nombre;
        foto = adopts.get(position).getFoto();
        nombre = adopts.get(position).getNombre();

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
