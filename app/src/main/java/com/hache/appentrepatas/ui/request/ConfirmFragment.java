package com.hache.appentrepatas.ui.request;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hache.appentrepatas.BaseFragment;
import com.hache.appentrepatas.HomeActivity;
import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;
import com.hache.appentrepatas.ui.home.HomeFragment;

public class ConfirmFragment extends BaseFragment implements View.OnClickListener {

    Button requestBtn;

    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Solicitud de adopción");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    public boolean onBackPressed() {
        //((MainActivity)getActivity()).setFragment(6);
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        requestBtn = (Button) view.findViewById(R.id.btn_confirm_request);
        requestBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm_request:
                ((MainActivity) getActivity()).setFragment(3);
                //((MainActivity) getActivity()).setFragment(new HomeFragment());
/*
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment,new HomeFragment());
                transaction.addToBackStack(null);
                transaction.commit();*/
                break;
            default:
                break;
        }
    }

}