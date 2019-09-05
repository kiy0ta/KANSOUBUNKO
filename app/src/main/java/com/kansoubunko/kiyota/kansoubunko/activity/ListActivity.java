package com.kansoubunko.kiyota.kansoubunko.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kansoubunko.kiyota.kansoubunko.R;

public class ListActivity extends AppCompatActivity {
    public static Intent getStartIntent(MainActivity mainActivity) {
        return new Intent(mainActivity, ListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

    }
}
