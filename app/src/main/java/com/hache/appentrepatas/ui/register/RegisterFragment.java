package com.hache.appentrepatas.ui.register;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.hache.appentrepatas.R;
import com.hache.appentrepatas.adapter.AdoptAdapter;
import com.hache.appentrepatas.adapter.RegisterAdapter;
import com.hache.appentrepatas.bean.Adopt;
import com.hache.appentrepatas.ui.adopt.AdoptFragment;
import com.hache.appentrepatas.util.CenterZoomLayoutManager;

import java.io.Console;
import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.xml.transform.Result;

import kotlin.contracts.Returns;

public class RegisterFragment extends Fragment {

    Spinner gender_spinner;
    String[] gender = {"Selecciona su sexo","Hembra","Macho"} ;
    Uri ruta;
    private final int RESULT_OK = -1;
    private final int PICKER = 1;
    private RecyclerView recyclerView;
    private ArrayList<Uri> items;
    private RegisterAdapter registerAdapter;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICKER) {
            //String[] medData = { MediaStore.Images.Media.DATA };
            ruta = data.getData();
            items.add(ruta);

           // registerAdapter = new RegisterAdapter(getContext(), new OnSelectClick(), items);
            recyclerView.setAdapter(registerAdapter);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        gender_spinner = (Spinner) view.findViewById(R.id.spnn_gender);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_register);

        gender_spinner.setAdapter(new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, gender));

        CenterZoomLayoutManager layoutManager = new CenterZoomLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setHasFixedSize(true);

        items = new ArrayList<Uri>();

        Uri uriImage = Uri.parse("android.resource://" +  getContext().getPackageName() +"/"+R.drawable.ic_add);
        items.add(uriImage);

        registerAdapter = new RegisterAdapter(getContext(), new OnSelectClick(), items);
        recyclerView.setAdapter(registerAdapter);

        return view;
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
                Intent pickIntent = new Intent();
                pickIntent.setType("image/*");
                pickIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(pickIntent, "Select Picture"), PICKER);

            }
            catch (Exception ex){
            }
            return true;
        }
    }
}