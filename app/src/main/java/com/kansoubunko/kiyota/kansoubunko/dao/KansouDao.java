package com.kansoubunko.kiyota.kansoubunko.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.kansoubunko.kiyota.kansoubunko.activity.MyApplication;
import com.kansoubunko.kiyota.kansoubunko.constants.KansouContract;
import com.kansoubunko.kiyota.kansoubunko.dto.KansouEntity;
import com.kansoubunko.kiyota.kansoubunko.util.BookInfoHelper;
import com.kansoubunko.kiyota.kansoubunko.util.UserInfoHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class KansouDao {


    private Context mContext;

    public KansouDao(Context context) {
        mContext = context;
        userInfoHelper = new UserInfoHelper(mContext);
    }

//    Context context = MyApplication.getInstance();

    private UserInfoHelper userInfoHelper = new UserInfoHelper(mContext);
    private BookInfoHelper bookInfoHelper = new BookInfoHelper(mContext);

    //プロテイン画像を押下したら、日付とタイプを登録する
//    public void registProteinAllInfo(String date, String type) {
//        ContentValues values = new ContentValues();
//        values.put(ProteinCalendarContract.Input.RECORD_DATE, date);
//        values.put(ProteinCalendarContract.Input.RECORD_TYPE, type);
//        values.put(ProteinCalendarContract.Input.RECORD_PRICE, DefaultData.defaultPrice);
//        values.put(ProteinCalendarContract.Input.RECORD_BOTTLE, DefaultData.defaultBottle);
//        values.put(ProteinCalendarContract.Input.RECORD_PROTEIN, DefaultData.defaultProtein);
//        mContext.getContentResolver().insert(ProteinCalendarContract.Input.CONTENT_URI, values);
//    }

    //sample
    public List<KansouEntity> sample() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        KansouEntity entity = null;
        List<KansouEntity> list = new ArrayList<>();
        Log.i("Kansou.db", "start");
        try {
            // DB取得
            db = this.userInfoHelper.getWritableDatabase();
            cursor = db.query(
                    userInfoHelper.TABLE_NAME,
                    new String[]{userInfoHelper.COLUMN_USER_NAME, userInfoHelper.COLUMN_USER_PASSWORD},
                    null, null, null, null, null, "1");
            cursor.moveToFirst();

            // 取得できた際、インスタンスに保存
            if (cursor.getCount() > 0) {
                entity = new KansouEntity();
                entity.setUserName(cursor.getString(0));
                entity.setUserPassword(cursor.getString(1));
//                entity.setBookId(cursor.getInt(2));
//                entity.setBookTitle(cursor.getString(3));
//                entity.setBookImage(cursor.getString(4));
//                entity.setBookReview(cursor.getString(5));
                list.add(entity);
                Log.i("Kansou.db", "sn" + cursor.getString(0));
            }
        } catch (Exception ex) {
            Log.e("Kansou.db", "error", ex);
        } finally {

            // クローズ処理
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (db != null) {
                db.close();
                db = null;
            }
        }
        return list;
    }

    //sample
    public void registUserInfo(String userName, String userPassword) {
        Log.i("Kansou.db", "start");

        // DB初期化
        SQLiteDatabase db = null;
        try {

            // DB取得
//            userInfoHelper.onCreate(db);
            db = this.userInfoHelper.getWritableDatabase();
            ContentValues value = new ContentValues();

            // 既存レコード削除
            db.execSQL("DELETE FROM " + userInfoHelper.TABLE_NAME);

            // 新規データ登録
            value.put(userInfoHelper.COLUMN_USER_NAME, userName);
            value.put(userInfoHelper.COLUMN_USER_PASSWORD, userPassword);
            db.insert(userInfoHelper.TABLE_NAME, null, value);

        } catch (Exception ex) {
            Log.e("Kansou.db", "error:", ex);

        } finally {
            // クローズ処理

            if (db != null) {
                db.close();
                db = null;
            }
        }
    }

    //ユーザーとパスワードを登録する
