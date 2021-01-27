package com.hache.appentrepatas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.bean.Adopt;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
    private RequestAdapter.MiListenerClick miListenerClick;
    private ArrayList<Adopt> adopts;
    private Adopt[] item = null;

    public RequestAdapter(RequestAdapter.MiListenerClick miListenerClick, ArrayList<Adopt> item) {
        this.miListenerClick = miListenerClick;
        this.adopts = item;
    }

    public interface MiListenerClick {
        void clickItem(View itemView, int posicion);
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtName;
        private ImageView imgFoto;
        private Button button;

        public RequestViewHolder(View itemView) {
            super(itemView);
            button = (Button)itemView.findViewById(R.id.btn_request_finalize);
            button.setOnClickListener(this);
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
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vistaItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new RequestViewHolder(vistaItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
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
