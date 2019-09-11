package com.kansoubunko.kiyota.kansoubunko.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.dao.KansouDao;
import com.kansoubunko.kiyota.kansoubunko.dto.KansouEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public KansouDao mDao;
    private SharedPreferences mSharedPreferences;


    public static Intent getStartIntent(SplashActivity splashActivity) {
        return new Intent(splashActivity, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDao = new KansouDao(this);
        final Resources res = getResources();

        final EditText userNameText = (EditText) findViewById(R.id.login_user_name);
        final EditText userPasswordText = (EditText) findViewById(R.id.login_user_password);
        final TextView errorTextView = findViewById(R.id.error_message);
        final ImageView errorIcView = findViewById(R.id.error_ic);

        //ログイン処理
        Button loginButtonView = findViewById(R.id.login_login);
        loginButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameText.getText().toString();
                String userPassword = userPasswordText.getText().toString();

                if (userName == null || userPassword == null) {
                    //バリデーションチョック文言を表示
                    errorIcView.setVisibility(View.VISIBLE);
                    errorTextView.setText(res.getString(R.string.login_login_error_message));
                    //空欄チェック
                    //文字数チェック(1~20)
                    //スペース確保（xml）
                    //文字カラー作成（青）
                }

                Boolean bln = mDao.findUserLoginInfo(userName, userPassword);
                if (bln) {
                    errorIcView.setVisibility(View.GONE);
                    errorTextView.setText("");
                    startActivity(MainActivity.getStartIntent(LoginActivity.this));
                } else {
                    //バリデーションメッセージ表示
                    //パスワードが違います、とか
                    errorIcView.setVisibility(View.VISIBLE);
                    errorTextView.setText(res.getString(R.string.login_regist_error_message));
                }
            }
        });

        //登録処理
        Button registButtonView = findViewById(R.id.login_regist);
        registButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameText.getText().toString();
                String userPassword = userPasswordText.getText().toString();

                if (userName == null || userPassword == null || userName.length() == 0 || userPassword.length() == 0) {
                    //バリデーションチョック文言を表示
                    errorIcView.setVisibility(View.VISIBLE);
                    errorTextView.setText(res.getString(R.string.login_regist_error_message));
                    //空欄チェック
                    //文字数チェック(1~20)
                    //スペース確保（xml）
                    //文字カラー作成（青）
                } else {
                    mDao.registUserInfo(userName, userPassword);
                    errorIcView.setVisibility(View.GONE);
                    errorTextView.setText("");
                    mSharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("userName",userName);
                    editor.putString("userPassword",userPassword);
                    startActivity(MainActivity.getStartIntent(LoginActivity.this));
                }
            }
        });
    }
}