//    public void registUserInfo(String userName, String userPassword) {
//        ContentValues values = new ContentValues();
//        values.put(KansouContract.Input.USER_NAME, userName);
//        values.put(KansouContract.Input.USER_PASSWORD, userPassword);
//        values.put(KansouContract.Input.BOOK_ID, "");
//        values.put(KansouContract.Input.BOOK_TITLE, "");
//        values.put(KansouContract.Input.BOOK_IMAGE, "");
//        values.put(KansouContract.Input.BOOK_REVIEW, "");
//        mContext.getContentResolver().insert(KansouContract.Input.CONTENT_URI, values);
//    }


    //ユーザーとパスワードを検索する
    public boolean findUserLoginInfo(String userName, String userPassword) {
        String[] projection = new String[]{
                KansouContract.Input.USER_NAME,
                KansouContract.Input.USER_PASSWORD
        };

        try (Cursor cur = mContext.getContentResolver().query(KansouContract.Input.CONTENT_URI, projection,
                KansouContract.Input.USER_NAME + " = " + userName + " AND " +
                        KansouContract.Input.USER_PASSWORD + " = " + userPassword,
                new String[]{userName, userPassword}, null)) {
            //DBの中に入っている件数を取得する
            if (cur != null && cur.getCount() > 0) {
                return true;
            }
            return false;
        } catch (SQLiteException e) {
            return false;
        }
    }

    //DBの中の本の総数をカウントする処理
    //DBに入っているデータ件数を取得する
    public int getTotalBookCount() {
        String[] projection = new String[]{
                KansouContract.Input.BOOK_ID
        };

        try (Cursor cur = mContext.getContentResolver().query(KansouContract.Input.CONTENT_URI, projection,
                null, null, null)) {
            //DBの中に入っている件数を取得する
            if (cur != null && cur.getCount() > 0) {
                return cur.getCount();
            }
            return 0;
        }
    }


    //DBに入っているデータをすべて取得してEntityに格納する
    public List<KansouEntity> selectAll() throws ParseException {
        List<KansouEntity> list = new ArrayList<KansouEntity>();
        String[] projection = new String[]{
                KansouContract.Input._ID,
                KansouContract.Input.USER_NAME,
                KansouContract.Input.USER_PASSWORD,
                KansouContract.Input.BOOK_ID,
                KansouContract.Input.BOOK_TITLE,
                KansouContract.Input.BOOK_IMAGE,
                KansouContract.Input.BOOK_REVIEW
        };

        try (Cursor cur = mContext.getContentResolver().query(KansouContract.Input.CONTENT_URI, projection,
                null, null, null)) {

            if (cur != null && cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    KansouEntity entity = new KansouEntity();
//                    entity.setId(cur.getInt(0));
                    entity.setUserName(cur.getString(1));
                    entity.setUserPassword(cur.getString(2));
                    entity.setBookId(cur.getInt(3));
                    entity.setBookTitle(cur.getString(4));
                    entity.setBookImage(cur.getString(5));
                    entity.setBookReview(cur.getString(6));
                    list.add(entity);
                }
            }
        }
        //return nullにすると落ちる
        return list;
    }

    //特定の日付のTypeの値を取得する
//    public List<KansouEntity> selectTypeDate(String date) throws ParseException {
//        List<ProteinEntity> list = new ArrayList<ProteinEntity>();
//        String[] projection = new String[]{
//
//                ProteinCalendarContract.Input.RECORD_TYPE
//        };
//
//        try (Cursor cur = mContext.getContentResolver().query(ProteinCalendarContract.Input.CONTENT_URI, projection,
//                ProteinCalendarContract.Input.RECORD_DATE + " like ?", new String[]{date}, null)) {
//
//            if (cur != null && cur.getCount() > 0) {
//                while (cur.moveToNext()) {
//                    ProteinEntity entity = new ProteinEntity();
//                    entity.setProteinType(cur.getInt(0));
//                    list.add(entity);
//                }
//            }
//        }
//        //return nullにすると落ちる
//        return list;
//    }

    //本のタイトルを更新する
    public void updateBookTitle(String bookId, String bookTitle) {
        ContentValues values = new ContentValues();
        values.put(KansouContract.Input.BOOK_TITLE, bookTitle);
        mContext.getContentResolver().update(KansouContract.Input.CONTENT_URI, values, KansouContract.Input.CONTENT_URI + bookId, null);
    }

    //本の画像を更新する
    public void updateBookImage(String bookId, String bookImage) {
        ContentValues values = new ContentValues();
        values.put(KansouContract.Input.BOOK_TITLE, bookImage);
        mContext.getContentResolver().update(KansouContract.Input.CONTENT_URI, values, KansouContract.Input.CONTENT_URI + bookId, null);
    }

    //本の感想文を更新する
    public void updateBookReview(String bookId, String bookReview) {
        ContentValues values = new ContentValues();
        values.put(KansouContract.Input.BOOK_TITLE, bookReview);
        mContext.getContentResolver().update(KansouContract.Input.CONTENT_URI, values, KansouContract.Input.CONTENT_URI + bookId, null);
    }
}
