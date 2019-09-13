package com.kansoubunko.kiyota.kansoubunko.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.kansoubunko.kiyota.kansoubunko.constants.KansouContract;
import com.kansoubunko.kiyota.kansoubunko.dto.BookInfoEntity;
import com.kansoubunko.kiyota.kansoubunko.dto.UserInfoEntity;
import com.kansoubunko.kiyota.kansoubunko.util.KansouHelper;

import java.util.ArrayList;
import java.util.List;

public class KansouDao {

    private Context mContext;
    private KansouHelper kansouHelper = new KansouHelper(mContext);

    public KansouDao(Context context) {
        mContext = context;
        kansouHelper = new KansouHelper(mContext);
    }

    //すべてのユーザーの情報を取得するメソッド
    public List<UserInfoEntity> selectAll() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        UserInfoEntity entity = null;
        List<UserInfoEntity> list = new ArrayList<>();
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                kansouHelper.COLUMN_USER_ID,
                kansouHelper.COLUMN_USER_NAME,
                kansouHelper.COLUMN_USER_PASSWORD
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            cursor = db.query(
                    kansouHelper.USER_TABLE_NAME, projection,
                    null, null, null, null, null, "1");
            cursor.moveToFirst();
            // 取得できた際、インスタンスに保存
            if (cursor.getCount() > 0) {
                entity = new UserInfoEntity();
                entity.setUserId(cursor.getString(0));
                entity.setUserName(cursor.getString(1));
                entity.setUserPassword(cursor.getString(2));
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

    //ユーザー情報を新規登録するメソッド
    public void registUserInfo(String userName, String userPassword) {
        Log.i("Kansou.db", "start");

        // DB初期化
        SQLiteDatabase db = null;
        try {

            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            ContentValues value = new ContentValues();

            // 新規データ登録
            value.put(kansouHelper.COLUMN_USER_NAME, userName);
            value.put(kansouHelper.COLUMN_USER_PASSWORD, userPassword);
            db.insert(kansouHelper.USER_TABLE_NAME, null, value);

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

    //ユーザー名を検索する
    public boolean findUserNameInfo(String userName) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                kansouHelper.COLUMN_USER_NAME
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            cursor = db.query(
                    kansouHelper.USER_TABLE_NAME, projection, kansouHelper.COLUMN_USER_NAME + " = ? ",
                    new String[]{userName}, null, null, null, null);
            cursor.moveToFirst();
            //DBの中に入っている件数を取得する
            if (cursor != null && cursor.getCount() > 0) {
                return false;
            }
            return true;
        } catch (SQLiteException e) {
            return false;
        }
    }

    //ユーザー名と合致するユーザーIDを取得する
    public String findUserIdInfo(String userName) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                kansouHelper.COLUMN_USER_NAME
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            cursor = db.query(
                    kansouHelper.USER_TABLE_NAME, projection, kansouHelper.COLUMN_USER_NAME + " = ? ",
                    new String[]{userName}, null, null, null, null);
            cursor.moveToFirst();
            // 取得できた際、インスタンスに保存
            if (cursor != null && cursor.getCount() > 0) {
                String userId = cursor.getString(0);
                Log.i("Kansou.db", "sn" + cursor.getString(0));
                return userId;
            }
            return "";
        } catch (SQLiteException e) {
            return "";
        }
    }

    //ユーザー名とパスワードが正しいかどうか確認するメソッド
    public Boolean findUserInfo(String userName, String userPassword) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                kansouHelper.COLUMN_USER_NAME,
                kansouHelper.COLUMN_USER_PASSWORD
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            cursor = db.query(
                    kansouHelper.USER_TABLE_NAME, projection,
                    kansouHelper.COLUMN_USER_NAME + " = ? " + "AND "
                            + kansouHelper.COLUMN_USER_PASSWORD + " = ? ",
                    new String[]{userName, userPassword}, null, null, null, null);
            cursor.moveToFirst();
            // 正しければtrueを返す
            if (cursor != null && cursor.getCount() > 0) {
                Log.i("Kansou.db", "sn" + cursor.getString(0));
                return true;
            }
            return false;
        } catch (SQLiteException e) {
            return false;
        }
    }

    //すべての本の情報を取得するメソッド
    public List<BookInfoEntity> selectBookInfoAll() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        BookInfoEntity entity = null;
        List<BookInfoEntity> list = new ArrayList<>();
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                kansouHelper.COLUMN_BOOK_ID,
                kansouHelper.COLUMN_BOOK_TITLE,
                kansouHelper.COLUMN_BOOK_IMAGE,
                kansouHelper.COLUMN_BOOK_REVIEW
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            cursor = db.query(
                    kansouHelper.BOOK_TABLE_NAME, projection,
                    null, null, null, null, null, "1");
            cursor.moveToFirst();
            // 取得できた際、インスタンスに保存
            entity = new BookInfoEntity();
            if (cursor.getCount() > 0) {
                entity.setBookId(cursor.getString(0));
                entity.setBookTitle(cursor.getString(1));
                entity.setBookImage(cursor.getString(2));
                entity.setBookReview(cursor.getString(3));
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

    //本に関する情報を新規登録するメソッド
    public void registBookInfo(String bookTitle, String bookImage, String bookReview) {
        Log.i("Kansou.db", "start");

        // DB初期化
        SQLiteDatabase db = null;
        try {

            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            ContentValues value = new ContentValues();

            // 新規データ登録
            value.put(kansouHelper.COLUMN_BOOK_TITLE, bookTitle);
            value.put(kansouHelper.COLUMN_BOOK_IMAGE, bookImage);
            value.put(kansouHelper.COLUMN_BOOK_REVIEW, bookReview);
            db.insert(kansouHelper.BOOK_TABLE_NAME, null, value);

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

    //本に関する情報を更新するメソッド
    public void updateBookInfo(String bookTitle, String bookImage, String bookReview) {
        Log.i("Kansou.db", "start");

        // DB初期化
        SQLiteDatabase db = null;
        try {

            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            ContentValues value = new ContentValues();

            // 新規データ登録
            value.put(kansouHelper.COLUMN_BOOK_TITLE, bookTitle);
            value.put(kansouHelper.COLUMN_BOOK_IMAGE, bookImage);
            value.put(kansouHelper.COLUMN_BOOK_REVIEW, bookReview);
            db.insert(kansouHelper.BOOK_TABLE_NAME, null, value);

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

    //本のタイトルを更新する
    public void updateBookTitle(String bookId, String bookTitle) {
        ContentValues values = new ContentValues();
        values.put(kansouHelper.COLUMN_BOOK_TITLE, bookTitle);
        mContext.getContentResolver().update(KansouContract.Input.CONTENT_URI, values, KansouContract.Input.CONTENT_URI + bookId, null);
    }

    //本の画像を更新する
    public void updateBookImage(String bookId, String bookImage) {
        ContentValues values = new ContentValues();
        values.put(kansouHelper.COLUMN_BOOK_TITLE, bookImage);
        mContext.getContentResolver().update(KansouContract.Input.CONTENT_URI, values, KansouContract.Input.CONTENT_URI + bookId, null);
    }

    //本の感想文を更新する
    public void updateBookReview(String bookId, String bookReview) {
        ContentValues values = new ContentValues();
        values.put(kansouHelper.COLUMN_BOOK_TITLE, bookReview);
        mContext.getContentResolver().update(KansouContract.Input.CONTENT_URI, values, KansouContract.Input.CONTENT_URI + bookId, null);
    }
}
