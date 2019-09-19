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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


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
        mDao = new KansouDao(getApplicationContext());
        final Resources res = getResources();

        //入力されたデータを保持するインスタンス
        final EditText userNameText = (EditText) findViewById(R.id.login_user_name);
        final EditText userPasswordText = (EditText) findViewById(R.id.login_user_password);

        //エラーメッセージ用インスタンス
        final TextView errorTextView = findViewById(R.id.error_message);
        final ImageView errorIcView = findViewById(R.id.error_ic);

        //ログイン処理
        Button loginButtonView = findViewById(R.id.login_login);
        loginButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //入力されたデータを取得する
                String userName = userNameText.getText().toString();
                String userPassword = userPasswordText.getText().toString();
                //入力されたデータがカラの場合
                if (userName == null || userPassword == null || userName.length() == 0 || userPassword.length() == 0) {
                    //バリデーションチョック文言を表示
                    errorIcView.setVisibility(View.VISIBLE);
                    errorTextView.setText(res.getString(R.string.login_error_message));
                    return;
                }
                //入力されたユーザー情報が正しいかどうか確認する
                //true:正しい
                Boolean bln = mDao.findUserInfo(userName, userPassword);
                if (!bln) {
                    //バリデーションメッセージ表示
                    errorIcView.setVisibility(View.VISIBLE);
                    errorTextView.setText(res.getString(R.string.login_login_error_message));
                    return;
                }
                //ID取得処理
                String userId = mDao.findUserIdInfo(userName);
                //エラーメッセージ用インスタンスを非表示化
                errorIcView.setVisibility(View.GONE);
                errorTextView.setText("");
                //ユーザー情報をSharedPreferencesに保持する
                mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("userId", userId);
                editor.putString("userName", userName);
                editor.putString("userPassword", userPassword);
                editor.apply();
                //Main画面を表示する
                startActivity(MainActivity.getStartIntent(LoginActivity.this));
            }
        });

        //登録処理
        Button registButtonView = findViewById(R.id.login_regist);
        registButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //入力されたデータを取得する
                String userName = userNameText.getText().toString();
                String userPassword = userPasswordText.getText().toString();
                //入力されたデータがカラの場合
                if (userName == null || userPassword == null || userName.length() == 0 || userPassword.length() == 0) {
                    //バリデーションチョック文言を表示
                    errorIcView.setVisibility(View.VISIBLE);
                    errorTextView.setText(res.getString(R.string.login_error_message));
                    return;
                }
                //入力された名前がすでに使われているかどうか確認する
                //true:使われていない
                Boolean bln = mDao.findUserNameInfo(userName);
                if (!bln) {
                    //バリデーションメッセージ表示
                    errorIcView.setVisibility(View.VISIBLE);
                    errorTextView.setText(res.getString(R.string.login_regist_same_name_error_message));
                    return;
                }
                //文字数チェック(1~20)
                if (21 <= userName.length() || 21 <= userPassword.length()) {
                    //バリデーションメッセージ表示
                    errorIcView.setVisibility(View.VISIBLE);
                    errorTextView.setText(res.getString(R.string.login_length_error_message));
                    return;
                }
                //登録処理
                mDao.registUserInfo(userName, userPassword);
                LocalDate today = LocalDate.now();
                String strToday = String.valueOf(today);
                //TODO:日付のフォーマット処理が必要
                mDao.registBookInfo(userName, "ハンバーグ", "no_book_img", "今日の天気は曇りで、風が吹いていて涼しいです。", strToday);
                //ID取得処理
                String userId = mDao.findUserIdInfo(userName);
                //エラーメッセージ用インスタンスを非表示化
                errorIcView.setVisibility(View.GONE);
                errorTextView.setText("");
                //ユーザー情報をSharedPreferencesに保持する
                mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("userId", userId);
                editor.putString("userName", userName);
                editor.putString("userPassword", userPassword);
                editor.apply();
                //Main画面を表示する
                startActivity(MainActivity.getStartIntent(LoginActivity.this));
            }
        });
    }
}
