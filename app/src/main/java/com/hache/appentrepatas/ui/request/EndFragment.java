package com.hache.appentrepatas.ui.request;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hache.appentrepatas.BaseFragment;
import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.dto.BaseResponse;
import com.hache.appentrepatas.dto.SolicitudDTO;
import com.hache.appentrepatas.dto.TerminoSolicitudRequest;
import com.hache.appentrepatas.http.SolicitudClient;
import com.hache.appentrepatas.http.SolicitudService;
import com.hache.appentrepatas.util.Constants;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndFragment extends Fragment implements View.OnClickListener, BaseFragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    FirebaseStorage storage;
    StorageReference storageReference;
    SolicitudService solicitudService;
    AlertDialog dialog;
    Button button;
    Button btnFoto;
    ImageView imgPhoto;
    int idSolicitud;
    boolean existeFoto = false;

    public EndFragment() {
        // Required empty public constructor
    }
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.titulo_finalizar_solicitud));
    }
    public boolean onBackPressed() {
        ((MainActivity)getActivity()).setFragment(6, null, false);
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_end, container, false);
        idSolicitud = getArguments().getInt(Constants.ID_SOLICITUD);
        initView(view);
        initStorage();
        initRetrofit();
        createDialog();
        return view;
    }

    private View initView(View view) {
        imgPhoto = view.findViewById(R.id.im_photo);
        button = (Button) view.findViewById(R.id.btn_request_end);
        btnFoto = view.findViewById(R.id.btn_request_photo);
        button.setOnClickListener(this);
        btnFoto.setOnClickListener(this);
        return view;
    }

    private void initStorage() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    private void initRetrofit() {
        solicitudService = SolicitudClient.getInstance().getSolicitudService();
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.titulo_confirmacion)
                .setMessage(R.string.mensaje_terminar_solicitud)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_Aceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cargarImagen();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_photo:
                checkPermissions();
                break;
            case R.id.btn_request_end:

                if (existeFoto) {
                    dialog.show();
                    return;
                }

                Toast.makeText(getContext(),R.string.mensaje_error_carga_foto, Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
    }

    private void checkPermissions() {
        PermissionListener dialogOnDeniedPermissionListener =
                DialogOnDeniedPermissionListener.Builder.withContext(getActivity())
                        .withTitle("Permisos")
                        .withMessage("Los permisos son necesarios para tomar la foto de termino de solicitud")
                        .withButtonText("Aceptar")
                        .withIcon(R.mipmap.ic_launcher)
                        .build();

        Dexter.withContext(getActivity())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        tomarFoto();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        dialogOnDeniedPermissionListener.onPermissionDenied(permissionDeniedResponse);
                        Toast.makeText(getContext(), "No se puede seleccionar la foto del perro.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(getContext(), "Error occurred! "+ error.toString() , Toast.LENGTH_SHORT).show();
            }})
                .check();
    }

    private void tomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bmpFoto = (Bitmap) extras.get("data");
            imgPhoto.setImageBitmap(bmpFoto);
            existeFoto = true;
        }
    }

    private void cargarImagen() {
        ((MainActivity) getActivity()).showLoading(getString(R.string.mensaje_procesando));
        StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString()+ ".jpge");
        Bitmap bitmap = ((BitmapDrawable) imgPhoto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        ref.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                terminarSolicitud(task.getResult().toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ((MainActivity) getActivity()).closeLoading();
                        Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void terminarSolicitud(String urlImagen) {
        TerminoSolicitudRequest request = new TerminoSolicitudRequest();
        request.setIdSolicitud(idSolicitud);
        request.setImgSolicitud(urlImagen);

        Call<BaseResponse<String>> call = solicitudService.terminarSolicitud(request);
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                ((MainActivity) getActivity()).closeLoading();
                if (!response.isSuccessful()) return;

                if(response.body().getCodigo() == 1) {
                    Toast.makeText(getContext(), R.string.mensaje_solicitud_terminada, Toast.LENGTH_SHORT).show();
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