package com.kansoubunko.kiyota.kansoubunko.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.adapter.BookListGridAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    public static Intent getStartIntent(MainActivity mainActivity) {
        return new Intent(mainActivity, ListActivity.class);
    }

    // 表示する画像の名前（拡張子無し）
    private String members[] = {
            "no_book_img", "no_book_img", "no_book_img", "no_book_img", "no_book_img",
            "no_book_img", "no_book_img", "no_book_img", "no_book_img", "no_book_img"
    };

    // Resource IDを格納するarray
    private List<Integer> imgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

// for-each member名をR.drawable.名前としてintに変換してarrayに登録
        for (String member : members) {
            int imageId = getResources().getIdentifier(
                    member, "drawable", getPackageName());
            imgList.add(imageId);
        }

        GridView bookListGridView = findViewById(R.id.list_book);
        BookListGridAdapter adapter = new BookListGridAdapter(this, R.layout.item_book_list, imgList, members);
        bookListGridView.setAdapter(adapter);

    }
}
