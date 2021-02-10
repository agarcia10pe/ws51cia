package com.hache.appentrepatas.ui.request;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.adapter.ManagementAdapter;
import com.hache.appentrepatas.adapter.RequestAdapter;
import com.hache.appentrepatas.bean.Adopt;

import java.security.PublicKey;
import java.util.ArrayList;

public class RequestFragment extends Fragment implements  View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView recyclerView ;
    private ArrayList<Adopt> adopts;
    private RequestAdapter requestAdapter;

    private  Adopt[] items = null;
    Adopt item;


    public RequestFragment() {
        // Required empty public constructor
    }

    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Mis solicitudes");
    }



    // TODO: Rename and change types and number of parameters
    public static RequestFragment newInstance(String param1, String param2) {
        RequestFragment fragment = new RequestFragment();
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
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_request);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adopts = new ArrayList<Adopt>();

        item = new Adopt();
        item.setEstado("Aprobado");
        adopts.add(item);

        requestAdapter = new RequestAdapter(new OnSelectClick(), adopts);
        recyclerView.setAdapter(requestAdapter);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:

                break;
        }
    }

    private class OnSelectClick implements RequestAdapter.MiListenerClick{

        @Override
        public void clickItem(View itemView, int posicion) {
            //onImprimir(articulos.get(posicion).getProducto());
            ((MainActivity) getActivity()).setFragment(4, null, true);
        }
    }
}