package com.hache.appentrepatas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.bean.Adopt;
import com.hache.appentrepatas.dto.SolicitudDTO;
import com.hache.appentrepatas.util.EnumSolicitud;

import java.util.ArrayList;

public class ManagementAdapter extends RecyclerView.Adapter<ManagementAdapter.ManagementViewHolder> {
    private Context context;
    private ManagementAdapter.MiListenerClick miListenerClick;
    private ArrayList<SolicitudDTO> listaSolicitud;
    private Adopt[] item = null;

    public ManagementAdapter(ManagementAdapter.MiListenerClick miListenerClick, ArrayList<SolicitudDTO> listaSolicitud, Context context) {
        this.miListenerClick = miListenerClick;
        this.listaSolicitud = listaSolicitud;
        this.context = context;
    }

    public interface MiListenerClick {
        void aprobar(View itemView, int posicion);
        void rechazar(View itemView, int posicion);
    }

    @NonNull
    @Override
    public ManagementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vistaItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_management, parent, false);
        return new ManagementViewHolder(vistaItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagementViewHolder holder, int position) {
        SolicitudDTO solicitudDTO = listaSolicitud.get(position);
        holder.txtName.setText(solicitudDTO.getNombrePerro());
        holder.txtSolicitud.setText(String.valueOf(listaSolicitud.get(position).getIdSolicitud()));
        holder.txtNombreCliente.setText(solicitudDTO.getNombre1() + " " + solicitudDTO.getApePaterno());

        if (solicitudDTO.getIdEstSolicitud() != EnumSolicitud.EstadoSolicitud.PENDIENTE.getCode()) {
            holder.btnAprobar.setVisibility(View.INVISIBLE);
            holder.btnRechazar.setVisibility(View.INVISIBLE);
        }

        Glide.with(context)
                .load(listaSolicitud.get(position).getImgPerro())
                .centerCrop()
                .into(holder.imgFoto);

    }

    @Override
    public int getItemCount() {
        return listaSolicitud.size();
    }

    public ArrayList<SolicitudDTO> getAdopt() {
        return listaSolicitud;
    }

    public void setAdopt(ArrayList<SolicitudDTO> listaSolicitud) {
        this.listaSolicitud = listaSolicitud;
    }

    public class ManagementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtName;
        private TextView txtNombreCliente;
        private TextView txtSolicitud;
        private ImageView imgFoto;
        private Button btnAprobar;
        private Button btnRechazar;

        public ManagementViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_NombrePerro);
            txtNombreCliente = itemView.findViewById(R.id.txt_NombreCliente);
            txtSolicitud = itemView.findViewById(R.id.txt_nroSolicitud);
            imgFoto = itemView.findViewById(R.id.im_request);
            btnAprobar = itemView.findViewById(R.id.btn_mangament_request);
            btnRechazar = itemView.findViewById(R.id.btn_mangament_cancel);

            btnAprobar.setOnClickListener(this);
            btnRechazar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int idSolicitud = listaSolicitud.get(getAdapterPosition()).getIdSolicitud();
            switch (v.getId()){
                case R.id.btn_mangament_request:
                    miListenerClick.aprobar(itemView, idSolicitud);
                    break;
                case R.id.btn_mangament_cancel:
                    miListenerClick.rechazar(itemView, idSolicitud);
                    break;
                default:
                    break;
            }
        }
    }
}
