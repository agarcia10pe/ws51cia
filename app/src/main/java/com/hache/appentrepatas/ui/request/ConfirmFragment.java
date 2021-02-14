package com.hache.appentrepatas.ui.request;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hache.appentrepatas.BaseFragment;
import com.hache.appentrepatas.HomeActivity;
import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.dto.BaseResponse;
import com.hache.appentrepatas.dto.ConfirmacionSolicitudRequest;
import com.hache.appentrepatas.http.SolicitudClient;
import com.hache.appentrepatas.http.SolicitudService;
import com.hache.appentrepatas.ui.home.HomeFragment;
import com.hache.appentrepatas.util.Constants;
import com.hache.appentrepatas.util.SeguridadUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmFragment extends Fragment implements View.OnClickListener, BaseFragment {

    Button requestBtn;
    AlertDialog dialog;
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private int idSolicitud;
    private SolicitudService solicitudService;

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
        idSolicitud = Integer.parseInt(getArguments().getString(Constants.ID_SOLICITUD));
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        initView(view);
        createDialog();
        return view;
    }

    private void initView(View view) {
        requestBtn = (Button) view.findViewById(R.id.btn_confirm_request);
        requestBtn.setOnClickListener(this);
    }

    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Solicitud de adopción");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Call<BaseResponse<String>> call = solicitudService.eliminarSolicitudInicializada(SeguridadUtil.getUsuario().getCorreo());
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {

            }
        });
    }

    public boolean onBackPressed() {
        ((MainActivity)getActivity()).setFragment(6, null, false);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm_request:
                dialog.show();
                break;
            default:
                break;
        }
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.titulo_confirmacion)
                .setMessage(R.string.mensaje_confirmar_solicitud)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_Aceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        confirmarSolicitudAdopcion();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.btn_Cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        dialog = builder.create();
    }


    private void confirmarSolicitudAdopcion() {
        ((MainActivity) getActivity()).showLoading(getString(R.string.mensaje_procesando));
        ConfirmacionSolicitudRequest request = new ConfirmacionSolicitudRequest();
        request.setIdSolicitud(idSolicitud);
        Call<BaseResponse<String>> call = solicitudService.confirmarSolicitud(request);
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                ((MainActivity) getActivity()).closeLoading();
                if (!response.isSuccessful()) return;

                if (response.body().getCodigo() == 1) {
                    ((MainActivity) getActivity()).setFragment(3, null, true);
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