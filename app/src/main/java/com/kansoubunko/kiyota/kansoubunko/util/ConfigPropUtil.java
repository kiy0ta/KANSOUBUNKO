package com.kansoubunko.kiyota.kansoubunko.util;

import android.util.Log;

import com.kansoubunko.kiyota.kansoubunko.activity.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropUtil {
    /**
     * プロパティファイル名
     */
    private static final String CONFIG_FILE = "config.properties";
    /**
     * 読み込んだプロパティ
     */
    private Properties prop;

    /**
     * コンストラクタ<br>
     * プロパティファイルを読み取りメンバー変数にセット。
     */
    private ConfigPropUtil() {
        prop = new Properties();
        InputStream is;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
            prop.load(is);
        } catch (IOException e) {
            Log.e("ConfigPropUtil", "error:", e);
        }
    }

    /**
     * ConfigPropUtilの唯一のインスタンスを取得するメソッド
     *
     * @return ConfigPropUtil唯一のインスタンス
     */
    public static ConfigPropUtil getInstance() {
        return MyApplication.getInstance().getConfigPropUtil();
    }

    /**
     * キーに対応するプロパティの値を取得するメソッド
     *
     * @param key 　プロパティのキー
     * @return　キーに対応した値
     */
    public String get(String key) {
        return (String) prop.get(key);
    }

}
