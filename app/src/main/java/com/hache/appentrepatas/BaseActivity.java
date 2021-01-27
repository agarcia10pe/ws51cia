package com.hache.appentrepatas;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import java.util.List;

public class BaseActivity extends Activity {
    @Override
    public void onBackPressed() {

        /*
        List fragmentList =  getSupportFragmentManager().getFragments();

        boolean handled = false;
        for(Fragment f : fragmentList) {
            if(f instanceof BaseFragment) {
                handled = ((BaseFragment)f).onBackPressed();

                if(handled) {
                    break;
                }
            }
        }

        if(!handled) {
            super.onBackPressed();
        } */
    }
}
