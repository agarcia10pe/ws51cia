package com.hache.appentrepatas.ui.adopt;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hache.appentrepatas.R;
import com.hache.appentrepatas.adapter.AdoptAdapter;
import com.hache.appentrepatas.bean.Adopt;

import java.util.ArrayList;

public class AdoptFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView ;
    private ArrayList<Adopt> adopts;
    private AdoptAdapter adoptAdapter;

    private  Adopt[] items = null;
    Adopt item;

    public AdoptFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_adopt, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_adopts);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adopts = new ArrayList<Adopt>();

        item = new Adopt();
        item.setNombre("Dacota");
        adopts.add(item);

        item = new Adopt();
        item.setNombre("Firulay");
        adopts.add(item);

        item = new Adopt();
        item.setNombre("Loki");
        adopts.add(item);

        adoptAdapter = new AdoptAdapter(new OnSelectClick(), adopts);
        recyclerView.setAdapter(adoptAdapter);

        return  root;
    }

    private class OnSelectClick implements AdoptAdapter.MiListenerClick{

        @Override
        public void clickItem(View itemView, int posicion) {
            //onImprimir(articulos.get(posicion).getProducto());
        }
    }


    @Override
    public void onClick(View v) {
    }
}