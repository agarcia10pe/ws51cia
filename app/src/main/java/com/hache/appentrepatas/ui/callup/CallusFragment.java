package com.hache.appentrepatas.ui.callup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hache.appentrepatas.R;


public class CallusFragment extends Fragment {

    public CallusFragment() {
        // Required empty public constructor
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
        View view =  inflater.inflate(R.layout.fragment_callus, container, false);
        String uri = "tel: 013510088" ;
        //Intent intent = new Intent(Intent.ACTION_CALL);
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(uri)));
        //intent.setData(Uri.parse(uri));
        //startActivity(intent);
        return view;
    }


}