package com.hache.appentrepatas.ui.register;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.adapter.AdoptAdapter;
import com.hache.appentrepatas.adapter.RegisterAdapter;
import com.hache.appentrepatas.bean.Adopt;
import com.hache.appentrepatas.dto.BaseResponse;
import com.hache.appentrepatas.dto.PerroDTO;
import com.hache.appentrepatas.dto.UsuarioDTO;
import com.hache.appentrepatas.helper.MyApp;
import com.hache.appentrepatas.http.SolicitudClient;
import com.hache.appentrepatas.http.SolicitudService;
import com.hache.appentrepatas.ui.adopt.AdoptFragment;
import com.hache.appentrepatas.util.CenterZoomLayoutManager;
import com.hache.appentrepatas.util.UriUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.Console;
import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.xml.transform.Result;

import kotlin.contracts.Returns;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment implements  View.OnClickListener {

    Spinner gender_spinner;
    EditText nombreTxt, edadTxt, pesoTxt;
    Button registerBtn;
    String[] gender = {"Selecciona su sexo","Hembra","Macho"} ;
    Uri ruta;
    private final int RESULT_OK = -1;
    private final int PICKER = 1;
    private PermissionListener allPermissionListener;
    private RecyclerView recyclerView;
    private ArrayList<Uri> items;
    private RegisterAdapter registerAdapter;
    private SolicitudService solicitudService;
    private String mediaPath;
    private Gson gson;
    int numFoto = 0;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICKER) {
            //String[] medData = { MediaStore.Images.Media.DATA };
            items = new ArrayList<>();
            ruta = data.getData();
            items.add(ruta);
            numFoto = 1;

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            cursor.close();
            registerAdapter = new RegisterAdapter(getContext(), new OnSelectClick(), items);
            recyclerView.setAdapter(registerAdapter);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        solicitudService = SolicitudClient.getInstance().getSolicitudService();
        gson = new Gson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        nombreTxt = (EditText) view.findViewById(R.id.nombre_registerTxt);
        edadTxt = (EditText) view.findViewById(R.id.edad_registerTxt);
        pesoTxt = (EditText) view.findViewById(R.id.peso_registerTxt);
        registerBtn = (Button)view.findViewById(R.id.btn_register_dog);
        gender_spinner = (Spinner) view.findViewById(R.id.spnn_gender);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_register);
        CenterZoomLayoutManager layoutManager = new CenterZoomLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setHasFixedSize(true);
        registerBtn.setOnClickListener(this);

        init();

        return view;
    }

    private void init() {
        numFoto = 0;
        nombreTxt.setText("");
        edadTxt.setText("");
        pesoTxt.setText("");
        nombreTxt.setText("");
        nombreTxt.setText("");
        gender_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, gender));
        items = new ArrayList<Uri>();
        Uri uriImage = Uri.parse("android.resource://" +  getContext().getPackageName() +"/"+R.drawable.ic_add);
        items.add(uriImage);

        registerAdapter = new RegisterAdapter(getContext(), new OnSelectClick(), items);
        recyclerView.setAdapter(registerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register_dog:
                registrarPerro();
                break;
            default:
                break;
        }
    }

    void registrarPerro() {
        if (!valRegistro()) return;
        ((MainActivity) getActivity()).showLoading(getString(R.string.mensaje_procesando));
        PerroDTO perroDTO = new PerroDTO();
        perroDTO.setNombre(nombreTxt.getText().toString());
        perroDTO.setEdad(edadTxt.getText().toString());
        perroDTO.setPeso(pesoTxt.getText().toString());
        perroDTO.setSexo(gender[(int)gender_spinner.getSelectedItemId()]);

        File file = new File(UriUtils.getPathFromUri(getContext(), items.get(0)));

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody perro = RequestBody.create(MediaType.parse("application/json"), gson.toJson(perroDTO));
        Call<BaseResponse<String>> call = solicitudService.registrarPerro(filePart, perro);

        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                ((MainActivity) getActivity()).closeLoading();
                if (!response.isSuccessful()) return;
                if (response.body().getCodigo() == 0) return;

                Toast.makeText(getContext(), getString(R.string.msg_register_exito), Toast.LENGTH_LONG).show();
                init();
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                ((MainActivity) getActivity()).closeLoading();
                Toast.makeText(getContext(), R.string.mensaje_error_conexion, Toast.LENGTH_SHORT).show();
            }
        });
    }

    boolean valRegistro(){
        if(nombreTxt.getText().toString().trim().length() == 0){
            Toast.makeText(getContext(), getString(R.string.msg_register_nombre), Toast.LENGTH_LONG).show();
            return false;
        }
        if(gender_spinner.getSelectedItemId() == 0){
            Toast.makeText(getContext(), getString(R.string.msg_register_sexo), Toast.LENGTH_LONG).show();
            return false;
        }
        if(edadTxt.getText().toString().trim().length() == 0){
            Toast.makeText(getContext(), getString(R.string.msg_register_edad), Toast.LENGTH_LONG).show();
            return false;
        }
        if(Integer.parseInt(edadTxt.getText().toString().trim()) < 1 || Integer.parseInt(edadTxt.getText().toString().trim()) > 100) {
            Toast.makeText(getContext(), getString(R.string.msg_register_edad), Toast.LENGTH_LONG).show();
            return false;
        }

        if(pesoTxt.getText().toString().trim().length() == 0){
            Toast.makeText(getContext(), getString(R.string.msg_register_peso), Toast.LENGTH_LONG).show();
            return false;
        }

        if( numFoto == 0){
            Toast.makeText(getContext(), getString(R.string.msg_register_foto), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private class OnSelectClick implements RegisterAdapter.MiListenerClick{

        @Override
        public void clickItem(View itemView, int posicion) {
            System.out.println("Click");

            //onImprimir(articulos.get(posicion).getProducto());
        }

        @Override
        public boolean openGalery() {
            try{
                checkPermissions();
            }
            catch (Exception ex){
                Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG);
                Log.d("Foto", ex.getMessage());
            }
            return true;
        }

        private void checkPermissions() {
            PermissionListener dialogOnDeniedPermissionListener =
                    DialogOnDeniedPermissionListener.Builder.withContext(getActivity())
                            .withTitle("Permisos")
                            .withMessage("Los permisos son necesarios para seleccionar una fotografía")
                            .withButtonText("Aceptar")
                            .withIcon(R.mipmap.ic_launcher)
                            .build();

            Dexter.withContext(getActivity())
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //      .withListener(allPermissionListener)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent pickIntent = new Intent();
                            pickIntent.setType("image/*");
                            pickIntent.setAction(Intent.ACTION_GET_CONTENT);

                            startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), PICKER);
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
    }
}