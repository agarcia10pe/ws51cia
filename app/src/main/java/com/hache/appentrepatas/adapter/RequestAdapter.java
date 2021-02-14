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
import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.bean.Adopt;
import com.hache.appentrepatas.dto.SolicitudPartialDTO;
import com.hache.appentrepatas.util.EnumSolicitud;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
    private RequestAdapter.MiListenerClick miListenerClick;
    private Context context;
    private ArrayList<SolicitudPartialDTO> listaSolicitud;
    private Adopt[] item = null;

    public RequestAdapter(RequestAdapter.MiListenerClick miListenerClick, ArrayList<SolicitudPartialDTO> listaSolicitud, Context context) {
        this.miListenerClick = miListenerClick;
        this.listaSolicitud = listaSolicitud;
        this.context = context;
    }

    public interface MiListenerClick {
        void clickItem(View itemView, int posicion);
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vistaItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new RequestViewHolder(vistaItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {

        SolicitudPartialDTO solicitudPartialDTO = listaSolicitud.get(position);
        holder.txtName.setText(solicitudPartialDTO.getNombrePerro());
        holder.txtState.setText(solicitudPartialDTO.getDescripcionEstado());
        holder.txtSolicitud.setText(String.valueOf(solicitudPartialDTO.getIdSolicitud()));

        if (solicitudPartialDTO.getIdEstSolicitud() != EnumSolicitud.EstadoSolicitud.APROBADO.getCode())
            holder.button.setVisibility(View.INVISIBLE);

        Glide.with(context)
                .load(listaSolicitud.get(position).getImgPerro())
                .centerCrop()
                .into(holder.imgFoto);
    }

    @Override
    public int getItemCount() {
        return listaSolicitud.size();
    }

    public ArrayList<SolicitudPartialDTO> getAdopt() {
        return listaSolicitud;
    }

    public void setAdopt(ArrayList<SolicitudPartialDTO> listaSolicitud) {
        this.listaSolicitud = listaSolicitud;
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtName;
        private TextView txtState;
        private TextView txtSolicitud;
        private ImageView imgFoto;
        private Button button;

        public RequestViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.btn_request_finalize);
            txtName = itemView.findViewById(R.id.txt_NombrePerro);
            txtSolicitud = itemView.findViewById(R.id.txt_nroSolicitud);
            imgFoto = itemView.findViewById(R.id.im_request);
            txtState = itemView.findViewById(R.id.txt_state);

            button.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                default:
                    SolicitudPartialDTO solicitudPartialDTO = listaSolicitud.get(getAdapterPosition());
                    if (solicitudPartialDTO.getIdEstSolicitud() == EnumSolicitud.EstadoSolicitud.APROBADO.getCode())
                        miListenerClick.clickItem(itemView, solicitudPartialDTO.getIdSolicitud());
                    break;
            }
        }
    }
}
