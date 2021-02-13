package com.hache.appentrepatas.ui.request;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hache.appentrepatas.BaseFragment;
import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.adapter.AdoptAdapter;
import com.hache.appentrepatas.adapter.DetailDogAdapter;
import com.hache.appentrepatas.adapter.RegisterAdapter;
import com.hache.appentrepatas.dto.BaseResponse;
import com.hache.appentrepatas.dto.PerroDTO;
import com.hache.appentrepatas.dto.PerroPartialDTO;
import com.hache.appentrepatas.dto.SolicitarAdopcionRequest;
import com.hache.appentrepatas.helper.SharedPreferencesManager;
import com.hache.appentrepatas.http.SolicitudClient;
import com.hache.appentrepatas.http.SolicitudService;
import com.hache.appentrepatas.ui.register.RegisterFragment;
import com.hache.appentrepatas.util.CenterZoomLayoutManager;
import com.hache.appentrepatas.util.SeguridadUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends Fragment implements  View.OnClickListener , BaseFragment {

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private final int RESULT_OK = -1;
    private final int PICKER = 1;

    private static final String TAG = "DetailFragment";

    private RecyclerView recyclerView;
    private ArrayList<String> items;
    private DetailDogAdapter detailDogAdapter;

    private EditText txtSexoPerro;
    private EditText txtPesoPerro;
    private EditText txtEdadPerro;
    private Button requestBtn;

    private SolicitudService solicitudService;
    private int idPerro;
    private PerroDTO perroDTO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        solicitudService = SolicitudClient.getInstance().getSolicitudService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        idPerro = Integer.parseInt(getArguments().getString("idPerro"));
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_detail);
        requestBtn = (Button) view.findViewById(R.id.btn_detail_request);
        txtSexoPerro = view.findViewById(R.id.txt_sexo);
        txtEdadPerro = view.findViewById(R.id.txt_edad);
        txtPesoPerro = view.findViewById(R.id.txt_peso);

        CenterZoomLayoutManager layoutManager = new CenterZoomLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        requestBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Call<BaseResponse<PerroDTO>> call = solicitudService.obtenerPerroPorId(idPerro);
        call.enqueue(new Callback<BaseResponse<PerroDTO>>() {
            @Override
            public void onResponse(Call<BaseResponse<PerroDTO>> call, Response<BaseResponse<PerroDTO>> response) {
                if (response.isSuccessful()) {
                    perroDTO = response.body().getData();
                    txtEdadPerro.setText(perroDTO.getEdad());
                    txtPesoPerro.setText(perroDTO.getPeso());
                    txtSexoPerro.setText(perroDTO.getSexo());

                    ((MainActivity) getActivity()).setActionBarTitle(perroDTO.getNombre());

                    items = new ArrayList<String>();
                    items.add(perroDTO.getImagen());
                    items.add(perroDTO.getImagen());
                    items.add(perroDTO.getImagen());

                    detailDogAdapter = new DetailDogAdapter(getContext(), items);
                    recyclerView.setAdapter(detailDogAdapter);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PerroDTO>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_detail_request:
                iniciarSolicitudAdopcion();
                break;
            default:
                break;
        }
    }

    private void iniciarSolicitudAdopcion() {
        SolicitarAdopcionRequest request = new SolicitarAdopcionRequest();
        request.setIdPerro((short) idPerro);
        request.setCorreo(SeguridadUtil.getUsuario().getCorreo());
        Call<BaseResponse<String>> call = solicitudService.solicitarAdopcion(request);
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if (!response.isSuccessful()) return;
                if (response.body().getCodigo() == 0) {
                    Toast.makeText(getContext(), response.body().getData(), Toast.LENGTH_LONG).show();
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("idSolicitud", response.body().getData());
                ((MainActivity) getActivity()).setFragment(2,  bundle, true);
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    public void onResume(){
        super.onResume();

        if (perroDTO != null) {
            ((MainActivity) getActivity()).setActionBarTitle(perroDTO.getNombre());
        }
    }

    public boolean onBackPressed() {
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.menu_adopt));
        ((MainActivity)getActivity()).setFragment(6, null, false);
        return false;
    }

    private class OnSelectClick implements RegisterAdapter.MiListenerClick{

        @Override
        public void clickItem(View itemView, int posicion) {
            System.out.println("Click");

            //onImprimir(articulos.get(posicion).getProducto());
        }

        @Override
        public boolean openGalery() {
           return true;
        }
    }
}