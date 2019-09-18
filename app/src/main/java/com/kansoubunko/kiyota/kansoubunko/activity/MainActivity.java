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
    private List<BookInfoEntity> kansou2;
    private List<BookInfoEntity> kansou3;

    public static Intent getStartIntent(LoginActivity loginActivity) {
        return new Intent(loginActivity, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KansouDao dao = new KansouDao(this);
        kansou = dao.selectAll();
        Log.d("loglog","list1;" + kansou);
        mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        String s = mSharedPreferences.getString("userName","");
        Log.d("loglog","userName;" + s);
        String t = mSharedPreferences.getString("userPassword","");
        String i = mSharedPreferences.getString("userId","");
        kansou2 = dao.selectBookInfo(s);
        Log.d("loglog","list2;" + kansou2);
        kansou3 = dao.selectBookInfoAll();
        Log.d("loglog","list3;" + kansou3);

//        //本の情報をすべて取得する
//        List<BookInfoEntity> bookInfoList = new ArrayList<>();
//        bookInfoList = mDao.selectBookInfoALl();


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
