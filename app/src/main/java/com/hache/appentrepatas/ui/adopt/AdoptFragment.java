package com.hache.appentrepatas.ui.adopt;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.adapter.AdoptAdapter;
import com.hache.appentrepatas.adapter.AdoptDogAdapter;
import com.hache.appentrepatas.bean.Adopt;
import com.hache.appentrepatas.dto.BaseResponse;
import com.hache.appentrepatas.dto.PerroPartialDTO;
import com.hache.appentrepatas.dto.UsuarioDTO;
import com.hache.appentrepatas.http.SolicitudClient;
import com.hache.appentrepatas.http.SolicitudService;
import com.hache.appentrepatas.ui.home.HomeFragment;
import com.hache.appentrepatas.ui.request.ConfirmFragment;
import com.hache.appentrepatas.ui.request.DetailFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdoptFragment extends Fragment implements View.OnClickListener {

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private RecyclerView recyclerView ;
    private AdoptDogAdapter adoptDogAdapter;
    SolicitudService solicitudService;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        solicitudService = SolicitudClient.getInstance().getSolicitudService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adopt, container, false);
        initView(root);
        return  root;
    }

    private void initView(View root) {
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.co_blue));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                listarPerros(true);
            }
        });

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_adopts);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listarPerros(false);
    }

    private void listarPerros(boolean swiped) {
        if (!swiped)
            ((MainActivity) getActivity()).showLoading(null);

        Call<BaseResponse<ArrayList<PerroPartialDTO>>> call = solicitudService.listarPerro();
        call.enqueue(new Callback<BaseResponse<ArrayList<PerroPartialDTO>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<PerroPartialDTO>>> call, Response<BaseResponse<ArrayList<PerroPartialDTO>>> response) {
                if (swiped) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    ((MainActivity) getActivity()).closeLoading();
                }

                if (!response.isSuccessful()) return;

                adoptDogAdapter = new AdoptDogAdapter(new OnSelectClick(), response.body().getData(), getContext());
                recyclerView.setAdapter(adoptDogAdapter);
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<PerroPartialDTO>>> call, Throwable t) {
                if (swiped) {
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    ((MainActivity) getActivity()).closeLoading();
                }

                Toast.makeText(getContext(), R.string.mensaje_error_conexion, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.menu_adopt));
    }

    private class OnSelectClick implements AdoptDogAdapter.MiListenerClick{

        @Override
        public void clickItem(View itemView, int idPerro) {
            Bundle bundle = new Bundle();
            adoptDogAdapter.setItems(new ArrayList<>());
            bundle.putString("idPerro", String.valueOf(idPerro));
            ((MainActivity) getActivity()).setFragment(1, bundle, true);
        }
    }

    @Override
    public void onClick(View v) {
    }

}