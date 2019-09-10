package com.kansoubunko.kiyota.kansoubunko.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.adapter.BookReviewGridAdapter;
import com.kansoubunko.kiyota.kansoubunko.fragment.BookReviewDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistActivity extends AppCompatActivity {
    public static Intent getStartIntent(MainActivity mainActivity) {
        return new Intent(mainActivity, RegistActivity.class);
    }

    public static Intent getStartIntent(ListActivity listActivity) {
        return new Intent(listActivity, RegistActivity.class);
    }

    private static final Map<Integer, String> BOOK_REVIEW_MAP = new HashMap<>();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        EditText text = findViewById(R.id.text);

        List<String> word = new ArrayList<>();

        String input = "こんにちは。今日の天気は曇りです。最高気温は24度です。";

        word = Arrays.asList(input.split(""));
//        if(word.size() <= 100){
//            int count = 100 - word.size();
//            for(int i = 0; i < count; i++){
//                word.add("あ");
//            }
//        }

        GridView bookReviewGridView = findViewById(R.id.regist_book_review);
        BookReviewGridAdapter adapter = new BookReviewGridAdapter(this, R.layout.item_book_review, word);
        bookReviewGridView.setAdapter(adapter);

        Button bookReviewButton = findViewById(R.id.regist_book_review_button);
        bookReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ダイアログを表示する
                BookReviewDialogFragment dialog = new BookReviewDialogFragment();
                // 表示  getFragmentManager()は固定、sampleは識別タグ
                dialog.show(getSupportFragmentManager(), "sample");

            }
        });

        TextView backTextView = findViewById(R.id.back_arrow);
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // ダイアログで入力した値をtextViewに入れる - ダイアログから呼び出される
    public void setTextView(String value) {
        EditText textView = findViewById(R.id.text);
    }
}
