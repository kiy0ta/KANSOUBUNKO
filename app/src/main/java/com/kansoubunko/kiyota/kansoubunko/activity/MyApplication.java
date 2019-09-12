package com.kansoubunko.kiyota.kansoubunko.activity;

import android.app.Application;

import com.kansoubunko.kiyota.kansoubunko.util.ConfigPropUtil;

public class MyApplication extends Application {

    private static MyApplication instance = null;
    /**
     * アプリケーションインスタンス
     */
    private static MyApplication app;

    /**
     * プロパティファイルインスタンス
     */
    private ConfigPropUtil configPropUtil;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    /**
     * アプリケーションインスタンス取得メソッド
     * @return　アプリケーションインスタンス
     *
     */
    public static MyApplication getInstance(){
        return app;
    }

    /**
     * プロパティファイルインスタンス取得メソッド
     * @return プロパティファイルインスタンス
     */
//    public MyApplication getConfigPropUtil(){
////        return this.configPropUtil;
//    }
}
