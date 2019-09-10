package com.kansoubunko.kiyota.kansoubunko.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.dao.KansouDao;

public class LoginActivity extends AppCompatActivity {

    public KansouDao mDao;


    public static Intent getStartIntent(SplashActivity splashActivity) {
        return new Intent(splashActivity, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText userNameText = (EditText) findViewById(R.id.login_user_name);
        final String userName = userNameText.getText().toString();
        EditText userPasswordText = (EditText) findViewById(R.id.login_user_password);
        final String userPassword = userPasswordText.getText().toString();

        Button loginButtonView = findViewById(R.id.login_login);
        loginButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.getStartIntent(LoginActivity.this));
            }
        });
        final Button registButtonView = findViewById(R.id.login_regist);
        registButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDao.registUserInfo(userName, userPassword);
                startActivity(MainActivity.getStartIntent(LoginActivity.this));
            }
        });
    }
}
