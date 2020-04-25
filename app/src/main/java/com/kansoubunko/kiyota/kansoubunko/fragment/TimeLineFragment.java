package com.kansoubunko.kiyota.kansoubunko.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.adapter.TimeLineListViewAdapter;
import com.kansoubunko.kiyota.kansoubunko.dao.KansouDao;
import com.kansoubunko.kiyota.kansoubunko.dto.BookInfoEntity;

import java.util.ArrayList;
import java.util.List;

public class TimeLineFragment extends Fragment {

    private List<BookInfoEntity> bookInfoList;
    private String username;
    private Bundle bundle;
    public KansouDao mDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 画面初期化処理
        final View inflate = inflater.inflate(R.layout.fragment_timeline, container, false);

        //ユーザー情報を取得する
        bundle = getArguments();
        username = bundle.getString("userName");

        //本のデータを取得する
        mDao = new KansouDao(getActivity());
        bookInfoList = new ArrayList<>();
        bookInfoList = mDao.selectBookInfoAll();
        ListView listView = inflate.findViewById(R.id.time_line_list_view);
        TimeLineListViewAdapter timeLineListViewAdapter = new TimeLineListViewAdapter(getActivity(), bookInfoList);
//        listView.setAdapter(timeLineListViewAdapter);
        return inflate;
    }
}
