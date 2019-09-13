package com.kansoubunko.kiyota.kansoubunko.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class PhoneInfo {

    /**
     * 画面幅取得メソッド
     *
     * @param context アプリケーションコンテキスト
     * @return 画面幅
     */
    public static int getPhoneWidth(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.widthPixels;
    }

    /**
     * 画面高さ取得メソッド
     *
     * @param context アプリケーションコンテキスト
     * @return 画面高さ
     */
    public static int getPhoneHeight(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.heightPixels;
    }

    /**
     * 画面情報取得共通処理
     *
     * @param context 　アプリケーションコンテキスト
     * @return 画面情報を持ったディスプレイメトリクス
     */
    private static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return displayMetrics;
    }

}
