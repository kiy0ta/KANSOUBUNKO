package com.kansoubunko.kiyota.kansoubunko.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.kansoubunko.kiyota.kansoubunko.R;

public class MainActivity extends AppCompatActivity {

    public static Intent getStartIntent(LoginActivity loginActivity) {
        return new Intent(loginActivity, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
