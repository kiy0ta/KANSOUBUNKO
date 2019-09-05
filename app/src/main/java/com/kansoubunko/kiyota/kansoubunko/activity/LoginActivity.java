package com.kansoubunko.kiyota.kansoubunko.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kansoubunko.kiyota.kansoubunko.R;

public class LoginActivity extends AppCompatActivity {

    public static Intent getStartIntent(SplashActivity splashActivity) {
        return new Intent(splashActivity, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButtonView = findViewById(R.id.login_login);
        loginButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.getStartIntent(LoginActivity.this));
            }
        });
        Button registButtonView = findViewById(R.id.login_login);
        registButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.getStartIntent(LoginActivity.this));
            }
        });
    }
}
