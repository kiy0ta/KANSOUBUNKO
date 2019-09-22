package com.kansoubunko.kiyota.kansoubunko.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.dao.KansouDao;
import com.kansoubunko.kiyota.kansoubunko.dto.BookInfoEntity;
import com.kansoubunko.kiyota.kansoubunko.dto.UserInfoEntity;
import com.kansoubunko.kiyota.kansoubunko.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Intent getStartIntent(LoginActivity loginActivity) {
        return new Intent(loginActivity, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fragmentを呼びだす
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_layout, new MainFragment(), "MainFragment");

        ImageView registButton = findViewById(R.id.tab_regist);
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegistActivity.getStartIntent(MainActivity.this));
            }
        });
        ImageView listButton = findViewById(R.id.tab_list);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ListActivity.getStartIntent(MainActivity.this));
            }
        });
        ImageView searchButton = findViewById(R.id.tab_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TimeLineActivity.getStartIntent(MainActivity.this));
            }
        });
        ImageView favoriteButton = findViewById(R.id.tab_favorite);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        ImageView followersButton = findViewById(R.id.tab_followers);
        followersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TimeLineActivity.getStartIntent(MainActivity.this));
            }
        });
        ImageView settingButton = findViewById(R.id.tab_setting);


    }
}

//もともとのMain画面
//        ImageView registImageView = findViewById(R.id.main_regist);
//        registImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(RegistActivity.getStartIntent(MainActivity.this));
//            }
//        });
//        ImageView listImageView = findViewById(R.id.main_list);
//        listImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(ListActivity.getStartIntent(MainActivity.this));
//            }
//        });
//        ImageView settingImageView = findViewById(R.id.main_setting);
//        settingImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(SettingActivity.getStartIntent(MainActivity.this));
//            }
//        });
//        ImageView timeLineImageView = findViewById(R.id.main_time_line);
//        timeLineImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(TimeLineActivity.getStartIntent(MainActivity.this));
//            }
//        });
