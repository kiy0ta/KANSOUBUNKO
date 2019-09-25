package com.kansoubunko.kiyota.kansoubunko.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.fragment.FavoriteFragment;
import com.kansoubunko.kiyota.kansoubunko.fragment.ListFragment;
import com.kansoubunko.kiyota.kansoubunko.fragment.RegistFragment;
import com.kansoubunko.kiyota.kansoubunko.fragment.SettingFragment;
import com.kansoubunko.kiyota.kansoubunko.fragment.TimeLineFragment;

public class MainActivity extends AppCompatActivity {

    private ImageView titleImage;
    private SharedPreferences mSharedPreferences;
    private String userId;
    private String userName;
    private String userPassword;

    public static Intent getStartIntent(LoginActivity loginActivity) {
        return new Intent(loginActivity, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleImage = findViewById(R.id.layout_title);
        mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = mSharedPreferences.getString("userId", "");
        userName = mSharedPreferences.getString("userName", "");
        userPassword = mSharedPreferences.getString("userPassword", "");

        ImageView registButton = findViewById(R.id.tab_regist);
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //タイトルを変更する
                titleImage.setImageResource(R.drawable.regist);
                //fragmentを呼びだす
                // Fragmentを作成します
                RegistFragment fragment = new RegistFragment();
                // Fragmentの追加や削除といった変更を行う際は、Transactionを利用します
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // 新しく追加を行うのでaddを使用します
                // 他にも、よく使う操作で、replace removeといったメソッドがあります
                // メソッドの1つ目の引数は対象のViewGroupのID、2つ目の引数は追加するfragment
                transaction.add(R.id.fragment_layout, fragment);
                // 最後にcommitを使用することで変更を反映します
                transaction.commit();
            }
        });
        ImageView listButton = findViewById(R.id.tab_list);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //タイトルを変更する
                titleImage.setImageResource(R.drawable.list);
                ListFragment fragment = new ListFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_layout, fragment);
                transaction.commit();
            }
        });
        ImageView searchButton = findViewById(R.id.tab_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //タイトルを変更する
                titleImage.setImageResource(R.drawable.timeline);
                TimeLineFragment fragment = new TimeLineFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_layout, fragment);
                transaction.commit();
            }
        });
        ImageView favoriteButton = findViewById(R.id.tab_favorite);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //タイトルを変更する
                titleImage.setImageResource(R.drawable.regist);
                FavoriteFragment fragment = new FavoriteFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_layout, fragment);
                transaction.commit();
            }
        });
        ImageView settingButton = findViewById(R.id.tab_setting);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //タイトルを変更する
                titleImage.setImageResource(R.drawable.setting);
                //ユーザー情報を渡す
                Bundle bundle = new Bundle();
                bundle.putString("userName", userName);
                SettingFragment fragment = new SettingFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_layout, fragment);
                transaction.commit();
            }
        });

    }
}

//かつてのMain画面
//        ImageView registImageView = findViewById(R.id.main_regist);
//        registImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(RegistFragment.getStartIntent(MainActivity.this));
//            }
//        });
//        ImageView listImageView = findViewById(R.id.main_list);
//        listImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(ListFragment.getStartIntent(MainActivity.this));
//            }
//        });
//        ImageView settingImageView = findViewById(R.id.main_setting);
//        settingImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(SettingFragment.getStartIntent(MainActivity.this));
//            }
//        });
//        ImageView timeLineImageView = findViewById(R.id.main_time_line);
//        timeLineImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(TimeLineFragment.getStartIntent(MainActivity.this));
//            }
//        });
