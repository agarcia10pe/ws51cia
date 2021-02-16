package com.hache.appentrepatas.ui.request;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.adapter.ManagementAdapter;
import com.hache.appentrepatas.bean.Adopt;
import com.hache.appentrepatas.dto.AprobacionSolicitudRequest;
import com.hache.appentrepatas.dto.BaseResponse;
import com.hache.appentrepatas.dto.SolicitudDTO;
import com.hache.appentrepatas.http.SolicitudClient;
import com.hache.appentrepatas.http.SolicitudService;
import com.hache.appentrepatas.util.SeguridadUtil;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ManagementFragment extends Fragment  implements View.OnClickListener {
    private SolicitudService solicitudService;
    private RecyclerView recyclerView ;
    private ArrayList<Adopt> adopts;
    private ManagementAdapter managementAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

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
        initView(view);
        return view;
    }

    private void initView(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.co_blue));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                listarSolicitud(true);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_management);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listarSolicitud(false);
    }

    private void listarSolicitud(boolean swiped) {
        if (!swiped)
            ((MainActivity) getActivity()).showLoading(null);

        Call<BaseResponse<ArrayList<SolicitudDTO>>> call = solicitudService.listarSolicitudes();
        call.enqueue(new Callback<BaseResponse<ArrayList<SolicitudDTO>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<SolicitudDTO>>> call, Response<BaseResponse<ArrayList<SolicitudDTO>>> response) {
                if (swiped) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    ((MainActivity) getActivity()).closeLoading();
                }

                if (!response.isSuccessful()) return;

                managementAdapter = new ManagementAdapter(new OnSelectClick(), response.body().getData(), getContext());
                recyclerView.setAdapter(managementAdapter);
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<SolicitudDTO>>> call, Throwable t) {
                if (swiped) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    ((MainActivity) getActivity()).closeLoading();
                }

                Toast.makeText(getContext(), R.string.mensaje_error_conexion, Toast.LENGTH_SHORT).show();
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
        public void aprobar(View itemView, int idSolicitud) {
            AlertDialog dialog = createDialog(R.string.mensaje_aprobar_solicitud, new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    aprobarSolicitud(idSolicitud);
                    return null;
                }
            });
            dialog.show();
        }

        @Override
        public void rechazar(View itemView, int idSolicitud) {
            AlertDialog dialog = createDialog(R.string.mensaje_rechazar_solicitud, new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    rechazarSolicitud(idSolicitud);
                    return null;
                }
            });
            dialog.show();
        }
    }

    private AlertDialog createDialog(int idResource, Callable fun) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.titulo_confirmacion)
                .setMessage(idResource)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_Aceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            fun.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.btn_Cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

    private void aprobarSolicitud(int idSolicitud) {
        ((MainActivity) getActivity()).showLoading(getString(R.string.mensaje_procesando));
        AprobacionSolicitudRequest request = new AprobacionSolicitudRequest();
        request.setIdSolicitud(idSolicitud);
        request.setCorreo2(SeguridadUtil.getUsuario().getCorreo());

        Call<BaseResponse<String>> call = solicitudService.aprobarSolicitud(request);
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                ((MainActivity) getActivity()).closeLoading();
                if (!response.isSuccessful()) return;
                if (response.body().getCodigo() == 1) {
                    Toast.makeText(getContext(), R.string.mensaje_solicitud_aprobada, Toast.LENGTH_SHORT).show();
                    listarSolicitud(false);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                ((MainActivity) getActivity()).closeLoading();
                Toast.makeText(getContext(), R.string.mensaje_error_conexion, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rechazarSolicitud(int idSolicitud) {
        ((MainActivity) getActivity()).showLoading(getString(R.string.mensaje_procesando));
        AprobacionSolicitudRequest request = new AprobacionSolicitudRequest();
        request.setIdSolicitud(idSolicitud);
        request.setCorreo2(SeguridadUtil.getUsuario().getCorreo());

        Call<BaseResponse<String>> call = solicitudService.rechazarSolicitud(request);
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                ((MainActivity) getActivity()).closeLoading();
                if (!response.isSuccessful()) return;
                if (response.body().getCodigo() == 1) {
                    Toast.makeText(getContext(), R.string.mensaje_solicitud_rechazada, Toast.LENGTH_SHORT).show();
                    listarSolicitud(false);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                ((MainActivity) getActivity()).closeLoading();
                Toast.makeText(getContext(), R.string.mensaje_error_conexion, Toast.LENGTH_SHORT).show();
            }
        });
    }
}