package com.kansoubunko.kiyota.kansoubunko.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.activity.MainActivity;
import com.kansoubunko.kiyota.kansoubunko.adapter.TimeLineListViewAdapter;
import com.kansoubunko.kiyota.kansoubunko.dao.KansouDao;
import com.kansoubunko.kiyota.kansoubunko.dto.BookInfoEntity;

import java.util.ArrayList;
import java.util.List;

public class TimeLineFragment extends AppCompatActivity {

    public KansouDao mDao;

    public static Intent getStartIntent(MainActivity mainActivity) {
        return new Intent(mainActivity, TimeLineFragment.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_timeline);

        List<BookInfoEntity> kansou = new ArrayList<>();
        mDao = new KansouDao(this);
        kansou = mDao.selectBookInfoAll();

        ListView listView = findViewById(R.id.time_line_list_view);
        TimeLineListViewAdapter timeLineListViewAdapter = new TimeLineListViewAdapter(this, kansou);
        listView.setAdapter(timeLineListViewAdapter);

    }
}
