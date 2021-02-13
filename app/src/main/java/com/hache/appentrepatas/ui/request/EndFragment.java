package com.hache.appentrepatas.ui.request;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
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

import com.hache.appentrepatas.BaseFragment;
import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EndFragment extends Fragment implements View.OnClickListener, BaseFragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Button button;
    Button btnFoto;
    ImageView imgPhoto;
    Bitmap bmpFoto;
    public EndFragment() {
        // Required empty public constructor
    }
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Finalizar solicitud");
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_end, container, false);
        imgPhoto = view.findViewById(R.id.im_photo);
        button = (Button) view.findViewById(R.id.btn_request_end);
        btnFoto = view.findViewById(R.id.btn_request_photo);
        button.setOnClickListener(this);
        btnFoto.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_photo:
                checkPermissions();
                break;
            case R.id.btn_request_end:
                ((MainActivity) getActivity()).setFragment(5, null, true);
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
            bmpFoto = (Bitmap) extras.get("data");
            imgPhoto.setImageBitmap(bmpFoto);
        }
    }

    public File convertBitmapToFile(Bitmap bmp) {
        // TimeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream fOut = null;
        File file = new File(path, "Photo" + timeStamp + ".jpg"); // the File to save to
        try {
            fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close(); // do not forget to close the stream

            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
        } catch (IOException e) {
            // whatever
        }
        return file;
    }
}