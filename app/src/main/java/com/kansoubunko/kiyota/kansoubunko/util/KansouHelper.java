package com.kansoubunko.kiyota.kansoubunko.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KansouHelper extends SQLiteOpenHelper {

    /**
     * テーブル名
     */
    public static final String USER_TABLE_NAME = "user_info";
    /**
     * カラム名
     */
    public static final String COLUMN_BOOK_USER_NAME = "book_user_name";
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
    /**
     * カラム名
     */
    public static final String COLUMN_USER_BIRTHDAY = "user_birthday";
    /**
     * カラム名
     */
    public static final String COLUMN_USER_FOLLOW = "user_follow";
    /**
     * カラム名
     */
    public static final String COLUMN_USER_FOLLOWERS = "user_followers";
    /**
     * カラム名
     */
    public static final String COLUMN_USER_IMAGE = "user_image";
    /**
     * カラム名
     */
    public static final String COLUMN_PROFILE = "profile";
    /**
     * テーブル名
     */
    public static final String BOOK_TABLE_NAME = "book_info";
    /**
     * カラム名
     */
    public static final String COLUMN_BOOK_ID = "book_id";
    /**
     * カラム名
     */
    public static final String COLUMN_BOOK_TITLE = "book_title";
    /**
     * カラム名
     */
    public static final String COLUMN_BOOK_IMAGE = "book_image";
    /**
     * カラム名
     */
    public static final String COLUMN_BOOK_REVIEW = "book_review";
    /**
     * カラム名
     */
    public static final String COLUMN_BOOK_DATE = "book_date";

    public KansouHelper(Context context) {
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
        db.execSQL("CREATE TABLE " + USER_TABLE_NAME +
                " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT , "
                + COLUMN_USER_PASSWORD + " TEXT , "
                + COLUMN_USER_BIRTHDAY + " TEXT , "
                + COLUMN_USER_FOLLOW + " TEXT , "
                + COLUMN_USER_FOLLOWERS + " TEXT , "
                + COLUMN_USER_IMAGE + " TEXT , "
                + COLUMN_PROFILE + " TEXT " + ");");

        db.execSQL("CREATE TABLE " + BOOK_TABLE_NAME +
                " (" + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + COLUMN_BOOK_USER_NAME + " TEXT , "
                + COLUMN_BOOK_TITLE + " TEXT , "
                + COLUMN_BOOK_IMAGE + " TEXT , "
                + COLUMN_BOOK_REVIEW + " TEXT , "
                + COLUMN_BOOK_DATE + " TEXT " + ");");
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
