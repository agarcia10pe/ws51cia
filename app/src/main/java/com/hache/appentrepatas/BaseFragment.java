package com.hache.appentrepatas;

import androidx.fragment.app.Fragment;

public interface BaseFragment {
    /**
     * If you return true the back press will not be taken into account, otherwise the activity will act naturally
     * @return true if your processing has priority if not false
     */
    boolean onBackPressed();
}
/*
public class BaseFragment extends Fragment {

    public boolean onBackPressed() {
        return false;
    }
}*/

