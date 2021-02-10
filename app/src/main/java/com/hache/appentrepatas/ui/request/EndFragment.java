package com.hache.appentrepatas.ui.request;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hache.appentrepatas.BaseFragment;
import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;

public class EndFragment extends Fragment implements View.OnClickListener, BaseFragment {

    Button button;
    public EndFragment() {
        // Required empty public constructor
    }
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Finalizar solicitud");
    }
    public boolean onBackPressed() {

        //((MainActivity)getActivity()).setFragment(6);
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
        button = (Button) view.findViewById(R.id.btn_request_end);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_request_end:
                ((MainActivity) getActivity()).setFragment(5, null, true);
                break;
            default:
                break;
        }
    }
}