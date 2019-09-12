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
import com.kansoubunko.kiyota.kansoubunko.dto.UserInfoEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;

    public static Intent getStartIntent(LoginActivity loginActivity) {
        return new Intent(loginActivity, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<UserInfoEntity> kansou = new ArrayList<>();
        KansouDao dao = new KansouDao(this);
        kansou = dao.selectALl();
        for(UserInfoEntity k:kansou){
            String n = k.getUserName();
            Log.d("loglog","string:"+n);
            Log.d("loglog","size:"+kansou.size());
        }

        mSharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        String s = mSharedPreferences.getString("userName","");
        String t = mSharedPreferences.getString("userPassword","");

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
    }

}
