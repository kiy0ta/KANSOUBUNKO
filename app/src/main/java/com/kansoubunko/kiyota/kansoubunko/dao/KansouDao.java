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
import com.kansoubunko.kiyota.kansoubunko.util.BookInfoHelper;
import com.kansoubunko.kiyota.kansoubunko.util.UserInfoHelper;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;

public class KansouDao {

    private Context mContext;
    private UserInfoHelper userInfoHelper = new UserInfoHelper(mContext);
    private BookInfoHelper bookInfoHelper = new BookInfoHelper(mContext);

    public KansouDao(Context context) {
        mContext = context;
        userInfoHelper = new UserInfoHelper(mContext);
    }

    //すべてのユーザーの情報を取得するメソッド
    public List<UserInfoEntity> selectALl() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        UserInfoEntity entity = null;
        List<UserInfoEntity> list = new ArrayList<>();
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                userInfoHelper.COLUMN_USER_ID,
                userInfoHelper.COLUMN_USER_NAME,
                userInfoHelper.COLUMN_USER_PASSWORD
        };
        try {
            // DB取得
            db = this.userInfoHelper.getWritableDatabase();
            cursor = db.query(
                    userInfoHelper.TABLE_NAME, projection,
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
            db = this.userInfoHelper.getWritableDatabase();
            ContentValues value = new ContentValues();

            // 既存レコード削除
//            db.execSQL("DELETE FROM " + userInfoHelper.TABLE_NAME);

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

    //ユーザー名を検索する
    public boolean findUserNameInfo(String userName) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                userInfoHelper.COLUMN_USER_NAME
        };
        try {
            // DB取得
            db = this.userInfoHelper.getWritableDatabase();
            cursor = db.query(
                    userInfoHelper.TABLE_NAME, projection, userInfoHelper.COLUMN_USER_NAME + " = ? ",
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
                userInfoHelper.COLUMN_USER_NAME
        };
        try {
            // DB取得
            db = this.userInfoHelper.getWritableDatabase();
            cursor = db.query(
                    userInfoHelper.TABLE_NAME, projection, userInfoHelper.COLUMN_USER_NAME + " = ? ",
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
                userInfoHelper.COLUMN_USER_NAME,
                userInfoHelper.COLUMN_USER_PASSWORD
        };
        try {
            // DB取得
            db = this.userInfoHelper.getWritableDatabase();
            cursor = db.query(
                    userInfoHelper.TABLE_NAME, projection,
                    userInfoHelper.COLUMN_USER_NAME + " = ? " + "AND "
                            + userInfoHelper.COLUMN_USER_PASSWORD + " = ? ",
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

    //すべてのユーザーの情報を取得するメソッド
    public List<BookInfoEntity> selectBookInfoALl() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        BookInfoEntity entity = null;
        List<BookInfoEntity> list = new ArrayList<>();
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                bookInfoHelper.COLUMN_BOOK_ID,
                bookInfoHelper.COLUMN_BOOK_TITLE,
                bookInfoHelper.COLUMN_BOOK_IMAGE,
                bookInfoHelper.COLUMN_BOOK_REVIEW
        };
        try {
            // DB取得
            db = this.bookInfoHelper.getWritableDatabase();
            cursor = db.query(
                    bookInfoHelper.TABLE_NAME, projection,
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

            entity.setBookId("1111");
            entity.setBookTitle("9月12日");
            entity.setBookImage("no_book_img");
            entity.setBookReview("no_book_img");
            list.add(entity);

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

    //DBの中の本の総数をカウントする処理
    //DBに入っているデータ件数を取得する
    public int getTotalBookCount(String bookId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                bookInfoHelper.COLUMN_BOOK_ID
        };
        try {
            // DB取得
            db = this.userInfoHelper.getWritableDatabase();
            cursor = db.query(
                    bookInfoHelper.TABLE_NAME, projection, bookInfoHelper.COLUMN_BOOK_ID + " = ? ",
                    new String[]{bookId}, null, null, null, null);
            cursor.moveToFirst();
            //DBの中に入っている件数を取得する
            if (cursor != null && cursor.getCount() > 0) {
                int count = cursor.getCount();
                return count;
            }
            return 0;
        } catch (SQLiteException e) {
            return 0;
        }
    }

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
