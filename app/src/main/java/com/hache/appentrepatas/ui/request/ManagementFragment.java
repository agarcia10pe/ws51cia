package com.hache.appentrepatas.ui.request;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hache.appentrepatas.R;
import com.hache.appentrepatas.adapter.AdoptAdapter;
import com.hache.appentrepatas.adapter.ManagementAdapter;
import com.hache.appentrepatas.bean.Adopt;
import com.hache.appentrepatas.ui.adopt.AdoptFragment;

import java.util.ArrayList;


public class ManagementFragment extends Fragment  implements View.OnClickListener {

    private RecyclerView recyclerView ;
    private ArrayList<Adopt> adopts;
    private ManagementAdapter managementAdapter;

    private  Adopt[] items = null;
    Adopt item;

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
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_management, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_management);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adopts = new ArrayList<Adopt>();

        item = new Adopt();
        item.setEstado("Aprobado");
        adopts.add(item);

        managementAdapter = new ManagementAdapter(new OnSelectClick(), adopts);
        recyclerView.setAdapter(managementAdapter);

        return view;
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
        public void clickItem(View itemView, int posicion) {
            //onImprimir(articulos.get(posicion).getProducto());
        }
    }
}