package com.kansoubunko.kiyota.kansoubunko.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kansoubunko.kiyota.kansoubunko.R;

public class MainFragment extends Fragment {

    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 画面初期化処理
        final View inflate = inflater.inflate(R.layout.fragment_main, container, false);
        TextView titleText = inflate.findViewById(R.id.textView);
        Button button = inflate.findViewById(R.id.button);
        return inflate;
    }

}
