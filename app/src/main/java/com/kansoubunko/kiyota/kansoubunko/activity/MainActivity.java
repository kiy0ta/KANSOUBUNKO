package com.kansoubunko.kiyota.kansoubunko.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.adapter.SectionsPagerAdapter;
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
    private Fragment[] fragments;
    public static final int INDEX_REGIST = 0;
    public static final int INDEX_LIST = 1;
    public static final int INDEX_TIMELINE = 2;
    public static final int INDEX_FAVORITE = 3;
    public static final int INDEX_SETTING = 4;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public static Intent getStartIntent(LoginActivity loginActivity) {
        return new Intent(loginActivity, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ユーザー情報を保持する
        titleImage = findViewById(R.id.layout_title);
        mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = mSharedPreferences.getString("userId", "");
        userName = mSharedPreferences.getString("userName", "");
        userPassword = mSharedPreferences.getString("userPassword", "");

        //フラグメントを配列に格納する
        fragments = new Fragment[5];
        fragments[INDEX_REGIST] = new RegistFragment();
        fragments[INDEX_LIST] = new ListFragment();
        fragments[INDEX_TIMELINE] = new TimeLineFragment();
        fragments[INDEX_FAVORITE] = new FavoriteFragment();
        fragments[INDEX_SETTING] = new SettingFragment();

        //ユーザー情報を保持する
        Bundle bundle = new Bundle();
        bundle.putString("userName", userName);
        fragments[INDEX_REGIST].setArguments(bundle);
        fragments[INDEX_LIST].setArguments(bundle);
        fragments[INDEX_TIMELINE].setArguments(bundle);
        fragments[INDEX_FAVORITE].setArguments(bundle);
        fragments[INDEX_SETTING].setArguments(bundle);

        //viewPager(=fragmentを埋め込むスペースのこと)を生成する
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager = (ViewPager) findViewById(R.id.fragment_layout);
        viewPager.setAdapter(sectionsPagerAdapter);

        // タブメニューデザイン初期化
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(INDEX_REGIST).setIcon(R.drawable.ic_add);
        tabLayout.getTabAt(INDEX_LIST).setIcon(R.drawable.ic_book);
        tabLayout.getTabAt(INDEX_TIMELINE).setIcon(R.drawable.ic_search);
        tabLayout.getTabAt(INDEX_FAVORITE).setIcon(R.drawable.ic_favorite);
        tabLayout.getTabAt(INDEX_SETTING).setIcon(R.drawable.ic_setting);

        //初期表示をセット
        tabLayout.getTabAt(getIntent().getIntExtra("STARTAT", INDEX_TIMELINE)).select();
    }
}
