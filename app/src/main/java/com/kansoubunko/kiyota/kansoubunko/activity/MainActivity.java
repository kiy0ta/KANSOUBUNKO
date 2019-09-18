package com.kansoubunko.kiyota.kansoubunko.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.dao.KansouDao;
import com.kansoubunko.kiyota.kansoubunko.dto.BookInfoEntity;
import com.kansoubunko.kiyota.kansoubunko.dto.UserInfoEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public KansouDao mDao;
    private SharedPreferences mSharedPreferences;
    private List<UserInfoEntity> kansou;

    public static Intent getStartIntent(LoginActivity loginActivity) {
        return new Intent(loginActivity, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KansouDao dao = new KansouDao(this);
        kansou = dao.selectAll();

//        //本の情報をすべて取得する
//        List<BookInfoEntity> bookInfoList = new ArrayList<>();
//        bookInfoList = mDao.selectBookInfoALl();

//        mSharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
//        String s = mSharedPreferences.getString("userName","");
//        String t = mSharedPreferences.getString("userPassword","");

        ImageView registImageView = findViewById(R.id.main_regist);
        registImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegistActivity.getStartIntent(MainActivity.this));
            }
        });
        ImageView listImageView = findViewById(R.id.main_list);
        listImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ListActivity.getStartIntent(MainActivity.this));
            }
        });
        ImageView settingImageView = findViewById(R.id.main_setting);
        settingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SettingActivity.getStartIntent(MainActivity.this));
            }
        });
        ImageView timeLineImageView = findViewById(R.id.main_time_line);
        timeLineImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TimeLineActivity.getStartIntent(MainActivity.this));
            }
        });
    }

}
