package com.hache.appentrepatas.ui.locate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hache.appentrepatas.R;


public class LocateFragment extends Fragment  {


    // TODO: Rename and change types and number of parameters
    public static LocateFragment newInstance(String param1, String param2) {
        LocateFragment fragment = new LocateFragment();
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
        View view = inflater.inflate(R.layout.fragment_locate, container, false);

        return view;
    }

}