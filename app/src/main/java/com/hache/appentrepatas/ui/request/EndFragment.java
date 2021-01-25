package com.hache.appentrepatas.ui.request;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hache.appentrepatas.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EndFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EndFragment extends Fragment {

    public EndFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EndFragment newInstance(String param1, String param2) {
        EndFragment fragment = new EndFragment();
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
        View view = inflater.inflate(R.layout.fragment_end, container, false);
        return view;
    }
}