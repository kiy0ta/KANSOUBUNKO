package com.kansoubunko.kiyota.kansoubunko.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookInfoHelper extends SQLiteOpenHelper {

    /**
     * テーブル名
     */
    public static final String TABLE_NAME = "book_info";
    /**
     * カラム名シリアルナンバー
     */
    public static final String COLUMN_SERIAL_NUMBER = "serial_number";

    public BookInfoHelper(Context context) {
        super(context, "Kansou.db" , null, 1);
    }

    /**
     * 初期化処理DBがない場合DBを作成
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // テーブル作成
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_SERIAL_NUMBER + " TEXT)");
    }

    /**
     * アップデート処理
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // アップデートの判別、古いバージョンは削除して新規作成
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
