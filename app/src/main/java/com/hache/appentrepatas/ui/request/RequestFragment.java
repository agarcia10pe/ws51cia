package com.hache.appentrepatas.ui.request;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import com.hache.appentrepatas.adapter.AdoptDogAdapter;
import com.hache.appentrepatas.adapter.ManagementAdapter;
import com.hache.appentrepatas.adapter.RequestAdapter;
import com.hache.appentrepatas.bean.Adopt;
import com.hache.appentrepatas.dto.BaseResponse;
import com.hache.appentrepatas.dto.PerroPartialDTO;
import com.hache.appentrepatas.dto.SolicitudPartialDTO;
import com.hache.appentrepatas.http.SolicitudClient;
import com.hache.appentrepatas.http.SolicitudService;
import com.hache.appentrepatas.ui.adopt.AdoptFragment;
import com.hache.appentrepatas.util.Constants;
import com.hache.appentrepatas.util.SeguridadUtil;

import java.security.PublicKey;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestFragment extends Fragment implements  View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView recyclerView ;
    private ArrayList<Adopt> adopts;
    private RequestAdapter requestAdapter;
    private SolicitudService solicitudService;
    SwipeRefreshLayout swipeRefreshLayout;

    public RequestFragment() {
        // Required empty public constructor
    }

    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Mis solicitudes");
    }

    // TODO: Rename and change types and number of parameters
    public static RequestFragment newInstance(String param1, String param2) {
        RequestFragment fragment = new RequestFragment();
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
        View view = inflater.inflate(R.layout.fragment_request, container, false);
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
                listarSolicitudes(true);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_request);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listarSolicitudes(false);
    }

    private void listarSolicitudes(boolean swiped) {
        if (!swiped)
            ((MainActivity) getActivity()).showLoading(null);

        Call<BaseResponse<ArrayList<SolicitudPartialDTO>>> call = solicitudService.listarSolicitudesPorCorreo(SeguridadUtil.getUsuario().getCorreo());
        call.enqueue(new Callback<BaseResponse<ArrayList<SolicitudPartialDTO>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<SolicitudPartialDTO>>> call, Response<BaseResponse<ArrayList<SolicitudPartialDTO>>> response) {
                if (swiped) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    ((MainActivity) getActivity()).closeLoading();
                }

                if (!response.isSuccessful()) return;

                requestAdapter = new RequestAdapter(new OnSelectClick(), response.body().getData(), getContext());
                recyclerView.setAdapter(requestAdapter);
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<SolicitudPartialDTO>>> call, Throwable t) {
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

    private class OnSelectClick implements RequestAdapter.MiListenerClick{

        @Override
        public void clickItem(View itemView, int idSolicitud) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.ID_SOLICITUD, idSolicitud);
            ((MainActivity) getActivity()).setFragment(4, bundle, true);
        }
    }
}