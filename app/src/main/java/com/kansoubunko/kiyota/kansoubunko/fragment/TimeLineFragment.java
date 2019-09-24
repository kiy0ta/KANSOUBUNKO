package com.kansoubunko.kiyota.kansoubunko.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.activity.MainActivity;
import com.kansoubunko.kiyota.kansoubunko.adapter.TimeLineListViewAdapter;
import com.kansoubunko.kiyota.kansoubunko.dao.KansouDao;
import com.kansoubunko.kiyota.kansoubunko.dto.BookInfoEntity;

import java.util.ArrayList;
import java.util.List;

public class TimeLineFragment extends Fragment {

    public KansouDao mDao;

    public static Intent getStartIntent(MainActivity mainActivity) {
        return new Intent(mainActivity, TimeLineFragment.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 画面初期化処理
        final View inflate = inflater.inflate(R.layout.fragment_setting, container, false);
        TextView titleText = inflate.findViewById(R.id.textView);
        Button button = inflate.findViewById(R.id.button);
        return inflate;
    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.fragment_timeline);
//
//        List<BookInfoEntity> kansou = new ArrayList<>();
//        mDao = new KansouDao(this);
//        kansou = mDao.selectBookInfoAll();
//
//        ListView listView = findViewById(R.id.time_line_list_view);
//        TimeLineListViewAdapter timeLineListViewAdapter = new TimeLineListViewAdapter(this, kansou);
//        listView.setAdapter(timeLineListViewAdapter);
//
//    }
}
