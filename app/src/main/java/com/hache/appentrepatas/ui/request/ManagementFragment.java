package com.hache.appentrepatas.ui.request;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hache.appentrepatas.R;
import com.hache.appentrepatas.adapter.AdoptAdapter;
import com.hache.appentrepatas.adapter.ManagementAdapter;
import com.hache.appentrepatas.bean.Adopt;
import com.hache.appentrepatas.dto.AprobacionSolicitudRequest;
import com.hache.appentrepatas.dto.BaseResponse;
import com.hache.appentrepatas.dto.SolicitudDTO;
import com.hache.appentrepatas.dto.SolicitudPartialDTO;
import com.hache.appentrepatas.http.SolicitudClient;
import com.hache.appentrepatas.http.SolicitudService;
import com.hache.appentrepatas.ui.adopt.AdoptFragment;
import com.hache.appentrepatas.util.SeguridadUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ManagementFragment extends Fragment  implements View.OnClickListener {
    private SolicitudService solicitudService;
    private RecyclerView recyclerView ;
    private ArrayList<Adopt> adopts;
    private ManagementAdapter managementAdapter;

    public ManagementFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ManagementFragment newInstance(String param1, String param2) {
        ManagementFragment fragment = new ManagementFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        solicitudService = SolicitudClient.getInstance().getSolicitudService();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_management, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_management);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listarSolicitud();
    }

    private void listarSolicitud() {
        Call<BaseResponse<ArrayList<SolicitudDTO>>> call = solicitudService.listarSolicitudes();
        call.enqueue(new Callback<BaseResponse<ArrayList<SolicitudDTO>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<SolicitudDTO>>> call, Response<BaseResponse<ArrayList<SolicitudDTO>>> response) {
                if (!response.isSuccessful()) return;

                managementAdapter = new ManagementAdapter(new OnSelectClick(), response.body().getData(), getContext());
                recyclerView.setAdapter(managementAdapter);
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<SolicitudDTO>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
        }
    }

    private class OnSelectClick implements ManagementAdapter.MiListenerClick{

        @Override
        public void aprobarSolicitud(View itemView, int idSolicitud) {
            AprobacionSolicitudRequest request = new AprobacionSolicitudRequest();
            request.setIdSolicitud(idSolicitud);
            request.setCorreo2(SeguridadUtil.getUsuario().getCorreo());

            Call<BaseResponse<String>> call = solicitudService.aprobarSolicitud(request);
            call.enqueue(new Callback<BaseResponse<String>>() {
                @Override
                public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                    if (!response.isSuccessful()) return;
                    if (response.body().getCodigo() == 1)
                        listarSolicitud();
                }

                @Override
                public void onFailure(Call<BaseResponse<String>> call, Throwable t) {

                }
            });
        }

        @Override
        public void rechazarSolicitud(View itemView, int idSolicitud) {
            AprobacionSolicitudRequest request = new AprobacionSolicitudRequest();
            request.setIdSolicitud(idSolicitud);
            request.setCorreo2(SeguridadUtil.getUsuario().getCorreo());

            Call<BaseResponse<String>> call = solicitudService.rechazarSolicitud(request);
            call.enqueue(new Callback<BaseResponse<String>>() {
                @Override
                public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                    if (!response.isSuccessful()) return;
                    if (response.body().getCodigo() == 1)
                        listarSolicitud();
                }

                @Override
                public void onFailure(Call<BaseResponse<String>> call, Throwable t) {

                }
            });
        }
    }
}