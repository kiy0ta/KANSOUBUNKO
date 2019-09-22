package com.kansoubunko.kiyota.kansoubunko.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

public class MainFragment extends Fragment {

    public void callFromOut() {
        Log.d("MainFragment", "callFromOut this method");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
