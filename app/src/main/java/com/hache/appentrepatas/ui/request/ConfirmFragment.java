package com.hache.appentrepatas.ui.request;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hache.appentrepatas.HomeActivity;
import com.hache.appentrepatas.MainActivity;
import com.hache.appentrepatas.R;

public class ConfirmFragment extends Fragment implements View.OnClickListener {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        requestBtn = (Button) view.findViewById(R.id.btn_confirm_request);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm_request:
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }
}