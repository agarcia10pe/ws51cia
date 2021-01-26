package com.hache.appentrepatas.ui.request;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.adapter.AdoptAdapter;
import com.hache.appentrepatas.adapter.RegisterAdapter;
import com.hache.appentrepatas.ui.register.RegisterFragment;
import com.hache.appentrepatas.util.CenterZoomLayoutManager;

import java.util.ArrayList;

public class DetailFragment extends Fragment implements  View.OnClickListener {

    private final int RESULT_OK = -1;
    private final int PICKER = 1;
    private RecyclerView recyclerView;
    private ArrayList<Uri> items;
    private RegisterAdapter registerAdapter;

    private Button requestBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Firulay");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_detail);
        requestBtn = (Button) view.findViewById(R.id.btn_detail_request);

        CenterZoomLayoutManager layoutManager = new CenterZoomLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<Uri>();

        Uri uriImage = Uri.parse("android.resource://" +  getContext().getPackageName() +"/"+R.drawable.ic_foto);
        items.add(uriImage);
        uriImage = Uri.parse("android.resource://" +  getContext().getPackageName() +"/"+R.drawable.ic_foto);
        items.add(uriImage);
        uriImage = Uri.parse("android.resource://" +  getContext().getPackageName() +"/"+R.drawable.ic_foto);
        items.add(uriImage);

        registerAdapter = new RegisterAdapter(getContext(), new DetailFragment.OnSelectClick(), items);
        recyclerView.setAdapter(registerAdapter);
        requestBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_detail_request:
                //miListenerClick.getIrFragment(new ConfirmFragment());
                ((MainActivity) getActivity()).setFragment(new ConfirmFragment());
            default:
                break;

        }
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