package com.kansoubunko.kiyota.kansoubunko.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * フラグメント管理クラス
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public Fragment[] fragments;

    /**
     * コンストラクタ
     *
     * @param fm フラグメントマネージャー
     */
    public SectionsPagerAdapter(FragmentManager fm, Fragment[] fragments) {

        super(fm);

        this.fragments = fragments;
    }

    /**
     * タブのインデックスに対応したフラグメントを取得するメソッド
     *
     * @param position タブのインデックス
     * @return　インデックスに対応したフラグメント
     */
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    /**
     * タブに登録されたフラグメントの数を取得するメソッド
     *
     * @return 登録されたフラグメントの数
     */
    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

}
