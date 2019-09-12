package com.kansoubunko.kiyota.kansoubunko.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserInfoHelper extends SQLiteOpenHelper {

    /**
     * テーブル名
     */
    public static final String TABLE_NAME = "user_info";
    /**
     * カラム名
     */
    public static final String COLUMN_USER_ID = "user_id";
    /**
     * カラム名
     */
    public static final String COLUMN_USER_NAME = "user_name";
    /**
     * カラム名
     */
    public static final String COLUMN_USER_PASSWORD = "user_password";

    public UserInfoHelper(Context context) {
        super(context, "Kansou.db", null, 1);
    }

    /**
     * 初期化処理DBがない場合DBを作成
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブル作成
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT , "
                + COLUMN_USER_PASSWORD + " TEXT " + ");");
    }

    /**
     * アップデート処理
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // アップデートの判別、古いバージョンは削除して新規作成
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
    }
}
